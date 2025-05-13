package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Payment System Project");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set a custom JPanel with background image
        setContentPane(new BackgroundPanel());

        setLayout(new BorderLayout());

        // Title Label
//        JLabel titleLabel = new JLabel("Payment System Project", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
//        add(titleLabel, BorderLayout.NORTH);

        // Start Button
        JButton startButton = new JButton("Continue");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setBackground(new Color(0, 255, 255));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setPreferredSize(new Dimension(250, 80));

        // Centering the button
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Make it transparent
        centerPanel.add(startButton);
        add(centerPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("@..Developed by MindMild Team");
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        footerPanel.setOpaque(false); // Transparent footer
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // Start Button Action
        startButton.addActionListener(e -> {
            new StudentPaymentSystem().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}

// Background panel with image
class BackgroundPanel extends JPanel {
    private Image bg;

    public BackgroundPanel() {
        bg = new ImageIcon(getClass().getResource("/org/example/BG.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
