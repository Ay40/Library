package dao;

import util.DBConnection;

import java.sql.*;

public class BorrowDAO {

//    public void borrowBook(int member_id, int book_id, int quantity, String due_date) {
//
//        Connection con = null;
//
//        try {
//            con = DBConnection.getConnection();
//            con.setAutoCommit(false); //  TRANSACTION START
//
//            // 1️ Insert into Borrow
//            String borrowSql = "INSERT INTO Borrow(member_id, borrow_date, admin_id) VALUES (?, NOW(), ?)";
//            PreparedStatement ps1 = con.prepareStatement(borrowSql, Statement.RETURN_GENERATED_KEYS);
//
//            ps1.setInt(1, member_id);
//            ps1.setInt(2, 1); // TEMP admin_id
//
//            ps1.executeUpdate();
//
//            ResultSet rs = ps1.getGeneratedKeys();
//            int borrow_id = 0;
//
//            if (rs.next()) {
//                borrow_id = rs.getInt(1);
//            }
//
//            // 2️ Insert into Borrow_Detail
//            String detailSql = "INSERT INTO Borrow_Detail(borrow_id, book_id, quantity, due_date, status) VALUES (?, ?, ?, ?, 'borrowed')";
//            PreparedStatement ps2 = con.prepareStatement(detailSql);
//
//            ps2.setInt(1, borrow_id);
//            ps2.setInt(2, book_id);
//            ps2.setInt(3, quantity);
//            ps2.setString(4, due_date);
//
//            ps2.executeUpdate();
//
//            // 3️ Update Book Quantity
//            String updateSql = "UPDATE Book SET available_quantity = available_quantity - ? WHERE book_id = ?";
//            PreparedStatement ps3 = con.prepareStatement(updateSql);
//
//            ps3.setInt(1, quantity);
//            ps3.setInt(2, book_id);
//
//            ps3.executeUpdate();
//
//            con.commit(); // SUCCESS
//
//        } catch (Exception e) {
//            try {
//                if (con != null) con.rollback(); // ERROR to roll back
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//    }
	public boolean borrowBook(int member_id, int book_id, int quantity, String due_date) {

	    Connection con = DBConnection.getConnection();

	    try {
	        // 1. Check available quantity
	        String checkSQL = "SELECT available_quantity FROM Book WHERE book_id = ?";
	        PreparedStatement ps1 = con.prepareStatement(checkSQL);
	        ps1.setInt(1, book_id);

	        ResultSet rs = ps1.executeQuery();

	        if (rs.next()) {
	            int available = rs.getInt("available_quantity");

	            // ❌ NOT ENOUGH BOOKS
	            if (quantity > available) {
	                System.out.println("Only " + available + " books available!");
	                return false;
	            }
	        } else {
	            System.out.println("Book not found!");
	            return false;
	        }

	        // 2. Insert into Borrow
	        String borrowSQL = "INSERT INTO Borrow (member_id, borrow_date, admin_id) VALUES (?, NOW(), 1)";
	        PreparedStatement ps2 = con.prepareStatement(borrowSQL, Statement.RETURN_GENERATED_KEYS);
	        ps2.setInt(1, member_id);
	        ps2.executeUpdate();

	        ResultSet rs2 = ps2.getGeneratedKeys();
	        int borrow_id = 0;
	        if (rs2.next()) {
	            borrow_id = rs2.getInt(1);
	        }

	        // 3. Insert Borrow Detail
	        String detailSQL = "INSERT INTO Borrow_Detail (borrow_id, book_id, quantity, due_date, status) VALUES (?, ?, ?, ?, 'Borrowed')";
	        PreparedStatement ps3 = con.prepareStatement(detailSQL);
	        ps3.setInt(1, borrow_id);
	        ps3.setInt(2, book_id);
	        ps3.setInt(3, quantity);
	        ps3.setString(4, due_date);
	        ps3.executeUpdate();

	        // 4. Update Book Quantity
	        String updateBook = "UPDATE Book SET available_quantity = available_quantity - ? WHERE book_id = ?";
	        PreparedStatement ps4 = con.prepareStatement(updateBook);
	        ps4.setInt(1, quantity);
	        ps4.setInt(2, book_id);
	        ps4.executeUpdate();

	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	    
	}
	public int getAvailableStock(int book_id) {

	    Connection con = DBConnection.getConnection();

	    try {
	        String sql = "SELECT available_quantity FROM Book WHERE book_id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, book_id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("available_quantity");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	
}