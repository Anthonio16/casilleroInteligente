package UserInterface.DesktopApp.Forms;

import DataAccess.DTOs.AuditoriaDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PEventos extends JFrame {

    private final AppComponent app;

    // Filtro solo admin
    private final JTextField txtIdCas = new JTextField("");

    // Tabla auditoría (cambia columnas según rol)
    private DefaultTableModel auditModel;
    private final JTable auditTable = new JTable();

    // Tablita resumen (siempre)
    private final DefaultTableModel resumenModel = new DefaultTableModel(
        new Object[]{"Métrica", "Valor"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable resumenTable = new JTable(resumenModel);

    // Botones
    private final JButton btnCargar = new JButton("Cargar");
    private final JButton btnVolver = new JButton("Volver");

    // Paneles para ocultar/mostrar según rol
    private final JPanel adminFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public PEventos(AppComponent app) {
        this.app = app;

        setTitle("Auditoría - Casillero Inteligente");
        setSize(1040, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        UIStyles.applyFrame(this);

        // ===== Top: título + acciones =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UIStyles.BG);
        top.setBorder(BorderFactory.createEmptyBorder(10, 14, 0, 14));

        JLabel title = new JLabel("Auditoría");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(UIStyles.BG);

        UIStyles.stylePrimary(btnCargar);
        UIStyles.styleSecondary(btnVolver);

        btnCargar.addActionListener(e -> refresh());
        btnVolver.addActionListener(e -> app.showHome());

        actions.add(btnCargar);
        actions.add(btnVolver);

        top.add(actions, BorderLayout.EAST);

        // ===== Admin filter panel (IdCasillero) =====
        adminFilterPanel.setBackground(UIStyles.BG);
        adminFilterPanel.setBorder(BorderFactory.createEmptyBorder(6, 14, 0, 14));
        adminFilterPanel.add(new JLabel("Filtrar por IdCasillero (opcional):"));
        txtIdCas.setColumns(8);
        adminFilterPanel.add(txtIdCas);

        // ===== Resumen table =====
        resumenTable.setRowHeight(22);
        resumenTable.setFillsViewportHeight(true);
        JScrollPane resumenScroll = new JScrollPane(resumenTable);

        JPanel resumenCard = UIStyles.card(resumenScroll);
        resumenCard.setPreferredSize(new Dimension(300, 140));

        // ===== Center: resumen + auditoría =====
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBackground(UIStyles.BG);
        center.setBorder(BorderFactory.createEmptyBorder(10, 14, 14, 14));

        // Auditoría table (se configura en refresh según rol)
        auditTable.setRowHeight(24);
        auditTable.setAutoCreateRowSorter(true);
        auditTable.setFillsViewportHeight(true);

        JScrollPane auditScroll = new JScrollPane(auditTable);
        JPanel auditCard = UIStyles.card(auditScroll);

        // Layout: resumen arriba a la izquierda + auditoría grande
        JPanel topCenter = new JPanel(new BorderLayout(10, 10));
        topCenter.setBackground(UIStyles.BG);
        topCenter.add(resumenCard, BorderLayout.WEST);
        topCenter.add(auditCard, BorderLayout.CENTER);

        center.add(topCenter, BorderLayout.CENTER);

        // Wrapper (top + filter + center)
        JPanel wrapperNorth = new JPanel(new BorderLayout());
        wrapperNorth.setBackground(UIStyles.BG);
        wrapperNorth.add(top, BorderLayout.NORTH);
        wrapperNorth.add(adminFilterPanel, BorderLayout.SOUTH);

        add(wrapperNorth, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    public void refresh() {
        try {
            if (app.getCurrentUser() == null) return;

            boolean isAdmin = app.isAdmin();

            // Mostrar filtro solo admin
            adminFilterPanel.setVisible(isAdmin);

            // Columnas según rol
            ensureAuditColumns(isAdmin);

            // Cargar data
            List<AuditoriaDTO> list;

            if (isAdmin) {
                int limit = 500;

                String raw = txtIdCas.getText() == null ? "" : txtIdCas.getText().trim();
                if (raw.isEmpty()) {
                    list = app.auditoriaController.admin(limit);
                } else {
                    int idCas = Integer.parseInt(raw);
                    list = app.auditoriaController.adminPorCasillero(idCas, limit);
                }

            } else {
                int limit = 200;
                int idEstudiante = app.getCurrentUser().getIdUsuario();
                list = app.auditoriaController.estudiante(idEstudiante, limit);
            }

            // Pintar tabla auditoría
            auditModel.setRowCount(0);
            if (list != null) {
                for (AuditoriaDTO ev : list) {
                    if (isAdmin) {
                        auditModel.addRow(new Object[]{
                            ev.getFecha(),
                            ev.getIdCasillero(),
                            ev.getCasilleroDescripcion(),
                            ev.getEstadoCasillero(),
                            ev.getEventoNombre(),
                            ev.getResultado(),
                            ev.getUsuarioNombre(),
                            ev.getUsuarioRol()
                        });
                    } else {
                        auditModel.addRow(new Object[]{
                            ev.getFecha(),
                            ev.getEventoNombre(),
                            ev.getResultado(),
                            ev.getEstadoCasillero()
                        });
                    }
                }
            }

            // Pintar “tablita resumen”
            fillResumen(list, isAdmin);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IdCasillero inválido", "Auditoría", JOptionPane.WARNING_MESSAGE);
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Auditoría", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ensureAuditColumns(boolean isAdmin) {
        Object[] colsAdmin = {"Fecha", "IdCas", "Casillero", "EstadoCas", "Evento", "Resultado", "Usuario", "Rol"};
        Object[] colsUser  = {"Fecha", "Evento", "Resultado", "EstadoCas"};

        Object[] cols = isAdmin ? colsAdmin : colsUser;

        auditModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        auditTable.setModel(auditModel);
        auditTable.setRowSorter(new TableRowSorter<>(auditModel));
    }

    // ===== Punto 7: tablita de resumen útil =====
    private void fillResumen(List<AuditoriaDTO> list, boolean isAdmin) {
        resumenModel.setRowCount(0);

        int total = (list == null) ? 0 : list.size();

        int fails = 0;
        int bloqueos = 0;
        Map<Integer, Integer> failsPorCasillero = new HashMap<>();

        if (list != null) {
            for (AuditoriaDTO e : list) {
                String res = safeUpper(e.getResultado());
                String evn = safeUpper(e.getEventoNombre());

                boolean isFail = "FAIL".equals(res) || evn.contains("FAIL") || evn.contains("RECHAZ");
                boolean isBloq = "BLOQUEO".equals(res) || evn.contains("LOCKED");

                if (isFail) {
                    fails++;
                    if (e.getIdCasillero() != null) {
                        failsPorCasillero.merge(e.getIdCasillero(), 1, Integer::sum);
                    }
                }
                if (isBloq) bloqueos++;
            }
        }

        addMetric("Eventos cargados", total);
        addMetric("FAIL cargados", fails);
        addMetric("Bloqueos cargados", bloqueos);

        if (isAdmin) {
            Integer topCas = null;
            int topFails = 0;
            for (var entry : failsPorCasillero.entrySet()) {
                if (entry.getValue() > topFails) {
                    topFails = entry.getValue();
                    topCas = entry.getKey();
                }
            }
            addMetric("Top casillero con FAIL", (topCas == null) ? "-" : (topCas + " (" + topFails + ")"));
            addMetric("Tip", "Deja IdCasillero vacío para ver todo");
        } else {
            addMetric("Tip", "Revisa FAIL/BLOQUEO si hay problemas");
        }
    }

    private void addMetric(String name, Object value) {
        resumenModel.addRow(new Object[]{name, value});
    }

    private String safeUpper(String s) {
        return (s == null) ? "" : s.trim().toUpperCase();
    }
}
