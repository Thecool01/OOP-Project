package oopproject.services;

import oopproject.academic.Course;
import oopproject.academic.RegistrationRequest;
import oopproject.exceptions.CourseAlreadyRegisteredException;
import oopproject.exceptions.CreditLimitExceededException;
import oopproject.system.UniversitySystem;
import oopproject.users.Manager;
import oopproject.users.Student;

public class CourseRegistrationService {
    private final UniversitySystem system;

    public CourseRegistrationService(UniversitySystem system) {
        this.system = system;
    }

    public RegistrationRequest register(Student student, Course course) {
        checkCreditLimit(student, course);
        if (student.getRegisteredCourses().contains(course)) {
            throw new CourseAlreadyRegisteredException(student.getId(), course.getCourseName());
        }
        RegistrationRequest request = new RegistrationRequest("REG-" + (system.getRegistrationRequests().size() + 1),
                student, course);
        system.addRegistrationRequest(request);
        return request;
    }

    public void approve(RegistrationRequest request, Manager manager) {
        if (request != null) {
            request.getStudent().enrollInCourse(request.getCourse());
            request.approve(manager);
        }
    }

    public void reject(RegistrationRequest request, Manager manager) {
        if (request != null) {
            request.reject(manager);
        }
    }

    public boolean checkCreditLimit(Student student, Course course) {
        if (student.getCreditsEnrolled() + course.getCredits() > 21) {
            throw new CreditLimitExceededException(student.getId(), course.getCourseName());
        }
        return true;
    }
}
