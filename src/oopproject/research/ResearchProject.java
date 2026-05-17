package oopproject.research;

import oopproject.exceptions.ResearchException;
import oopproject.enums.ResearchProjectStatus;
import oopproject.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private String projectId;
    private String topic;
    private List<ResearchPaper> publishedPapers = new ArrayList<>();
    private List<Researcher> participants = new ArrayList<>();
    private ResearchProjectStatus status = ResearchProjectStatus.PLANNED;

    public ResearchProject() {
    }

    public ResearchProject(String topic) {
        this.topic = topic;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public void setPublishedPapers(List<ResearchPaper> publishedPapers) {
        this.publishedPapers = publishedPapers;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Researcher> participants) {
        this.participants = participants;
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null) {
            publishedPapers.add(paper);
        }
    }

    public void addParticipant(Researcher researcher) {
        if (researcher != null && !participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void addParticipant(User user) {
        String login = user == null ? "unknown" : user.getLogin();
        throw new ResearchException(topic, "user '" + login + "' is not decorated as a researcher");
    }

    public void removeParticipant(Researcher researcher) {
        participants.remove(researcher);
    }

    public ResearchProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ResearchProjectStatus status) {
        this.status = status;
    }

    public void printProjectInfo() {
        System.out.println(topic + " [" + status + "] participants=" + participants.size());
    }
}
