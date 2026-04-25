package oopproject;

import java.util.Scanner;
import oopproject.storage.DataStorage;
import oopproject.users.*;

public class Main {
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            DataStorage.getInstance().loadData();
        } catch (Exception e) {
            System.out.println("No existing data found. Starting fresh.");
            DataStorage.getInstance().getUsers().add(new Admin("A1", "admin", "admin123", "System", "Admin", 0, new java.util.Date()));
        }

        System.out.println("Welcome to University Management System!");

        // 2. Логин
        while (currentUser == null) {
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();

            for (User u : DataStorage.getInstance().getUsers()) {
                if (u.getLogin().equals(login) && u.getPassword().equals(pass)) {
                    currentUser = u;
                    DataStorage.getInstance().addLog("User " + login + " logged in.");
                    break;
                }
            }
            if (currentUser == null) System.out.println("Invalid credentials!");
        }

        runMenu();
    }

    private static void runMenu() {
        if (currentUser instanceof Admin) {
            showAdminMenu((Admin) currentUser);
        }
    }

    private static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n1. Add User\n2. View Logs\n3. Save & Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                System.out.println("Enter login for new student:");
                String login = scanner.nextLine();
                admin.addUser(new Student("S"+(int)(Math.random()*100), login, "123", "New", "Student", 1, 0, 0, "CS"));
                DataStorage.getInstance().addLog("Admin added user: " + login);
            } else if (choice == 2) {
                admin.viewLogs();
            } else if (choice == 3) {
                DataStorage.getInstance().saveData();
                break;
            }
        }
    }
}