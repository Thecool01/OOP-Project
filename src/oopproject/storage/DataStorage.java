package oopproject.storage;

import java.io.*;
import java.util.*;
import oopproject.academic.Course;
import oopproject.research.ResearchProject;
import oopproject.users.User;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static DataStorage instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<ResearchProject> researchProjects = new ArrayList<>();
    private List<String> logs = new ArrayList<>();

    private DataStorage() {}

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }


    public List<User> getUsers() { return users; }
    
    public User findUserByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst()
                .orElse(null);
    }

    public List<Course> getCourses() { return courses; }

    public List<ResearchProject> getResearchProjects() { return researchProjects; }


    public void addLog(String message) {
        String logEntry = new Date() + ": " + message;
        logs.add(logEntry);
        System.out.println("[LOG] " + logEntry);
    }

    public List<String> getLogs() { return logs; }


    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            oos.writeObject(this);
            addLog("System data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"))) {
            instance = (DataStorage) ois.readObject();
            System.out.println("Data loaded successfully.");
        }
    }
}