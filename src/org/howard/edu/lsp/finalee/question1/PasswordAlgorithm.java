package org.howard.edu.lsp.finale.question1;

/**
 * Password generation strategy interface.
 */
interface PasswordAlgorithm {
    /**
     * Generate a password of the specified length.
     * @param length desired password length (>= 0)
     * @return generated password
     */
    String generate(int length);
}
