package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.util.*;

/**
 * Main application for the ETL pipeline.
 */
public class ETLApp {
    public static void main(String[] args) {
        String inputPath = "data/products.csv";
        String outputPath = "data/transformed_products.csv";

        CSVExtractor extractor = new CSVExtractor();
        Transformer transformer = new Transformer();
        CSVLoader loader = new CSVLoader();

        try {
            List<Product> products = extractor.extract(inputPath);

            for (Product product : products) {
                transformer.transform(product);
            }

            loader.load(products, outputPath);

            System.out.println("ETL complete. " + products.size() + " products processed.");
            System.out.println("Output written to " + outputPath);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
