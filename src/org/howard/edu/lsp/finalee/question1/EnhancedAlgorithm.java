package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Enhanced algorithm: letters (A-Z,a-z) and digits using SecureRandom.
 */
class EnhancedAlgorithm implements PasswordAlgorithm {
    private static final String ALLOWED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        int n = ALLOWED.length();
        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(n);
            sb.append(ALLOWED.charAt(idx));
        }
        return sb.toString();
    }
}
