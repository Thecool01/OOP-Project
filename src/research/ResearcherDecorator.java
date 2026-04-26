package research;
import java.util.*;

public class ResearcherDecorator implements Researcher {
    private List<ResearchPaper> papers = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    public void addProject(ResearchProject project) {
        projects.add(project);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        papers.sort(c);
        for (ResearchPaper p : papers) {
            System.out.println(p);
        }
    }

    @Override
    public int calculateHIndex() {
        if (papers.isEmpty()) return 0;

        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper p : papers) {
            citations.add(p.getCitations());
        }

        citations.sort(Collections.reverseOrder());

        int hIndex = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                hIndex = i + 1;
            } else {
                break;
            }
        }
        return hIndex;
    }
}
