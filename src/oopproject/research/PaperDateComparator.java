package oopproject.research;

import java.util.Comparator;
import java.util.Date;

public class PaperDateComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper first, ResearchPaper second) {
        Date firstDate = first.getPublicationDate();
        Date secondDate = second.getPublicationDate();
        if (firstDate == null && secondDate == null) {
            return 0;
        }
        if (firstDate == null) {
            return 1;
        }
        if (secondDate == null) {
            return -1;
        }
        return secondDate.compareTo(firstDate);
    }
}
