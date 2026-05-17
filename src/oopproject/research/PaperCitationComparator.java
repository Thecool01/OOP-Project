package oopproject.research;

import java.util.Comparator;

public class PaperCitationComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper first, ResearchPaper second) {
        return Integer.compare(second.getCitations(), first.getCitations());
    }
}
