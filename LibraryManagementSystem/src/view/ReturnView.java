package view;

import controller.ReturnController;

import javax.swing.*;
import java.awt.*;

public class ReturnView extends JFrame {

    private JTextField borrowDetailField;
    private ReturnController controller;

    public ReturnView() {

        controller = new ReturnController();

        setTitle("Return Book");
        setSize(350, 200);
        setLayout(new GridLayout(3, 1, 10, 10));

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Borrow Detail ID:"));

        borrowDetailField = new JTextField(15);
        inputPanel.add(borrowDetailField);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        JButton returnBtn = new JButton("Return Book");
        buttonPanel.add(returnBtn);

        // ===== ADD TO FRAME =====
        add(inputPanel);
        add(buttonPanel);

        // ===== BUTTON ACTION =====
        returnBtn.addActionListener(e -> handleReturn());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // center
        setVisible(true);
    }

    private void handleReturn() {

        try {
            String input = borrowDetailField.getText().trim();

            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Borrow Detail ID!");
                return;
            }

            int id = Integer.parseInt(input);

            boolean success = controller.returnBook(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Book Returned Successfully!");
                borrowDetailField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Return Failed! Check ID.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
        }
    }
}