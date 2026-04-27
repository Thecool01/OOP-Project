package oopproject.users;

import oopproject.academic.Course;
import oopproject.enums.ManagerType;
import oopproject.exceptions.RegistrationException;

import java.util.Date;

public class Manager extends Employee {
    private ManagerType type;

    public Manager() {
    }

    public Manager(String id, String login, String password, String firstName, String lastName,
                   double salary, Date hireDate, ManagerType type) {
        super(id, login, password, firstName, lastName, salary, hireDate);
        this.type = type;
    }

    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    public void approveRegistration() {
        System.out.println("Registration approved by manager " + getLogin());
    }

    public void approveRegistration(Student student, Course course) {
        if (student == null || course == null) {
            throw new RegistrationException(student == null ? null : student.getId(),
                    course == null ? null : course.getCourseName(),
                    "student and course must not be null");
        }
        student.registerForCourse(course);
        System.out.println("Registration approved by manager " + getLogin());
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            throw new RegistrationException(null,
                    course == null ? null : course.getCourseName(),
                    "course and teacher must not be null");
        }
        course.addInstructor(teacher);
    }
}
