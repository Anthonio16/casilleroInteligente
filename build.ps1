# build.ps1 - Casillero Inteligente (Java + SQLite) -> jar, fat-jar y instalador .exe (jpackage)
$ErrorActionPreference = "Stop"

function Ensure-Tool($tool) {
    if (-not (Get-Command $tool -ErrorAction SilentlyContinue)) {
        throw "No se encontró '$tool' en PATH. Asegúrate de tener JDK instalado y reinicia PowerShell."
    }
}

Ensure-Tool "java"
Ensure-Tool "javac"
Ensure-Tool "jar"
Ensure-Tool "jpackage"

# Asegurar WiX en PATH (por sesión) - ajusta si tu ruta difiere
$wixBin = "C:\Program Files (x86)\WiX Toolset v3.14\bin"
if (Test-Path $wixBin) { $env:Path = "$wixBin;$env:Path" }

# --- Rutas base ---
$ROOT = Get-Location
$SRC  = Join-Path $ROOT "src"
$LIB  = Join-Path $ROOT "lib"
$BUILD_CLASSES = Join-Path $ROOT "build\classes"
$BUILD_FATJAR  = Join-Path $ROOT "build\fatjar"
$DIST = Join-Path $ROOT "dist"
$PKG_RES = Join-Path $ROOT "package-resources"

$SQLITE_JAR = Join-Path $LIB "sqlite-jdbc-3.40.0.0.jar"
$MAIN_CLASS = "UserInterface.DesktopApp.AppStart"

if (-not (Test-Path $SRC)) { throw "No existe carpeta src en: $SRC" }
if (-not (Test-Path $SQLITE_JAR)) { throw "No se encontró sqlite-jdbc en: $SQLITE_JAR" }

Write-Host "== Casillero Inteligente Build ==" -ForegroundColor Cyan
Write-Host "Root: $ROOT"
Write-Host "Main: $MAIN_CLASS"
Write-Host ""

# --- Crear carpetas ---
New-Item -ItemType Directory -Force -Path $BUILD_CLASSES | Out-Null
New-Item -ItemType Directory -Force -Path $DIST | Out-Null
New-Item -ItemType Directory -Force -Path $PKG_RES | Out-Null

# --- Compilar Java ---
Write-Host "1) Compilando Java..." -ForegroundColor Yellow
$javaFiles = Get-ChildItem -Path $SRC -Recurse -Filter *.java | ForEach-Object { $_.FullName }
& javac -encoding UTF-8 -cp $SQLITE_JAR -d $BUILD_CLASSES $javaFiles
Write-Host "   OK compilado -> $BUILD_CLASSES" -ForegroundColor Green

# --- Copiar recursos ---
Write-Host "2) Copiando recursos..." -ForegroundColor Yellow
$appProps = Join-Path $SRC "app.properties"
if (-not (Test-Path $appProps)) { throw "No existe src/app.properties" }
Copy-Item $appProps (Join-Path $BUILD_CLASSES "app.properties") -Force

$assetsSrc = Join-Path $SRC "Infrastructure\Assets"
if (Test-Path $assetsSrc) {
    New-Item -ItemType Directory -Force -Path (Join-Path $BUILD_CLASSES "Infrastructure") | Out-Null
    Copy-Item $assetsSrc (Join-Path $BUILD_CLASSES "Infrastructure\Assets") -Recurse -Force
}
Write-Host "   OK recursos copiados" -ForegroundColor Green

# --- JAR normal ---
Write-Host "3) Creando JAR normal..." -ForegroundColor Yellow
$manifest = Join-Path $DIST "manifest.mf"
@"
Main-Class: $MAIN_CLASS
"@ | Set-Content -Path $manifest -Encoding ASCII

$jarNormal = Join-Path $DIST "CasilleroInteligente.jar"
if (Test-Path $jarNormal) { Remove-Item $jarNormal -Force }
& jar --create --file $jarNormal --manifest $manifest -C $BUILD_CLASSES .
Copy-Item $SQLITE_JAR (Join-Path $DIST (Split-Path $SQLITE_JAR -Leaf)) -Force
Write-Host "   OK -> $jarNormal" -ForegroundColor Green

# --- FAT JAR ---
Write-Host "4) Creando FAT JAR..." -ForegroundColor Yellow
if (Test-Path $BUILD_FATJAR) { Remove-Item $BUILD_FATJAR -Recurse -Force }
New-Item -ItemType Directory -Force -Path $BUILD_FATJAR | Out-Null

Push-Location $BUILD_FATJAR
& jar xf $jarNormal
& jar xf (Join-Path $DIST (Split-Path $SQLITE_JAR -Leaf))
$metaInf = Join-Path $BUILD_FATJAR "META-INF"
if (Test-Path $metaInf) {
    Get-ChildItem $metaInf -Filter *.SF  -ErrorAction SilentlyContinue | Remove-Item -Force -ErrorAction SilentlyContinue
    Get-ChildItem $metaInf -Filter *.RSA -ErrorAction SilentlyContinue | Remove-Item -Force -ErrorAction SilentlyContinue
    Get-ChildItem $metaInf -Filter *.DSA -ErrorAction SilentlyContinue | Remove-Item -Force -ErrorAction SilentlyContinue
}
Pop-Location

$manifestFat = Join-Path $DIST "manifest-fat.mf"
@"
Main-Class: $MAIN_CLASS
"@ | Set-Content -Path $manifestFat -Encoding ASCII

$jarFat = Join-Path $DIST "CasilleroInteligente-fat.jar"
if (Test-Path $jarFat) { Remove-Item $jarFat -Force }
& jar --create --file $jarFat --manifest $manifestFat -C $BUILD_FATJAR .
Write-Host "   OK -> $jarFat" -ForegroundColor Green

# --- package-resources/storage ---
Write-Host "5) Preparando package-resources (storage)..." -ForegroundColor Yellow
$storageSrc = Join-Path $ROOT "storage"
$storageDst = Join-Path $PKG_RES "storage"
if (-not (Test-Path $storageSrc)) {
    throw "No existe carpeta 'storage' en la raíz. El instalador quedará sin BD."
}
if (Test-Path $storageDst) { Remove-Item $storageDst -Recurse -Force }
Copy-Item $storageSrc $storageDst -Recurse -Force
New-Item -ItemType Directory -Force -Path (Join-Path $storageDst "Logs") | Out-Null
Write-Host "   OK -> $storageDst" -ForegroundColor Green

# --- Generar instalador ---
Write-Host "6) Generando instalador .exe con jpackage..." -ForegroundColor Yellow
& jpackage `
  --name "CasilleroInteligente" `
  --type exe `
  --input $DIST `
  --main-jar "CasilleroInteligente-fat.jar" `
  --main-class $MAIN_CLASS `
  --resource-dir $PKG_RES `
  --java-options "-Dfile.encoding=UTF-8" `
  --java-options "--enable-native-access=ALL-UNNAMED" `
  --win-menu `
  --win-shortcut `
  --win-dir-chooser `
  --win-per-user-install

Write-Host ""
Write-Host "✅ Listo. Busca el instalador .exe en:" -ForegroundColor Green
Write-Host "   $ROOT" -ForegroundColor Green
