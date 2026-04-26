package research;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject {
    private String topic;
    private List<ResearchPaper> publishedPapers = new ArrayList<>();
    private List<Researcher> participants = new ArrayList<>();

    public ResearchProject(String topic) {
        this.topic = topic;
    }

    public void addParticipant(Researcher researcher) throws NonResearcherJoinProjectException {
        if (researcher == null) {
            throw new NonResearcherJoinProjectException("Only researchers can join!");
        }
        participants.add(researcher);
    }

    public void addPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
    }

    public String getTopic() {
        return topic;
    }
}
