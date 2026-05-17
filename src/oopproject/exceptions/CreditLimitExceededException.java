package oopproject.exceptions;

public class CreditLimitExceededException extends RegistrationException {
    public CreditLimitExceededException(String studentId, String courseName) {
        super(studentId, courseName, "credit limit exceeded");
    }
}
