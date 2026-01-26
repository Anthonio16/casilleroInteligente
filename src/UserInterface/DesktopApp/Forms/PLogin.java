package UserInterface.DesktopApp.Forms;

import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.*;
import javax.swing.*;

public class PLogin extends JFrame {

    private final AppComponent app;
    private final JTextField txtUser = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();

    public PLogin(AppComponent app) {
        this.app = app;

        setTitle("Login - Casillero Inteligente");
        setSize(520, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        UIStyles.applyFrame(this);

        JPanel form = new JPanel(new GridLayout(2,2,10,10));
        form.setBackground(UIStyles.CARD);
        form.add(new JLabel("Usuario:"));
        form.add(txtUser);
        form.add(new JLabel("Clave:"));
        form.add(txtPass);

        JButton btn = new JButton("Ingresar");
        btn.addActionListener(e -> onLogin());
        UIStyles.stylePrimary(btn);

        JLabel t = new JLabel("Iniciar sesión");
        t.setFont(t.getFont().deriveFont(Font.BOLD, 18f));

        JPanel cardInner = new JPanel(new BorderLayout(10,10));
        cardInner.setBackground(UIStyles.CARD);
        cardInner.add(t, BorderLayout.NORTH);
        cardInner.add(form, BorderLayout.CENTER);

        JPanel card = UIStyles.card(cardInner);
        card.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIStyles.BG);
        center.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        center.add(card, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(UIStyles.BG);
        south.setBorder(BorderFactory.createEmptyBorder(0, 14, 14, 14));
        south.add(btn);

        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
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

