package UserInterface.DesktopApp.Forms;

import BusinessLogic.Services.CasilleroService;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.awt.*;
import javax.swing.*;

public class PValidarPin extends JFrame {

    private final AppComponent app;
    private final JTextField txtIdCas = new JTextField("1");
    private final JPasswordField txtPin = new JPasswordField();

    public PValidarPin(AppComponent app) {
        this.app = app;

        setTitle("PValidarPin");
        setSize(480, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel center = new JPanel(new GridLayout(2,2,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        center.add(new JLabel("IdCasillero:"));
        center.add(txtIdCas);
        center.add(new JLabel("PIN:"));
        center.add(txtPin);

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
        txtPin.setText("");
    }

    private void onValidar() {
        try {
            int idCas = Integer.parseInt(txtIdCas.getText().trim());
            String pin = new String(txtPin.getPassword());

            CasilleroService.ResultadoValidacionPin r = app.casilleroController.validarPin(idCas, pin);

            JOptionPane.showMessageDialog(this,
                "Resultado: " + r,
                "Validación PIN",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "PIN", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IdCasillero inválido", "PIN", JOptionPane.WARNING_MESSAGE);
        }
    }
}

