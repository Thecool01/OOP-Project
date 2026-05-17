package oopproject.research;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    void printPapers(Comparator<ResearchPaper> comparator);

    List<ResearchPaper> getResearchPapers();

    List<ResearchProject> getResearchProjects();

    int getHIndex();

    int calculateHIndex();

    default List<ResearchPaper> getPapers() {
        return getResearchPapers();
    }

    default int getTotalCitations() {
        return getResearchPapers().stream()
                .mapToInt(ResearchPaper::getCitations)
                .sum();
    }

    default int calculateTotalCitations() {
        return getTotalCitations();
    }
}
