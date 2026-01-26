package UserInterface.DesktopApp.Forms;

import DataAccess.DTOs.RegistroEventoNombreDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PEventos extends JFrame {

    private final AppComponent app;
    private final JTextField txtIdCas = new JTextField("1");

    private final DefaultTableModel model = new DefaultTableModel(
        new Object[]{"#","Fecha","Tipo de evento","Estado","IdUsuario","IdCasillero"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);

    public PEventos(AppComponent app) {
        this.app = app;

        setTitle("Eventos - Casillero Inteligente");
        setSize(980, 460);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        UIStyles.applyFrame(this);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(UIStyles.BG);
        top.setBorder(BorderFactory.createEmptyBorder(10, 14, 0, 14));
        top.add(new JLabel("IdCasillero:"));
        txtIdCas.setColumns(6);
        top.add(txtIdCas);

        JButton btn = new JButton("Cargar");
        btn.addActionListener(e -> refresh());
        JButton back = new JButton("Volver");
        back.addActionListener(e -> app.showHome());

        UIStyles.stylePrimary(btn);
        UIStyles.styleSecondary(back);

        top.add(btn);
        top.add(back);

        table.setRowHeight(24);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setRowSorter(new TableRowSorter<>(model));

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIStyles.BG);
        center.setBorder(BorderFactory.createEmptyBorder(10, 14, 14, 14));
        center.add(UIStyles.card(new JScrollPane(table)), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
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
                    (ev.getNombre() != null && !ev.getNombre().trim().isEmpty()) ? ev.getNombre() : ("Tipo #" + ev.getIdTipoEvento()),
                    ev.getEstado(),
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

