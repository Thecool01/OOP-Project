package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Student extends User {
    private int yearOfStudy;
    private double gpa;
    private int creditsEnrolled;
    private String major;
    private final List<Course> registeredCourses = new ArrayList<>();
    //Хранение оценок: ключ = курс, значение оценка по курсу
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
        if (c != null && !registeredCourses.contains(c)) {
            registeredCourses.add(c);
            creditsEnrolled += c.getCredits();
        }
    }

    // Добавление оценки студенту по конкретному курсу
    public void AddMark(Course course, Mark mark){
        if (course == null || mark == null){
            throw new IllegalArgumentException("Course and mark must not be null");
        }

        if (!registeredCourses.contains(course)) {
            throw new IllegalArgumentException("Student is not registered for this course");
        }

        marks.put(course, mark);
        }
    // Получить оценку по одному курсу
    public Mark getMark(Course course) {
        return marks.get(course);
    }

    // Получить все оценки
    public Map<Course, Mark> getMarks() {
        return marks;
    }

    // Просмотр всех оценок
    public void viewMarks() {
        if (marks.isEmpty()) {
            System.out.println("No marks yet for " + getLogin()); //getLogin можно убрать
            return;
        }

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            System.out.println(entry.getKey().getCourseName() + ": " + entry.getValue().getTotal());
        }
    }
}
