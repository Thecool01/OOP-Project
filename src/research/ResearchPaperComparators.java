package research;
import java.util.Comparator;

public class ResearchPaperComparators {
    public static Comparator<ResearchPaper> byCitations = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p2.getCitations(), p1.getCitations());
        }
    };

    public static Comparator<ResearchPaper> byDate = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return p1.getDatePublished().compareTo(p2.getDatePublished());
        }
    };

    public static Comparator<ResearchPaper> byPages = new Comparator<ResearchPaper>() {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p1.getPages(), p2.getPages());
        }
    };
}
