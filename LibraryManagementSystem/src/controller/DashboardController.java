package controller;

import dao.DashboardDAO;
import java.sql.ResultSet;

public class DashboardController {

    private DashboardDAO dao = new DashboardDAO();

    public int getTotalBooks() {
        return dao.countBooks();
    }

    public int getTotalMembers() {
        return dao.countMembers();
    }

    public int getBorrowedBooks() {
        return dao.countBorrowedBooks();
    }

    public int getAvailableBooks() {
        return dao.countAvailableBooks();
    }
    public int getOverdueBooks() {
        return dao.countOverdueBooks();
    }

    public int getLowStockBooks() {
        return dao.countLowStockBooks();
    }

    public int getExpiringMemberships() {
        return dao.countExpiringMemberships();
    }
    public ResultSet getRecentBorrows() {
        return dao.getRecentBorrows();
    }

    public ResultSet getOverdueList() {
        return dao.getOverdueList();
    }
    
    
}