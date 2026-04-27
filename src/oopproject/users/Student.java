package oopproject.users;

import oopproject.academic.Course;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private int yearOfStudy;
    private double gpa;
    private int creditsEnrolled;
    private String major;
    private final List<Course> registeredCourses = new ArrayList<>();

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
        if (c != null && !registeredCourses.contains(c)) {
            registeredCourses.add(c);
            creditsEnrolled += c.getCredits();
        }
    }

    public void viewMarks() {
        System.out.println("Viewing marks is not implemented yet for " + getLogin());
    }
}
