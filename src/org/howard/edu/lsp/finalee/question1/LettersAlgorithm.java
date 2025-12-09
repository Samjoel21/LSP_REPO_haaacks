package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Letters-only algorithm: A-Z and a-z using SecureRandom.
 */
class LettersAlgorithm implements PasswordAlgorithm {
    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        int n = LETTERS.length();
        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(n);
            sb.append(LETTERS.charAt(idx));
        }
        return sb.toString();
    }
}
