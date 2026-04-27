package oopproject.app;

import oopproject.academic.Course;
import oopproject.enums.ManagerType;
import oopproject.enums.TeacherTitle;
import oopproject.storage.DataStorage;
import oopproject.users.Admin;
import oopproject.users.Manager;
import oopproject.users.Student;
import oopproject.users.Teacher;

import java.util.Date;

public class DemoDataLoader {
    public void loadDemoData(DataStorage storage) {
        Teacher professor = new Teacher("T-1", "professor", "pass", "Demo", "Professor",
                500000, new Date(), TeacherTitle.PROFESSOR);
        Manager manager = new Manager("M-1", "manager", "pass", "Demo", "Manager",
                450000, new Date(), ManagerType.OR);
        Admin admin = new Admin("A-1", "admin", "pass", "Demo", "Admin",
                400000, new Date());
        Student student = new Student("S-1", "student", "pass", "Demo", "Student",
                1, 3.5, 0, "CS");
        Course course = new Course("Object-Oriented Programming", 5);

        course.addInstructor(professor);
        storage.addUser(professor);
        storage.addUser(manager);
        storage.addUser(admin);
        storage.addUser(student);
        storage.addCourse(course);
        storage.addLog("system", "demo data loaded");
    }
}
