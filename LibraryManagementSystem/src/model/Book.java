package model;

public class Book {
    private int book_id;
    private String title;
    private int category_id;
    private int total_quantity;
    private int available_quantity;

    public Book() {}

    public Book(int book_id, String title, int category_id, int total_quantity, int available_quantity) {
        this.book_id = book_id;
        this.title = title;
        this.category_id = category_id;
        this.total_quantity = total_quantity;
        this.available_quantity = available_quantity;
    }

    public int getBook_id() { return book_id; }
    public void setBook_id(int book_id) { this.book_id = book_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }

    public int getTotal_quantity() { return total_quantity; }
    public void setTotal_quantity(int total_quantity) { this.total_quantity = total_quantity; }

    public int getAvailable_quantity() { return available_quantity; }
    public void setAvailable_quantity(int available_quantity) { this.available_quantity = available_quantity; }
}