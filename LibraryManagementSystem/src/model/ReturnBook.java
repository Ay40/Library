package model;

public class ReturnBook {

    private int return_id;
    private int borrow_detail_id;
    private String return_date;

    public ReturnBook() {}

    public ReturnBook(int return_id, int borrow_detail_id, String return_date) {
        this.return_id = return_id;
        this.borrow_detail_id = borrow_detail_id;
        this.return_date = return_date;
    }

    public int getReturn_id() { return return_id; }
    public void setReturn_id(int return_id) { this.return_id = return_id; }

    public int getBorrow_detail_id() { return borrow_detail_id; }
    public void setBorrow_detail_id(int borrow_detail_id) { this.borrow_detail_id = borrow_detail_id; }

    public String getReturn_date() { return return_date; }
    public void setReturn_date(String return_date) { this.return_date = return_date; }
}