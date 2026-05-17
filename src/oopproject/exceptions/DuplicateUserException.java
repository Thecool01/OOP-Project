package oopproject.exceptions;

public class DuplicateUserException extends UniversitySystemException {
    public DuplicateUserException(String userId) {
        super("User already exists: " + userId);
    }
}
