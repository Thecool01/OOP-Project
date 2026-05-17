package oopproject.app;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import oopproject.academic.Course;
import oopproject.academic.Lesson;
import oopproject.academic.Mark;
import oopproject.academic.RegistrationRequest;
import oopproject.enums.LessonType;
import oopproject.enums.ManagerType;
import oopproject.enums.ResearchProjectStatus;
import oopproject.enums.TeacherTitle;
import oopproject.exceptions.UserNotFoundException;
import oopproject.facade.UniversityFacade;
import oopproject.research.ResearchPaper;
import oopproject.research.ResearchProfile;
import oopproject.research.ResearchProject;
import oopproject.services.MarkService;
import oopproject.system.UniversitySystem;
import oopproject.teaching.News;
import oopproject.users.Admin;
import oopproject.users.Employee;
import oopproject.users.Manager;
import oopproject.users.Student;
import oopproject.users.Teacher;
import oopproject.users.User;

public class DemoDataLoader {
    private final UniversitySystem system = UniversitySystem.getInstance();
    private final MarkService markService = new MarkService();

    public void loadDemoData(UniversityFacade facade) {
        seedUsers(facade);
        seedCourses(facade);
        seedAssignments();
        seedAcademicProgress();
        seedTeachingAndManagement();
        seedResearch();
        system.addLog("system", "demo data loaded");
    }

    private void seedUsers(UniversityFacade facade) {
        addUserIfMissing(facade, new Admin("A-1", "admin", "pass", "Dana", "Admin", 520000, new Date()));
        addUserIfMissing(facade, new Admin("A-2", "sysadmin", "pass", "Arman", "Sysadmin", 500000, new Date()));

        addUserIfMissing(facade, new Manager("M-1", "manager", "pass", "Aruzhan", "Manager", 470000, new Date(), ManagerType.OR));
        addUserIfMissing(facade, new Manager("M-2", "dean", "pass", "Nurlan", "Dean", 620000, new Date(), ManagerType.DEPARTMENT));

        addUserIfMissing(facade, new Teacher("T-1", "professor", "pass", "Aigerim", "Professor", 610000, new Date(), TeacherTitle.PROFESSOR));
        addUserIfMissing(facade, new Teacher("T-2", "lecturer", "pass", "Timur", "Lecturer", 430000, new Date(), TeacherTitle.LECTOR));
        addUserIfMissing(facade, new Teacher("T-3", "tutor", "pass", "Madina", "Tutor", 350000, new Date(), TeacherTitle.TUTOR));

        addUserIfMissing(facade, new Student("S-1", "student", "pass", "Demo", "Student", 1, 3.5, 0, "Computer Science"));
        addUserIfMissing(facade, new Student("S-2", "student2", "pass", "Alina", "Kim", 2, 3.7, 0, "Computer Science"));
        addUserIfMissing(facade, new Student("S-3", "student3", "pass", "Dias", "Omarov", 3, 3.2, 0, "Information Systems"));
        addUserIfMissing(facade, new Student("S-4", "senior", "pass", "Mira", "Sadykova", 4, 3.9, 0, "Artificial Intelligence"));
        addUserIfMissing(facade, new Student("S-5", "researcher", "pass", "Ilyas", "Researcher", 4, 3.8, 0, "Data Science"));
    }

    private void seedCourses(UniversityFacade facade) {
        addCourseIfMissing(facade, course("CS101", "Object-Oriented Programming", 5,
                "Classes, inheritance, polymorphism, patterns and console applications.", "Computer Science", 1,
                lesson("L-CS101-1", "Encapsulation and classes", LessonType.LECTURE, 1, "B-204"),
                lesson("L-CS101-2", "OOP practice lab", LessonType.PRACTICE, 3, "Lab-3")));
        addCourseIfMissing(facade, course("CS102", "Algorithms and Data Structures", 5,
                "Sorting, graphs, complexity and algorithmic thinking.", "Computer Science", 1,
                lesson("L-CS102-1", "Complexity analysis", LessonType.LECTURE, 2, "B-101"),
                lesson("L-CS102-2", "Graph traversal", LessonType.PRACTICE, 4, "Lab-2")));
        addCourseIfMissing(facade, course("DB201", "Database Systems", 5,
                "Relational design, SQL, transactions and storage.", "Information Systems", 2,
                lesson("L-DB201-1", "Relational model", LessonType.LECTURE, 1, "A-312"),
                lesson("L-DB201-2", "SQL workshop", LessonType.PRACTICE, 3, "Lab-1")));
        addCourseIfMissing(facade, course("SE202", "Software Engineering", 4,
                "Requirements, UML, testing, version control and team workflows.", "Software Engineering", 2,
                lesson("L-SE202-1", "UML and requirements", LessonType.LECTURE, 2, "A-201")));
        addCourseIfMissing(facade, course("AI301", "Artificial Intelligence", 6,
                "Search, machine learning basics and intelligent systems.", "Artificial Intelligence", 3,
                lesson("L-AI301-1", "Search algorithms", LessonType.LECTURE, 1, "C-404"),
                lesson("L-AI301-2", "Model evaluation", LessonType.PRACTICE, 4, "AI-Lab")));
        addCourseIfMissing(facade, course("RM401", "Research Methods", 3,
                "Research design, academic writing, citations and project planning.", "Data Science", 4,
                lesson("L-RM401-1", "Research questions", LessonType.LECTURE, 5, "R-12")));
    }

    private void seedAssignments() {
        assign("T-1", "CS101");
        assign("T-1", "AI301");
        assign("T-1", "RM401");
        assign("T-2", "CS102");
        assign("T-2", "DB201");
        assign("T-3", "SE202");
    }

    private void seedAcademicProgress() {
        Manager manager = manager("M-1");
        enroll("S-1", "CS101", manager);
        enroll("S-1", "CS102", manager);
        enroll("S-2", "CS101", manager);
        enroll("S-2", "DB201", manager);
        enroll("S-3", "DB201", manager);
        enroll("S-3", "SE202", manager);
        enroll("S-4", "AI301", manager);
        enroll("S-4", "RM401", manager);
        enroll("S-5", "AI301", manager);
        enroll("S-5", "RM401", manager);

        mark("T-1", "S-1", "CS101", 28, 30, 32);
        mark("T-2", "S-1", "CS102", 25, 27, 31);
        mark("T-1", "S-2", "CS101", 30, 29, 35);
        mark("T-2", "S-2", "DB201", 24, 26, 28);
        mark("T-2", "S-3", "DB201", 20, 25, 24);
        mark("T-3", "S-3", "SE202", 29, 30, 34);
        mark("T-1", "S-4", "AI301", 31, 30, 33);
        mark("T-1", "S-4", "RM401", 30, 30, 35);
    }

    private void seedTeachingAndManagement() {
        Manager manager = manager("M-1");
        Manager dean = manager("M-2");
        if (manager != null && system.getNews().isEmpty()) {
            addNews("NEWS-1", "Registration week is open", "Students can send course registration requests.", manager);
            addNews("NEWS-2", "Research seminar", "AI in Education seminar starts on Friday.", dean);
            addNews("NEWS-3", "Transcript check", "Students should verify marks before the end of week.", manager);
        }

        Teacher professor = teacher("T-1");
        Teacher lecturer = teacher("T-2");
        if (professor != null && manager != null && professor.getRequests().isEmpty()) {
            professor.sendMessage(manager, "Please approve additional lab equipment for AI301.");
            professor.sendComplaint("Need GPU lab access for AI301 practical classes.");
        }
        if (lecturer != null && dean != null && lecturer.getRequests().isEmpty()) {
            lecturer.sendMessage(dean, "DB201 students need a larger classroom.");
            lecturer.sendRequest("Move DB201 practice to Lab-1 permanently.");
        }
    }

    private void seedResearch() {
        Teacher professor = teacher("T-1");
        Teacher lecturer = teacher("T-2");
        Student senior = student("S-4");
        Student researcherStudent = student("S-5");
        if (professor == null || professor.getResearchProfile() == null) {
            return;
        }

        ResearchProfile professorProfile = professor.getResearchProfile();
        addPaper(professorProfile, "RP-1", "AI Tutors in Programming Education", "Aigerim Professor", "IEEE Access", 12, 45, "10.1000/ai-tutors");
        addPaper(professorProfile, "RP-2", "Learning Analytics for OOP Courses", "Aigerim Professor, Timur Lecturer", "ACM TOCE", 9, 25, "10.1000/oop-analytics");
        addPaper(professorProfile, "RP-3", "Adaptive Feedback in University Systems", "Aigerim Professor", "Springer Education", 11, 16, "10.1000/adaptive-feedback");
        addPaper(professorProfile, "RP-4", "Student Success Prediction", "Aigerim Professor", "IEEE EDUCON", 7, 8, "10.1000/success");

        if (lecturer != null && lecturer.getResearchProfile() == null) {
            ResearchProfile lecturerProfile = new ResearchProfile(lecturer);
            lecturer.setResearchProfile(lecturerProfile);
            addPaper(lecturerProfile, "RP-5", "SQL Practice Automation", "Timur Lecturer", "DB Journal", 6, 4, "10.1000/sql-practice");
        }

        if (researcherStudent != null && researcherStudent.getResearchProfile() == null) {
            ResearchProfile studentProfile = new ResearchProfile(researcherStudent);
            researcherStudent.setResearchProfile(studentProfile);
            addPaper(studentProfile, "RP-6", "Undergraduate Research Portfolios", "Ilyas Researcher", "Student Research Review", 5, 3, "10.1000/portfolio");
        }

        ResearchProject aiProject = project("PR-1", "AI in Education", ResearchProjectStatus.ACTIVE);
        ResearchProject storageProject = project("PR-2", "Reliable University Data Storage", ResearchProjectStatus.PLANNED);
        ResearchProject analyticsProject = project("PR-3", "Learning Analytics Dashboard", ResearchProjectStatus.ACTIVE);

        join(professorProfile, aiProject);
        join(professorProfile, analyticsProject);
        if (lecturer != null && lecturer.getResearchProfile() != null) {
            join(lecturer.getResearchProfile(), storageProject);
        }
        if (researcherStudent != null && researcherStudent.getResearchProfile() != null) {
            join(researcherStudent.getResearchProfile(), analyticsProject);
        }

        if (senior != null && senior.getSupervisor() == null) {
            senior.assignSupervisor(professorProfile);
        }
        if (researcherStudent != null && researcherStudent.getSupervisor() == null) {
            researcherStudent.assignSupervisor(professorProfile);
        }

        if (aiProject.getPublishedPapers().isEmpty()) {
            aiProject.addPaper(professorProfile.getMostCitedPaper());
        }
    }

    private Course course(String id, String title, int credits, String description, String major, int year, Lesson... lessons) {
        Course course = new Course(id, title, credits);
        course.setDescription(description);
        course.setMajor(major);
        course.setYear(year);
        for (Lesson lesson : lessons) {
            course.addLesson(lesson);
        }
        return course;
    }

    private Lesson lesson(String id, String topic, LessonType type, int daysFromNow, String room) {
        return new Lesson(id, topic, type, LocalDateTime.now().plusDays(daysFromNow), room);
    }

    private void addUserIfMissing(UniversityFacade facade, User user) {
        try {
            facade.findUserById(user.getId());
        } catch (UserNotFoundException e) {
            facade.addUser(user);
        }
    }

    private void addCourseIfMissing(UniversityFacade facade, Course course) {
        if (course(course.getCourseId()) == null) {
            facade.addCourse(course);
        }
    }

    private void assign(String teacherId, String courseId) {
        Teacher teacher = teacher(teacherId);
        Course course = course(courseId);
        if (teacher != null && course != null && !course.hasInstructor(teacher)) {
            course.addInstructor(teacher);
        }
    }

    private void enroll(String studentId, String courseId, Manager manager) {
        Student student = student(studentId);
        Course course = course(courseId);
        if (student == null || course == null || manager == null || student.getRegisteredCourses().contains(course)) {
            return;
        }
        RegistrationRequest request = new RegistrationRequest("REG-" + studentId + "-" + courseId, student, course);
        system.addRegistrationRequest(request);
        manager.approveRegistration(request);
    }

    private void mark(String teacherId, String studentId, String courseId, double first, double second, double exam) {
        Teacher teacher = teacher(teacherId);
        Student student = student(studentId);
        Course course = course(courseId);
        if (teacher == null || student == null || course == null || student.getMarks().containsKey(course)) {
            return;
        }
        Mark mark = new Mark("MK-" + studentId + "-" + courseId, student, course, first, second, exam);
        markService.validateMark(mark);
        student.addMark(course, mark);
    }

    private void addNews(String id, String title, String text, Manager author) {
        News news = new News(id, title, text, author);
        author.manageNews(news);
        system.addNews(news);
    }

    private void addPaper(ResearchProfile profile, String id, String title, String authors, String journal,
                          int pages, int citations, String doi) {
        if (profile.getResearchPapers().stream().anyMatch(paper -> id.equals(paper.getPaperId()))) {
            return;
        }
        ResearchPaper paper = new ResearchPaper(title, authors, journal, pages, new Date(), citations, doi);
        paper.setPaperId(id);
        paper.setPublisher("University Research Press");
        profile.addPaper(paper);
    }

    private ResearchProject project(String id, String topic, ResearchProjectStatus status) {
        ResearchProject existing = system.getResearchProjects().stream()
                .filter(project -> id.equals(project.getProjectId()))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            return existing;
        }
        ResearchProject project = new ResearchProject(topic);
        project.setProjectId(id);
        project.setStatus(status);
        system.addResearchProject(project);
        return project;
    }

    private void join(ResearchProfile profile, ResearchProject project) {
        if (profile != null && project != null && !project.containsResearcher(profile)) {
            profile.addProject(project);
        }
    }

    private Course course(String id) {
        return system.getCourses().stream()
                .filter(course -> id.equals(course.getCourseId()))
                .findFirst()
                .orElse(null);
    }

    private Student student(String id) {
        return system.findUserById(id) instanceof Student student ? student : null;
    }

    private Teacher teacher(String id) {
        return system.findUserById(id) instanceof Teacher teacher ? teacher : null;
    }

    private Manager manager(String id) {
        return system.findUserById(id) instanceof Manager manager ? manager : null;
    }
}
