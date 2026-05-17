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
import oopproject.users.Student;
import oopproject.users.Teacher;
import oopproject.users.User;

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

    public void putMark(Teacher teacher, Student student, Course course, Mark mark) {
        markService.putMark(teacher, student, course, mark);
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
