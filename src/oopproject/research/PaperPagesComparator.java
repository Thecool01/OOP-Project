package oopproject.research;

import java.util.Comparator;

public class PaperPagesComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper first, ResearchPaper second) {
        return Integer.compare(second.getPages(), first.getPages());
    }
}
