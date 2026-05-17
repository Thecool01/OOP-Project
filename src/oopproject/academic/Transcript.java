package oopproject.academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import oopproject.users.Student;

public class Transcript implements Serializable {
    private Student student;
    private final List<Mark> marks = new ArrayList<>();

    public Transcript() {
    }

    public Transcript(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void addMark(Mark mark) {
        if (mark != null && !marks.contains(mark)) {
            marks.add(mark);
        }
    }

    public double calculateGPA() {
        if (marks.isEmpty()) {
            return 0;
        }
        double average = marks.stream().mapToDouble(Mark::calculateTotal).average().orElse(0);
        return average / 100.0 * 4.0;
    }

    public void printTranscript() {
        System.out.println("Transcript for " + 
            (student == null ? "unknown" : student.getFirstName()));
        marks.forEach(mark -> System.out.println(
            mark.getCourse().getTitle() + ": " + mark.calculateTotal()
        ));
        System.out.println("GPA: " + String.format("%.2f", calculateGPA()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transcript t)) return false;
        return Objects.equals(student, t.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student);
    }

    @Override
    public String toString() {
        return "Transcript{" +
            "student=" + (student == null ? "null" : student.getId()) +
            ", marks=" + marks.size() +
            ", gpa=" + String.format("%.2f", calculateGPA()) +
            '}';
}
}
