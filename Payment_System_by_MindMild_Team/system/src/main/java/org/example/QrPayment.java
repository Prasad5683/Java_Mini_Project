package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;



public class QrPayment extends JFrame {

    public QrPayment(double amount) {
        setTitle("QR Payment");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Scan & Pay", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        try {
            String upiId = "piseprasad5683@okhdfcbank"; // replace with your actual UPI ID
            String name = "MindMild Team";
            String transactionNote = "Payment to MindMild";
            String currency = "INR";

            String upiUri = String.format("upi://pay?pa=%s&pn=%s&tn=%s&am=%.2f&cu=%s",
                    URLEncoder.encode(upiId, StandardCharsets.UTF_8),
                    URLEncoder.encode(name, StandardCharsets.UTF_8),
                    URLEncoder.encode(transactionNote, StandardCharsets.UTF_8),
                    amount,
                    currency
            );

            // Generate QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(upiUri, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            JLabel qrLabel = new JLabel(new ImageIcon(qrImage));
            add(qrLabel, BorderLayout.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
            JLabel error = new JLabel("Failed to generate QR code", SwingConstants.CENTER);
            error.setForeground(Color.RED);
            add(error, BorderLayout.CENTER);
        }

        JLabel footerLabel = new JLabel("Amount: ₹" + amount, SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        add(footerLabel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QrPayment(2).setVisible(true)); // test with 65000
    }
}