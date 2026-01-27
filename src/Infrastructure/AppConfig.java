package Infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Configuración centralizada (estilo wsAnt):
 * - Lee src/app.properties desde el classpath
 * - Evita rutas hardcodeadas en código
 */
public final class AppConfig {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = AppConfig.class.getResourceAsStream("/app.properties")) {
            if (in == null) {
                throw new RuntimeException("No se encontró /app.properties en el classpath. Debe existir en src/app.properties.");
            }
            PROPS.load(in);
        } catch (IOException ex) {
            throw new RuntimeException("Error cargando app.properties", ex);
        }
    }

    private AppConfig() {}

    // ===== Mensajes por defecto (compatibilidad con tu proyecto) =====
    public static final String MSG_DEFAULT_ERROR  = "Ups! Error inesperado. Por favor, contacte al administrador del sistema.";
    public static final String MSG_DEFAULT_CLASS  = "undefined";
    public static final String MSG_DEFAULT_METHOD = "undefined";

    // ===== Helpers =====
    public static String get(String key) {
        String v = PROPS.getProperty(key);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta la propiedad: " + key);
        }
        return v.trim();
    }

    public static String getOrDefault(String key, String defaultValue) {
        String v = PROPS.getProperty(key);
        return (v == null || v.trim().isEmpty()) ? defaultValue : v.trim();
    }

    // ===== Keys típicas =====
    public static String getDATABASE() {
        return get("db.File");
    }

    public static String getLOGFILE() {
        return get("df.logFile");
    }

    public static String getDATAFILE() {
        return getOrDefault("df.dataFile", "storage/DataFiles/casilleroInteligente.csv");
    }

    /**
     * Obtiene un recurso del classpath a partir de una key de properties.
     * Ejemplo: res.img.Logo=/Infrastructure/Assets/Img/logo.png
     */
    public static URL getAppResource(String key) {
        String path = get(key);
        URL url = AppConfig.class.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("No se encontró el recurso '" + path + "' para la key: " + key);
        }
        return url;
    }
}
