package oopproject.academic;

import oopproject.enums.RegistrationStatus;
import oopproject.users.Manager;
import oopproject.users.Student;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class RegistrationRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String requestId;
    private Student student;
    private Course course;
    private RegistrationStatus status = RegistrationStatus.PENDING;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private Manager approvedBy;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String requestId, Student student, Course course) {
        this.requestId = requestId;
        this.student = student;
        this.course = course;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Manager getApprovedBy() {
        return approvedBy;
    }

    public void approve(Manager manager) {
        this.status = RegistrationStatus.APPROVED;
        this.approvedBy = manager;
    }

    public void reject(Manager manager) {
        this.status = RegistrationStatus.REJECTED;
        this.approvedBy = manager;
    }

    public boolean isPending() {
        return status == RegistrationStatus.PENDING;
    }

    public boolean isApproved() {
        return status == RegistrationStatus.APPROVED;
    }

    public boolean isRejected() {
        return status == RegistrationStatus.REJECTED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationRequest that)) return false;
        return requestId != null && Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return requestId == null ? System.identityHashCode(this) : Objects.hash(requestId);
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "requestId='" + requestId + '\'' +
                ", student=" + student +
                ", course=" + course +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
