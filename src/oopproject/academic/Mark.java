package oopproject.academic;

import oopproject.users.Student;

import java.io.Serializable;
import java.util.Objects;
import oopproject.users.Student;

public class Mark implements Serializable, Comparable<Mark> {
    private String markId;
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private Course course;
    private Student student;

    public Mark() {
    }

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    public Mark(String markId, Student student, Course course, double firstAttestation,
                double secondAttestation, double finalExam) {
        this.markId = markId;
        this.student = student;
        this.course = course;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
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
        this.firstAttestation = firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public void setSecondAttestation(double secondAttestation) {
        this.secondAttestation = secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
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

    @Override
    public int compareTo(Mark other) {
        return Double.compare(calculateTotal(), other == null ? 0 : other.calculateTotal());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark mark)) return false;
        return markId != null && Objects.equals(markId, mark.markId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markId);
    }

    @Override
    public String toString() {
        return "Mark{" +
            "student=" + (student == null ? "null" : student.getId()) +
            ", course=" + (course == null ? "null" : course.getCourseName()) +
            ", first=" + firstAttestation +
            ", second=" + secondAttestation +
            ", final=" + finalExam +
            ", total=" + calculateTotal() +
            '}';
    }
}
