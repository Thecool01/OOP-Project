package oopproject.exceptions;

public class NotResearcherException extends ResearchException {
    public NotResearcherException(String userName) {
        super("research project", "user '" + userName + "' is not a researcher");
    }
}
