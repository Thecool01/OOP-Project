package oopproject.users;

import oopproject.storage.DataStorage;

import java.util.Date;

public class Admin extends Employee {
    public Admin() {
    }

    public Admin(String id, String login, String password, String firstName, String lastName,
                 double salary, Date hireDate) {
        super(id, login, password, firstName, lastName, salary, hireDate);
    }

    public void addUser(User user) {
        DataStorage.getInstance().getUsers().add(user);
    }

    public void removeUser(User user) {
        DataStorage.getInstance().getUsers().remove(user);
    }

    public void viewLogs() {
        System.out.println("Logs viewing is not implemented yet.");
    }
}
