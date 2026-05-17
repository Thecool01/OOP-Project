package oopproject.exceptions;

public class RegistrationNotFoundException extends RegistrationException {
    public RegistrationNotFoundException(String studentId, String courseName) {
        super(studentId, courseName, "registration request not found");
    }
}