package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Basic algorithm: digits only (0-9) using java.util.Random.
 */
class BasicAlgorithm implements PasswordAlgorithm {
    private static final String DIGITS = "0123456789";
    private final Random random = new Random();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        int n = DIGITS.length();
        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(n);
            sb.append(DIGITS.charAt(idx));
        }
        return sb.toString();
    }
}
