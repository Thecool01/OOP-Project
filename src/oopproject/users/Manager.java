package oopproject.users;

import oopproject.academic.Course;
import oopproject.enums.ManagerType;
import oopproject.storage.DataStorage;

import java.util.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager extends Employee {
    private ManagerType type;

    public Manager() {
    }

    public Manager(String id,
                   String login,
                   String password,
                   String firstName,
                   String lastName,
                   double salary,
                   Date hireDate,
                   ManagerType type) {
        super(id, login, password, firstName, lastName, salary, hireDate);
        this.type = type;
    }

    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    // Manager назначает преподавателя на курс
    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            throw new IllegalArgumentException("Course and teacher must not be null");
        }

        // Добавляем преподавателя в список instructors у Course
        course.addInstructor(teacher);

        // Добавляем курс в список курсов самого Teacher
        teacher.addCourse(course);

        System.out.println("Teacher " + teacher.getLogin()
                + " was assigned to course " + course.getCourseName());
    }

    // Manager подтверждает регистрацию студента на курс
    public void approveRegistration(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course must not be null");
        }

        student.registerForCourse(course);

        System.out.println("Registration approved: "
                + student.getLogin() + " -> " + course.getCourseName());
    }

    // Получить всех студентов из DataStorage
    public List<Student> viewStudents() {
        List<Student> students = new ArrayList<>();

        for (User user : DataStorage.getInstance().getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }

        return students;
    }

    // Получить всех преподавателей из DataStorage
    public List<Teacher> viewTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        for (User user : DataStorage.getInstance().getUsers()) {
            if (user instanceof Teacher) {
                teachers.add((Teacher) user);
            }
        }

        return teachers;
    }

    // Студенты, зарегистрированные на конкретный курс
    public List<Student> viewStudentsByCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course must not be null");
        }

        List<Student> result = new ArrayList<>();

        for (Student student : viewStudents()) {
            if (student.getRegisteredCourses().contains(course)) {
                result.add(student);
            }
        }

        return result;
    }

    // Студенты по GPA: от высокого к низкому
    public List<Student> viewStudentsSortedByGpa() {
        List<Student> students = viewStudents();

        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());

        return students;
    }

    // Студенты по алфавиту: сначала фамилия, потом имя
    public List<Student> viewStudentsSortedAlphabetically() {
        List<Student> students = viewStudents();

        students.sort(
                Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName)
        );

        return students;
    }

    // Студенты конкретного курса по алфавиту
    public List<Student> viewStudentsByCourseSortedAlphabetically(Course course) {
        List<Student> students = viewStudentsByCourse(course);

        students.sort(
                Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName)
        );

        return students;
    }

    // Студенты конкретного курса по GPA
    public List<Student> viewStudentsByCourseSortedByGpa(Course course) {
        List<Student> students = viewStudentsByCourse(course);

        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());

        return students;
    }

    // Преподаватели по алфавиту: сначала фамилия, потом имя
    public List<Teacher> viewTeachersSortedAlphabetically() {
        List<Teacher> teachers = viewTeachers();

        teachers.sort(
                Comparator.comparing(Teacher::getLastName)
                        .thenComparing(Teacher::getFirstName)
        );

        return teachers;
    }
}