package UserInterface.DesktopApp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Utilidades simples de estilo para Swing.
 */
public final class UIStyles {

    private UIStyles() {}

    // Paleta (mantenla consistente con UITheme)
    public static final Color BG = new Color(245, 247, 250);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(25, 34, 49);
    public static final Color PRIMARY = new Color(46, 124, 246);
    public static final Color PRIMARY_TEXT = Color.WHITE;

    public static void applyFrame(JFrame f) {
        if (f.getContentPane() != null) {
            f.getContentPane().setBackground(BG);
        }
    }

    public static JPanel card(JComponent inner) {
        JPanel p = new JPanel();
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 232, 243)),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        p.setLayout(new java.awt.BorderLayout());
        p.add(inner);
        return p;
    }

    public static void stylePrimary(JButton b) {
        b.setBackground(PRIMARY);
        b.setForeground(PRIMARY_TEXT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        b.setFont(b.getFont().deriveFont(Font.BOLD));
        b.setMargin(new Insets(10, 16, 10, 16));
    }

    public static void styleSecondary(JButton b) {
        b.setBackground(CARD);
        b.setForeground(TEXT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 232, 243)),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        b.setMargin(new Insets(10, 16, 10, 16));
    }

    public static void setOpaqueRecursive(Component c, boolean opaque) {
        if (c instanceof JComponent jc) {
            jc.setOpaque(opaque);
        }
        if (c instanceof java.awt.Container container) {
            for (Component child : container.getComponents()) {
                setOpaqueRecursive(child, opaque);
            }
        }
    }
}
