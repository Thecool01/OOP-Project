package oopproject.academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import oopproject.enums.CourseStatus;
import oopproject.users.Student;
import oopproject.users.Teacher;

public class Course implements Serializable {
    private String courseName;
    private int credits;
    private List<Teacher> instructors = new ArrayList<>();

    public Course() {
    }

    public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.title = courseName;
        this.credits = credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Teacher> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Teacher> instructors) {
        this.instructors = instructors;
    }

    public void addInstructor(Teacher teacher) {
        if (teacher != null && !instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", instructors=" + instructors.size() +
                '}';
    }
}
