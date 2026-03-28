package model;

public class Supplier {

    private int supplier_id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Supplier(int supplier_id, String name, String phone, String email, String address) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}