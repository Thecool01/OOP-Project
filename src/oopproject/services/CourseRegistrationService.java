package oopproject.services;

import oopproject.academic.Course;
import oopproject.academic.RegistrationRequest;
import oopproject.exceptions.CourseAlreadyRegisteredException;
import oopproject.exceptions.CreditLimitExceededException;
import oopproject.exceptions.RegistrationException;
import oopproject.system.UniversitySystem;
import oopproject.users.Manager;
import oopproject.users.Student;

public class CourseRegistrationService {
    private final UniversitySystem system;

    public CourseRegistrationService(UniversitySystem system) {
        this.system = system;
    }

    public RegistrationRequest register(Student student, Course course) {
        if (student == null || course == null) {
            throw new RegistrationException("unknown", "unknown", "student and course must not be null");
        }

        if (!student.canRegister(course)) {
            throw new RegistrationException(
                    student.getId(),
                    course.getCourseName(),
                    "student cannot register for this course"
            );
        }

        boolean hasPendingOrApprovedRequest = system.getRegistrationRequests().stream()
                .anyMatch(request -> request.getStudent().equals(student)
                        && request.getCourse().equals(course)
                        && (request.isPending() || request.isApproved()));

        if (hasPendingOrApprovedRequest) {
            throw new CourseAlreadyRegisteredException(student.getId(), course.getCourseName());
        }

        RegistrationRequest request = new RegistrationRequest("REG-" + (system.getRegistrationRequests().size() + 1),
                student, course);
        system.addRegistrationRequest(request);
        system.addLog(student.getLogin(), "registration request created for " + course.getCourseId());
        return request;
    }

    public void approve(RegistrationRequest request, Manager manager) {
        if (request != null) {
            request.getStudent().enrollInCourse(request.getCourse());
            request.approve(manager);
            system.addLog(manager == null ? "system" : manager.getLogin(),
                    "registration approved: " + request.getRequestId());
        }
    }

    public void reject(RegistrationRequest request, Manager manager) {
        if (request != null) {
            request.reject(manager);
            system.addLog(manager == null ? "system" : manager.getLogin(),
                    "registration rejected: " + request.getRequestId());
        }
    }

    public boolean checkCreditLimit(Student student, Course course) {
        if (student.getCreditsEnrolled() + course.getCredits() > 21) {
            throw new CreditLimitExceededException(student.getId(), course.getCourseName());
        }
        return true;
    }
}
