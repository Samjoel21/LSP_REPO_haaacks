Project Info

Assignment: CSCI 363/540 – Assignment 1: CSV ETL Pipeline in Java
Student: Sam-Joel Blankson
File Structure:

src/org/howard/edu/lsp/assignment2/ETLPipeline.java
data/products.csv
data/transformed_products.csv

Assumptions

Input CSV has no commas or quotes inside fields.

Categories are case-insensitive when checking for "Electronics".

Price values are valid decimals. Malformed rows are skipped.

Output always includes a header row, even if input is empty.

Design Notes

Decomposition: Code is split into extract (read file), transformLine (apply transformations), and load (write output).

Transform order: Uppercase name → discount → recategorize → compute PriceRange.

Rounding: Used BigDecimal.setScale(2, RoundingMode.HALF_UP).

Error Handling:

Missing input file → print error and exit.

Empty input → output file with just header.

Malformed rows → skipped, with warning.

Relative Paths: All file I/O uses Paths.get("data", "filename"), so program works when run from project root.

Testing

Case A (normal input): Verified transformed_products.csv matches provided golden output.

Case B (empty input): Produced only header row in output.

Case C (missing input): Printed clear error message, no crash.

Edge cases: Tested prices 10.00, 10.01, 100.00, 100.01, 500.00, 500.01 → price ranges assigned correctly.

AI Usage

I used ChatGPT to help with code structure and README drafting.

Prompt : “Help me write a Java code to implement an ETL pipeline that reads a CSV, applies transformations, and writes output.”

Response excerpt considered: ChatGPT suggested using BigDecimal.setScale(2, RoundingMode.HALF_UP) for rounding.

How I used it: Adopted that rounding technique in my program after testing with sample values.

External Sources

Oracle Java Docs: BigDecimal.setScale
 – used to confirm rounding syntax.

No other external code was copied.