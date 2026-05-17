package oopproject.services;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.exceptions.InvalidMarkException;
import oopproject.users.Student;
import oopproject.users.Teacher;

public class MarkService {
    public void putMark(Teacher teacher, Student student, Course course, Mark mark) {
        validateMark(mark);
        if (mark != null) {
            mark.setStudent(student);
            mark.setCourse(course);
        }
        teacher.putMark(student, course, mark);
    }

    public boolean validateMark(Mark mark) {
        if (mark == null) {
            throw new InvalidMarkException(null, null, "mark must not be null");
        }
        if (mark.getFirstAttestation() < 0 || mark.getSecondAttestation() < 0 || mark.getFinalExam() < 0) {
            throw new InvalidMarkException(null, null, "mark parts must be non-negative");
        }
        if (mark.calculateTotal() > 100) {
            throw new InvalidMarkException(null, null, "total mark must be <= 100");
        }
        return true;
    }
}
