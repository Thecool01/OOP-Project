package oopproject.system;

import oopproject.academic.Course;
import oopproject.academic.RegistrationRequest;
import oopproject.research.ResearchProject;
import oopproject.storage.DataStore;
import oopproject.storage.FileDataStore;
import oopproject.storage.LogEntry;
import oopproject.teaching.News;
import oopproject.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UniversitySystem implements Serializable {
    private static final UniversitySystem INSTANCE = new UniversitySystem();

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<RegistrationRequest> registrationRequests = new ArrayList<>();
    private List<ResearchProject> researchProjects = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<LogEntry> logs = new ArrayList<>();
    private transient DataStore dataStore = new FileDataStore();

    private UniversitySystem() {
    }

    public static UniversitySystem getInstance() {
        return INSTANCE;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public List<ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    public List<News> getNews() {
        return news;
    }

    public List<LogEntry> getLogs() {
        return logs;
    }

    public DataStore getDataStore() {
        if (dataStore == null) {
            dataStore = new FileDataStore();
        }
        return dataStore;
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addUser(User user) {
        if (user != null && !users.contains(user)) {
            users.add(user);
            addLog(user.getLogin(), "user added");
        }
    }

    public void updateUser(User user) {
        if (user == null) {
            return;
        }
        removeUser(user);
        addUser(user);
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

    public void addRegistrationRequest(RegistrationRequest request) {
        if (request != null && !registrationRequests.contains(request)) {
            registrationRequests.add(request);
        }
    }

    public void addResearchProject(ResearchProject project) {
        if (project != null && !researchProjects.contains(project)) {
            researchProjects.add(project);
        }
    }

    public void addNews(News item) {
        if (item != null && !news.contains(item)) {
            news.add(item);
        }
    }

    public void addLog(String username, String action) {
        logs.add(new LogEntry(username, action));
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

    public void save() {
        getDataStore().save(this);
    }

    public UniversitySystem load() {
        return getDataStore().load();
    }
}
