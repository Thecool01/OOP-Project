package oopproject.research;

import oopproject.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import oopproject.exceptions.ResearchException;

public class ResearchProfile implements Researcher, Serializable {
    private User owner;
    private int hIndex;
    private final List<ResearchPaper> papers = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public ResearchProfile() {
    }

    public ResearchProfile(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void addPaper(ResearchPaper paper) {
        if (paper == null) {
            return;
        }

        if (paper.getPages() <= 0 || paper.getCitations() < 0) {
            throw new ResearchException("Paper Error", "Invalid research paper data: check page count or citations!");
        }

        if (!papers.contains(paper)) {
            papers.add(paper);
            hIndex = calculateHIndex();
        }
    }

    public void addProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
            project.addParticipant(this);
        }
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return papers;
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return projects;
    }

    @Override
    public int getHIndex() {
        return hIndex;
    }

    public void setHIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    @Override
    public int calculateHIndex() {
        List<Integer> citations = papers.stream()
                .map(ResearchPaper::getCitations)
                .sorted(Comparator.reverseOrder())
                .toList();
        int calculated = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                calculated = i + 1;
            }
        }
        return calculated;
    }

    @Override
    public int calculateTotalCitations() {
        return Researcher.super.calculateTotalCitations();
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        papers.stream().sorted(comparator).forEach(System.out::println);
    }
}
