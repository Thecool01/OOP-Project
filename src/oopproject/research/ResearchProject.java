package oopproject.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private String topic;
    private List<ResearchPaper> publishedPapers = new ArrayList<>();
    private List<Researcher> participants = new ArrayList<>();

    public ResearchProject() {
    }

    public ResearchProject(String topic) {
        this.topic = topic;
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

    public void addParticipant(Researcher researcher) throws NonResearcherJoinProjectException {
        if (researcher == null) {
            throw new NonResearcherJoinProjectException("Only researchers can join!");
        }
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null) {
            publishedPapers.add(paper);
        }
    }
}
