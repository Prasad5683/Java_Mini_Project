package org.example;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class UpiPaymentPage extends JFrame implements ActionListener {

    private JTextField upiIdField;
    private JPasswordField pinField;
    private JButton payButton;
    private JComboBox<String> bankComboBox;
    private JCheckBox saveUpiCheckBox;
    private JLabel upiErrorLabel;
    private JLabel pinErrorLabel;

    // Regular expression for UPI ID validation
    private static final String UPI_ID_REGEX = "^[a-zA-Z0-9._-]{3,}@[a-zA-Z]{3,}$";
    private static final Pattern UPI_PATTERN = Pattern.compile(UPI_ID_REGEX);

    public UpiPaymentPage() {
        setTitle("UPI Payment Gateway");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel header = new JLabel("Enter UPI Payment Details", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header, gbc);

        // UPI ID Label and Field
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("UPI ID:"), gbc);

        gbc.gridx = 1;
        upiIdField = new JTextField(20);
        upiIdField.setToolTipText("Enter your UPI ID (e.g., name@bank)");
        add(upiIdField, gbc);

        // UPI ID Error Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        upiErrorLabel = new JLabel(" ");
        upiErrorLabel.setForeground(Color.RED);
        upiErrorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        add(upiErrorLabel, gbc);

        // Bank Selection
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(new JLabel("Bank:"), gbc);

        gbc.gridx = 1;
        String[] banks = {"Select Bank", "SBI", "HDFC", "ICICI", "Axis", "PNB", "BOB", "Kotak", "Yes Bank"};
        bankComboBox = new JComboBox<>(banks);
        add(bankComboBox, gbc);

        // UPI PIN
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("UPI PIN:"), gbc);

        gbc.gridx = 1;
        pinField = new JPasswordField(6);
        pinField.setToolTipText("Enter your 4 or 6 digit UPI PIN");
        add(pinField, gbc);

        // PIN Error Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        pinErrorLabel = new JLabel(" ");
        pinErrorLabel.setForeground(Color.RED);
        pinErrorLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        add(pinErrorLabel, gbc);

        // Save UPI Checkbox
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        saveUpiCheckBox = new JCheckBox("Save this UPI ID for future payments");
        add(saveUpiCheckBox, gbc);

        // Pay Button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        payButton = new JButton("Make Payment");
        payButton.setBackground(new Color(0, 100, 0));
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        payButton.addActionListener(this);
        add(payButton, gbc);

        // Real-time validation
        upiIdField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validateFields(); }
            public void removeUpdate(DocumentEvent e) { validateFields(); }
            public void insertUpdate(DocumentEvent e) { validateFields(); }
        });

        pinField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { validateFields(); }
            public void removeUpdate(DocumentEvent e) { validateFields(); }
            public void insertUpdate(DocumentEvent e) { validateFields(); }
        });

        bankComboBox.addActionListener(e -> validateFields());

        validateFields(); // Initial validation

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void validateFields() {
        String upiId = upiIdField.getText().trim();
        char[] pin = pinField.getPassword();
        String selectedBank = (String) bankComboBox.getSelectedItem();

        // UPI ID validation
        if (upiId.isEmpty()) {
            upiErrorLabel.setText(" ");
        } else if (!isValidUpiId(upiId)) {
            upiErrorLabel.setText("Invalid UPI ID format (e.g., name@bank)");
        } else {
            upiErrorLabel.setText(" ");
        }

        // PIN validation
        if (pin.length == 0) {
            pinErrorLabel.setText(" ");
        } else if (pin.length < 4 || pin.length > 6) {
            pinErrorLabel.setText("PIN must be 4-6 digits");
        } else {
            pinErrorLabel.setText(" ");
        }

        // Bank validation
        boolean bankValid = !selectedBank.equals("Select Bank");
    }

    private boolean isValidUpiId(String upiId) {
        Matcher matcher = UPI_PATTERN.matcher(upiId);
        return matcher.matches();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payButton) {
            String upiId = upiIdField.getText().trim();
            char[] pin = pinField.getPassword();
            String selectedBank = (String) bankComboBox.getSelectedItem();

            // Validate all fields
            boolean valid = true;
            StringBuilder errorMessage = new StringBuilder();

            if (upiId.isEmpty()) {
                errorMessage.append("• UPI ID is required\n");
                valid = false;
            } else if (!isValidUpiId(upiId)) {
                errorMessage.append("• Invalid UPI ID format (e.g., name@bank)\n");
                valid = false;
            }

            if (selectedBank.equals("Select Bank")) {
                errorMessage.append("• Please select your bank\n");
                valid = false;
            }

            if (pin.length < 4 || pin.length > 6) {
                errorMessage.append("• UPI PIN must be 4-6 digits\n");
                valid = false;
            }

            if (!valid) {
                JOptionPane.showMessageDialog(this,
                        "Please fix the following errors:\n\n" + errorMessage.toString(),
                        "Payment Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Process payment
            String message = "Payment Successful!\n\n" +
                    "UPI ID: " + upiId + "\n" +
                    "Bank: " + selectedBank + "\n\n" +
                    (saveUpiCheckBox.isSelected() ?
                            "This UPI ID has been saved for future payments" :
                            "Thank you for your payment");

            JOptionPane.showMessageDialog(this,
                    message,
                    "Payment Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear sensitive data
            pinField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                // Set nicer button styling
                UIManager.put("Button.select", new Color(0, 70, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            new UpiPaymentPage();
        });
    }
}