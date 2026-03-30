package view;

import controller.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseView extends JFrame {

    JComboBox<String> supplierCombo, bookCombo;
    JTextField phoneField, emailField, addressField;

    JTextField bookIdField, bookTitleField, totalQtyField;
    JTextField qtyField, priceField;

    JTable table;
    JLabel totalLabel;

    JButton addButton, saveButton;

    DefaultTableModel model;

    public PurchaseView() {

        setTitle("Purchase Books");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Supplier Info"));

        supplierCombo = new JComboBox<>();
        

        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();

        phoneField.setEditable(false);
        emailField.setEditable(false);
        addressField.setEditable(false);

        topPanel.add(new JLabel("Supplier:"));
        topPanel.add(supplierCombo);

        topPanel.add(new JLabel("Phone:"));
        topPanel.add(phoneField);

        topPanel.add(new JLabel("Email:"));
        topPanel.add(emailField);

        topPanel.add(new JLabel("Address:"));
        topPanel.add(addressField);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        String[] cols = {"Book ID", "Title", "Qty", "Price", "Total"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= BOTTOM PANEL =================
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Book"));

        bookCombo = new JComboBox<>();
        bookIdField = new JTextField();
        bookTitleField = new JTextField();
        totalQtyField = new JTextField();

        bookIdField.setEditable(false);
        bookTitleField.setEditable(false);
        totalQtyField.setEditable(false);

        qtyField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Book:"));
        formPanel.add(bookCombo);

        formPanel.add(new JLabel("Book ID:"));
        formPanel.add(bookIdField);

        formPanel.add(new JLabel("Title:"));
        formPanel.add(bookTitleField);

        formPanel.add(new JLabel("Total Qty:"));
        formPanel.add(totalQtyField);

        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(qtyField);

        formPanel.add(new JLabel("Unit Price:"));
        formPanel.add(priceField);

        JPanel actionPanel = new JPanel();

        addButton = new JButton("Add");
        saveButton = new JButton("Save Purchase");
        totalLabel = new JLabel("Total: 0");

        actionPanel.add(addButton);
        actionPanel.add(saveButton);
        actionPanel.add(totalLabel);

        JPanel south = new JPanel(new BorderLayout());
        south.add(formPanel, BorderLayout.CENTER);
        south.add(actionPanel, BorderLayout.SOUTH);

        add(south, BorderLayout.SOUTH);

        // ================= LOAD DATA =================
        loadSuppliers();
        loadBooks();

        // ================= EVENTS =================

        // Supplier auto-fill
        supplierCombo.addActionListener(e -> {
            if (supplierCombo.getSelectedItem() == null) return;

            int id = Integer.parseInt(
                    supplierCombo.getSelectedItem().toString().split(" - ")[0]
            );

            Supplier s = new SupplierController().getSupplier(id);

            if (s != null) {
                phoneField.setText(s.getPhone());
                emailField.setText(s.getEmail());
                addressField.setText(s.getAddress());
            }
        });

        // Book auto-fill
        bookCombo.addActionListener(e -> {

            if (bookCombo.getSelectedItem() == null) return;

            String selected = bookCombo.getSelectedItem().toString();
            int id = Integer.parseInt(selected.split(" - ")[0]);

            if (id == 0) {
                // 🔥 NEW BOOK MODE
                bookIdField.setText("AUTO");
                bookTitleField.setText("");
                totalQtyField.setText("0");

                bookTitleField.setEditable(true);

            } else {
                // EXISTING BOOK
                Book b = new BookController().getBookById(id);

                bookIdField.setText(String.valueOf(b.getBook_id()));
                bookTitleField.setText(b.getTitle());
                totalQtyField.setText(String.valueOf(b.getTotal_quantity()));

                bookTitleField.setEditable(false);
            }
        });

        // ADD BUTTON
        addButton.addActionListener(e -> {

            try {
            	int bookId;
            	String title;

            	if (bookIdField.getText().equals("AUTO")) {
            	    bookId = 0; // 🔥 mark as new book
            	    title = bookTitleField.getText();

            	    if (title.isEmpty()) {
            	        JOptionPane.showMessageDialog(this, "Enter book title!");
            	        return;
            	    }

            	} else {
            	    bookId = Integer.parseInt(bookIdField.getText());
            	    title = bookTitleField.getText();
            	}
                int qty = Integer.parseInt(qtyField.getText());
                double price = Double.parseDouble(priceField.getText());

                if (qty <= 0 || price <= 0) {
                    JOptionPane.showMessageDialog(this, "Qty & Price must be > 0");
                    return;
                }

                // 🔥 Prevent duplicate book
                for (int i = 0; i < model.getRowCount(); i++) {
                    if ((int) model.getValueAt(i, 0) == bookId) {

                        int oldQty = (int) model.getValueAt(i, 2);
                        int newQty = oldQty + qty;

                        model.setValueAt(newQty, i, 2);
                        model.setValueAt(price, i, 3);
                        model.setValueAt(newQty * price, i, 4);

                        updateTotal();
                        return;
                    }
                }

                double rowTotal = qty * price;

                model.addRow(new Object[]{bookId, title, qty, price, rowTotal});

                updateTotal();

                qtyField.setText("");
                priceField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        // SAVE BUTTON
        saveButton.addActionListener(e -> {

            try {

                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No items added!");
                    return;
                }

                int supplier_id = Integer.parseInt(
                        supplierCombo.getSelectedItem().toString().split(" - ")[0]
                );

                int admin_id = 1;
                

                List<PurchaseDetail> list = new ArrayList<>();

                for (int i = 0; i < model.getRowCount(); i++) {

                    int bookId = (int) model.getValueAt(i, 0);
                    int qty = (int) model.getValueAt(i, 2);
                    double price = (double) model.getValueAt(i, 3);

//                    list.add(new PurchaseDetail(bookId, qty, price));
                    String title = (String) model.getValueAt(i, 1);
                    list.add(new PurchaseDetail(bookId, title, qty, price));
                }

                new PurchaseController().processPurchase(
                        supplier_id, admin_id, list
                );

                
                JOptionPane.showMessageDialog(this, "Purchase Saved!");

             // 🔥 OPEN DASHBOARD AGAIN (refresh)
                new DashboardView();

//                dispose();

                // 🔥 CLEAR AFTER SAVE
                model.setRowCount(0);
                updateTotal();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving purchase!");
            }
        });
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += (double) model.getValueAt(i, 4);
        }
        totalLabel.setText("Total: " + total);
    }

    private void loadSuppliers() {
        List<Supplier> list = new SupplierController().getSuppliers();
        for (Supplier s : list) {
            supplierCombo.addItem(s.getSupplier_id() + " - " + s.getName());
        }
    }

    private void loadBooks() {
    	bookCombo.addItem("0 - ADD NEW BOOK"); 
        List<Book> list = new BookController().getBooks();
        for (Book b : list) {
            bookCombo.addItem(b.getBook_id() + " - " + b.getTitle());
        }
    }
}