package oopproject.users;

import java.util.Date;
import oopproject.storage.DataStorage;

public class Admin extends Employee {
    public Admin() {}

    public Admin(String id, String login, String password, String firstName, String lastName,
                 double salary, Date hireDate) {
        super(id, login, password, firstName, lastName, salary, hireDate);
    }

    public void addUser(User user) {
        if (user != null) {
            DataStorage.getInstance().getUsers().add(user);
            DataStorage.getInstance().addLog("Admin " + getLogin() + " added user: " + user.getLogin());
        }
    }

    public void removeUser(User user) {
        if (user != null) {
            DataStorage.getInstance().getUsers().remove(user);
            DataStorage.getInstance().addLog("Admin " + getLogin() + " removed user: " + user.getLogin());
        }
    }

    public void viewLogs() {
        System.out.println("\n--- SYSTEM ACTION LOGS ---");
        if (DataStorage.getInstance().getActionLogs().isEmpty()) {
            System.out.println("No logs available.");
        } else {
            for (String log : DataStorage.getInstance().getActionLogs()) {
                System.out.println(log);
            }
        }
        System.out.println("--------------------------\n");
    }
}