package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Applies transformations to products.
 */
public class Transformer {
    public void transform(Product product) {
        // Step 1: Uppercase name
        product.setName(product.getName().toUpperCase());

        // Step 2: Apply discount if Electronics
        double price = product.getPrice();
        if (product.getCategory().equals("Electronics")) {
            price = round(price * 0.9, 2);
            product.setPrice(price);
        }

        // Step 3: Recategorize
        if (product.getCategory().equals("Electronics") && price > 500.0) {
            product.setCategory("Premium Electronics");
        }

        // Step 4: Price range
        String priceRange;
        if (price <= 10.0) priceRange = "Low";
        else if (price <= 100.0) priceRange = "Medium";
        else if (price <= 500.0) priceRange = "High";
        else priceRange = "Premium";

        product.setPriceRange(priceRange);
    }

    private double round(double value, int places) {
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
