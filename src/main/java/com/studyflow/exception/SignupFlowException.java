package com.studyflow.exception;

public class SignupFlowException extends RuntimeException {
    private final String errorCode;

    public SignupFlowException(String errorCode) {
        super(errorCode); // Store the code as the exception message
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    // Password stuff
    public static class PasswordLeaked extends SignupFlowException {
        public PasswordLeaked() {
            super("auth.password.leaked");
        }
    }

    public static class PasswordTooShort extends SignupFlowException {
        public PasswordTooShort() {
            super("auth.password.short");
        }
    }

    public static class InvalidEmail extends SignupFlowException {
        public InvalidEmail() {
            super("auth.invalid.email");
        }
    }

    public static class UserExists extends SignupFlowException {
        public UserExists() {
            super("auth.signup.user_exists");
        }
    }

    public static class SignupError extends SignupFlowException {
        public SignupError() {
            super("auth.signup.error_generic");
        }
    }
}