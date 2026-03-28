package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.MemberController;
import model.Member;
import java.util.List;

public class MemberView extends JFrame {

    private JTable table;
    private JTextField nameField, emailField, phoneField, addressField;
    private JComboBox<String> statusBox;
    
    private MemberController controller = new MemberController();
    
    private JButton addBtn, updateBtn, deleteBtn, clearBtn;
    
    private int selectedMemberId = -1;

    public MemberView() {

        setTitle("Member Management");
        setSize(900, 600);
        setLayout(new BorderLayout());

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Member Form"));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();

        statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Address"));
        formPanel.add(addressField);

        formPanel.add(new JLabel("Status"));
        formPanel.add(statusBox);

        // ===== BUTTON PANEL =====
        JPanel btnPanel = new JPanel();

        addBtn = new JButton("Add");
        addBtn.addActionListener(e -> addMember());
        updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> updateMember());
        deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(e -> deleteMember());
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearForm());

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);
        

        // ===== TABLE =====
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fillFormFromTable();
            }
        });

        // TEMP TABLE MODEL
        table.setModel(new DefaultTableModel(
                new Object[]{"ID", "Name", "Email", "Phone", "Address", "Status"}, 0
        ));

        // ===== ADD TO FRAME =====
        add(formPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        
        loadMembers();
    }
    private void loadMembers() {

        List<Member> list = controller.getMembers();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // clear table

        for (Member m : list) {
            model.addRow(new Object[]{
                    m.getMember_id(),
                    m.getName(),
                    m.getEmail(),
                    m.getPhone(),
                    m.getAddress(),
                    m.getStatus()
            });
        }
    }
    private void addMember() {

        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String status = statusBox.getSelectedItem().toString();

        // Simple validation
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email are required!");
            return;
        }

        // Call controller
        controller.addMember(name, email, phone, address, status);

        JOptionPane.showMessageDialog(this, "Member Added Successfully!");

        clearForm();
        loadMembers();
    }
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        statusBox.setSelectedIndex(0);
    }
    
    private void fillFormFromTable() {

        int row = table.getSelectedRow();

        if (row == -1) return;

        selectedMemberId = Integer.parseInt(table.getValueAt(row, 0).toString());

        nameField.setText(table.getValueAt(row, 1).toString());
        emailField.setText(table.getValueAt(row, 2).toString());
        phoneField.setText(table.getValueAt(row, 3).toString());
        addressField.setText(table.getValueAt(row, 4).toString());

        statusBox.setSelectedItem(table.getValueAt(row, 5).toString());
    }
    private void updateMember() {

        if (selectedMemberId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member first!");
            return;
        }

        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String status = statusBox.getSelectedItem().toString();

        controller.updateMember(selectedMemberId, name, email, phone, address, status);

        JOptionPane.showMessageDialog(this, "Member Updated Successfully!");

        clearForm();
        loadMembers();

        selectedMemberId = -1;
    }
    
    private void deleteMember() {

        if (selectedMemberId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member first!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this member?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            controller.deleteMember(selectedMemberId);

            JOptionPane.showMessageDialog(this, "Member Deleted Successfully!");

            clearForm();
            loadMembers();

            selectedMemberId = -1;
        }
    }
}