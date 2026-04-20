package oopproject.storage;

import oopproject.academic.Course;
import oopproject.users.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {
    private static final String STORAGE_FILE = "data.ser";
    private static final DataStorage INSTANCE = new DataStorage();

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        return INSTANCE;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void saveData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            outputStream.writeObject(users);
            outputStream.writeObject(courses);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save data", e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            users = (List<User>) inputStream.readObject();
            courses = (List<Course>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load data", e);
        }
    }
}
