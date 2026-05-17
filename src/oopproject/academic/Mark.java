package oopproject.academic;

import oopproject.exceptions.InvalidMarkException;
import oopproject.users.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Mark implements Serializable, Comparable<Mark> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String markId;
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private Course course;
    private Student student;

    public Mark() {
    }

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        setFirstAttestation(firstAttestation);
        setSecondAttestation(secondAttestation);
        setFinalExam(finalExam);
    }

    public Mark(String markId, Student student, Course course, double firstAttestation,
                double secondAttestation, double finalExam) {
        this.markId = markId;
        this.student = student;
        this.course = course;
        setFirstAttestation(firstAttestation);
        setSecondAttestation(secondAttestation);
        setFinalExam(finalExam);
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public void setFirstAttestation(double firstAttestation) {
        validateScore(firstAttestation);
        this.firstAttestation = firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public void setSecondAttestation(double secondAttestation) {
        validateScore(secondAttestation);
        this.secondAttestation = secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        validateScore(finalExam);
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return calculateTotal();
    }

    public double calculateTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public boolean isPassed() {
        return calculateTotal() >= 50;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    private void validateScore(double score) {
        if (score < 0) {
            throw new InvalidMarkException(
                    student != null ? student.getStudentId() : "unknown",
                    course != null ? course.getTitle() : "unknown",
                    "Score cannot be negative"
            );
        }
    }

    @Override
    public int compareTo(Mark other) {
        return Double.compare(calculateTotal(), other == null ? 0 : other.calculateTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark mark)) return false;

        if (markId == null || mark.markId == null) {
            return false;
        }

        return Objects.equals(markId, mark.markId);
    }

    @Override
    public int hashCode() {
        return markId == null ? System.identityHashCode(this) : Objects.hash(markId);
    }

    @Override
    public String toString() {
        return "Mark{" +
                "markId='" + markId + '\'' +
                ", firstAttestation=" + firstAttestation +
                ", secondAttestation=" + secondAttestation +
                ", finalExam=" + finalExam +
                ", total=" + calculateTotal() +
                ", passed=" + isPassed() +
                '}';
    }
}
