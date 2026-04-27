package oopproject.exceptions;

public class AuthenticationException extends UniversitySystemException {
    private final String login;
    private final String reason;

    public AuthenticationException(String login, String reason) {
        super("Authentication failed for login '" + login + "': " + reason);
        this.login = login;
        this.reason = reason;
    }

    public String getLogin() {
        return login;
    }

    public String getReason() {
        return reason;
    }
}
