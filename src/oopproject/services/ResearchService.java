package oopproject.services;

import oopproject.exceptions.LowHIndexSupervisorException;
import oopproject.exceptions.NotResearcherException;
import oopproject.exceptions.ResearchException;
import oopproject.research.ResearchPaper;
import oopproject.research.ResearchProject;
import oopproject.research.Researcher;
import oopproject.research.ResearchProfile;
import oopproject.research.ResearcherDecorator;
import oopproject.system.UniversitySystem;
import oopproject.users.Student;
import oopproject.users.Teacher;
import oopproject.users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ResearchService {
    private final UniversitySystem system;

    public ResearchService(UniversitySystem system) {
        this.system = system;
    }

    public void addPaper(Researcher researcher, ResearchPaper paper) {
        validateResearcher(researcher);
        validatePaper(paper);
        if (researcher instanceof ResearchProfile profile) {
            profile.addPaper(paper);
        } else if (researcher instanceof ResearcherDecorator decorator) {
            decorator.addPaper(paper);
        } else {
            researcher.getResearchPapers().add(paper);
        }
        system.addLog(researcherLogin(researcher), "research paper added: " + paper.getTitle());
    }

    public void joinProject(Researcher researcher, ResearchProject project) {
        validateResearcher(researcher);
        if (project == null) {
            throw new ResearchException("unknown", "project must not be null");
        }
        project.addParticipant(researcher);
        if (researcher instanceof ResearchProfile profile && !profile.getResearchProjects().contains(project)) {
            profile.addProject(project);
        } else if (researcher instanceof ResearcherDecorator decorator && !decorator.getResearchProjects().contains(project)) {
            decorator.addProject(project);
        }
        system.addLog(researcherLogin(researcher), "joined research project: " + project.getTopic());
    }

    public void assignSupervisor(Student student, Researcher supervisor) {
        if (student == null) {
            throw new ResearchException("research supervisor", "student must not be null");
        }
        if (supervisor != null && supervisor.getHIndex() < 3) {
            throw new LowHIndexSupervisorException(String.valueOf(supervisor));
        }
        student.assignSupervisor(supervisor);
        system.addLog(student.getLogin(), "research supervisor assigned: " + researcherLogin(supervisor));
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        collectUniversityPapers()
                .distinct()
                .sorted(resolveComparator(comparator))
                .forEach(System.out::println);
    }

    public Optional<Researcher> findTopCitedResearcher() {
        return collectUniversityResearchers()
                .distinct()
                .max(Comparator.comparingInt(Researcher::calculateTotalCitations));
    }

    private void validateResearcher(Researcher researcher) {
        if (researcher == null) {
            throw new NotResearcherException("unknown");
        }
    }

    private void validatePaper(ResearchPaper paper) {
        if (paper == null || paper.getPages() <= 0 || paper.getCitations() < 0) {
            throw new ResearchException("research paper", "paper must have positive pages and non-negative citations");
        }
    }

    private Stream<Researcher> collectUniversityResearchers() {
        Stream<Researcher> projectResearchers = system.getResearchProjects().stream()
                .flatMap(project -> project.getParticipants().stream());
        Stream<Researcher> userResearchers = system.getUsers().stream()
                .map(this::extractResearcher)
                .flatMap(Optional::stream);
        return Stream.concat(projectResearchers, userResearchers);
    }

    private Optional<Researcher> extractResearcher(User user) {
        if (user instanceof Student student) {
            return Optional.ofNullable(student.getResearchProfile());
        }
        if (user instanceof Teacher teacher) {
            return Optional.ofNullable(teacher.getResearchProfile());
        }
        if (user instanceof Researcher researcher) {
            return Optional.of(researcher);
        }
        return Optional.empty();
    }

    private String researcherLogin(Researcher researcher) {
        if (researcher instanceof ResearchProfile profile && profile.getOwner() != null) {
            return profile.getOwner().getLogin();
        }
        if (researcher instanceof ResearcherDecorator decorator && decorator.getUser() != null) {
            return decorator.getUser().getLogin();
        }
        if (researcher instanceof User user) {
            return user.getLogin();
        }
        return "research";
    }

    private Stream<ResearchPaper> collectUniversityPapers() {
        List<ResearchPaper> papers = new ArrayList<>();
        collectUniversityResearchers()
                .forEach(researcher -> papers.addAll(researcher.getResearchPapers()));
        system.getResearchProjects().stream()
                .flatMap(project -> project.getPublishedPapers().stream())
                .forEach(papers::add);
        return papers.stream();
    }

    private Comparator<ResearchPaper> resolveComparator(Comparator<ResearchPaper> comparator) {
        return comparator == null ? Comparator.naturalOrder() : comparator;
    }
}
