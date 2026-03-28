package view;

import controller.BorrowController;

import javax.swing.*;
import java.awt.*;

public class BorrowView extends JFrame {

    private JTextField memberField, bookField, quantityField, dueDateField;
    private BorrowController controller;

    public BorrowView() {

        controller = new BorrowController();

        setTitle("Borrow Book");
        setSize(400, 300);
        setLayout(new FlowLayout());

        memberField = new JTextField(10);
        bookField = new JTextField(10);
        quantityField = new JTextField(5);
        dueDateField = new JTextField(10);

        JButton borrowBtn = new JButton("Borrow");

        add(new JLabel("Member ID:")); add(memberField);
        add(new JLabel("Book ID:")); add(bookField);
        add(new JLabel("Quantity:")); add(quantityField);
        add(new JLabel("Due Date (YYYY-MM-DD):")); add(dueDateField);

        add(borrowBtn);

        borrowBtn.addActionListener(e -> {

            try {
                int member_id = Integer.parseInt(memberField.getText());
                int book_id = Integer.parseInt(bookField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String due_date = dueDateField.getText();

                // 🔥 STEP 1: Check available stock
                int available = controller.getAvailableStock( book_id);

                if (available == 0) {
                    JOptionPane.showMessageDialog(this, "❌ No books available!");
                    return;
                }

                // 🔥 STEP 2: If requested > available
                if (quantity > available) {

                    int option = JOptionPane.showConfirmDialog(
                            this,
                            "Only " + available + " books available.\nDo you want to borrow " + available + " instead?",
                            "Stock Warning",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        quantity = available; // ✅ adjust quantity
                    } else {
                        return; // ❌ cancel
                    }
                }

                // 🔥 STEP 3: Proceed with borrow
                boolean success = controller.borrowBook(member_id, book_id, quantity, due_date);

                if (success) {
                    JOptionPane.showMessageDialog(this, " Book Borrowed Successfully!");
                    
                    // Clear fields
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, " Borrow Failed!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter correct values.");
            }

        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}