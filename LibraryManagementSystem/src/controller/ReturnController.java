package controller;

import dao.ReturnDAO;

public class ReturnController {

    private ReturnDAO dao = new ReturnDAO();

    public boolean returnBook(int borrow_detail_id) {
        return dao.returnBook(borrow_detail_id);
    }
}