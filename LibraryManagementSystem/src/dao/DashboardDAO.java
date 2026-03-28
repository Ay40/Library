package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DashboardDAO {

    public int countBooks() {
        return getCount("SELECT COUNT(*) FROM Book");
    }

    public int countMembers() {
        return getCount("SELECT COUNT(*) FROM Member");
    }

    public int countBorrowedBooks() {
        return getCount("SELECT COUNT(*) FROM Borrow_Detail WHERE status = 'borrowed'");
    }

    public int countAvailableBooks() {
        return getCount("SELECT SUM(available_quantity) FROM Book");
    }

    private int getCount(String sql) {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
 //  Overdue Books Count
    public int countOverdueBooks() {
        String sql = "SELECT COUNT(*) FROM Borrow_Detail WHERE due_date < CURDATE() AND status = 'borrowed'";
        return getCount(sql);
    }

    //  Low Stock Books
    public int countLowStockBooks() {
        String sql = "SELECT COUNT(*) FROM Book WHERE available_quantity < 2";
        return getCount(sql);
    }

    //  Membership Expiring
    public int countExpiringMemberships() {
        String sql = "SELECT COUNT(*) FROM Membership WHERE expire_date <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)";
        return getCount(sql);
    }
    
    public ResultSet getRecentBorrows() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT b.borrow_id, m.name, bk.title, bd.due_date " +
                         "FROM Borrow b " +
                         "JOIN Member m ON b.member_id = m.member_id " +
                         "JOIN Borrow_Detail bd ON b.borrow_id = bd.borrow_id " +
                         "JOIN Book bk ON bd.book_id = bk.book_id " +
                         "ORDER BY b.borrow_date DESC LIMIT 5";

            Statement st = con.createStatement();
            return st.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultSet getOverdueList() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT m.name, bk.title, bd.due_date, " +
                         "DATEDIFF(CURDATE(), bd.due_date) AS days_late " +
                         "FROM Borrow_Detail bd " +
                         "JOIN Borrow b ON bd.borrow_id = b.borrow_id " +
                         "JOIN Member m ON b.member_id = m.member_id " +
                         "JOIN Book bk ON bd.book_id = bk.book_id " +
                         "WHERE bd.due_date < CURDATE() AND bd.status = 'borrowed'";

            Statement st = con.createStatement();
            return st.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}