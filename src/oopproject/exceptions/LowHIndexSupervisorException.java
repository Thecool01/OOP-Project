package oopproject.exceptions;

public class LowHIndexSupervisorException extends ResearchException {
    public LowHIndexSupervisorException(String supervisorName) {
        super("research supervisor", "supervisor '" + supervisorName + "' must have h-index >= 3");
    }
}
