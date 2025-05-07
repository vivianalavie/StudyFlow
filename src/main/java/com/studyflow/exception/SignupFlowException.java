package com.studyflow.exception;

public class SignupFlowException {

    public static class SignupError extends RuntimeException {
        public SignupError() {
            super("auth.signup.error_generic");
        }
    }

    public static class PasswordLeaked extends RuntimeException {
        public PasswordLeaked() {
            super("auth.password.leaked");
        }
    }

    public static class InvalidEmail extends RuntimeException {
        public InvalidEmail() {
            super("auth.invalid.email");
        }
    }

    public static class UserExists extends RuntimeException {
        public UserExists() {
            super("auth.signup.user_exists");
        }
    }

}