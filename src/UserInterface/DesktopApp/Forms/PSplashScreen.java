package UserInterface.DesktopApp.Forms;

import UserInterface.DesktopApp.AppComponent;
import javax.swing.*;

public class PSplashScreen extends JFrame {
    public PSplashScreen(AppComponent app) {
        setTitle("Casillero Inteligente - Splash");
        setSize(360, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new JLabel("Cargando Casillero Inteligente...", SwingConstants.CENTER));
    }
}

