package controller;

import dao.BorrowDAO;

public class BorrowController {

    private BorrowDAO dao = new BorrowDAO();

    public boolean borrowBook(int member_id, int book_id, int quantity, String due_date) {
        return dao.borrowBook(member_id, book_id, quantity, due_date);
    }
    public int getAvailableStock(int book_id) {
        return dao.getAvailableStock(book_id);
    }
	
}
