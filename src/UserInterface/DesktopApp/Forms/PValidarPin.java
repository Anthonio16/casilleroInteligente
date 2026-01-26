package UserInterface.DesktopApp.Forms;

import BusinessLogic.Services.CasilleroService;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.*;
import javax.swing.*;

public class PValidarPin extends JFrame {

    private final AppComponent app;
    private final JTextField txtIdCas = new JTextField("1");
    private final JPasswordField txtPin = new JPasswordField();

    public PValidarPin(AppComponent app) {
        this.app = app;

        setTitle("Validar PIN - Casillero Inteligente");
        setSize(560, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        UIStyles.applyFrame(this);

        JPanel form = new JPanel(new GridLayout(2,2,10,10));
        form.setBackground(UIStyles.CARD);
        form.add(new JLabel("IdCasillero:"));
        form.add(txtIdCas);
        form.add(new JLabel("PIN:"));
        form.add(txtPin);

        JLabel t = new JLabel("Validar PIN del casillero");
        t.setFont(t.getFont().deriveFont(Font.BOLD, 16f));
        JPanel cardInner = new JPanel(new BorderLayout(10,10));
        cardInner.setBackground(UIStyles.CARD);
        cardInner.add(t, BorderLayout.NORTH);
        cardInner.add(form, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIStyles.BG);
        center.setBorder(BorderFactory.createEmptyBorder(14, 14, 0, 14));
        center.add(UIStyles.card(cardInner), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(UIStyles.BG);
        south.setBorder(BorderFactory.createEmptyBorder(10, 14, 14, 14));
        JButton btn = new JButton("Validar");
        JButton back = new JButton("Volver");
        back.addActionListener(e -> app.showHome());
        btn.addActionListener(e -> onValidar());

        UIStyles.stylePrimary(btn);
        UIStyles.styleSecondary(back);

        south.add(back);
        south.add(btn);

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

