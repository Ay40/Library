package dao;

import model.Supplier;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM Supplier";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                suppliers.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Add a new supplier
    public void addSupplier(Supplier s) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO Supplier (name, phone, email, address) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getPhone());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getAddress());
            ps.executeUpdate();
            System.out.println("Supplier added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Find a supplier by ID
    public Supplier getSupplierById(int supplier_id) {
        Supplier s = null;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Supplier WHERE supplier_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, supplier_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}