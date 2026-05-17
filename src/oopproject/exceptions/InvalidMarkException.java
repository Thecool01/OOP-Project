package oopproject.exceptions;

public class InvalidMarkException extends MarkException {
    public InvalidMarkException(String studentId, String courseName, String reason) {
        super(studentId, courseName, reason);
    }
}
