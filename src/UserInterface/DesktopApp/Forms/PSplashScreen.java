package UserInterface.DesktopApp.Forms;

import UserInterface.DesktopApp.AppComponent;
import UserInterface.DesktopApp.UIStyles;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;

public class PSplashScreen extends JFrame {
    public PSplashScreen(AppComponent app) {
        setTitle("Casillero Inteligente - Splash");
        setSize(520, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        UIStyles.applyFrame(this);

        JLabel title = new JLabel("Casillero Inteligente", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JLabel subtitle = new JLabel("Cargando...", SwingConstants.CENTER);

        JPanel inner = new JPanel(new BorderLayout(10,10));
        inner.setBackground(UIStyles.CARD);
        inner.add(title, BorderLayout.NORTH);
        inner.add(subtitle, BorderLayout.CENTER);
        inner.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));

        JPanel card = UIStyles.card(inner);
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(card, BorderLayout.CENTER);
    }
}

