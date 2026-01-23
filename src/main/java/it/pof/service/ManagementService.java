package it.pof.service;

import it.pof.models.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManagementService {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Sale> sales = new ArrayList<>();

    public void startup() {
        System.out.println("🌿 Welcome to Planty of Food. Initialization in progress...");
        
        loadCSV("products.csv", v -> products.add(new Product(
            Integer.parseInt(v[0]), v[2], v[1], v[4], v[3], v[5].equalsIgnoreCase("YES")
        )));

        loadCSV("users.csv", v -> users.add(new User(
            Integer.parseInt(v[0]), v[1], v[2], v[3], v[4], v[5]
        )));

        loadCSV("sales.csv", v -> sales.add(new Sale(
            Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2])
        )));
        
        System.out.println("✅ Database synchronized. Ready to use.");
    }

    private void loadCSV(String path, java.util.function.Consumer<String[]> parser) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {

                if (line.isBlank() || line.trim().startsWith(";;")) continue;
                
                String[] values = line.split(";", -1);

                for (int i = 0; i < values.length; i++) values[i] = values[i].trim();
                
                if (!values[0].isEmpty()) parser.accept(values);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Note: File " + path + " not found or empty.");
        }
    }

    public void viewProducts() {
        System.out.println("\n--- 🛒 WAREHOUSE AVAILABILITY ---");
        if (products.isEmpty()) System.out.println("No products in the catalog.");
        else products.forEach(System.out::println);
    }

    public void buyProduct(int productId, int userId) {
        Product p = products.stream()
                .filter(prod -> prod.getId() == productId && prod.isAvailable())
                .findFirst()
                .orElse(null);

        boolean userExists = users.stream().anyMatch(u -> u.getId() == userId);

        if (p == null) {
            System.out.println("❌ Error: Product not available or ID does not exist.");
            return;
        }

        if (!userExists) {
            System.out.println("❌ Error: User ID " + userId + " not found. Register them first!");
            return;
        }

        p.setAvailable(false);
        int newSaleId = sales.stream().mapToInt(Sale::getId).max().orElse(0) + 1;
        sales.add(new Sale(newSaleId, productId, userId));
        
        syncAll();
        System.out.println("🎉 Purchase registered! Sale ID: " + newSaleId);
    }

    public void returnProduct(int saleId) {
        Sale s = sales.stream().filter(sale -> sale.getId() == saleId).findFirst().orElse(null);

        if (s != null) {
            products.stream().filter(p -> p.getId() == s.getProductId()).forEach(p -> p.setAvailable(true));
            sales.remove(s);
            syncAll();
            System.out.println("🔄 Return completed. The product is back in the warehouse.");
        } else {
            System.out.println("❌ Sale ID not found.");
        }
    }

    public void addUser(int id, String n, String ln, String d, String a, String doc) {
        if (users.stream().anyMatch(u -> u.getId() == id)) {
            System.out.println("⚠️ Error: This User ID is already taken.");
            return;
        }

        users.add(new User(id, n, ln, d, a, doc));
        syncAll();
        System.out.println("👤 Welcome " + n + ", profile created successfully.");
    }

    private void syncAll() {
        saveCSV("products.csv", "ID;Name;Insertion Date;Price;Brand;Available", 
            products.stream().map(p -> p.getId() + ";" + p.getName() + ";" + p.getInsertionDate() + ";" + p.getPrice() + ";" + p.getBrand() + ";" + (p.isAvailable() ? "YES" : "NO")).toList());
        
        saveCSV("users.csv", "ID;Name;Last Name;Birth Date;Address;Document ID", 
            users.stream().map(u -> u.getId() + ";" + u.getName() + ";" + u.getLastName() + ";" + u.getBirthDate() + ";" + u.getAddress() + ";" + u.getDocumentId()).toList());
        
        saveCSV("sales.csv", "ID;Product ID;User ID", 
            sales.stream().map(s -> s.getId() + ";" + s.getProductId() + ";" + s.getUserId()).toList());
    }

    private void saveCSV(String path, String header, List<String> rows) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(header);
            rows.forEach(pw::println);
        } catch (IOException e) {
            System.err.println("🚨 Write error on " + path);
        }
    }

    public void exportAvailable() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String filename = "available_products_" + date + ".csv";
        
        try (PrintWriter pw = new PrintWriter(new File(filename))) {
            pw.println("ID;Insertion Date;Name;Brand;Price");
            products.stream()
                .filter(Product::isAvailable)
                .forEach(p -> pw.println(p.getId() + ";" + p.getInsertionDate() + ";" + p.getName() + ";" + p.getBrand() + ";" + p.getPrice()));
            System.out.println("📂 Report generated: " + filename);
        } catch (Exception e) {
            System.err.println("❌ Error during export.");
        }
    }
}
