package oopproject.users;

import java.util.Date;
import oopproject.storage.DataStorage;

public class Admin extends Employee {
    
    public Admin(String id, String login, String password, String firstName, String lastName, double salary, Date hireDate) {
        super(id, login, password, firstName, lastName, salary, hireDate);
    }

    public void addUser(User user) {
        DataStorage.getInstance().getUsers().add(user);
        DataStorage.getInstance().addLog("Admin added new user: " + user.getLogin());
    }

    public void removeUser(User user) {
        if (DataStorage.getInstance().getUsers().remove(user)) {
            DataStorage.getInstance().addLog("Admin removed user: " + user.getLogin());
        }
    }

    public void updateUser(User user, String newFirstName, String newLastName, String newPassword) {
        if (user != null) {
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setPassword(newPassword);
            DataStorage.getInstance().addLog("Admin updated details for user: " + user.getLogin());
        }
    }

    public void viewLogs() {
        System.out.println("\n--- SYSTEM LOGS ---");
        for (String log : DataStorage.getInstance().getLogs()) {
            System.out.println(log);
        }
    }
}