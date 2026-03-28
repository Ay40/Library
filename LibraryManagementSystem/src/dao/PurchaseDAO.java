package dao;

import util.DBConnection;
import java.sql.*;

public class PurchaseDAO {
	
	public void processPurchase(int supplier_id, int admin_id, java.util.List<model.PurchaseDetail> details) {

	    Connection con = null;

	    try {
	        con = DBConnection.getConnection();
	        con.setAutoCommit(false); //  START TRANSACTION

	        double total = 0;

	        for (model.PurchaseDetail d : details) {
	            total += d.getQuantity() * d.getUnit_price();
	        }

	        // 1. Insert Purchase
	        String purchaseSQL = "INSERT INTO Purchase (supplier_id, admin_id, purchase_date, total_amount) VALUES (?, ?, NOW(), ?)";
	        PreparedStatement ps1 = con.prepareStatement(purchaseSQL, Statement.RETURN_GENERATED_KEYS);

	        ps1.setInt(1, supplier_id);
	        ps1.setInt(2, admin_id);
	        ps1.setDouble(3, total);
	        ps1.executeUpdate();

	        ResultSet rs = ps1.getGeneratedKeys();
	        int purchase_id = 0;
	        if (rs.next()) {
	            purchase_id = rs.getInt(1);
	        }

	        // 2. Loop Details
	        for (model.PurchaseDetail d : details) {
	        	
	        	// Check book exists FIRST
	        	String check = "SELECT book_id FROM Book WHERE book_id = ?";
	        	PreparedStatement psCheck = con.prepareStatement(check);
	        	psCheck.setInt(1, d.getBook_id());

	        	ResultSet rsCheck = psCheck.executeQuery();

	        	if (!rsCheck.next()) {
	        	    throw new Exception("Book ID does not exist: " + d.getBook_id());
	        	}

	            // Insert Purchase Detail
	            String detailSQL = "INSERT INTO Purchase_Detail (purchase_id, book_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps2 = con.prepareStatement(detailSQL);

	            ps2.setInt(1, purchase_id);
	            ps2.setInt(2, d.getBook_id());
	            ps2.setInt(3, d.getQuantity());
	            ps2.setDouble(4, d.getUnit_price());
	            ps2.executeUpdate();

	            // 3. Update Stock (EXISTING ONLY)
	            String update = "UPDATE Book SET total_quantity = total_quantity + ?, available_quantity = available_quantity + ? WHERE book_id = ?";
	            PreparedStatement ps3 = con.prepareStatement(update);

	            ps3.setInt(1, d.getQuantity());
	            ps3.setInt(2, d.getQuantity());
	            ps3.setInt(3, d.getBook_id());

	            int rows = ps3.executeUpdate();

	            // 4. If book NOT exist → throw error (UI must handle new book)
	            if (rows == 0) {
	                throw new Exception("Book ID not found: " + d.getBook_id());
	            }
	        }

	        con.commit(); // ✅ SUCCESS
	        System.out.println("Purchase completed successfully!");

	    } catch (Exception e) {
	        try {
	            if (con != null) con.rollback(); // ❌ ROLLBACK
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    }
	}
    
}