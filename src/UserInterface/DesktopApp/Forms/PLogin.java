package UserInterface.DesktopApp.Forms;

import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.awt.*;
import javax.swing.*;

public class PLogin extends JFrame {

    private final AppComponent app;
    private final JTextField txtUser = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();

    public PLogin(AppComponent app) {
        this.app = app;

        setTitle("PLogin - Casillero Inteligente");
        setSize(420, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel center = new JPanel(new GridLayout(2,2,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        center.add(new JLabel("Usuario:"));
        center.add(txtUser);
        center.add(new JLabel("Clave:"));
        center.add(txtPass);

        JButton btn = new JButton("Ingresar");
        btn.addActionListener(e -> onLogin());

        add(center, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);
    }

    private void onLogin() {
        try {
            app.authController.login(txtUser.getText(), new String(txtPass.getPassword()));
            app.showHome();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado", "Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}

