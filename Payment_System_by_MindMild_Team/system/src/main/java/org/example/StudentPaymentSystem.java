package org.example;

import javax.swing.*;
import java.awt.*;

public class StudentPaymentSystem extends JFrame {
    public JTextField nameField, regField,deptField, DivField, rollField;
    public JCheckBox[] feeChecks;
    public JTextField[] feeFields;
    public JTextField totalCostField;
    public JTextArea receiptArea;
    public String[] feeLabels = {
            "Admission Fee", "Re-Admission Fee", "Semester Fee", "Lab Fee", "Library Fee",
            "Examination Fee", "Improvement Fee", "Marks Sheet Fee", "Fine/ Late Fee"
    };

    public double[] defaultFees = {
            65000, 80000, 1800, 100, 500,
            1100, 250, 2, 100
    };

    public StudentPaymentSystem() {
        setTitle("Student Payment System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fonts
        Font labelFont = new Font("Arial", Font.BOLD, 60);
        Font fieldFont = new Font("Arial", Font.PLAIN, 28);
        Font checkboxFont = new Font("Arial", Font.PLAIN, 28);
        Font buttonFont = new Font("Arial", Font.BOLD, 28);
        Font receiptFont = new Font("Monospaced", Font.PLAIN, 28);




        // Load and scale image
        ImageIcon icon = new ImageIcon("src/main/resources/org/example/logo.png");
        Image img = icon.getImage().getScaledInstance(1000, 150, Image.SCALE_SMOOTH); // Adjust width & height
        ImageIcon scaledIcon = new ImageIcon(img);

        // JLabel to hold image
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(500, 45, 1000, 150); // Set bounds to match image size
        add(imageLabel);





        // Student Info
        JLabel nameLabel = new JLabel("Student Name:");
        JLabel regLabel = new JLabel("Registration No:");
        JLabel deptLabel = new JLabel("Department:");
        JLabel DivLabel = new JLabel("Div No:");
        JLabel rollLabel = new JLabel("Roll No:");



        nameField = new JTextField();
        regField = new JTextField();
        DivField = new JTextField();
        rollField = new JTextField();
        deptField = new JTextField();

        int y = 30;
        JLabel[] labels = { nameLabel, regLabel,deptLabel, DivLabel, rollLabel };
        JTextField[] fields = { nameField,deptField, regField, DivField, rollField };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(30, y, 120, 30);
            fields[i].setBounds(160, y, 200, 30);
            add(labels[i]);
            add(fields[i]);
            y += 40;
        }




        // Fee Panel
        JPanel feePanel = new JPanel();
        feePanel.setLayout(new GridLayout(9, 3, 50, 5));
        feePanel.setBounds(100, 250, 600, 350);

        feeChecks = new JCheckBox[feeLabels.length];
        feeFields = new JTextField[feeLabels.length];


        JLabel RLabel = new JLabel("Receipt ");
        RLabel.setBounds(100, 50, 100, 30);
        RLabel.setBounds(700, y, 120, 30);



        for (int i = 0; i < feeLabels.length; i++) {
            int index = i;
            feeChecks[i] = new JCheckBox(feeLabels[i]);
            feeFields[i] = new JTextField("0");
            feeFields[i].setEditable(false);

            feeChecks[i].addActionListener(e -> {
                if (feeChecks[index].isSelected()) {
                    feeFields[index].setText(String.valueOf(defaultFees[index]));
                } else {
                    feeFields[index].setText("0");
                }
                calculateTotal(); // Update total dynamically
            });

            feePanel.add(feeChecks[i]);
            feePanel.add(feeFields[i]);
        }
        add(feePanel);

        // Total Cost
        JLabel totalLabel = new JLabel("Total Cost:");
        totalLabel.setBounds(625, 650, 100, 55);
        add(totalLabel);

        totalCostField = new JTextField();
        totalCostField.setBounds(700, 650, 120, 35);
        totalCostField.setEditable(false);
        add(totalCostField);

        JButton totalBtn = new JButton("Total");
        totalBtn.setBounds(650, 700, 100, 50);
        totalBtn.setBackground(new Color(116, 232, 197));
        totalBtn.addActionListener(e -> calculateTotal());
        add(totalBtn);

        // Receipt Area
        receiptArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBounds(1100, 280, 400, 325);
        add(scrollPane);

        // Buttons
        JButton getReceiptBtn = new JButton("Get Receipt");
        getReceiptBtn.setBounds(1125, 620, 120, 30);
        getReceiptBtn.setBackground(new Color(250, 156, 32));
        getReceiptBtn.addActionListener(e -> generateReceipt());
        add(getReceiptBtn);

        JButton printBtn = new JButton("Print Receipt");
        printBtn.setBounds(1350, 620, 120, 30);
        printBtn.setBackground(new Color(11, 209, 139));
        printBtn.addActionListener(e -> GenerateReceipt.create(this));
        add(printBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBounds(30, 700, 80, 30);
        resetBtn.setBackground(new Color(244, 88, 125, 255));
        resetBtn.addActionListener(e -> resetForm());
        add(resetBtn);

        JButton paymentBtn = new JButton("Get Payment");
        paymentBtn.setBounds(1200, 700, 200, 50);
        paymentBtn.setBackground(new Color(136, 0, 255));
        paymentBtn.addActionListener(e -> new PaymentOption(this, Double.parseDouble(totalCostField.getText())).setVisible(true));


        add(paymentBtn);
    }

    void calculateTotal() {
        double total = 0;
        for (int i = 0; i < feeChecks.length; i++) {
            if (feeChecks[i].isSelected()) {
                try {
                    total += Double.parseDouble(feeFields[i].getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input for " + feeLabels[i]);
                    return;
                }
            }
        }
        totalCostField.setText(String.valueOf(total));
    }

    boolean isStudentInfoComplete() {
        return !nameField.getText().trim().isEmpty()
                && !regField.getText().trim().isEmpty()
                && !DivField.getText().trim().isEmpty()
                && !rollField.getText().trim().isEmpty()
                && !deptField.getText().trim().isEmpty();
    }

    void generateReceipt() {
        if (!isStudentInfoComplete()) {
            JOptionPane.showMessageDialog(this, "Please fill all student details.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("----*** DYPCET Kolhapur ***-----\n");
        receipt.append("----- ** Student Payment Receipt ** -----\n");
        receipt.append("---> Name: ").append(nameField.getText()).append("\n");
        receipt.append("---> Department: ").append(deptField.getText()).append("\n\n");
        receipt.append("---> Reg No: ").append(regField.getText()).append("\n");
        receipt.append("---> Batch: ").append(DivField.getText()).append("\n");
        receipt.append("---> Roll No: ").append(rollField.getText()).append("\n");


        receipt.append("===> Fees Paid:\n");
        for (int i = 0; i < feeChecks.length; i++) {
            if (feeChecks[i].isSelected()) {
                receipt.append(feeLabels[i]).append(": ₹").append(feeFields[i].getText()).append("\n");
            }
        }

        receipt.append("\nTotal: ₹").append(totalCostField.getText()).append("\n");
        receipt.append("----------------------------------\n");
        receipt.append("----------------( @Developed by MindMild Team )------------------\n");

        receiptArea.setText(receipt.toString());
    }

    void resetForm() {
        nameField.setText("");
        deptField.setText("");
        regField.setText("");
        DivField.setText("");
        rollField.setText("");

        totalCostField.setText("");
        receiptArea.setText("");

        for (int i = 0; i < feeChecks.length; i++) {
            feeChecks[i].setSelected(false);
            feeFields[i].setText("0");
        }
    }

    public static void main(String[] args) {
        new StudentPaymentSystem().setVisible(true);
    }
}
  