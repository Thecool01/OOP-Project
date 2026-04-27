package oopproject.users;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.enums.TeacherTitle;
import oopproject.exceptions.MarkException;

import java.util.Date;

public class Teacher extends Employee {
    private TeacherTitle title;

    public Teacher() {
    }

    public Teacher(String id, String login, String password, String firstName, String lastName,
                   double salary, Date hireDate, TeacherTitle title) {
        super(id, login, password, firstName, lastName, salary, hireDate);
        this.title = title;
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

    public void sendComplaint() {
        System.out.println("Complaint was sent by teacher " + getLogin());
    }
}
