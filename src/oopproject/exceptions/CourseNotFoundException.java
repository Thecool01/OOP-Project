package oopproject.exceptions;

public class CourseNotFoundException extends RegistrationException {
    public CourseNotFoundException(String studentId, String courseName) {
        super(studentId, courseName, "course not found");
    }
}