package dao;

import model.Book;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "INSERT INTO Book(title, category_id, total_quantity, available_quantity) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getCategory_id());
            ps.setInt(3, book.getTotal_quantity());
            ps.setInt(4, book.getAvailable_quantity());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book getBookById(int bookId) {

        Book book = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM book WHERE book_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getInt("category_id"),
                    rs.getInt("total_quantity"),
                    rs.getInt("available_quantity")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }
    
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book";

        
        
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("category_id"),
                        rs.getInt("total_quantity"),
                        rs.getInt("available_quantity")
                );
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}