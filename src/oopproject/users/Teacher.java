package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.enums.TeacherTitle;
import oopproject.enums.UserRole;
import oopproject.exceptions.MarkException;
import oopproject.research.ResearchProfile;
import oopproject.teaching.EmployeeRequest;

import java.io.Serial;
import java.util.*;

public class Teacher extends Employee {
    @Serial
    private static final long serialVersionUID = 1L;

    private String teacherId;
    private TeacherTitle title;
    private final List<Course> assignedCourses = new ArrayList<>();
    private ResearchProfile researchProfile;

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
        return Collections.unmodifiableList(assignedCourses);
    }

    public List<Course> getAssignedCourses() {
        return Collections.unmodifiableList(assignedCourses);
    }

    public void addAssignedCourse(Course course) {
        if (course != null && !assignedCourses.contains(course)) {
            assignedCourses.add(course);
        }
    }

    public boolean isAssignedToCourse(Course course) {
        return assignedCourses.contains(course);
    }

    public List<Student> viewStudents(Course course) {
        if (course == null || !assignedCourses.contains(course)) {
            return Collections.emptyList();
        }

        return course.getStudents();
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (student == null || course == null || mark == null) {
            throw new MarkException(
                    student == null ? "unknown" : student.getId(),
                    course == null ? "unknown" : course.getTitle(),
                    "student, course and mark must not be null"
            );
        }

        if (!assignedCourses.contains(course)) {
            throw new MarkException(
                    student.getId(),
                    course.getTitle(),
                    "teacher is not assigned to this course"
            );
        }

        if (!course.hasStudent(student)) {
            throw new MarkException(
                    student.getId(),
                    course.getTitle(),
                    "student is not registered for this course"
            );
        }

        mark.setStudent(student);
        mark.setCourse(course);

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

    public EmployeeRequest sendComplaint(String text) {
        return sendRequest(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher teacher)) return false;
        return Objects.equals(teacherId, teacher.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", title=" + title +
                ", assignedCourses=" + assignedCourses.size() +
                '}';
    }
}
