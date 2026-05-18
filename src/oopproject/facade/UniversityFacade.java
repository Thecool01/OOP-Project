package oopproject.facade;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.academic.RegistrationRequest;
import oopproject.auth.AuthService;
import oopproject.enums.ReportType;
import oopproject.research.ResearchPaper;
import oopproject.research.ResearchProject;
import oopproject.research.Researcher;
import oopproject.services.CourseRegistrationService;
import oopproject.services.MarkService;
import oopproject.services.ReportService;
import oopproject.services.ResearchService;
import oopproject.services.UserService;
import oopproject.system.UniversitySystem;
import oopproject.teaching.Report;
import oopproject.storage.LogEntry;
import oopproject.users.Student;
import oopproject.users.Teacher;
import oopproject.users.User;

import java.util.List;
import java.util.Optional;

public class UniversityFacade {
    private final UniversitySystem system;
    private final AuthService authService;
    private final UserService userService;
    private final CourseRegistrationService registrationService;
    private final MarkService markService;
    private final ResearchService researchService;
    private final ReportService reportService;

    public UniversityFacade() {
        this(UniversitySystem.getInstance());
    }

    public UniversityFacade(UniversitySystem system) {
        this.system = system;
        this.authService = new AuthService(system);
        this.userService = new UserService(system);
        this.registrationService = new CourseRegistrationService(system);
        this.markService = new MarkService();
        this.researchService = new ResearchService(system);
        this.reportService = new ReportService(system);
    }

    public Optional<User> login(String username, String password) {
        return authService.login(username, password);
    }

    public RegistrationRequest registerForCourse(Student student, Course course) {
        return registrationService.register(student, course);
    }

    public void addUser(User user) {
        userService.addUser(user);
    }

    public void removeUser(User user) {
        userService.removeUser(user);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public User findUserById(String id) {
        return userService.findUserById(id);
    }

    public void addCourse(Course course) {
        system.addCourse(course);
    }

    public List<LogEntry> getLogs() {
        return system.getLogs();
    }

    public void putMark(Teacher teacher, Student student, Course course, Mark mark) {
        markService.putMark(teacher, student, course, mark);
        system.addLog(teacher == null ? "system" : teacher.getLogin(),
                "mark saved for " + (student == null ? "unknown" : student.getLogin())
                        + " in " + (course == null ? "unknown course" : course.getCourseId()));
    }

    public void addResearchPaper(Researcher researcher, ResearchPaper paper) {
        researchService.addPaper(researcher, paper);
    }

    public Report createAcademicReport(ReportType type) {
        return reportService.generateAcademicReport(type);
    }

    public void joinResearchProject(Researcher researcher, ResearchProject project) {
        researchService.joinProject(researcher, project);
    }

    public void saveData() {
        system.save();
    }

    public UniversitySystem loadData() {
        return system.load();
    }

    public UserService getUserService() {
        return userService;
    }
}
