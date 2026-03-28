package view;

import controller.DashboardController;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import view.*;

public class DashboardView extends JFrame {

    private DashboardController controller;

    private JTextArea alertArea = new JTextArea(5, 30);

    private JLabel totalBooksLabel = new JLabel("0");
    private JLabel totalMembersLabel = new JLabel("0");
    private JLabel borrowedBooksLabel = new JLabel("0");
    private JLabel availableBooksLabel = new JLabel("0");

    private JTable recentTable;
    private JTable overdueTable;

    public DashboardView() {

        controller = new DashboardController();

        setTitle("Library Dashboard");
        setSize(1000, 650);
        setLayout(new BorderLayout());

        // ===== TOP NAVBAR =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setPreferredSize(new Dimension(1000, 50));

        JLabel title = new JLabel("  Library Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        topPanel.add(title, BorderLayout.WEST);

        // ===== SIDEBAR =====
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(10, 1, 5, 5));
        sidePanel.setPreferredSize(new Dimension(160, 600));
        sidePanel.setBackground(Color.LIGHT_GRAY);

        String[] menus = {
                "Dashboard", "Members", "Books", "Borrowing","Return",  
                "Requests", "Suppliers", "Purchases", "Fines", "Messages"
        };

//        for (String m : menus) {
//            JButton btn = new JButton(m);
//            sidePanel.add(btn);
//        }
        for (String m : menus) {
            JButton btn = new JButton(m);

            btn.addActionListener(e -> handleMenuClick(m));

            sidePanel.add(btn);
        }

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== KPI PANEL =====
        JPanel kpiPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        kpiPanel.add(createCard("Total Books", totalBooksLabel));
        kpiPanel.add(createCard("Total Members", totalMembersLabel));
        kpiPanel.add(createCard("Borrowed Books", borrowedBooksLabel));
        kpiPanel.add(createCard("Available Books", availableBooksLabel));

        // ===== ALERT PANEL =====
        JPanel alertPanel = new JPanel(new BorderLayout());
        alertPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel alertTitle = new JLabel("SYSTEM ALERTS", JLabel.CENTER);
        alertTitle.setFont(new Font("Arial", Font.BOLD, 14));

        alertArea.setEditable(false);

        alertPanel.add(alertTitle, BorderLayout.NORTH);
        alertPanel.add(new JScrollPane(alertArea), BorderLayout.CENTER);

        // ===== TABLE PANEL =====
        JPanel tablePanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Recent Borrow Table
        recentTable = new JTable();
        recentTable.setFillsViewportHeight(true);

        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.add(new JLabel("Recent Borrow Transactions", JLabel.CENTER), BorderLayout.NORTH);
        recentPanel.add(new JScrollPane(recentTable), BorderLayout.CENTER);

        // Overdue Table
        overdueTable = new JTable();
        overdueTable.setFillsViewportHeight(true);

        JPanel overduePanel = new JPanel(new BorderLayout());
        overduePanel.add(new JLabel("Overdue Books", JLabel.CENTER), BorderLayout.NORTH);
        overduePanel.add(new JScrollPane(overdueTable), BorderLayout.CENTER);

        tablePanel.add(recentPanel);
        tablePanel.add(overduePanel);

        // ===== SPLIT (Better Layout) =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, alertPanel, tablePanel);
        splitPane.setDividerLocation(150);

        // ===== ADD TO MAIN PANEL =====
        mainPanel.add(kpiPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // ===== ADD TO FRAME (IMPORTANT FIX) =====
        add(topPanel, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // ===== LOAD DATA =====
        loadData();
        loadTables();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center screen
        setVisible(true);
    }

    // ===== KPI CARD =====
    private JPanel createCard(String title, JLabel valueLabel) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(new Color(230, 240, 255));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    // ===== LOAD KPI + ALERTS =====
    private void loadData() {

        totalBooksLabel.setText(String.valueOf(controller.getTotalBooks()));
        totalMembersLabel.setText(String.valueOf(controller.getTotalMembers()));
        borrowedBooksLabel.setText(String.valueOf(controller.getBorrowedBooks()));
        availableBooksLabel.setText(String.valueOf(controller.getAvailableBooks()));

        int overdue = controller.getOverdueBooks();
        int lowStock = controller.getLowStockBooks();
        int expiring = controller.getExpiringMemberships();

        alertArea.setText("");

        if (overdue > 0)
            alertArea.append("⚠ " + overdue + " Books are overdue\n");

        if (expiring > 0)
            alertArea.append("⚠ " + expiring + " Memberships expiring soon\n");

        if (lowStock > 0)
            alertArea.append("⚠ " + lowStock + " Books low stock\n");
    }

    // ===== LOAD TABLES =====
    private void loadTables() {
        try {
            ResultSet rs1 = controller.getRecentBorrows();
            recentTable.setModel(buildTableModel(rs1));

            ResultSet rs2 = controller.getOverdueList();
            overdueTable.setModel(buildTableModel(rs2));

            // close ResultSet (important)
            if (rs1 != null) rs1.close();
            if (rs2 != null) rs2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== RESULTSET → TABLE MODEL =====
    private DefaultTableModel buildTableModel(ResultSet rs) throws Exception {

        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        String[] columnNames = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = meta.getColumnName(i);
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        while (rs.next()) {
            Object[] row = new Object[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }

            model.addRow(row);
        }

        return model;
    }
    private void handleMenuClick(String menu) {

        switch (menu) {

            case "Members":
                new MemberView();
                break;

            case "Books":
                new BookView();
                break;

            case "Borrowing":
                new BorrowView();
                break;
                
            case "Return":   
                new ReturnView();
                break;
            case "Dashboard":
                new DashboardView();
                break;

            default:
                JOptionPane.showMessageDialog(this, menu + " Page Coming Soon!");
        }

        dispose(); // close current window
    }
}