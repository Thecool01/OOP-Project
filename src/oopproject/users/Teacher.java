package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.enums.TeacherTitle;
import oopproject.enums.UserRole;
import oopproject.exceptions.MarkException;
import oopproject.research.ResearchProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Teacher extends Employee {
    private String teacherId;
    private TeacherTitle title;
    private final List<Course> assignedCourses = new ArrayList<>();
    private ResearchProfile researchProfile;

    // Список курсов, которые ведет преподаватель
    private final List<Course> courses = new ArrayList<>();

    public Teacher() {
        setRole(UserRole.TEACHER);
    }

    public Teacher(String id, String login, String password, String firstName, String lastName,
                   double salary, Date hireDate, TeacherTitle title) {
        super(id, login, password, firstName, lastName, salary, hireDate);
        this.teacherId = id;
        this.title = title;
        setRole(UserRole.TEACHER);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
        setEmployeeId(teacherId);
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public boolean isProfessor() {
        return title == TeacherTitle.PROFESSOR;
    }

    public List<Course> viewAssignedCourses() {
        return assignedCourses;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void addAssignedCourse(Course course) {
        if (course != null && !assignedCourses.contains(course)) {
            assignedCourses.add(course);
        }
    }

    public List<Student> viewStudents(Course course) {
        return course == null ? List.of() : course.getStudents();
    }

    public void putMark(Student student, Mark mark) {
        if (student == null || mark == null) {
            throw new MarkException(student == null ? null : student.getId(), null, "student and mark must not be null");
        }
        System.out.println("Mark was assigned to student " + student.getLogin());
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (student == null || course == null || mark == null) {
            throw new MarkException(student == null ? null : student.getId(),
                    course == null ? null : course.getCourseName(),
                    "student, course and mark must not be null");
        }
        student.addMark(course, mark);
        System.out.println("Mark was assigned to student " + student.getLogin());
    }

    public void manageCourse(Course course) {
        addAssignedCourse(course);
    }

    public ResearchProfile getResearchProfile() {
        return researchProfile;
    }

    public void setResearchProfile(ResearchProfile researchProfile) {
        this.researchProfile = researchProfile;
    }

    public void sendComplaint() {
        System.out.println("Complaint was sent by teacher " + getLogin());
    }
}
