package oopproject.academic;

import oopproject.users.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Transcript implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
        return Collections.unmodifiableList(marks);
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
        marks.forEach(System.out::println);
    }

    public Mark getMarkByCourse(Course course) {
        return marks.stream()
                .filter(mark -> Objects.equals(mark.getCourse(), course))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transcript that)) return false;
        return Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student);
    }

    @Override
    public String toString() {
        return "Transcript{" +
                "student=" + student +
                ", marksCount=" + marks.size() +
                ", GPA=" + calculateGPA() +
                '}';
    }
}
