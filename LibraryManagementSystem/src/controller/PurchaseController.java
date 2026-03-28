package controller;

import dao.PurchaseDAO;
import model.PurchaseDetail;

public class PurchaseController {

    private PurchaseDAO dao = new PurchaseDAO();

//    public int createPurchase(int supplier_id, int admin_id, double total) {
//        return dao.createPurchase(supplier_id, admin_id, total);
//    }
//    public void addPurchaseDetail(int purchase_id, int book_id, int qty, double price) {
//        dao.addPurchaseDetail(purchase_id, book_id, qty, price);
//    }
//    public void updateStock(int book_id, int qty) {
//        dao.updateBookStock(book_id, qty);
//    }
    public void processPurchase(int supplier_id, int admin_id, java.util.List<PurchaseDetail> list) {
        dao.processPurchase(supplier_id, admin_id, list);
    }
}