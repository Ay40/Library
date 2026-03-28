package model;

public class Member {

    private int member_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String registered_date;
    private String status;

    public Member() {}

    public Member(int member_id, String name, String email, String phone, String address, String registered_date, String status) {
        this.member_id = member_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.registered_date = registered_date;
        this.status = status;
    }

    public int getMember_id() { return member_id; }
    public void setMember_id(int member_id) { this.member_id = member_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRegistered_date() { return registered_date; }
    public void setRegistered_date(String registered_date) { this.registered_date = registered_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
