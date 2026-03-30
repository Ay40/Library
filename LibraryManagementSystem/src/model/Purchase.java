package model;

public class Purchase {

    private int purchase_id;
    private int supplier_id;
    private int admin_id;
    private String purchase_date;
    private double total_amount;
    

    public Purchase() {}

    public Purchase(int supplier_id, int admin_id, double total_amount) {
        this.supplier_id = supplier_id;
        this.admin_id = admin_id;
        this.total_amount = total_amount;
        
    }

    // Getters & Setters
    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

   
}