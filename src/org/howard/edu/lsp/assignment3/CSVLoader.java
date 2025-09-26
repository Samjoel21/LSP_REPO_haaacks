package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.util.*;

/**
 * Loads transformed product data into a CSV file.
 */
public class CSVLoader {
    public void load(List<Product> products, String outputPath) throws IOException {
        File file = new File(outputPath);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Header
            writer.println("ProductID,Name,Price,Category,PriceRange");

            for (Product product : products) {
                writer.printf("%d,%s,%.2f,%s,%s%n",
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getPriceRange());
            }
        }
    }
}
