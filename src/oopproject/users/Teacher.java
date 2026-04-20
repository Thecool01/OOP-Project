package oopproject.users;

import oopproject.academic.Mark;
import oopproject.enums.TeacherTitle;

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

    public void putMark(Student s, Mark m) {
        if (s == null || m == null) {
            throw new IllegalArgumentException("Student and mark must not be null");
        }
        System.out.println("Mark was assigned to student " + s.getLogin());
    }


    // Метод для отправки жалобы
    public void sendComplaint() {
        System.out.println("Complaint was sent by teacher " + getLogin());
    }
}
