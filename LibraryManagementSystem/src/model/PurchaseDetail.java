package model;

public class PurchaseDetail {

    private int book_id;
    private int quantity;
    private double unit_price;

    // Constructor
    public PurchaseDetail(int book_id, int quantity, double unit_price) {
        this.book_id = book_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    // Getters
    public int getBook_id() {
        return book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }
    
    

    // Setters (optional)
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }


}