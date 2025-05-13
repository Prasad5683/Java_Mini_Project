package org.example;



import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class CardPaymentPage extends JFrame implements ActionListener {

    private JTextField nameField, cardNumberField, expiryField, amountField;
    private JPasswordField cvvField;
    private JCheckBox saveCardCheckBox;
    private JButton payButton;
    private JLabel nameError, cardError, expiryError, cvvError;

    private static final Pattern CARD_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern EXPIRY_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3}$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    public CardPaymentPage() {
        setTitle("Card Payment Gateway");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel header = new JLabel("Enter Card Payment Details", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header, gbc);

        // Name
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridx = 0;
        add(new JLabel("Name on Card:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        nameError = new JLabel(" ");
        nameError.setForeground(Color.RED);
        nameError.setFont(new Font("Arial", Font.PLAIN, 10));
        add(nameError, gbc);

        // Card Number
        gbc.gridy = 3; gbc.gridwidth = 1; gbc.gridx = 0;
        add(new JLabel("Card Number:"), gbc);
        gbc.gridx = 1;
        cardNumberField = new JTextField(16);
        cardNumberField.setToolTipText("Enter 16-digit card number");
        add(cardNumberField, gbc);
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        cardError = new JLabel(" ");
        cardError.setForeground(Color.RED);
        cardError.setFont(new Font("Arial", Font.PLAIN, 10));
        add(cardError, gbc);

        // Expiry
        gbc.gridy = 5; gbc.gridwidth = 1; gbc.gridx = 0;
        add(new JLabel("Expiry (MM/YY):"), gbc);
        gbc.gridx = 1;
        expiryField = new JTextField(5);
        expiryField.setToolTipText("Enter expiry in MM/YY");
        add(expiryField, gbc);
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        expiryError = new JLabel(" ");
        expiryError.setForeground(Color.RED);
        expiryError.setFont(new Font("Arial", Font.PLAIN, 10));
        add(expiryError, gbc);

        // CVV
        gbc.gridy = 7; gbc.gridwidth = 1; gbc.gridx = 0;
        add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        cvvField = new JPasswordField(3);
        cvvField.setToolTipText("Enter 3-digit CVV");
        add(cvvField, gbc);
        gbc.gridy = 8; gbc.gridx = 0; gbc.gridwidth = 2;
        cvvError = new JLabel(" ");
        cvvError.setForeground(Color.RED);
        cvvError.setFont(new Font("Arial", Font.PLAIN, 10));
        add(cvvError, gbc);

        // Amount
        gbc.gridy = 9; gbc.gridwidth = 1; gbc.gridx = 0;
        add(new JLabel("Amount (Rs):"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(10);
        amountField.setToolTipText("Enter amount to pay");
        add(amountField, gbc);

        // Save card checkbox
        gbc.gridy = 10; gbc.gridwidth = 2; gbc.gridx = 0;
        saveCardCheckBox = new JCheckBox("Save this card for future payments");
        add(saveCardCheckBox, gbc);

        // Pay Button
        gbc.gridy = 11;
        payButton = new JButton("Make Payment");
        payButton.setBackground(new Color(0, 100, 77));
        payButton.setForeground(Color.black);
        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        payButton.addActionListener(this);
        add(payButton, gbc);

        // Real-time validation
        nameField.getDocument().addDocumentListener(docListener);
        cardNumberField.getDocument().addDocumentListener(docListener);
        expiryField.getDocument().addDocumentListener(docListener);
        cvvField.getDocument().addDocumentListener(docListener);
        amountField.getDocument().addDocumentListener(docListener);

        validateFields(); // initial validation
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private final DocumentListener docListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent e) { validateFields(); }
        public void removeUpdate(DocumentEvent e) { validateFields(); }
        public void insertUpdate(DocumentEvent e) { validateFields(); }
    };

    private void validateFields() {
        String name = nameField.getText().trim();
        String card = cardNumberField.getText().trim();
        String expiry = expiryField.getText().trim();
        String cvv = new String(cvvField.getPassword());

        nameError.setText(name.isEmpty() ? "Name is required" : " ");
        cardError.setText(!CARD_PATTERN.matcher(card).matches() ? "Enter a valid 16-digit card number" : " ");
        expiryError.setText(!EXPIRY_PATTERN.matcher(expiry).matches() ? "Enter valid expiry (MM/YY)" : " ");
        cvvError.setText(!CVV_PATTERN.matcher(cvv).matches() ? "CVV must be 3 digits" : " ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String card = cardNumberField.getText().trim();
        String expiry = expiryField.getText().trim();
        String cvv = new String(cvvField.getPassword());
        String amount = amountField.getText().trim();

        StringBuilder errorMessage = new StringBuilder();
        boolean valid = true;

        if (name.isEmpty()) {
            errorMessage.append("• Name is required\n");
            valid = false;
        }
        if (!CARD_PATTERN.matcher(card).matches()) {
            errorMessage.append("• Invalid card number (must be 16 digits)\n");
            valid = false;
        }
        if (!EXPIRY_PATTERN.matcher(expiry).matches()) {
            errorMessage.append("• Invalid expiry (use MM/YY)\n");
            valid = false;
        }
        if (!CVV_PATTERN.matcher(cvv).matches()) {
            errorMessage.append("• Invalid CVV (must be 3 digits)\n");
            valid = false;
        }
        if (amount.isEmpty() || !AMOUNT_PATTERN.matcher(amount).matches()) {
            errorMessage.append("• Invalid amount (must be a number)\n");
            valid = false;
        }

        if (!valid) {
            JOptionPane.showMessageDialog(this,
                    "Please fix the following errors:\n\n" + errorMessage.toString(),
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = "Payment Successful!\n\n" +
                "Name: " + name + "\n" +
                "Card: **** **** **** " + card.substring(12) + "\n" +
                "Expiry: " + expiry + "\n" +
                "Amount: Rs. " + amount + "\n\n" +
                (saveCardCheckBox.isSelected() ? "Card saved for future use." : "Thank you for your payment.");

        JOptionPane.showMessageDialog(this, msg, "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);

        // Clear CVV
        cvvField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("Button.select", new Color(0, 14, 70));
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CardPaymentPage();
        });
    }
}