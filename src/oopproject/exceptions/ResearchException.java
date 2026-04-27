package oopproject.exceptions;

public class ResearchException extends UniversitySystemException {
    private final String projectTopic;
    private final String reason;

    public ResearchException(String projectTopic, String reason) {
        super("Research operation failed for project '" + projectTopic + "': " + reason);
        this.projectTopic = projectTopic;
        this.reason = reason;
    }

    public String getProjectTopic() {
        return projectTopic;
    }

    public String getReason() {
        return reason;
    }
}
