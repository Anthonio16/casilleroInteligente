package UserInterface.DesktopApp;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * Tema visual simple (sin librerías externas) para que la app Swing
 * se vea más moderna y consistente.
 */
public final class UITheme {

    private UITheme() {}

    public static void init() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
            // Si falla, seguimos con el LAF por defecto.
        }

        // Paleta (suave, tipo "dashboard")
        Color bg = new Color(245, 247, 250);
        Color card = Color.WHITE;
        Color text = new Color(25, 34, 49);
        Color primary = new Color(46, 124, 246);

        // Nimbus keys (no todas aplican en todos los SO, pero ayuda bastante)
        UIManager.put("control", bg);
        UIManager.put("nimbusLightBackground", card);
        UIManager.put("info", card);
        UIManager.put("text", text);
        UIManager.put("nimbusBase", primary);
        UIManager.put("nimbusFocus", primary);
        UIManager.put("Table.alternateRowColor", new Color(240, 244, 250));

        setGlobalFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private static void setGlobalFont(Font font) {
        UIDefaults defaults = UIManager.getDefaults();
        Enumeration<Object> keys = defaults.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = defaults.get(key);
            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        }
    }
}
