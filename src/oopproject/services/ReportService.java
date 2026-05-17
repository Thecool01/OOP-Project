package oopproject.services;

import oopproject.academic.Course;
import oopproject.academic.Mark;
import oopproject.enums.ReportType;
import oopproject.system.UniversitySystem;
import oopproject.teaching.Report;
import oopproject.users.Student;

public class ReportService {
    private final UniversitySystem system;

    public ReportService(UniversitySystem system) {
        this.system = system;
    }

    public Report generateAcademicReport(ReportType type) {
        return new Report("REP-" + System.currentTimeMillis(), type,
                "users=" + system.getUsers().size() + ", courses=" + system.getCourses().size());
    }

    public double calculateAverageGPA() {
        return system.getUsers().stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0);
    }

    public double calculateAverageMark(Course course) {
        return system.getUsers().stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .map(student -> student.getMarks().get(course))
                .filter(mark -> mark != null)
                .mapToDouble(Mark::calculateTotal)
                .average()
                .orElse(0);
    }
}
