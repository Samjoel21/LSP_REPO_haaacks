package org.howard.edu.lsp.assignment3;

/**
 * Represents a product record from the CSV file.
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    private String category;
    private String priceRange;

    public Product(int productId, String name, double price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getPriceRange() { return priceRange; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
}
