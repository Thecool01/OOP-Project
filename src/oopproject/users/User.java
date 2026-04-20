package oopproject.users;

import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    protected User() {
    }

    protected User(String id, String login, String password, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean login() {
        return login != null && !login.isBlank() && password != null && !password.isBlank();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id != null ? id : login);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
