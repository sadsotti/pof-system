package it.pof;

import it.pof.service.ManagementService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ManagementService service = new ManagementService();
        service.startup();
        Scanner sc = new Scanner(System.in);
        String choice = "";

        while (!choice.equals("0")) {
            System.out.println("\n--- POF: Planty of Food ---");
            System.out.println("1 = View all products");
            System.out.println("2 = Buy a product");
            System.out.println("3 = Return a product");
            System.out.println("4 = Add new user");
            System.out.println("5 = Export available products");
            System.out.println("0 = Exit");
            System.out.print("Select operation: ");
            choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> service.viewProducts();
                    case "2" -> {
                        System.out.print("Product ID: "); int productId = Integer.parseInt(sc.nextLine());
                        System.out.print("User ID: "); int userId = Integer.parseInt(sc.nextLine());
                        service.buyProduct(productId, userId);
                    }
                    case "3" -> {
                        System.out.print("Sale ID: "); int saleId = Integer.parseInt(sc.nextLine());
                        service.returnProduct(saleId);
                    }
                    case "4" -> {
                        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Name: "); String n = sc.nextLine();
                        System.out.print("Last Name: "); String ln = sc.nextLine();
                        System.out.print("Birth Date (dd/mm/yyyy): "); String d = sc.nextLine();
                        System.out.print("Address: "); String addr = sc.nextLine();
                        System.out.print("Document ID: "); String doc = sc.nextLine();
                        service.addUser(id, n, ln, d, addr, doc);
                    }
                    case "5" -> service.exportAvailable();
                    case "0" -> System.out.println("Goodbye!");
                    default -> System.out.println("Command not recognized.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            }
        }
        sc.close();
    }
}
