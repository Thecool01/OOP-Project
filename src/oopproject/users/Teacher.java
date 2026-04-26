package oopproject.users;

import oopproject.academic.Mark;
import oopproject.enums.TeacherTitle;
import oopproject.storage.DataStorage;
import oopproject.academic.Course;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee {
    private TeacherTitle title;

    // Список курсов, которые ведет преподаватель
    private final List<Course> courses = new ArrayList<>();

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

    // Получить все курсы преподавателя
    public List<Course> getCourses() {
        return courses;
    }

    // Добавить курс преподавателю
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    // Поставить оценку студенту по конкретному курсу
    public void putMark(Student student, Course course, Mark mark) {

        if (student == null || course == null || mark == null) {
            throw new IllegalArgumentException("Student, course and mark must not be null");
        }

        // Проверка: ведет ли преподаватель этот курс
        if (!courses.contains(course)) {
            throw new IllegalArgumentException("Teacher does not teach this course");
        }

        // Проверка: зарегистрирован ли студент на курс
        if (!student.getRegisteredCourses().contains(course)) {
            throw new IllegalArgumentException("Student is not registered for this course");
        }

        // Сохраняем оценку студенту
        student.addMark(course, mark);

        System.out.println("Mark was assigned to student "
                + student.getLogin()
                + " for course "
                + course.getCourseName());
    }

    // Просмотр курсов преподавателя
    public void viewCourses() {

        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }

        for (Course course : courses) {
            System.out.println(course.getCourseName());
        }
    }

    // Просмотр студентов конкретного курса
    public void viewStudents(Course course) {

        if (course == null) {
            throw new IllegalArgumentException("Course must not be null");
        }

        System.out.println("Students for course: " + course.getCourseName());

        boolean found = false;

        // Получаем всех пользователей из DataStorage
        for (User user : DataStorage.getInstance().getUsers()) {

            // Проверяем, является ли пользователь студентом
            if (user instanceof Student) {

                Student student = (Student) user;

                // Проверяем, зарегистрирован ли студент на курс
                if (student.getRegisteredCourses().contains(course)) {
                    System.out.println(
                            student.getFirstName() + " " + student.getLastName()
                    );
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No students registered for this course.");
        }
    }


    // Метод для отправки жалобы
    public void sendComplaint() {
        System.out.println("Complaint was sent by teacher " + getLogin());
    }
}
