package oopproject.research;

import oopproject.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResearcherDecorator implements Researcher, Serializable {
    private User user;
    private List<ResearchPaper> papers = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();

    public ResearcherDecorator() {
    }

    public ResearcherDecorator(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers;
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void setProjects(List<ResearchProject> projects) {
        this.projects = projects;
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null) {
            papers.add(paper);
        }
    }

    public void addProject(ResearchProject project) {
        if (project != null) {
            projects.add(project);
        }
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        papers.stream()
                .sorted(comparator)
                .forEach(System.out::println);
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
