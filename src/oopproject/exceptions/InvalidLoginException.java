package oopproject.exceptions;

public class InvalidLoginException extends AuthenticationException {
    public InvalidLoginException(String login) {
        super(login, "invalid login or password");
    }
}
