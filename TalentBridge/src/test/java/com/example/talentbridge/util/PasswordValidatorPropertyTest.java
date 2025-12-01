package com.example.talentbridge.util;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for PasswordValidator
 * Feature: forgot-password, Property 3: Password validation completeness
 * Validates: Requirements 3.1, 3.2, 3.3, 3.5
 */
class PasswordValidatorPropertyTest {

    /**
     * Feature: forgot-password, Property 3: Password validation completeness
     * For any password string, the validation function SHALL return failure if and only if
     * the password fails to meet at least one of: minimum 8 characters, at least one uppercase letter,
     * at least one number. Conversely, any password meeting all three requirements SHALL pass validation.
     */
    @Property(tries = 100)
    void validPasswordsShouldPassValidation(
            @ForAll @StringLength(min = 8, max = 50) String basePassword
    ) {
        // Generate a valid password by ensuring all requirements are met
        String validPassword = ensureValidPassword(basePassword);
        
        PasswordValidator.ValidationResult result = PasswordValidator.validate(validPassword);
        
        assertThat(result.isValid())
                .as("Password '%s' should be valid", validPassword)
                .isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    /**
     * Feature: forgot-password, Property 3: Password validation completeness
     * Passwords shorter than 8 characters should fail validation
     */
    @Property(tries = 100)
    void shortPasswordsShouldFailValidation(
            @ForAll @StringLength(min = 1, max = 7) String shortPassword
    ) {
        PasswordValidator.ValidationResult result = PasswordValidator.validate(shortPassword);
        
        assertThat(result.isValid()).isFalse();
        assertThat(PasswordValidator.hasMinLength(shortPassword)).isFalse();
    }

    /**
     * Feature: forgot-password, Property 3: Password validation completeness
     * Passwords without uppercase should fail validation
     */
    @Property(tries = 100)
    void passwordsWithoutUppercaseShouldFailValidation(
            @ForAll @StringLength(min = 8, max = 50) @LowerChars String lowercasePassword
    ) {
        // Add a number to isolate the uppercase test
        String passwordWithNumber = lowercasePassword + "1";
        
        PasswordValidator.ValidationResult result = PasswordValidator.validate(passwordWithNumber);
        
        assertThat(PasswordValidator.hasUppercase(passwordWithNumber)).isFalse();
        assertThat(result.isValid()).isFalse();
    }

    /**
     * Feature: forgot-password, Property 3: Password validation completeness
     * Passwords without numbers should fail validation
     */
    @Property(tries = 100)
    void passwordsWithoutNumbersShouldFailValidation(
            @ForAll @StringLength(min = 8, max = 50) @AlphaChars String alphaOnlyPassword
    ) {
        // Ensure at least one uppercase
        String passwordWithUppercase = alphaOnlyPassword + "A";
        
        // Remove any numbers that might have been generated
        String noNumbers = passwordWithUppercase.replaceAll("[0-9]", "a");
        if (noNumbers.length() < 8) {
            noNumbers = noNumbers + "aaaaaaaa".substring(0, 8 - noNumbers.length());
        }
        
        PasswordValidator.ValidationResult result = PasswordValidator.validate(noNumbers);
        
        assertThat(PasswordValidator.hasNumber(noNumbers)).isFalse();
        assertThat(result.isValid()).isFalse();
    }

    /**
     * Feature: forgot-password, Property 4: Password confirmation matching
     * For any two strings provided as password and confirmPassword, the validation SHALL fail
     * if and only if the strings are not identical.
     * Validates: Requirements 3.4
     */
    @Property(tries = 100)
    void identicalPasswordsShouldMatch(
            @ForAll @StringLength(min = 1, max = 100) String password
    ) {
        assertThat(PasswordValidator.validatePasswordMatch(password, password)).isTrue();
    }

    /**
     * Feature: forgot-password, Property 4: Password confirmation matching
     * Different passwords should not match
     */
    @Property(tries = 100)
    void differentPasswordsShouldNotMatch(
            @ForAll @StringLength(min = 1, max = 50) String password1,
            @ForAll @StringLength(min = 1, max = 50) String password2
    ) {
        Assume.that(!password1.equals(password2));
        
        assertThat(PasswordValidator.validatePasswordMatch(password1, password2)).isFalse();
    }

    /**
     * Feature: forgot-password, Property 3: Password validation completeness
     * isValid should return true if and only if all three checks pass
     */
    @Property(tries = 100)
    void isValidShouldBeConsistentWithIndividualChecks(
            @ForAll @StringLength(min = 0, max = 50) String password
    ) {
        boolean hasMinLength = PasswordValidator.hasMinLength(password);
        boolean hasUppercase = PasswordValidator.hasUppercase(password);
        boolean hasNumber = PasswordValidator.hasNumber(password);
        
        boolean expectedValid = hasMinLength && hasUppercase && hasNumber;
        
        assertThat(PasswordValidator.isValid(password)).isEqualTo(expectedValid);
    }

    private String ensureValidPassword(String base) {
        StringBuilder sb = new StringBuilder();
        
        // Ensure minimum length
        if (base.length() < 8) {
            sb.append("Abcd1234");
        } else {
            sb.append(base);
        }
        
        // Ensure uppercase
        if (!sb.toString().matches(".*[A-Z].*")) {
            sb.append("A");
        }
        
        // Ensure number
        if (!sb.toString().matches(".*[0-9].*")) {
            sb.append("1");
        }
        
        return sb.toString();
    }
}
