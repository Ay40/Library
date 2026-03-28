package view;

import controller.BookController;
import model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookView extends JFrame {

    private JTextField titleField, categoryField, totalField, availableField;
    private JTextArea displayArea;
    private BookController controller;

    public BookView() {
        controller = new BookController();

        setTitle("Book Management");
        setSize(400, 400);
        setLayout(new FlowLayout());

        titleField = new JTextField(20);
        categoryField = new JTextField(5);
        totalField = new JTextField(5);
        availableField = new JTextField(5);

        JButton addBtn = new JButton("Add Book");
        JButton showBtn = new JButton("Show Books");

        displayArea = new JTextArea(10, 30);

        add(new JLabel("Title:")); add(titleField);
        add(new JLabel("Category ID:")); add(categoryField);
        add(new JLabel("Total:")); add(totalField);
        add(new JLabel("Available:")); add(availableField);

        add(addBtn);
        add(showBtn);
        add(displayArea);

        addBtn.addActionListener(e -> {
            controller.addBook(
                    titleField.getText(),
                    Integer.parseInt(categoryField.getText()),
                    Integer.parseInt(totalField.getText()),
                    Integer.parseInt(availableField.getText())
            );
            JOptionPane.showMessageDialog(this, "Book Added!");
        });

        showBtn.addActionListener(e -> {
            List<Book> books = controller.getBooks();
            displayArea.setText("");

            for (Book b : books) {
                displayArea.append(b.getTitle() + "\n");
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}