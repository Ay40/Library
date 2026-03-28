package controller;

import dao.BookDAO;
import model.Book;

import java.util.List;

public class BookController {

    private BookDAO bookDAO = new BookDAO();

    public void addBook(String title, int category_id, int total, int available) {
        Book book = new Book(0, title, category_id, total, available);
        bookDAO.addBook(book);
    }

    public List<Book> getBooks() {
        return bookDAO.getAllBooks();
    }
}