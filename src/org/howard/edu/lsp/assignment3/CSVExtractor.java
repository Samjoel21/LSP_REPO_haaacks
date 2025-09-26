package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.util.*;

/**
 * Extracts product data from a CSV file.
 */
public class CSVExtractor {
    public List<Product> extract(String inputPath) throws IOException {
        List<Product> products = new ArrayList<>();
        File file = new File(inputPath);

        if (!file.exists()) {
            System.out.println("Error: Input file not found at " + inputPath);
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // header
            if (line == null) return products;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String category = parts[3];

                products.add(new Product(id, name, price, category));
            }
        }
        return products;
    }
}
