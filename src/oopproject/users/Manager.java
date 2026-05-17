package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.RegistrationRequest;
import oopproject.enums.ManagerType;
import oopproject.enums.ReportType;
import oopproject.enums.UserRole;
import oopproject.exceptions.RegistrationException;
import oopproject.teaching.EmployeeRequest;
import oopproject.teaching.News;
import oopproject.teaching.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager extends Employee {
    private ManagerType type;
    private final List<Report> reports = new ArrayList<>();
    private final List<News> news = new ArrayList<>();
    private final List<RegistrationRequest> registrationRequests = new ArrayList<>();

    public Manager() {
        setRole(UserRole.MANAGER);
    }

    public Manager(String id,
                   String login,
                   String password,
                   String firstName,
                   String lastName,
                   double salary,
                   Date hireDate,
                   ManagerType type) {
        super(id, login, password, firstName, lastName, salary, hireDate);
        this.type = type;
        setRole(UserRole.MANAGER);
    }

    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    public void approveRegistration() {
        System.out.println("Registration approved by manager " + getLogin());
    }

    public void approveRegistration(Student student, Course course) {
        if (student == null || course == null) {
            throw new RegistrationException(student == null ? null : student.getId(),
                    course == null ? null : course.getCourseName(),
                    "student and course must not be null");
        }
        student.registerForCourse(course);
        System.out.println("Registration approved by manager " + getLogin());
    }

    public void approveRegistration(RegistrationRequest request) {
        if (request == null) {
            throw new RegistrationException(null, null, "registration request must not be null");
        }
        request.approve(this);
        request.getStudent().registerForCourse(request.getCourse());
    }

    public void rejectRegistration(RegistrationRequest request) {
        if (request != null) {
            request.reject(this);
        }
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            throw new RegistrationException(null,
                    course == null ? null : course.getCourseName(),
                    "course and teacher must not be null");
        }

        // Добавляем преподавателя в список instructors у Course
        course.addInstructor(teacher);

        // Добавляем курс в список курсов самого Teacher
        teacher.addCourse(course);

        System.out.println("Teacher " + teacher.getLogin()
                + " was assigned to course " + course.getCourseName());
    }

    public void assignTeacher(Course course, Teacher teacher) {
        assignCourseToTeacher(course, teacher);
    }

    public void addCourseForRegistration(Course course) {
        if (course != null) {
            course.setStatus(oopproject.enums.CourseStatus.OPEN_FOR_REGISTRATION);
        }
    }

    public Report createReport(ReportType type, String content) {
        Report report = new Report("REP-" + (reports.size() + 1), type, content);
        reports.add(report);
        return report;
    }

    public void manageNews(News item) {
        if (item != null) {
            news.add(item);
        }
    }

    public List<EmployeeRequest> viewEmployeeRequests() {
        return getRequests();
    }

    public List<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }
}