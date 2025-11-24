package edu.ccrm.util;

/**
 * Container of custom exceptions used across the project.
 */
public final class CustomExceptions {
    private CustomExceptions() { }

    // Checked exception for exceeding max credit limit
    public static class MaxCreditLimitExceededException extends Exception {
        public MaxCreditLimitExceededException(String message) {
            super(message);
        }
    }

    // Unchecked exception for duplicate enrollments
    public static class DuplicateEnrollmentException extends RuntimeException {
        public DuplicateEnrollmentException(String message) {
            super(message);
        }
    }
}
