package model;

public class BorrowDetail {

    private int borrow_detail_id;
    private int borrow_id;
    private int book_id;
    private int quantity;
    private String due_date;
    private String status;

    public BorrowDetail() {}

    public BorrowDetail(int borrow_detail_id, int borrow_id, int book_id, int quantity, String due_date, String status) {
        this.borrow_detail_id = borrow_detail_id;
        this.borrow_id = borrow_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.due_date = due_date;
        this.status = status;
    }

    public int getBorrow_detail_id() { return borrow_detail_id; }
    public void setBorrow_detail_id(int borrow_detail_id) { this.borrow_detail_id = borrow_detail_id; }

    public int getBorrow_id() { return borrow_id; }
    public void setBorrow_id(int borrow_id) { this.borrow_id = borrow_id; }

    public int getBook_id() { return book_id; }
    public void setBook_id(int book_id) { this.book_id = book_id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDue_date() { return due_date; }
    public void setDue_date(String due_date) { this.due_date = due_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}