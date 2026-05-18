package oopproject.users;

import oopproject.enums.AccountStatus;
import oopproject.enums.UserRole;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private UserRole role;
    private AccountStatus status = AccountStatus.ACTIVE;

    protected User() {
    }

    protected User(String id, String login, String password, String firstName, String lastName) {
        this.id = id;
        this.username = login;
        this.password = password;
        this.fullName = ((firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName)).trim();
    }

    public boolean login() {
        return username != null && !username.isBlank() && password != null && !password.isBlank()
                && status == AccountStatus.ACTIVE;
    }

    public void logout() {
    }

    public String viewProfile() {
        return toString();
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (Objects.equals(password, oldPassword) && newPassword != null && !newPassword.isBlank()) {
            password = newPassword;
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return username;
    }

    public void setLogin(String login) {
        this.username = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        if (fullName == null || fullName.isBlank()) {
            return "";
        }
        return fullName.split(" ", 2)[0];
    }

    public void setFirstName(String firstName) {
        String lastName = getLastName();
        this.fullName = ((firstName == null ? "" : firstName) + " " + lastName).trim();
    }

    public String getLastName() {
        if (fullName == null || !fullName.contains(" ")) {
            return "";
        }
        return fullName.substring(fullName.indexOf(' ') + 1);
    }

    public void setLastName(String lastName) {
        String firstName = getFirstName();
        this.fullName = (firstName + " " + (lastName == null ? "" : lastName)).trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        if (id != null && user.id != null) {
            return Objects.equals(id, user.id);
        }
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id != null ? id : username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
