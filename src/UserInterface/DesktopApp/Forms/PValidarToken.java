package UserInterface.DesktopApp.Forms;

import BusinessLogic.Services.TokenService;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.awt.*;
import javax.swing.*;

public class PValidarToken extends JFrame {

    private final AppComponent app;
    private final JTextField txtIdCas = new JTextField("1");
    private final JTextField txtToken = new JTextField();

    public PValidarToken(AppComponent app) {
        this.app = app;

        setTitle("PValidarToken");
        setSize(520, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel center = new JPanel(new GridLayout(2,2,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        center.add(new JLabel("IdCasillero:"));
        center.add(txtIdCas);
        center.add(new JLabel("Token:"));
        center.add(txtToken);

        JPanel south = new JPanel(new FlowLayout());
        JButton btn = new JButton("Validar");
        JButton back = new JButton("Volver");
        back.addActionListener(e -> app.showHome());
        btn.addActionListener(e -> onValidar());

        south.add(btn);
        south.add(back);

        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }

    public void refresh() {
        txtToken.setText("");
    }

    private void onValidar() {
        try {
            int idCas = Integer.parseInt(txtIdCas.getText().trim());
            String token = txtToken.getText().trim();

            TokenService.ResultadoValidacionToken r = app.tokenController.validarToken(idCas, token);

            JOptionPane.showMessageDialog(this,
                "Resultado: " + r,
                "Validación Token",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Token", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IdCasillero inválido", "Token", JOptionPane.WARNING_MESSAGE);
        }
    }
}

