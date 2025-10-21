# Assignment 3 Reflection – Object-Oriented ETL Pipeline

## Overview
In Assignment 2, I built a single-file ETL pipeline (`ETLPipeline.java`) that read a CSV file, transformed the data, and wrote the output.  
In Assignment 3, I redesigned the program to follow an object-oriented structure.

## Design Changes from Assignment 2 to Assignment 3
- **Assignment 2:** All extract, transform, and load logic was inside one class and one main method.
- **Assignment 3:** I split the code into multiple classes:
  - `CSVExtractor` – handles reading `products.csv` and producing `Product` objects.
  - `Product` – represents a single product record.
  - `Transformer` – applies all required transformations (uppercase name, discount, recategorization, price range).
  - `CSVLoader` – writes transformed products to `data/transformed_products.csv`.
  - `ETLApp` – coordinates the pipeline (main method).

## How Assignment 3 Is More Object-Oriented
- **Encapsulation:** Each class hides its own implementation details. For example, `CSVExtractor` knows how to read CSV files but nothing about transformations.
- **Single Responsibility Principle:** Each class has one job.
- **Reusability:** I can reuse `Product` or `Transformer` independently.
- **Polymorphism/Inheritance (if used):** [Describe here if you created a base class or interface, e.g., `Extractor` interface with multiple implementations.]

## OO Concepts Used
- **Objects & Classes:** `Product` objects hold data; other classes operate on them.
- **Encapsulation:** Private fields with public getters/setters.
- **(Optional) Inheritance:** e.g., a base `AbstractExtractor` class.
- **Polymorphism:** e.g., multiple transformers could implement a common interface.

## Testing and Verification
- I ran the same input files (normal, empty, missing) as in Assignment 2.
- Verified that `data/transformed_products.csv` matched the golden output from Assignment 2.
- Checked error handling still works correctly.

## Lessons Learned
- Breaking the pipeline into classes made the code easier to read and test.
- Adding Javadocs to each class helped me clarify responsibilities.
- Using an AI assistant gave me ideas for class design, but I reviewed and edited everything myself.