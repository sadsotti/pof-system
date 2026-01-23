package it.pof.models;

public class Sale {
    private int id;
    private int productId;
    private int userId;

    public Sale(int id, int productId, int userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public int getUserId() { return userId; }
}
