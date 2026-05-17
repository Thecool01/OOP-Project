package oopproject.exceptions;

public class UniversitySystemException extends RuntimeException {
    public UniversitySystemException(String message) {
        super(message);
    }

    public UniversitySystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
