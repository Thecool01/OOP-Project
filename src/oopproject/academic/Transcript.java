package oopproject.academic;

import oopproject.users.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        marks.forEach(System.out::println);
    }
}
