package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.academic.RegistrationRequest;
import oopproject.academic.Transcript;
import oopproject.enums.UserRole;
import oopproject.exceptions.RegistrationException;
import oopproject.research.ResearchProfile;
import oopproject.research.Researcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private static final int MAX_CREDITS = 21;

    private String studentId;
    private int yearOfStudy;
    private double gpa;
    private int creditsEnrolled;
    private String major;
    private Transcript transcript = new Transcript(this);
    private ResearchProfile researchProfile;
    private Researcher supervisor;
    private final List<Course> registeredCourses = new ArrayList<>();
    private final Map<Course, Mark> marks = new HashMap<>();

    public Student() {
        setRole(UserRole.STUDENT);
    }

    public Student(String id, String login, String password, String firstName, String lastName,
                   int yearOfStudy, double gpa, int creditsEnrolled, String major) {
        super(id, login, password, firstName, lastName);
        this.studentId = id;
        this.yearOfStudy = yearOfStudy;
        this.gpa = gpa;
        this.creditsEnrolled = creditsEnrolled;
        this.major = major;
        setRole(UserRole.STUDENT);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        setId(studentId);
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

    public List<Course> viewCourses(List<Course> availableCourses) {
        return availableCourses == null ? List.of() : availableCourses;
    }

    public RegistrationRequest registerForCourse(Course c) {
        validateCourseRegistration(c);
        return new RegistrationRequest(getId() + "_" + c.getCourseId(), this, c);
    }

    public void enrollInCourse(Course c) {
        validateCourseRegistration(c);
        registeredCourses.add(c);
        c.addStudent(this);
        creditsEnrolled += c.getCredits();
    }

    private void validateCourseRegistration(Course c) {
        if (c == null) {
            throw new RegistrationException(getId(), null, "course is null");
        }
        if (registeredCourses.contains(c)) {
            throw new RegistrationException(getId(), c.getCourseName(), "student is already registered for this course");
        }
        if (creditsEnrolled + c.getCredits() > MAX_CREDITS) {
            throw new RegistrationException(getId(), c.getCourseName(), "student cannot register for more than 21 credits");
        }
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
        transcript.addMark(mark);
    }

    public void viewMarks() {
        marks.forEach((course, mark) -> System.out.println(course.getCourseName() + ": " + mark.getTotal()));
    }

    public void viewTranscript() {
        transcript.printTranscript();
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public ResearchProfile getResearchProfile() {
        return researchProfile;
    }

    public void setResearchProfile(ResearchProfile researchProfile) {
        this.researchProfile = researchProfile;
    }

    public void assignSupervisor(Researcher supervisor) {
        setSupervisor(supervisor);
    }

    public void rateTeacher(Teacher teacher, int rating) {
        System.out.println("Teacher " + (teacher == null ? "unknown" : teacher.getLogin()) + " rated as " + rating);
    }
}
