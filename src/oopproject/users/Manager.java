package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.academic.Transcript;
import oopproject.academic.RegistrationRequest;
import oopproject.enums.CourseStatus;
import oopproject.enums.ManagerType;
import oopproject.enums.ReportType;
import oopproject.enums.UserRole;
import oopproject.exceptions.RegistrationException;
import oopproject.teaching.EmployeeRequest;
import oopproject.teaching.News;
import oopproject.teaching.Report;

import java.io.Serial;
import java.util.*;

public class Manager extends Employee {
    @Serial
    private static final long serialVersionUID = 1L;

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

    public void approveRegistration(RegistrationRequest request) {
        if (request == null) {
            throw new RegistrationException(null, null, "registration request must not be null");
        }

        if (!request.isPending()) {
            throw new RegistrationException(
                    request.getStudent() == null ? "unknown" : request.getStudent().getId(),
                    request.getCourse() == null ? "unknown" : request.getCourse().getCourseName(),
                    "registration request is already processed"
            );
        }

        if (!registrationRequests.contains(request)) {
            registrationRequests.add(request);
        }

        if (request.getStudent() != null && request.getCourse() != null) {
            request.getStudent().enrollInCourse(request.getCourse());
        }

        request.approve(this);
    }

    public void rejectRegistration(RegistrationRequest request) {
        if (request == null) {
            throw new RegistrationException(null, null, "registration request must not be null");
        }

        if (!request.isPending()) {
            throw new RegistrationException(
                    request.getStudent() == null ? "unknown" : request.getStudent().getId(),
                    request.getCourse() == null ? "unknown" : request.getCourse().getCourseName(),
                    "registration request is already processed"
            );
        }

        request.reject(this);

        if (!registrationRequests.contains(request)) {
            registrationRequests.add(request);
        }
    }

    public boolean checkTeacherAvailability(Teacher teacher, Course course) {
        return teacher != null
                && course != null
                && !course.hasInstructor(teacher)
                && !teacher.isAssignedToCourse(course);
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            throw new RegistrationException(null,
                    course == null ? null : course.getCourseName(),
                    "course and teacher must not be null");
        }

        if (!checkTeacherAvailability(teacher, course)) {
            throw new RegistrationException(
                    null,
                    course.getCourseName(),
                    "teacher is already assigned to this course"
            );
        }

        // Добавляем преподавателя в список instructors у Course
        course.addInstructor(teacher);

        System.out.println("Teacher " + teacher.getLogin()
                + " was assigned to course " + course.getCourseName());
    }

    public void assignTeacher(Course course, Teacher teacher) {
        assignCourseToTeacher(course, teacher);
    }

    public void addCourseForRegistration(Course course) {
        if (course == null) {
            throw new RegistrationException(
                    null,
                    "unknown",
                    "course must not be null"
            );
        }

        course.setStatus(CourseStatus.OPEN_FOR_REGISTRATION);
    }

    public Report createReport(List<Student> students) {
        if (students == null) {
            students = Collections.emptyList();
        }

        double averageGpa = students.stream()
                .map(Student::getTranscript)
                .filter(Objects::nonNull)
                .mapToDouble(Transcript::calculateGPA)
                .average()
                .orElse(0.0);

        long passedMarks = students.stream()
                .map(Student::getTranscript)
                .filter(Objects::nonNull)
                .flatMap(transcript -> transcript.getMarks().stream())
                .filter(Mark::isPassed)
                .count();

        long totalMarks = students.stream()
                .map(Student::getTranscript)
                .filter(Objects::nonNull)
                .mapToLong(transcript -> transcript.getMarks().size())
                .sum();

        String content = "Academic Report\n" +
                "Students count: " + students.size() + "\n" +
                "Average GPA: " + averageGpa + "\n" +
                "Passed marks: " + passedMarks + "/" + totalMarks;

        Report report = new Report("REP-" + (reports.size() + 1), ReportType.COURSE_STATISTICS, content);
        reports.add(report);
        return report;
    }

    public void manageNews(News item) {
        if (item != null && !news.contains(item)) {
            news.add(item);
        }
    }

    public List<EmployeeRequest> viewEmployeeRequests() {
        return getRequests();
    }

    public List<RegistrationRequest> getRegistrationRequests() {
        return Collections.unmodifiableList(registrationRequests);
    }
    public List<Report> getReports() {
        return Collections.unmodifiableList(reports);
    }

    public List<News> getNews() {
        return Collections.unmodifiableList(news);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager manager)) return false;
        return Objects.equals(getId(), manager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id='" + getId() + '\'' +
                ", type=" + type +
                ", reports=" + reports.size() +
                ", news=" + news.size() +
                '}';
    }
}
