package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ETLPipeline for Assignment 1:
 * - Reads data/products.csv (relative path)
 * - Applies transforms (uppercase name, 10% discount for Electronics, recategorize, compute price range)
 * - Writes data/transformed_products.csv with header: ProductID,Name,Price,Category,PriceRange
 *
 * Error handling:
 * - Missing input file -> prints clear error and exits.
 * - Header-only input -> produces output with header only.
 * - Malformed lines are skipped and counted.
 */
public class ETLPipeline {

    private static final Path INPUT_PATH = Paths.get("data", "products.csv");
    private static final Path OUTPUT_PATH = Paths.get("data", "transformed_products.csv");
    private static final String OUTPUT_HEADER = "ProductID,Name,Price,Category,PriceRange";

    private int rowsRead = 0;
    private int rowsTransformed = 0;
    private int rowsSkipped = 0;

    public static void main(String[] args) {
        new ETLPipeline().run();
    }

    public void run() {
        // Check input existence
        if (!Files.exists(INPUT_PATH)) {
            System.err.println("ERROR: Input file not found: " + INPUT_PATH.toString());
            System.err.println("Make sure you run the program from the project root and that 'data/products.csv' exists.");
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(INPUT_PATH);
             BufferedWriter bw = Files.newBufferedWriter(OUTPUT_PATH)) {

            // Read and ignore input header
            String inputHeader = br.readLine(); // may be null if file empty
            // Always write required output header
            bw.write(OUTPUT_HEADER);
            bw.newLine();

            if (inputHeader == null) {
                // Empty file (no header): output already has header; print summary and exit
                printSummary();
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip blank lines silently
                }
                rowsRead++;
                try {
                    String outLine = transformLine(line);
                    if (outLine != null) {
                        bw.write(outLine);
                        bw.newLine();
                        rowsTransformed++;
                    } else {
                        rowsSkipped++;
                    }
                } catch (Exception e) {
                    // Any parsing/logic error for this row -> skip and continue
                    System.err.println("Warning: skipping malformed line " + rowsRead + ": " + line);
                    rowsSkipped++;
                }
            }

            bw.flush();
            printSummary();

        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }

    /**
     * Transform a single CSV row (ProductID,Name,Price,Category) into the output CSV line
     * (ProductID,Name,Price,Category,PriceRange) following the specified transform order.
     *
     * Returns the output csv line or throws an exception if the row is malformed.
     */
    private String transformLine(String line) {
        // Split on comma; fields guaranteed to not contain commas/quotes per spec.
        String[] parts = line.split(",", -1);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Not enough columns");
        }

        String productId = parts[0].trim();
        String name = parts[1].trim();
        String priceStr = parts[2].trim();
        String category = parts[3].trim();
        String originalCategory = category; // used for electronics checks

        // 1) UPPERCASE name
        String transformedName = name.toUpperCase();

        // 2) Discount (if Electronics)
        BigDecimal price = new BigDecimal(priceStr);
        if ("Electronics".equalsIgnoreCase(originalCategory)) {
            price = price.multiply(new BigDecimal("0.9")); // 10% discount
        }
        // Rounding: two decimals, round half up
        price = price.setScale(2, RoundingMode.HALF_UP);

        // 3) Recategorization: if post-discount price > 500.00 and original category was Electronics
        if ("Electronics".equalsIgnoreCase(originalCategory)
                && price.compareTo(new BigDecimal("500.00")) > 0) {
            category = "Premium Electronics";
        }

        // 4) PriceRange from final price
        String priceRange = computePriceRange(price);

        // Build CSV output line
        // Ensure price printed with exactly two decimals (BigDecimal.toPlainString() works since we've setScale(2))
        return String.join(",", productId, transformedName, price.toPlainString(), category, priceRange);
    }

    /**
     * Price ranges:
     * 0.00–10.00   -> Low
     * 10.01–100.00 -> Medium
     * 100.01–500.00-> High
     * 500.01+      -> Premium
     *
     * Note: price is already rounded to 2 decimals by caller.
     */
    private String computePriceRange(BigDecimal price) {
        BigDecimal p = price;
        if (p.compareTo(new BigDecimal("0.00")) >= 0 && p.compareTo(new BigDecimal("10.00")) <= 0) {
            return "Low";
        }
        if (p.compareTo(new BigDecimal("10.01")) >= 0 && p.compareTo(new BigDecimal("100.00")) <= 0) {
            return "Medium";
        }
        if (p.compareTo(new BigDecimal("100.01")) >= 0 && p.compareTo(new BigDecimal("500.00")) <= 0) {
            return "High";
        }
        if (p.compareTo(new BigDecimal("500.01")) >= 0) {
            return "Premium";
        }
        // Edge cases (e.g., 10.005) shouldn't occur because we've rounded, but pick sensible default:
        if (p.compareTo(new BigDecimal("10.00")) > 0 && p.compareTo(new BigDecimal("100.00")) <= 0) {
            return "Medium";
        }
        return "Low";
    }

    private void printSummary() {
        System.out.println("Run summary:");
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Transformed: " + rowsTransformed);
        System.out.println("Skipped: " + rowsSkipped);
        System.out.println("Output written to: " + OUTPUT_PATH.toString());
    }
}
