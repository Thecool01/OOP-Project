package oopproject.users;

import java.util.Date;
import oopproject.enums.UserRole;
import oopproject.system.UniversitySystem;

public class Admin extends Employee {
    public Admin() {
        setRole(UserRole.ADMIN);
    }

    public Admin(String id, String login, String password, String firstName, String lastName,
                 double salary, Date hireDate) {
        super(id, login, password, firstName, lastName, salary, hireDate);
        setRole(UserRole.ADMIN);
    }

    public void addUser(User user) {
        UniversitySystem.getInstance().addUser(user);
    }

    public void removeUser(User user) {
        UniversitySystem.getInstance().removeUser(user);
    }

    public void updateUser(User user) {
        UniversitySystem.getInstance().updateUser(user);
    }

    public void viewLogs() {
        UniversitySystem.getInstance().getLogs().forEach(System.out::println);
    }
}