package UserInterface.DesktopApp.Forms;

import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PRecuperaciones extends JFrame {

    private final AppComponent app;

    private final DefaultTableModel model = new DefaultTableModel(
        new Object[]{"#","IdCasillero","IdAdmin","Estado","Creada"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);

    public PRecuperaciones(AppComponent app) {
        this.app = app;

        setTitle("PRecuperaciones (Admin)");
        setSize(920, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        UIStyles.applyFrame(this);

        table.setRowHeight(24);
        table.setFillsViewportHeight(true);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIStyles.BG);
        center.setBorder(BorderFactory.createEmptyBorder(10, 14, 14, 14));
        center.add(UIStyles.card(new JScrollPane(table)), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(UIStyles.BG);
        south.setBorder(BorderFactory.createEmptyBorder(0, 14, 14, 14));

        JButton btnRef = new JButton("Refrescar");
        JButton btnApr = new JButton("Aprobar");
        JButton btnRec = new JButton("Rechazar");
        JButton btnBack = new JButton("Volver");

        UIStyles.styleSecondary(btnRef);
        UIStyles.stylePrimary(btnApr);
        UIStyles.styleSecondary(btnRec);
        UIStyles.styleSecondary(btnBack);

        btnRef.addActionListener(e -> refresh());
        btnApr.addActionListener(e -> aprobarSeleccion());
        btnRec.addActionListener(e -> rechazarSeleccion());
        btnBack.addActionListener(e -> app.showHome());

        south.add(btnBack);
        south.add(btnRef);
        south.add(btnRec);
        south.add(btnApr);

        add(south, BorderLayout.SOUTH);
    }

    public void refresh() {
        if (!app.isAdmin()) {
            JOptionPane.showMessageDialog(this, "Solo ADMIN", "Recuperaciones", JOptionPane.WARNING_MESSAGE);
            app.showHome();
            return;
        }

        try {
            model.setRowCount(0);
            List<SolicitudDTO> list = app.recuperacionController.listarPendientes();
            if (list == null) return;

            for (SolicitudDTO s : list) {
                model.addRow(new Object[]{
                    s.getIdSolicitud(),
                    s.getIdCasillero(),
                    s.getIdAdmin(),
                    (s.getEstado() != null && !s.getEstado().trim().isEmpty()) ? s.getEstado() : ("Estado #" + s.getIdEstadoSolicitud()),
                    s.getFechaCreacion()
                });
            }
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Recuperaciones", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Integer getSelectedIdSolicitud() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        Object v = model.getValueAt(row, 0);
        return (v == null) ? null : Integer.parseInt(v.toString());
    }

    private Integer getSelectedIdCasillero() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        Object v = model.getValueAt(row, 1);
        return (v == null) ? null : Integer.parseInt(v.toString());
    }

    private void aprobarSeleccion() {
        try {
            Integer idSol = getSelectedIdSolicitud();
            Integer idCas = getSelectedIdCasillero();
            if (idSol == null || idCas == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una solicitud", "Aprobar", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer idToken = app.recuperacionController.aprobar(idSol);
            TokenAccesoDTO token = app.recuperacionController.tokenActivo(idCas);

            JOptionPane.showMessageDialog(this,
                "Aprobada.\nIdToken: " + idToken + "\nToken activo (hash): " + (token==null? "null" : token.getTokenHash()),
                "Aprobar",
                JOptionPane.INFORMATION_MESSAGE
            );

            refresh();

        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aprobar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechazarSeleccion() {
        try {
            Integer idSol = getSelectedIdSolicitud();
            if (idSol == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una solicitud", "Rechazar", JOptionPane.WARNING_MESSAGE);
                return;
            }
            app.recuperacionController.rechazar(idSol);
            JOptionPane.showMessageDialog(this, "Rechazada.", "Rechazar", JOptionPane.INFORMATION_MESSAGE);
            refresh();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Rechazar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
