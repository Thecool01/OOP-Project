package oopproject.research;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    void printPapers(Comparator<ResearchPaper> comparator);

    List<ResearchPaper> getPapers();

    int calculateHIndex();

    default int getTotalCitations() {
        return getPapers().stream()
                .mapToInt(ResearchPaper::getCitations)
                .sum();
    }
}
