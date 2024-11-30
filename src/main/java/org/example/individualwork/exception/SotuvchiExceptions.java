package org.example.individualwork.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class SotuvchiExceptions {


    @Getter
    public static class UsernameAlreadyTakenException extends RuntimeException {
        private final HttpStatus status = HttpStatus.CONFLICT;

        public UsernameAlreadyTakenException(String message) {
            super(message);
        }
    }

    @Getter
    public static class UnauthorizedException extends RuntimeException {
        private final HttpStatus status = HttpStatus.UNAUTHORIZED;

        public UnauthorizedException(String message) {
            super(message);
        }
    }

    @Getter
    public static class IllegalArgumentException extends RuntimeException {
        private final HttpStatus status = HttpStatus.BAD_REQUEST;

        public IllegalArgumentException(String message) {
            super(message);
        }
    }

    @Getter
    public static class UserNotFoundException extends RuntimeException {
        private final HttpStatus status = HttpStatus.NOT_FOUND;

        public UserNotFoundException(String message) {
            super(message);
        }
    }

    @Getter
    public static class AccountExpiredException extends RuntimeException {
        private final HttpStatus status = HttpStatus.FORBIDDEN;

        public AccountExpiredException(String message) {
            super(message);
        }
    }

    @Getter
    public static class InvalidUsernameOrPasswordException extends RuntimeException {
        private final HttpStatus status = HttpStatus.FORBIDDEN;

        public InvalidUsernameOrPasswordException(String message) {
            super(message);
        }
    }

    @Getter
    public static class Exception extends RuntimeException {
        private final HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

        public Exception(String message) {
            super(message);
        }

    }
}
