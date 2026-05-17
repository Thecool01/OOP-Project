package oopproject.academic;

import oopproject.enums.CourseStatus;
import oopproject.users.Student;
import oopproject.users.Teacher;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.List;

public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String courseId;
    private String title;
    private String description;
    private int credits;
    private String major;
    private int year;
    private CourseStatus status = CourseStatus.OPEN_FOR_REGISTRATION;
    private final List<Teacher> instructors = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Lesson> lessons = new ArrayList<>();

    public Course() {
    }

    public Course(String courseName, int credits) {
        this.courseId = courseName;
        this.title = courseName;
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return title;
    }

    public void setCourseName(String courseName) {
        this.title = courseName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public List<Teacher> getInstructors() {
        return Collections.unmodifiableList(instructors);
    }

    public void setInstructors(List<Teacher> instructors) {
        this.instructors.clear();
        if (instructors != null) {
            this.instructors.addAll(instructors);
        }
    }

    public void addInstructor(Teacher teacher) {
        if (teacher != null && !instructors.contains(teacher)) {
            instructors.add(teacher);
            teacher.addAssignedCourse(this);
        }
    }

    public void removeInstructor(Teacher teacher) {
        instructors.remove(teacher);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public int getTotalStudents() {
        return students.size();
    }

    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    public boolean hasInstructor(Teacher teacher) {
        return instructors.contains(teacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course course)) {
            return false;
        }
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", instructors=" + instructors.size() +
                ", students=" + students.size() +
                '}';
    }
}
