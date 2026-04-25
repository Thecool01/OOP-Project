package oopproject.storage;

import oopproject.academic.Course;
import oopproject.users.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataStorage implements Serializable {
    private static final String STORAGE_FILE = "data.ser";
    private static DataStorage INSTANCE = new DataStorage();

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<String> actionLogs = new ArrayList<>();

    private DataStorage() {}

    public static DataStorage getInstance() {
        return INSTANCE;
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }

    public void addLog(String message) {
        actionLogs.add(new Date() + ": " + message);
    }

    public List<String> getActionLogs() {
        return actionLogs;
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(this); 
            addLog("System state saved to file.");
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    public static void loadData() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            INSTANCE = (DataStorage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load data: " + e.getMessage());
        }
    }
}