package oopproject.research;

import java.util.Comparator;

public interface Researcher {
    void printPapers(Comparator<ResearchPaper> c);
    int calculateHIndex();
}
