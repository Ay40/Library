import view.BookView;
import view.MemberView;
import view.PurchaseView;
import view.BorrowView;
import view.ReturnView;
import view.DashboardView;

public class Main {
    public static void main(String[] args) {
//        new BookView();
//        new MemberView();
//    	new BorrowView();
//    	ReturnView view = new ReturnView();
//        view.returnBook();

//    	new DashboardView();
    	PurchaseView view = new PurchaseView();
        view.finalPurchase();
    }
}