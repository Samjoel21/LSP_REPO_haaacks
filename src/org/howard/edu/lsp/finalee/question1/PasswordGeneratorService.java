package org.howard.edu.lsp.finale.question1;

/**
 * Singleton service that generates passwords using selectable algorithms.
 *
 * Design Patterns used:
 * 1) Singleton - to provide a single shared access point for the password generator service
 *    across the application (required: "Only one instance of the service may exist").
 * 2) Strategy - to encapsulate the password generation algorithms behind a common interface.
 *    This makes algorithms swappable at run time and allows extension without modifying client code.
 *
 * The combination of Singleton + Strategy fits the requirements: a single service object
 * exposes a runtime-selectable strategy for generating passwords and new strategies can be
 * added without modifying existing client code.
 */
public class PasswordGeneratorService {

    private static PasswordGeneratorService instance;

    private PasswordAlgorithm algorithm;
    private String algorithmName;

    private PasswordGeneratorService() {
        this.algorithm = null;
        this.algorithmName = null;
    }

    public static synchronized PasswordGeneratorService getInstance() {
        if (instance == null) {
            instance = new PasswordGeneratorService();
        } else {
            // reset algorithm state so each caller/test can start fresh
            instance.algorithm = null;
            instance.algorithmName = null;
        }
        return instance;
    }

    /**
     * Selects the algorithm to use for future password generation.
     *
     * Supported names (case-insensitive):
     * - "basic"    -> digits only, uses java.util.Random
     * - "enhanced" -> letters+digits, uses java.security.SecureRandom
     * - "letters"  -> letters only, uses SecureRandom
     *
     * @param name algorithm name
     * @throws IllegalArgumentException if name is null or not a supported algorithm
     */
    public synchronized void setAlgorithm(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Algorithm name cannot be null");
        }
        String key = name.trim().toLowerCase();
        switch (key) {
            case "basic":
                this.algorithm = new BasicAlgorithm();
                this.algorithmName = "basic";
                break;
            case "enhanced":
                this.algorithm = new EnhancedAlgorithm();
                this.algorithmName = "enhanced";
                break;
            case "letters":
                this.algorithm = new LettersAlgorithm();
                this.algorithmName = "letters";
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + name);
        }
    }

    /**
     * Generates a password of the specified length using the currently selected algorithm.
     *
     * @param length requested password length (must be non-negative)
     * @return generated password string
     * @throws IllegalStateException if no algorithm has been selected
     * @throws IllegalArgumentException if length is negative
     */
    public synchronized String generatePassword(int length) {
        if (this.algorithm == null) {
            throw new IllegalStateException("No algorithm selected");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative");
        }
        return this.algorithm.generate(length);
    }

    /**
     * For debugging/testing only.
     */
    String getSelectedAlgorithmName() {
        return this.algorithmName;
    }
}
