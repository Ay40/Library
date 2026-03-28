package model;

public class Borrow {

    private int borrow_id;
    private int member_id;
    private String borrow_date;
    private int admin_id;

    public Borrow() {}

    public Borrow(int borrow_id, int member_id, String borrow_date, int admin_id) {
        this.borrow_id = borrow_id;
        this.member_id = member_id;
        this.borrow_date = borrow_date;
        this.admin_id = admin_id;
    }

    public int getBorrow_id() { return borrow_id; }
    public void setBorrow_id(int borrow_id) { this.borrow_id = borrow_id; }

    public int getMember_id() { return member_id; }
    public void setMember_id(int member_id) { this.member_id = member_id; }

    public String getBorrow_date() { return borrow_date; }
    public void setBorrow_date(String borrow_date) { this.borrow_date = borrow_date; }

    public int getAdmin_id() { return admin_id; }
    public void setAdmin_id(int admin_id) { this.admin_id = admin_id; }
}