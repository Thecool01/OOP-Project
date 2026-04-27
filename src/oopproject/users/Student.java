package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;

import java.util.*;

public class Student {

    private static final int MAX_CREDITS = 21;
    private static final int MAX_ALLOWED_FAILS = 3;

    private final String name;
    private final String id;
    private final int yearOfStudy;

    private final Map<Course, Mark> courseMarks;

    public Student(String name, String id) {
        this(name, id, 1);
    }

    public Student(String name, String id, int yearOfStudy) {
        this.name = name;
        this.id = id;
        this.yearOfStudy = yearOfStudy;
        this.courseMarks = new LinkedHashMap<>();
    }

    //registration

    public void registerForCourse(Course course)
            throws CreditLimitExceededException, AlreadyRegisteredException {

        if (courseMarks.containsKey(course)) {
            throw new AlreadyRegisteredException(
                "Already registered for \"" + course.getName() + "\".");
        }

        int projected = getTotalCredits() + course.getCredits();
        if (projected > MAX_CREDITS) {
            throw new CreditLimitExceededException(
                "Cannot register for \"" + course.getName() + "\": would reach "
                + projected + " credits (limit is " + MAX_CREDITS + "). "
                + "Current load: " + getTotalCredits() + " credits.");
        }

        courseMarks.put(course, null);
        course.addStudent(this);
        System.out.println(name + " registered for: " + course);
    }

    public void dropCourse(Course course) throws CourseNotFoundException {
        if (!courseMarks.containsKey(course)) {
            throw new CourseNotFoundException(
                "Cannot drop \"" + course.getName() + "\": not registered.");
        }
        courseMarks.remove(course);
        course.removeStudent(this);
        System.out.println(name + " dropped: " + course.getName());
    }

    //marks

    public void addMark(Course course, Mark mark) throws CourseNotFoundException {
        if (!courseMarks.containsKey(course)) {
            throw new CourseNotFoundException(
                name + " is not registered for \"" + course.getName() + "\".");
        }
        courseMarks.put(course, mark);
    }

    public Mark getMark(Course course) {
        return courseMarks.get(course);
    }

    public void viewMarks() {
        System.out.println("MARKS " + name);
        if (courseMarks.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        for (Map.Entry<Course, Mark> entry : courseMarks.entrySet()) {
            Mark m = entry.getValue();
            System.out.println(entry.getKey().getName() + ": " + (m != null ? m : "not graded yet"));
        }
    }

    //transcript

    public Transcript getTranscript() {
        Map<Course, Mark> graded = new LinkedHashMap<>();
        for (Map.Entry<Course, Mark> e : courseMarks.entrySet()) {
            if (e.getValue() != null) graded.put(e.getKey(), e.getValue());
        }
        return new Transcript(this, graded);
    }

    //statistics

    public int getTotalCredits() {
        return courseMarks.keySet().stream().mapToInt(Course::getCredits).sum();
    }

    public int getRemainingCredits() {
        return MAX_CREDITS - getTotalCredits();
    }

    public double getGPA() {
        double weightedSum = 0;
        int totalCredits = 0;
        for (Map.Entry<Course, Mark> e : courseMarks.entrySet()) {
            Mark m = e.getValue();
            if (m == null) continue;
            int credits = e.getKey().getCredits();
            weightedSum += m.getTotal() * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : weightedSum / totalCredits;
    }

    public long countFails() {
        return courseMarks.values().stream()
                .filter(m -> m != null && !m.isPassed()).count();
    }

    public boolean isAtRisk() {
        return countFails() >= MAX_ALLOWED_FAILS;
    }

    public void getStatistics() {
        long graded = courseMarks.values().stream().filter(Objects::nonNull).count();
        long passed = courseMarks.values().stream().filter(m -> m != null && m.isPassed()).count();

        System.out.println("STATISTICS " + name);
        System.out.println("Registered courses: " + courseMarks.size());
        System.out.println("Total credits: " + getTotalCredits());
        System.out.println("Remaining credits: " + getRemainingCredits());
        System.out.println("Graded: " + graded);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + countFails());
        System.out.printf("GPA: %.2f%n", getGPA());
        System.out.println("Academic risk: " + (isAtRisk() ? "YES" : "No"));
    }

    //getters

    public String getName()       { return name; }
    public String getId()         { return id; }
    public int getYearOfStudy()   { return yearOfStudy; }
    public List<Course> getCourses() { return new ArrayList<>(courseMarks.keySet()); }
    public Map<Course, Mark> getCourseMarks() { return Collections.unmodifiableMap(courseMarks); }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name=" + name + ", year=" + yearOfStudy
                + ", credits=" + getTotalCredits() + "/" + MAX_CREDITS + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student)) return false;
        return id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}