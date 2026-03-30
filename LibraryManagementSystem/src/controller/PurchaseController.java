package controller;

import java.util.List;

import dao.PurchaseDAO;
import model.PurchaseDetail;

public class PurchaseController {

    private PurchaseDAO dao = new PurchaseDAO();
    public void processPurchase(int supplier_id, int admin_id, List<PurchaseDetail> list) {
        new dao.PurchaseDAO().processPurchase(supplier_id, admin_id, list);
    }
 
	
}