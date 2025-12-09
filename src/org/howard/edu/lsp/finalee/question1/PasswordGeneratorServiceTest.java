package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite for PasswordGeneratorService (final exam).
 * All tests are deterministic in their checks (length and allowed character sets).
 */
public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        // getInstance resets the chosen algorithm to null (see implementation) so each test starts clean
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        assertNotNull(service, "getInstance() should not return null");
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        // same object instance (singleton)
        assertSame(service, second, "getInstance() must return the identical singleton instance");
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        // Ensure no algorithm is set (getInstance resets it in this implementation)
        assertThrows(IllegalStateException.class, () -> {
            PasswordGeneratorService s = PasswordGeneratorService.getInstance();
            s.generatePassword(8);
        }, "generatePassword() must throw IllegalStateException when algorithm not selected");
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);
        assertEquals(10, p.length(), "Password length must match requested length");
        assertTrue(p.matches("\\d{10}"), "Basic algorithm must generate digits only");
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);
        assertEquals(12, p.length());
        assertTrue(p.matches("[A-Za-z0-9]{12}"),
                   "Enhanced algorithm must generate letters and digits only");
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);
        assertEquals(8, p.length());
        assertTrue(p.matches("[A-Za-z]{8}"),
                   "Letters algorithm must generate letters only");
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);
        assertEquals(10, p1.length());
        assertTrue(p1.matches("\\d{10}"), "Basic output must be digits only");

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);
        assertEquals(10, p2.length());
        assertTrue(p2.matches("[A-Za-z]{10}"), "Letters output must be letters only");

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);
        assertEquals(10, p3.length());
        assertTrue(p3.matches("[A-Za-z0-9]{10}"),
                   "Enhanced output must be letters or digits");
    }
}
