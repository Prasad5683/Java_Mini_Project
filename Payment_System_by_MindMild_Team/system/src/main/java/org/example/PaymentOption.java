package org.example;

import javax.swing.*;
import java.awt.*;

public class PaymentOption extends JFrame {
    private double totalCost;

    public PaymentOption(StudentPaymentSystem sps, double totalCost) {
        this.totalCost = totalCost;
        setTitle("Choose Payment Option");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load background image
        ImageIcon icon = new ImageIcon("src/main/resources/org/example/bgg.jpg");
        Image bgImage = icon.getImage();

        // Custom JPanel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Select a Payment Option", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 36));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        backgroundPanel.add(header, BorderLayout.NORTH);

        // Buttons
        JPanel optionPanel = new JPanel(new GridLayout(2, 2, 50, 50));
        optionPanel.setOpaque(false); // Make it transparent to show background
        optionPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));

        JButton qrButton = new JButton("QR Scan");
        JButton upiButton = new JButton("UPI Payment");
        JButton cardButton = new JButton("Card Payment");
        JButton cashButton = new JButton("Cash");

        Font buttonFont = new Font("SansSerif", Font.BOLD, 24);
        Color aqua = new Color(198, 58, 244);

        for (JButton btn : new JButton[]{qrButton, upiButton, cardButton, cashButton}) {
            btn.setFont(buttonFont);
            btn.setBackground(aqua);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        optionPanel.add(qrButton);
        optionPanel.add(upiButton);
        optionPanel.add(cardButton);
        optionPanel.add(cashButton);
        backgroundPanel.add(optionPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("Powered by MindMild Team", SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 14));
        footer.setForeground(Color.WHITE);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        backgroundPanel.add(footer, BorderLayout.SOUTH);

        // Add to frame
        setContentPane(backgroundPanel);

        // Button actions
        qrButton.addActionListener(e -> {
            new QrPayment(totalCost).setVisible(true);
        });

        cashButton.addActionListener(e -> {
            new CashACK().setVisible(true);
        });
        cardButton.addActionListener(e -> {
            new CardPaymentPage().setVisible(true);
        });

        upiButton.addActionListener(e -> {
            new UpiPaymentPage().setVisible(true);
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentOption(null, 0).setVisible(true));
    }
}
