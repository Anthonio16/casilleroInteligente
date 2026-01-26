package UserInterface.DesktopApp.Forms;

import UserInterface.DesktopApp.AppComponent;
import java.awt.*;
import javax.swing.*;

public class PHome extends JFrame {

    private final AppComponent app;
    private final JLabel lbl = new JLabel();

    public PHome(AppComponent app) {
        this.app = app;

        setTitle("PHome - Casillero Inteligente");
        setSize(520, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        lbl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(lbl, BorderLayout.NORTH);

        JPanel actions = new JPanel(new FlowLayout());

        JButton btnPin = new JButton("Validar PIN");
        btnPin.addActionListener(e -> app.showValidarPin());

        JButton btnToken = new JButton("Validar TOKEN");
        btnToken.addActionListener(e -> app.showValidarToken());

        JButton btnEventos = new JButton("Eventos");
        btnEventos.addActionListener(e -> app.showEventos());

        JButton btnRec = new JButton("Recuperaciones (Admin)");
        btnRec.addActionListener(e -> app.showRecuperaciones());

        JButton btnLogout = new JButton("Salir");
        btnLogout.addActionListener(e -> { app.authController.logout(); app.showLogin(); });

        actions.add(btnPin);
        actions.add(btnToken);
        actions.add(btnEventos);
        actions.add(btnRec);
        actions.add(btnLogout);

        add(actions, BorderLayout.CENTER);
    }

    public void refresh() {
        if (app.getCurrentUser() == null) return;
        String rol = app.isAdmin() ? "ADMIN" : "ESTUDIANTE";
        lbl.setText("Bienvenido: " + app.getCurrentUser().getNombre() + " | Rol: " + rol);
    }
}

