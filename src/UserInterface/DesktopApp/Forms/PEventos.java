package UserInterface.DesktopApp.Forms;

import DataAccess.DTOs.RegistroEventoNombreDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PEventos extends JFrame {

    private final AppComponent app;
    private final JTextField txtIdCas = new JTextField("1");

    private final DefaultTableModel model = new DefaultTableModel(
        new Object[]{"idEvento","fecha","tipoEvento","idUsuario","idCasillero"}, 0
    );
    private final JTable table = new JTable(model);

    public PEventos(AppComponent app) {
        this.app = app;

        setTitle("PEventos");
        setSize(900, 380);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("IdCasillero:"));
        txtIdCas.setColumns(6);
        top.add(txtIdCas);

        JButton btn = new JButton("Cargar");
        btn.addActionListener(e -> refresh());
        JButton back = new JButton("Volver");
        back.addActionListener(e -> app.showHome());

        top.add(btn);
        top.add(back);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refresh() {
        try {
            int idCas = Integer.parseInt(txtIdCas.getText().trim());

            model.setRowCount(0);
            List<RegistroEventoNombreDTO> list = app.eventoController.listar(idCas);
            if (list == null) return;

            for (RegistroEventoNombreDTO ev : list) {
                model.addRow(new Object[]{
                    ev.getIdRegistroEvento(),
                    ev.getFechaCreacion(),
                    ev.getIdTipoEvento(),
                    ev.getIdUsuario(),
                    ev.getIdCasillero()
                });
            }

        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Eventos", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IdCasillero inválido", "Eventos", JOptionPane.WARNING_MESSAGE);
        }
    }
}

