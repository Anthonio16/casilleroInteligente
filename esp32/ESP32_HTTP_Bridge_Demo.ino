#include <WiFi.h>
#include <HTTPClient.h>

// ====== CONFIG ======
const char* WIFI_SSID = "TU_WIFI";
const char* WIFI_PASS = "TU_PASS";

// IP del PC/Servidor donde corre Backend.HttpDeviceServer
// Ej: "192.168.1.50" (misma red)
const char* SERVER_HOST = "192.168.1.50";
const int   SERVER_PORT = 8080;

// IDs de ejemplo (ajusta a tu caso)
static const int ID_CASILLERO = 1;
static const int ID_USUARIO   = 2;

String postForm(const String& path, const String& formBody, uint32_t timeoutMs = 4000) {
  HTTPClient http;
  WiFiClient client;

  String url = String("http://") + SERVER_HOST + ":" + String(SERVER_PORT) + path;

  http.setTimeout(timeoutMs);
  if (!http.begin(client, url)) {
    return "ERR;HTTP_BEGIN";
  }

  http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  int code = http.POST(formBody);

  if (code <= 0) {
    http.end();
    return "ERR;HTTP_POST";
  }

  String resp = http.getString();
  http.end();

  resp.trim();
  return resp; // OK | FAIL | BLOQUEADO | ERR:...
}

String validarPin(const String& pinPlano) {
  String body = "idCasillero=" + String(ID_CASILLERO) + "&idUsuario=" + String(ID_USUARIO) + "&pin=" + pinPlano;
  return postForm("/api/pin", body);
}

String validarToken(const String& tokenPlano) {
  String body = "idCasillero=" + String(ID_CASILLERO) + "&idUsuario=" + String(ID_USUARIO) + "&token=" + tokenPlano;
  return postForm("/api/token", body);
}

void setup() {
  Serial.begin(115200);
  delay(200);

  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASS);

  Serial.print("Conectando WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(300);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("WiFi OK. IP ESP32: " + WiFi.localIP().toString());

  // DEMO: prueba PIN y TOKEN (reemplaza por tu teclado)
  String r1 = validarPin("123456");
  Serial.println("validarPin -> " + r1);

  String r2 = validarToken("987654");
  Serial.println("validarToken -> " + r2);
}

void loop() {
  // Aquí iría tu máquina de estados del keypad
  delay(2000);
}
