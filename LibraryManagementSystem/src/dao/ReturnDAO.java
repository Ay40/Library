package dao;

import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReturnDAO {

	public boolean returnBook(int borrow_detail_id) {

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false); 

            // 1. Insert into Book_Return
            String insertReturn = "INSERT INTO Book_Return (borrow_detail_id, return_date) VALUES (?, NOW())";
            PreparedStatement ps1 = con.prepareStatement(insertReturn);
            ps1.setInt(1, borrow_detail_id);
            ps1.executeUpdate();

            // 2. Update Borrow_Detail
            String updateBorrow = "UPDATE Borrow_Detail SET status = 'Returned' WHERE borrow_detail_id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateBorrow);
            ps2.setInt(1, borrow_detail_id);
            ps2.executeUpdate();

            // 3. Update Book stock
            String updateBook = "UPDATE Book b " +
                    "JOIN Borrow_Detail bd ON b.book_id = bd.book_id " +
                    "SET b.available_quantity = b.available_quantity + bd.quantity " +
                    "WHERE bd.borrow_detail_id = ?";
            PreparedStatement ps3 = con.prepareStatement(updateBook);
            ps3.setInt(1, borrow_detail_id);
            ps3.executeUpdate();

            con.commit(); // ✅ success

            return true;

        } catch (Exception e) {
            try {
                con.rollback(); // ❗ rollback if error
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;
        }
}
	}