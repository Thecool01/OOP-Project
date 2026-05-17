package oopproject.academic;

import oopproject.enums.RegistrationStatus;
import oopproject.users.Manager;
import oopproject.users.Student;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegistrationRequest implements Serializable {
    private String requestId;
    private Student student;
    private Course course;
    private RegistrationStatus status = RegistrationStatus.PENDING;
    private LocalDateTime createdAt = LocalDateTime.now();
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}
