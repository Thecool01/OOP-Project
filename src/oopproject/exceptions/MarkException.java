package oopproject.exceptions;

public class MarkException extends UniversitySystemException {
    private final String studentId;
    private final String courseName;
    private final String reason;

    public MarkException(String studentId, String courseName, String reason) {
        super("Mark operation failed for student '" + studentId + "' and course '" + courseName + "': " + reason);
        this.studentId = studentId;
        this.courseName = courseName;
        this.reason = reason;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getReason() {
        return reason;
    }
}
