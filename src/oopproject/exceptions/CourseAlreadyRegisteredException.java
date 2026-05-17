package oopproject.exceptions;

public class CourseAlreadyRegisteredException extends RegistrationException {
    public CourseAlreadyRegisteredException(String studentId, String courseName) {
        super(studentId, courseName, "student already has this course");
    }
}
