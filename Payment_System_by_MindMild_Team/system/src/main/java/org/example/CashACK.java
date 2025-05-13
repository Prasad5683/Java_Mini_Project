package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CashACK extends JFrame {

    private JCheckBox confirmCheckBox;
    private JButton confirmButton;

    public CashACK() {
        setTitle("Cash Payment Acknowledgement");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout(10, 10));

        // Message panel
        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("<html><center><h3>Cash Payment Received</h3><p>Thank you for the payment.</p></center></html>");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messagePanel.add(messageLabel);

        // Checkbox panel
        JPanel checkboxPanel = new JPanel();
        confirmCheckBox = new JCheckBox("I confirm the above acknowledgment.");

        checkboxPanel.add(confirmCheckBox);

        // Button panel
        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        Color aqua = new Color(0, 255, 255);
        buttonPanel.add(confirmButton);

        // Add listeners
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (confirmCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(CashACK.this, "Payment confirmation completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(CashACK.this, "Please check the confirmation box.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Add all to frame
        add(messagePanel, BorderLayout.NORTH);
        add(checkboxPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CashACK().setVisible(true);
        });
    }
}
