package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.exceptions.RegistrationException;
import oopproject.research.Researcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private static final int MAX_CREDITS = 21;

    private int yearOfStudy;
    private double gpa;
    private int creditsEnrolled;
    private String major;
    private Researcher supervisor;
    private final List<Course> registeredCourses = new ArrayList<>();
    private final Map<Course, Mark> marks = new HashMap<>();

    public Student() {
    }

    public Student(String id, String login, String password, String firstName, String lastName,
                   int yearOfStudy, double gpa, int creditsEnrolled, String major) {
        super(id, login, password, firstName, lastName);
        this.yearOfStudy = yearOfStudy;
        this.gpa = gpa;
        this.creditsEnrolled = creditsEnrolled;
        this.major = major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCreditsEnrolled() {
        return creditsEnrolled;
    }

    public void setCreditsEnrolled(int creditsEnrolled) {
        this.creditsEnrolled = creditsEnrolled;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerForCourse(Course c) {
        if (c == null) {
            throw new RegistrationException(getId(), null, "course is null");
        }
        if (registeredCourses.contains(c)) {
            return;
        }
        if (creditsEnrolled + c.getCredits() > MAX_CREDITS) {
            throw new RegistrationException(getId(), c.getCourseName(), "student cannot register for more than 21 credits");
        }
        registeredCourses.add(c);
        creditsEnrolled += c.getCredits();
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Researcher supervisor) {
        if (yearOfStudy >= 4 && supervisor != null && supervisor.calculateHIndex() < 3) {
            throw new RegistrationException(getId(), "research supervisor", "supervisor h-index must be at least 3");
        }
        this.supervisor = supervisor;
    }

    public Map<Course, Mark> getMarks() {
        return marks;
    }

    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
    }

    public void viewMarks() {
        marks.forEach((course, mark) -> System.out.println(course.getCourseName() + ": " + mark.getTotal()));
    }

    public void viewTranscript() {
        viewMarks();
    }
}
