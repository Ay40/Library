package view;

import controller.PurchaseController;
import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PurchaseView {

    private PurchaseController controller = new PurchaseController();
 
    public void finalPurchase() {

        Scanner sc = new Scanner(System.in);

        // 1️⃣ Fetch and show available books FIRST
        BookController bookController = new BookController();
        List<Book> books = bookController.getBooks();
        System.out.println("Book count = " + books.size()); // debug

        if (books.isEmpty()) {
            System.out.println("No books available. Exiting...");
            return;
        }

        System.out.println("Available Books:");
        for (Book b : books) {
            System.out.println(b.getBook_id() + " - " + b.getTitle());
        }
        SupplierController supplierController = new SupplierController();
//        List<Supplier> suppliers = supplierController.getAllSuppliers();
        List<Supplier> suppliers = supplierController.getSuppliers();

        System.out.println("\n===== AVAILABLE SUPPLIERS =====");

        for (Supplier s : suppliers) {
            System.out.println(s.getSupplier_id() + " - " + s.getName());
        }

        System.out.println("===============================\n");

        // 2️⃣ Now ask for Supplier and Admin IDs
        System.out.print("Supplier ID: ");
        int supplier_id = sc.nextInt();

        System.out.print("Admin ID: ");
        int admin_id = sc.nextInt();
        
        Supplier selectedSupplier = supplierController.getSupplier(supplier_id);

        if (selectedSupplier != null) {
            System.out.println("Phone: " + selectedSupplier.getPhone());
            System.out.println("Email: " + selectedSupplier.getEmail());
            System.out.println("Address: " + selectedSupplier.getAddress());
        } else {
            System.out.println("Supplier not found!");
        }
        
        

        // 3️⃣ Add purchase details
        List<PurchaseDetail> list = new ArrayList<>();
        double total = 0;

        while (true) {
            System.out.print("Book ID: ");
            int book_id = sc.nextInt();

            System.out.print("Quantity: ");
            int qty = sc.nextInt();

            System.out.print("Unit Price: ");
            double price = sc.nextDouble();

            list.add(new PurchaseDetail(book_id, qty, price));
            total += qty * price;

            System.out.print("Add more? (y/n): ");
            if (!sc.next().equalsIgnoreCase("y")) break;
        }

        System.out.println("Total = " + total);

        controller.processPurchase(supplier_id, admin_id, list);
    }
}