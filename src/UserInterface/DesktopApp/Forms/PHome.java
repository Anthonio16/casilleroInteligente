package UserInterface.DesktopApp.Forms;

import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.*;
import javax.swing.*;

public class PHome extends JFrame {

    private final AppComponent app;
    private final JLabel lbl = new JLabel();
    private final JPanel actions = new JPanel();

    public PHome(AppComponent app) {
        this.app = app;

        setTitle("Inicio - Casillero Inteligente");
        setSize(680, 360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        UIStyles.applyFrame(this);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));
        header.setBackground(UIStyles.BG);

        JLabel title = new JLabel("Casillero Inteligente");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        header.add(title, BorderLayout.NORTH);

        lbl.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        header.add(lbl, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Acciones (se renderiza según rol)
        actions.setBackground(UIStyles.BG);
        actions.setBorder(BorderFactory.createEmptyBorder(8, 14, 14, 14));
        actions.setLayout(new GridLayout(0, 2, 12, 12));
        add(actions, BorderLayout.CENTER);
    }

    public void refresh() {
        if (app.getCurrentUser() == null) return;
        String rol = app.isAdmin() ? "ADMIN" : "ESTUDIANTE";
        lbl.setText("Bienvenido: " + app.getCurrentUser().getNombre() + " | Rol: " + rol);

        // Botonera por rol
        actions.removeAll();

        if (app.isAdmin()) {
            addAction("Eventos", true, () -> app.showEventos());
            addAction("Recuperaciones", true, () -> app.showRecuperaciones());
        } else {
            addAction("Validar PIN", true, () -> app.showValidarPin());
            addAction("Validar TOKEN", true, () -> app.showValidarToken());
            addAction("Eventos", false, () -> app.showEventos());
        }

        addAction("Cerrar sesión", false, () -> { app.authController.logout(); app.showLogin(); });

        actions.revalidate();
        actions.repaint();
    }

    private void addAction(String text, boolean primary, Runnable onClick) {
        JButton b = new JButton(text);
        if (primary) UIStyles.stylePrimary(b); else UIStyles.styleSecondary(b);
        b.addActionListener(e -> onClick.run());
        actions.add(b);
    }
}

