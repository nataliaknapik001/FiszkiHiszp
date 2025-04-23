package frontend;

import javax.swing.*;
import java.awt.*;

public class StatystykiFrame extends JFrame {
    public StatystykiFrame(int kategoriaId) {
        setTitle("Statystyki nauki");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel lblInfo = new JLabel("Statystyki dla kategorii ID: " + kategoriaId, SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton btnPowrot = new JButton("Powrót");
        btnPowrot.addActionListener(e -> {
            dispose();
            new StartFrame().setVisible(true);
        });
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(lblInfo, BorderLayout.CENTER);
        panel.add(btnPowrot, BorderLayout.SOUTH);
        
        add(panel);
    }
}