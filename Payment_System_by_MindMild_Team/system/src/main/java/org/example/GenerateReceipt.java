package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateReceipt {

    public static void create(StudentPaymentSystem system) {
        StringBuilder receipt = new StringBuilder();

        receipt.append("----- Student Payment Receipt -----\n");
        receipt.append("Name: ").append(system.nameField.getText()).append("\n");
        receipt.append("Reg No: ").append(system.regField.getText()).append("\n");
        receipt.append("Batch: ").append(system.DivField.getText()).append("\n");
        receipt.append("Roll No: ").append(system.rollField.getText()).append("\n");
        receipt.append("Department: ").append(system.deptField.getText()).append("\n\n");

        receipt.append("Fees Paid:\n");
        for (int i = 0; i < system.feeChecks.length; i++) {
            if (system.feeChecks[i].isSelected()) {
                receipt.append(system.feeLabels[i]).append(": ₹").append(system.feeFields[i].getText()).append("\n");
            }
        }

        receipt.append("\nTotal: ₹").append(system.totalCostField.getText()).append("\n");
        receipt.append("----------------------------------\n");

        // Show in text area
        String receiptText = receipt.toString();
        system.receiptArea.setText(receiptText);

        // Save to text file


        // Now send to printer
        printReceipt(receiptText, system);
    }

    private static void printReceipt(String text, Component parentComponent) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Student Payment Receipt");

        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));

            int y = 0;
            for (String line : text.split("\n")) {
                y += g2d.getFontMetrics().getHeight();
                g2d.drawString(line, 10, y);
            }

            return Printable.PAGE_EXISTS;
        });

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(parentComponent, "Printing failed: " + e.getMessage());
            }
        }
    }
}
