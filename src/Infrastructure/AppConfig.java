//  © 2K26 ❱──💀──❰ pat_mic ? code is life : life is code
package Infrastructure;

import java.net.URL;

public abstract class AppConfig {
    //Paths Storage
    public static final String DATABASE = "jdbc:sqlite:casilleroInteligente\\storage\\Databases\\casilleroInteligente.sqlite";
    public static final String DATAFILE = "casilleroInteligente\\storage\\DataFiles\\casilleroInteligente.csv";
    public static final String LOGFILE  = "casilleroInteligente\\storage\\Logs\\AppErrors.log";

    //Resources
    public static final URL URL_MAIN    = AppConfig.class.getResource("Resource/logo.png");
    public static final URL URL_LOGO    = AppConfig.class.getResource("Resource/logo.png");
    public static final URL URL_SPLASH  = AppConfig.class.getResource("/Infrastructure/Assets/Img/Splash.png");

    //AppMSGs
    public static final String MSG_DEFAULT_ERROR    = "Ups! Error inesperado. Por favor, contacte al administrador del sistema.";
    public static final String MSG_DEFAULT_CLASS    = "undefined";
    public static final String MSG_DEFAULT_METHOD   = "undefined";

    private AppConfig() {}
}
