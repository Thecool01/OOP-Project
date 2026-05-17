package oopproject.services;

import oopproject.exceptions.LowHIndexSupervisorException;
import oopproject.research.ResearchPaper;
import oopproject.research.ResearchProject;
import oopproject.research.Researcher;
import oopproject.system.UniversitySystem;
import oopproject.users.Student;

import java.util.Comparator;
import java.util.Optional;

public class ResearchService {
    private final UniversitySystem system;

    public ResearchService(UniversitySystem system) {
        this.system = system;
    }

    public void addPaper(Researcher researcher, ResearchPaper paper) {
        if (researcher instanceof oopproject.research.ResearchProfile profile) {
            profile.addPaper(paper);
        }
    }

    public void joinProject(Researcher researcher, ResearchProject project) {
        if (project != null) {
            project.addParticipant(researcher);
        }
    }

    public void assignSupervisor(Student student, Researcher supervisor) {
        if (supervisor != null && supervisor.getHIndex() < 3) {
            throw new LowHIndexSupervisorException(String.valueOf(supervisor));
        }
        student.assignSupervisor(supervisor);
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        system.getResearchProjects().stream()
                .flatMap(project -> project.getPublishedPapers().stream())
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public Optional<Researcher> findTopCitedResearcher() {
        return system.getResearchProjects().stream()
                .flatMap(project -> project.getParticipants().stream())
                .distinct()
                .max(Comparator.comparingInt(Researcher::calculateTotalCitations));
    }
}
