package com.example.talentbridge.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationResult {
        private boolean valid;
        private List<String> errors;

        public static ValidationResult success() {
            return new ValidationResult(true, new ArrayList<>());
        }

        public static ValidationResult failure(List<String> errors) {
            return new ValidationResult(false, errors);
        }
    }

    public static ValidationResult validate(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.length() < MIN_LENGTH) {
            errors.add("Mật khẩu phải có ít nhất 8 ký tự");
        }

        if (password == null || !UPPERCASE_PATTERN.matcher(password).find()) {
            errors.add("Mật khẩu phải có ít nhất 1 chữ hoa");
        }

        if (password == null || !NUMBER_PATTERN.matcher(password).find()) {
            errors.add("Mật khẩu phải có ít nhất 1 số");
        }

        if (errors.isEmpty()) {
            return ValidationResult.success();
        }

        return ValidationResult.failure(errors);
    }

    public static boolean validatePasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }

    public static boolean hasMinLength(String password) {
        return password != null && password.length() >= MIN_LENGTH;
    }

    public static boolean hasUppercase(String password) {
        return password != null && UPPERCASE_PATTERN.matcher(password).find();
    }

    public static boolean hasNumber(String password) {
        return password != null && NUMBER_PATTERN.matcher(password).find();
    }

    public static boolean isValid(String password) {
        return hasMinLength(password) && hasUppercase(password) && hasNumber(password);
    }
}
