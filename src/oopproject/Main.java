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
            System.out.println("Starting with fresh database...");
            DataStorage.getInstance().getUsers().add(
                new Admin("A1", "admin", "admin123", "System", "Admin", 0, new java.util.Date())
            );
        }

        System.out.println("=== University Management System ===");

        while (currentUser == null) {
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();

            currentUser = AuthService.authenticate(login, pass);

            if (currentUser == null) {
                System.out.println("Invalid login or password. Try again.");
            }
        }

        System.out.println("Successfully logged in as " + currentUser.getFirstName());
        
        runMenu();
    }

    private static void runMenu() {
        if (currentUser instanceof Admin) {
            showAdminMenu((Admin) currentUser);
        } else if (currentUser instanceof Student) {
            System.out.println("Student menu coming soon...");
        }
    }

    private static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add User\n2. Update User\n3. View Logs\n4. Save & Exit");
            
            int choice = Integer.parseInt(scanner.nextLine());
            
            if (choice == 1) {
                System.out.print("New user login: ");
                String login = scanner.nextLine();
                admin.addUser(new Student("S"+(int)(Math.random()*100), login, "123", "New", "Student", 1, 0, 0, "CS"));
            } else if (choice == 2) {
                System.out.print("Enter login to update: ");
                String login = scanner.nextLine();
                User userToUpdate = DataStorage.getInstance().findUserByLogin(login);
                if (userToUpdate != null) {
                    admin.updateUser(userToUpdate, "UpdatedName", "UpdatedLast", "newpass123");
                    System.out.println("User updated!");
                }
            } else if (choice == 3) {
                admin.viewLogs();
            } else if (choice == 4) {
                DataStorage.getInstance().saveData();
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
    }
}