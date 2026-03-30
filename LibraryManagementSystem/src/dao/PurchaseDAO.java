package dao;

import util.DBConnection;
import java.sql.*;
import java.util.List;
import model.PurchaseDetail;

public class PurchaseDAO {

    public void processPurchase(int supplier_id, int admin_id, List<PurchaseDetail> details) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // START TRANSACTION

            // ================= 1. CALCULATE TOTAL =================
            double total = 0;
            for (PurchaseDetail d : details) {
                total += d.getQuantity() * d.getUnit_price();
            }

            // ================= 2. INSERT PURCHASE =================
            String purchaseSQL = "INSERT INTO Purchase (supplier_id, admin_id, purchase_date, total_amount) VALUES (?, ?, NOW(), ?)";
            PreparedStatement psPurchase = con.prepareStatement(purchaseSQL, Statement.RETURN_GENERATED_KEYS);

            psPurchase.setInt(1, supplier_id);
            psPurchase.setInt(2, admin_id);
            psPurchase.setDouble(3, total);

            psPurchase.executeUpdate();

            ResultSet rsPurchase = psPurchase.getGeneratedKeys();
            int purchase_id = 0;
            if (rsPurchase.next()) {
                purchase_id = rsPurchase.getInt(1);
            }

            // ================= 3. LOOP PURCHASE DETAILS =================
            for (PurchaseDetail d : details) {

                int bookId = d.getBook_id();

                // 🔹 CASE 1: NEW BOOK
                if (bookId == 0) {
                    String insertBookSQL = "INSERT INTO Book (title, total_quantity, available_quantity) VALUES (?, ?, ?)";
                    PreparedStatement psNewBook = con.prepareStatement(insertBookSQL, Statement.RETURN_GENERATED_KEYS);

                    psNewBook.setString(1, d.getTitle());
                    psNewBook.setInt(2, d.getQuantity());
                    psNewBook.setInt(3, d.getQuantity());

                    psNewBook.executeUpdate();

                    ResultSet rsBook = psNewBook.getGeneratedKeys();
                    if (rsBook.next()) {
                        bookId = rsBook.getInt(1); // NEW GENERATED BOOK ID
                    }

                    rsBook.close();
                    psNewBook.close();
                }
                // 🔹 CASE 2: EXISTING BOOK → UPDATE STOCK
                else {
                    String updateSQL = "UPDATE Book SET total_quantity = total_quantity + ?, available_quantity = available_quantity + ? WHERE book_id = ?";
                    PreparedStatement psUpdate = con.prepareStatement(updateSQL);

                    psUpdate.setInt(1, d.getQuantity());
                    psUpdate.setInt(2, d.getQuantity());
                    psUpdate.setInt(3, bookId);

                    int rows = psUpdate.executeUpdate();
                    if (rows == 0) {
                        throw new Exception("Book not found: " + bookId);
                    }

                    psUpdate.close();
                }

                // 🔹 INSERT PURCHASE DETAIL
                String detailSQL = "INSERT INTO Purchase_Detail (purchase_id, book_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
                PreparedStatement psDetail = con.prepareStatement(detailSQL);

                psDetail.setInt(1, purchase_id);
                psDetail.setInt(2, bookId);
                psDetail.setInt(3, d.getQuantity());
                psDetail.setDouble(4, d.getUnit_price());

                psDetail.executeUpdate();
                psDetail.close();
            }

            con.commit(); // ✅ COMMIT TRANSACTION
            System.out.println("Purchase saved successfully!");

        } catch (Exception e) {
            try {
                if (con != null) con.rollback(); // ❌ ROLLBACK
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}