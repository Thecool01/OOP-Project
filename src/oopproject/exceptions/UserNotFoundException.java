package oopproject.exceptions;

public class UserNotFoundException extends UniversitySystemException {
    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }
}
