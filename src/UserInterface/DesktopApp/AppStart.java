package UserInterface.DesktopApp;

import Infrastructure.AppException;

public class AppStart {
    public static void main(String[] args) {
        try {
            AppComponent app = new AppComponent();
            app.pSplash.setLocationRelativeTo(null);
            app.pSplash.setVisible(true);

            // Splash rápido -> Login
            javax.swing.Timer t = new javax.swing.Timer(900, e -> app.showLogin());
            t.setRepeats(false);
            t.start();

        } catch (AppException e) {
            System.out.println("Error al iniciar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

