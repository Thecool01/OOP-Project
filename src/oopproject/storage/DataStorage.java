package oopproject.storage;

import oopproject.academic.Course;
import oopproject.exceptions.StorageException;
import oopproject.research.ResearchPaper;
import oopproject.research.ResearchProject;
import oopproject.research.Researcher;
import oopproject.users.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DataStorage implements Serializable {
    private static final String STORAGE_FILE = "data.ser";
    private static final DataStorage INSTANCE = new DataStorage();

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<ResearchProject> researchProjects = new ArrayList<>();
    private List<LogEntry> actionLogs = new ArrayList<>();

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

    public List<ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    public void setResearchProjects(List<ResearchProject> researchProjects) {
        this.researchProjects = researchProjects;
    }

    public List<LogEntry> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<LogEntry> actionLogs) {
        this.actionLogs = actionLogs;
    }

    public void addUser(User user) {
        if (user != null && !users.contains(user)) {
            users.add(user);
            addLog(user.getLogin(), "user added");
        }
    }

    public void removeUser(User user) {
        if (user != null && users.remove(user)) {
            addLog(user.getLogin(), "user removed");
        }
    }

    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            addLog("system", "course added: " + course.getCourseName());
        }
    }

    public void addResearchProject(ResearchProject project) {
        if (project != null && !researchProjects.contains(project)) {
            researchProjects.add(project);
            addLog("system", "research project added: " + project.getTopic());
        }
    }

    public void addLog(String username, String action) {
        actionLogs.add(new LogEntry(username, action));
    }

    public User findUserById(String id) {
        return users.stream()
                .filter(user -> id != null && id.equals(user.getId()))
                .findFirst()
                .orElse(null);
    }

    public Course findCourseByName(String courseName) {
        return courses.stream()
                .filter(course -> courseName != null && courseName.equals(course.getCourseName()))
                .findFirst()
                .orElse(null);
    }

    public void printAllResearchPapers(Comparator<ResearchPaper> comparator) {
        researchProjects.stream()
                .flatMap(project -> project.getPublishedPapers().stream())
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public Optional<Researcher> findTopCitedResearcher() {
        return researchProjects.stream()
                .flatMap(project -> project.getParticipants().stream())
                .distinct()
                .max(Comparator.comparingInt(Researcher::getTotalCitations));
    }

    public void saveData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            outputStream.writeObject(users);
            outputStream.writeObject(courses);
            outputStream.writeObject(researchProjects);
            outputStream.writeObject(actionLogs);
        } catch (IOException e) {
            throw new StorageException(STORAGE_FILE, "failed to save data", e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            users = (List<User>) inputStream.readObject();
            courses = (List<Course>) inputStream.readObject();
            researchProjects = (List<ResearchProject>) inputStream.readObject();
            actionLogs = (List<LogEntry>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException(STORAGE_FILE, "failed to load data", e);
        }
    }
}
