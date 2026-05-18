package oopproject.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import oopproject.academic.Course;
import oopproject.academic.Lesson;
import oopproject.academic.Mark;
import oopproject.academic.RegistrationRequest;
import oopproject.enums.AccountStatus;
import oopproject.enums.CourseStatus;
import oopproject.enums.ManagerType;
import oopproject.enums.ReportType;
import oopproject.enums.TeacherTitle;
import oopproject.enums.UserRole;
import oopproject.exceptions.UniversitySystemException;
import oopproject.facade.UniversityFacade;
import oopproject.factory.UserFactory;
import oopproject.research.PaperCitationComparator;
import oopproject.research.PaperDateComparator;
import oopproject.research.PaperPagesComparator;
import oopproject.research.ResearchPaper;
import oopproject.research.ResearchProfile;
import oopproject.research.ResearchProject;
import oopproject.research.Researcher;
import oopproject.services.CourseRegistrationService;
import oopproject.services.ResearchService;
import oopproject.storage.LogEntry;
import oopproject.system.UniversitySystem;
import oopproject.teaching.EmployeeRequest;
import oopproject.teaching.Message;
import oopproject.teaching.News;
import oopproject.teaching.Report;
import oopproject.users.Admin;
import oopproject.users.Employee;
import oopproject.users.Manager;
import oopproject.users.Student;
import oopproject.users.Teacher;
import oopproject.users.User;

public class Main {
    private static final String STORAGE_FILE = "university-system.ser";
    private static final int LOG_PAGE_SIZE = 8;

    private final Scanner scanner = new Scanner(System.in);
    private final UniversitySystem system = UniversitySystem.getInstance();
    private final UniversityFacade facade = new UniversityFacade(system);
    private final ResearchService researchService = new ResearchService(system);
    private final CourseRegistrationService registrationService = new CourseRegistrationService(system);
    private final UserFactory userFactory = new UserFactory();

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        loadExistingDataIfPresent();
        new DemoDataLoader().loadDemoData(facade);
        system.addLog("system", "application started");
        showStartupBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            switch (readInt()) {
                case 1 -> login();
                case 2 -> printDemoAccounts();
                case 3 -> printSystemSnapshot();
                case 4 -> printCourseCatalog();
                case 5 -> printNews();
                case 6 -> saveData();
                case 7 -> loadData();
                case 0 -> {
                    saveData();
                    running = false;
                    System.out.println("Session finished. Data saved to " + STORAGE_FILE + ".");
                }
                default -> warnUnknown();
            }
        }
    }

    private void printMainMenu() {
        printHeader("Research-Oriented University System");
        ConsoleUI.option(1, "Login");
        ConsoleUI.option(2, "Show demo accounts");
        ConsoleUI.option(3, "Show university dashboard");
        ConsoleUI.option(4, "Browse course catalog");
        ConsoleUI.option(5, "Read university news");
        ConsoleUI.option(6, "Save data");
        ConsoleUI.option(7, "Load data");
        ConsoleUI.exitOption("Exit");
        prompt();
    }

    private void login() {
        System.out.print("Login: ");
        String login = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        try {
            Optional<User> user = facade.login(login, password);
            user.ifPresent(this::showMenuForUser);
        } catch (RuntimeException e) {
            printError("Login failed", e);
        }
    }

    private void showMenuForUser(User user) {
        printSuccess("Logged in as " + user.getLogin() + " (" + user.getRole() + ")");
        if (user instanceof Admin admin) {
            showAdminMenu(admin);
        } else if (user instanceof Manager manager) {
            showManagerMenu(manager);
        } else if (user instanceof Teacher teacher) {
            showTeacherMenu(teacher);
        } else if (user instanceof Student student) {
            showStudentMenu(student);
        } else {
            System.out.println(user.viewProfile());
        }
    }

    private void showStudentMenu(Student student) {
        boolean back = false;
        while (!back) {
            printHeader("Student Workspace - " + student.getFullName());
            ConsoleUI.option(1, "View profile");
            ConsoleUI.option(2, "Browse courses");
            ConsoleUI.option(3, "My courses and schedule");
            ConsoleUI.option(4, "Register for a course");
            ConsoleUI.option(5, "Registration requests");
            ConsoleUI.option(6, "Marks and transcript");
            ConsoleUI.option(7, "Research workspace");
            ConsoleUI.option(8, "News");
            ConsoleUI.option(9, "Change password");
            ConsoleUI.exitOption("Logout");
            prompt();
            try {
                switch (readInt()) {
                    case 1 -> System.out.println(student.viewProfile());
                    case 2 -> printCourseCatalog();
                    case 3 -> printStudentCourses(student);
                    case 4 -> registerForSelectedCourse(student);
                    case 5 -> printStudentRequests(student);
                    case 6 -> {
                        student.viewTranscript();
                        pause();
                    }
                    case 7 -> showResearchMenu(student);
                    case 8 -> printNews();
                    case 9 -> changePassword(student);
                    case 0 -> back = true;
                    default -> warnUnknown();
                }
            } catch (RuntimeException e) {
                printError("Operation failed", e);
            }
        }
    }

    private void showTeacherMenu(Teacher teacher) {
        boolean back = false;
        while (!back) {
            printHeader("Teacher Workspace - " + teacher.getFullName());
            ConsoleUI.option(1, "View profile");
            ConsoleUI.option(2, "Assigned courses");
            ConsoleUI.option(3, "View students on a course");
            ConsoleUI.option(4, "Put mark");
            ConsoleUI.option(5, "Messages");
            ConsoleUI.option(6, "Send message");
            ConsoleUI.option(7, "Send employee request");
            ConsoleUI.option(8, "Research workspace");
            ConsoleUI.option(9, "Change password");
            ConsoleUI.exitOption("Logout");
            prompt();
            try {
                switch (readInt()) {
                    case 1 -> System.out.println(teacher.viewProfile());
                    case 2 -> printCourses(teacher.getAssignedCourses());
                    case 3 -> viewStudentsOnCourse(teacher);
                    case 4 -> putMark(teacher);
                    case 5 -> printMessages(teacher);
                    case 6 -> sendMessage(teacher);
                    case 7 -> sendEmployeeRequest(teacher);
                    case 8 -> showResearchMenu(teacher);
                    case 9 -> changePassword(teacher);
                    case 0 -> back = true;
                    default -> warnUnknown();
                }
            } catch (RuntimeException e) {
                printError("Operation failed", e);
            }
        }
    }

    private void showManagerMenu(Manager manager) {
        boolean back = false;
        while (!back) {
            printHeader("Manager Workspace - " + manager.getFullName());
            ConsoleUI.option(1, "View profile");
            ConsoleUI.option(2, "Registration requests");
            ConsoleUI.option(3, "Process registration request");
            ConsoleUI.option(4, "Assign teacher to course");
            ConsoleUI.option(5, "Create academic report");
            ConsoleUI.option(6, "News management");
            ConsoleUI.option(7, "Employee requests");
            ConsoleUI.option(8, "Research supervisors");
            ConsoleUI.option(9, "University dashboard");
            ConsoleUI.exitOption("Logout");
            prompt();
            try {
                switch (readInt()) {
                    case 1 -> System.out.println(manager.viewProfile());
                    case 2 -> printRegistrationRequests();
                    case 3 -> processRegistrationRequest(manager);
                    case 4 -> assignTeacher(manager);
                    case 5 -> createAcademicReport(manager);
                    case 6 -> showNewsManagement(manager);
                    case 7 -> processEmployeeRequests(manager);
                    case 8 -> assignResearchSupervisor();
                    case 9 -> printSystemSnapshot();
                    case 0 -> back = true;
                    default -> warnUnknown();
                }
            } catch (RuntimeException e) {
                printError("Operation failed", e);
            }
        }
    }

    private void showAdminMenu(Admin admin) {
        boolean back = false;
        while (!back) {
            printHeader("Admin Workspace - " + admin.getFullName());
            ConsoleUI.option(1, "View users");
            ConsoleUI.option(2, "Create user");
            ConsoleUI.option(3, "Remove user");
            ConsoleUI.option(4, "Change account status");
            ConsoleUI.option(5, "View logs");
            ConsoleUI.option(6, "View storage status");
            ConsoleUI.option(7, "Create course");
            ConsoleUI.option(8, "Change course status");
            ConsoleUI.option(9, "Save data");
            ConsoleUI.option(10, "Load data");
            ConsoleUI.option(11, "University dashboard");
            ConsoleUI.exitOption("Logout");
            prompt();
            try {
                switch (readInt()) {
                    case 1 -> printUsers();
                    case 2 -> createUser();
                    case 3 -> removeUser();
                    case 4 -> changeAccountStatus();
                    case 5 -> printLogs();
                    case 6 -> printStorageStatus();
                    case 7 -> createCourse();
                    case 8 -> changeCourseStatus();
                    case 9 -> saveData();
                    case 10 -> loadData();
                    case 11 -> printSystemSnapshot();
                    case 0 -> back = true;
                    default -> warnUnknown();
                }
            } catch (RuntimeException e) {
                printError("Operation failed", e);
            }
        }
    }

    private void showResearchMenu(User user) {
        ResearchProfile profile = getOrCreateResearchProfile(user);
        boolean back = false;
        while (!back) {
            printHeader("Research Workspace - " + user.getFullName());
            ConsoleUI.option(1, "View research profile");
            ConsoleUI.option(2, "Add research paper");
            ConsoleUI.option(3, "Print papers sorted");
            ConsoleUI.option(4, "View research projects");
            ConsoleUI.option(5, "Join research project");
            ConsoleUI.option(6, "Create research project");
            ConsoleUI.option(7, "View top cited researcher");
            ConsoleUI.exitOption("Back");
            prompt();
            try {
                switch (readInt()) {
                    case 1 -> printResearchProfile(profile);
                    case 2 -> addResearchPaper(profile);
                    case 3 -> printPapersSorted(profile);
                    case 4 -> printResearchProjects();
                    case 5 -> joinResearchProject(profile);
                    case 6 -> createResearchProject(profile);
                    case 7 -> {
                        System.out.println(researchService.findTopCitedResearcher().orElse(null));
                        pause();
                    }
                    case 0 -> back = true;
                    default -> warnUnknown();
                }
            } catch (RuntimeException e) {
                printError("Operation failed", e);
            }
        }
    }

    private void printCourseCatalog() {
        printHeader("Course Catalog");
        if (system.getCourses().isEmpty()) {
            System.out.println("No courses.");
            pause();
            return;
        }
        for (int i = 0; i < system.getCourses().size(); i++) {
            Course course = system.getCourses().get(i);
            System.out.printf("%d. %s | %s | credits=%d | students=%d | instructors=%s%n",
                    i + 1,
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCredits(),
                    course.getStudents().size(),
                    course.getInstructors().stream().map(User::getLogin).toList());
            if (course.getDescription() != null) {
                System.out.println("   " + course.getDescription());
            }
            if (!course.getLessons().isEmpty()) {
                course.getLessons().forEach(lesson -> System.out.println("   " + lesson));
            }
        }
        pause();
    }

    private void printStudentCourses(Student student) {
        printHeader("My Courses");
        if (student.getRegisteredCourses().isEmpty()) {
            System.out.println("No registered courses yet.");
            pause();
            return;
        }
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course);
            course.getLessons().forEach(lesson -> System.out.println("   " + lesson));
        }
        pause();
    }

    private void registerForSelectedCourse(Student student) {
        List<Course> available = system.getCourses().stream()
                .filter(student::canRegister)
                .toList();
        Course course = chooseFromList("Choose course", available, item ->
                item.getCourseId() + " - " + item.getCourseName() + " (" + item.getCredits() + " credits)");
        if (course == null) {
            return;
        }
        RegistrationRequest request = facade.registerForCourse(student, course);
        printSuccess("Registration request created: " + request.getRequestId());
    }

    private void printStudentRequests(Student student) {
        printHeader("My Registration Requests");
        List<RegistrationRequest> requests = system.getRegistrationRequests().stream()
                .filter(request -> request.getStudent().equals(student))
                .toList();
        if (requests.isEmpty()) {
            System.out.println("No registration requests yet.");
        } else {
            requests.forEach(System.out::println);
        }
        pause();
    }

    private void printRegistrationRequests() {
        printHeader("Registration Requests");
        if (system.getRegistrationRequests().isEmpty()) {
            System.out.println("No requests.");
            pause();
            return;
        }
        system.getRegistrationRequests().forEach(System.out::println);
        pause();
    }

    private void processRegistrationRequest(Manager manager) {
        RegistrationRequest request = chooseFromList("Choose pending request",
                system.getRegistrationRequests().stream().filter(RegistrationRequest::isPending).toList(),
                requestItem -> requestItem.getRequestId() + " | " + requestItem.getStudent().getLogin()
                        + " -> " + requestItem.getCourse().getCourseName());
        if (request == null) {
            return;
        }
        ConsoleUI.option(1, "Approve");
        ConsoleUI.option(2, "Reject");
        prompt();
        int choice = readInt();
        if (choice == 1) {
            manager.approveRegistration(request);
            system.addLog(manager.getLogin(), "registration approved: " + request.getRequestId());
            printSuccess("Approved.");
        } else if (choice == 2) {
            manager.rejectRegistration(request);
            system.addLog(manager.getLogin(), "registration rejected: " + request.getRequestId());
            printSuccess("Rejected.");
        }
    }

    private void assignTeacher(Manager manager) {
        Teacher teacher = chooseFromList("Choose teacher", teachers(), teacherItem ->
                teacherItem.getLogin() + " | " + teacherItem.getTitle());
        Course course = chooseFromList("Choose course", system.getCourses(), courseItem ->
                courseItem.getCourseId() + " - " + courseItem.getCourseName());
        if (teacher == null || course == null) {
            return;
        }
        manager.assignCourseToTeacher(course, teacher);
    }

    private void viewStudentsOnCourse(Teacher teacher) {
        Course course = chooseFromList("Choose assigned course", teacher.getAssignedCourses(), Course::getCourseName);
        if (course == null) {
            return;
        }
        printHeader("Students on " + course.getCourseName());
        if (course.getStudents().isEmpty()) {
            System.out.println("No students on this course.");
            return;
        }
        course.getStudents().forEach(System.out::println);
    }

    private void putMark(Teacher teacher) {
        Course course = chooseFromList("Choose assigned course", teacher.getAssignedCourses(), Course::getCourseName);
        if (course == null) {
            return;
        }
        Student student = chooseFromList("Choose student", course.getStudents(), User::getLogin);
        if (student == null) {
            return;
        }
        double first = readDouble("First attestation: ");
        double second = readDouble("Second attestation: ");
        double finalExam = readDouble("Final exam: ");
        Mark mark = new Mark("MK-" + student.getId() + "-" + course.getCourseId() + "-" + System.currentTimeMillis(),
                student, course, first, second, finalExam);
        facade.putMark(teacher, student, course, mark);
        printSuccess("Mark saved. Total=" + mark.calculateTotal());
    }

    private void createAcademicReport(Manager manager) {
        List<Student> students = students();
        Report report = manager.createReport(students);
        report.print();
        pause();
    }

    private void showNewsManagement(Manager manager) {
        ConsoleUI.option(1, "List news");
        ConsoleUI.option(2, "Publish news");
        ConsoleUI.option(3, "Edit news text");
        prompt();
        switch (readInt()) {
            case 1 -> printNews();
            case 2 -> publishNews(manager);
            case 3 -> editNews();
            default -> warnUnknown();
        }
    }

    private void publishNews(Manager manager) {
        String title = readText("Title: ");
        String text = readText("Text: ");
        News news = new News("NEWS-" + System.currentTimeMillis(), title, text, manager);
        manager.manageNews(news);
        system.addNews(news);
        news.publish();
    }

    private void editNews() {
        News news = chooseFromList("Choose news", system.getNews(), item -> item.getTitle() + " | " + item.getText());
        if (news == null) {
            return;
        }
        news.edit(readText("New text: "));
        printSuccess("News updated.");
    }

    private void processEmployeeRequests(Manager manager) {
        List<EmployeeRequest> requests = employeeRequests();
        EmployeeRequest request = chooseFromList("Choose employee request", requests, requestItem ->
                requestItem.getRequestId() + " | " + requestItem.getSender().getLogin()
                        + " | " + requestItem.getStatus() + " | " + requestItem.getText());
        if (request == null) {
            return;
        }
        ConsoleUI.option(1, "Approve");
        ConsoleUI.option(2, "Reject");
        prompt();
        int choice = readInt();
        if (choice == 1) {
            request.approve(manager);
            printSuccess("Request approved.");
        } else if (choice == 2) {
            request.reject(manager);
            printSuccess("Request rejected.");
        }
    }

    private void assignResearchSupervisor() {
        Student student = chooseFromList("Choose 4th year student", students().stream()
                .filter(item -> item.getYearOfStudy() >= 4)
                .toList(), studentItem -> studentItem.getLogin() + " | " + studentItem.getMajor());
        Researcher supervisor = chooseFromList("Choose supervisor", researchers(), researcher ->
                describeResearcher(researcher) + " | h-index=" + researcher.getHIndex());
        if (student == null || supervisor == null) {
            return;
        }
        researchService.assignSupervisor(student, supervisor);
        printSuccess("Supervisor assigned.");
    }

    private void printResearchProfile(ResearchProfile profile) {
        System.out.println("Owner: " + (profile.getOwner() == null ? "unknown" : profile.getOwner().getFullName()));
        System.out.println("H-index: " + profile.getHIndex());
        System.out.println("Total citations: " + profile.calculateTotalCitations());
        System.out.println("Most cited paper: " + profile.getMostCitedPaper());
        System.out.println("Papers: " + profile.getResearchPapers().size());
        System.out.println("Projects: " + profile.getResearchProjects().size());
        pause();
    }

    private void addResearchPaper(ResearchProfile profile) {
        String title = readText("Title: ");
        String authors = readText("Authors: ");
        String journal = readText("Journal: ");
        int pages = readInt("Pages: ");
        int citations = readInt("Citations: ");
        String doi = readText("DOI: ");
        ResearchPaper paper = new ResearchPaper(title, authors, journal, pages, new Date(), citations, doi);
        paper.setPaperId("RP-" + System.currentTimeMillis());
        facade.addResearchPaper(profile, paper);
        printSuccess("Paper added. Current h-index=" + profile.getHIndex());
    }

    private void printPapersSorted(ResearchProfile profile) {
        Comparator<ResearchPaper> comparator = choosePaperComparator();
        profile.printPapers(comparator);
        pause();
    }

    private void printResearchProjects() {
        printHeader("Research Projects");
        if (system.getResearchProjects().isEmpty()) {
            System.out.println("No projects.");
            pause();
            return;
        }
        for (ResearchProject project : system.getResearchProjects()) {
            project.printProjectInfo();
        }
        pause();
    }

    private void joinResearchProject(ResearchProfile profile) {
        ResearchProject project = chooseFromList("Choose research project", system.getResearchProjects(),
                item -> item.getProjectId() + " | " + item.getTopic() + " | " + item.getStatus());
        if (project == null) {
            return;
        }
        facade.joinResearchProject(profile, project);
        printSuccess("Joined project.");
    }

    private void createResearchProject(ResearchProfile profile) {
        String topic = readText("Project topic: ");
        ResearchProject project = new ResearchProject(topic);
        project.setProjectId("PR-" + System.currentTimeMillis());
        system.addResearchProject(project);
        system.addLog(researchActor(profile), "research project created: " + project.getTopic());
        facade.joinResearchProject(profile, project);
        printSuccess("Project created and joined.");
    }

    private Comparator<ResearchPaper> choosePaperComparator() {
        ConsoleUI.option(1, "By citations");
        ConsoleUI.option(2, "By publication date");
        ConsoleUI.option(3, "By pages");
        prompt();
        return switch (readInt()) {
            case 2 -> new PaperDateComparator();
            case 3 -> new PaperPagesComparator();
            default -> new PaperCitationComparator();
        };
    }

    private void sendMessage(Employee sender) {
        Employee receiver = chooseFromList("Choose receiver", employees().stream()
                .filter(employee -> !employee.equals(sender))
                .toList(), User::getLogin);
        if (receiver == null) {
            return;
        }
        Message message = sender.sendMessage(receiver, readText("Message: "));
        printSuccess("Message sent: " + message.getMessageId());
    }

    private void printMessages(Employee employee) {
        printHeader("Messages");
        if (employee.getMessages().isEmpty()) {
            System.out.println("No messages.");
            pause();
            return;
        }
        employee.getMessages().forEach(System.out::println);
        pause();
    }

    private void sendEmployeeRequest(Employee employee) {
        EmployeeRequest request = employee.sendRequest(readText("Request text: "));
        printSuccess("Request created: " + request.getRequestId());
    }

    private void printUsers() {
        printHeader("Users");
        system.getUsers().forEach(System.out::println);
        pause();
    }

    private void createUser() {
        ConsoleUI.section("Choose role");
        ConsoleUI.option(1, "Student");
        ConsoleUI.option(2, "Teacher");
        ConsoleUI.option(3, "Manager");
        ConsoleUI.option(4, "Admin");
        prompt();
        UserRole role = switch (readInt()) {
            case 2 -> UserRole.TEACHER;
            case 3 -> UserRole.MANAGER;
            case 4 -> UserRole.ADMIN;
            default -> UserRole.STUDENT;
        };
        String id = readText("Id: ");
        String login = readText("Login: ");
        String password = readText("Password: ");
        String fullName = readText("Full name: ");
        User user = userFactory.createUser(role, id, login, password, fullName);
        facade.addUser(user);
        printSuccess("User created: " + login);
    }

    private void removeUser() {
        User user = chooseFromList("Choose user", system.getUsers(), item ->
                item.getId() + " | " + item.getLogin() + " | " + item.getRole());
        if (user == null) {
            return;
        }
        facade.removeUser(user);
        printSuccess("User removed.");
    }

    private void changeAccountStatus() {
        User user = chooseFromList("Choose user", system.getUsers(), item ->
                item.getId() + " | " + item.getLogin() + " | " + item.getStatus());
        if (user == null) {
            return;
        }
        ConsoleUI.option(1, "ACTIVE");
        ConsoleUI.option(2, "BLOCKED");
        prompt();
        user.setStatus(readInt() == 2 ? AccountStatus.BLOCKED : AccountStatus.ACTIVE);
        facade.updateUser(user);
        printSuccess("Status updated.");
    }

    private void createCourse() {
        String id = readText("Course id: ");
        String title = readText("Course title: ");
        int credits = readInt("Credits: ");
        String major = readText("Major: ");
        int year = readInt("Recommended year: ");
        String description = readText("Description: ");

        Course course = new Course(id, title, credits);
        course.setMajor(major);
        course.setYear(year);
        course.setDescription(description);
        facade.addCourse(course);
        printSuccess("Course created: " + course.getCourseName());
    }

    private void changeCourseStatus() {
        Course course = chooseFromList("Choose course", system.getCourses(), item ->
                item.getCourseId() + " | " + item.getCourseName() + " | " + item.getStatus());
        if (course == null) {
            return;
        }
        ConsoleUI.option(1, "OPEN_FOR_REGISTRATION");
        ConsoleUI.option(2, "ACTIVE");
        ConsoleUI.option(3, "CLOSED");
        ConsoleUI.option(4, "FINISHED");
        prompt();
        CourseStatus status = switch (readInt()) {
            case 2 -> CourseStatus.ACTIVE;
            case 3 -> CourseStatus.CLOSED;
            case 4 -> CourseStatus.FINISHED;
            default -> CourseStatus.OPEN_FOR_REGISTRATION;
        };
        course.setStatus(status);
        system.addLog("admin", "course status changed: " + course.getCourseId() + " -> " + status);
        printSuccess("Course status updated.");
    }

    private void printLogs() {
        List<LogEntry> logs = facade.getLogs();
        if (logs.isEmpty()) {
            printHeader("Logs");
            System.out.println("No logs.");
            pause();
            return;
        }

        int page = Math.max(0, (logs.size() - 1) / LOG_PAGE_SIZE);
        while (true) {
            int totalPages = (int) Math.ceil((double) logs.size() / LOG_PAGE_SIZE);
            int start = page * LOG_PAGE_SIZE;
            int end = Math.min(start + LOG_PAGE_SIZE, logs.size());

            printHeader("Logs - Page " + (page + 1) + "/" + totalPages);
            for (int i = start; i < end; i++) {
                System.out.println((i + 1) + ". " + logs.get(i));
            }

            ConsoleUI.info("[N] next page  [P] previous page  [0] back");
            prompt();
            String command = scanner.nextLine().trim().toLowerCase();
            if (command.isEmpty() || "n".equals(command)) {
                if (page < totalPages - 1) {
                    page++;
                } else {
                    return;
                }
            } else if ("p".equals(command)) {
                if (page > 0) {
                    page--;
                }
            } else if ("0".equals(command) || "q".equals(command) || "b".equals(command)) {
                return;
            } else {
                warnUnknown();
            }
        }
    }

    private void printStorageStatus() {
        File file = new File(STORAGE_FILE);
        System.out.println("Storage file: " + file.getAbsolutePath());
        System.out.println("Exists: " + file.exists());
        if (file.exists()) {
            System.out.println("Size: " + file.length() + " bytes");
        }
        pause();
    }

    private void printNews() {
        printHeader("University News");
        if (system.getNews().isEmpty()) {
            System.out.println("No news.");
            pause();
            return;
        }
        system.getNews().forEach(System.out::println);
        pause();
    }

    private void printSystemSnapshot() {
        printHeader("University Dashboard");
        ConsoleUI.dashboard(new String[][]{
                {"Users", String.valueOf(system.getUsers().size())},
                {"Students", String.valueOf(students().size())},
                {"Teachers", String.valueOf(teachers().size())},
                {"Managers", String.valueOf(managers().size())},
                {"Courses", String.valueOf(system.getCourses().size())},
                {"Registration requests", String.valueOf(system.getRegistrationRequests().size())},
                {"Research projects", String.valueOf(system.getResearchProjects().size())},
                {"News", String.valueOf(system.getNews().size())},
                {"Logs", String.valueOf(system.getLogs().size())}
        });
        pause();
    }

    private void printDemoAccounts() {
        printHeader("Demo Accounts");
        System.out.println("admin / pass");
        System.out.println("sysadmin / pass");
        System.out.println("manager / pass");
        System.out.println("dean / pass");
        System.out.println("professor / pass");
        System.out.println("lecturer / pass");
        System.out.println("tutor / pass");
        System.out.println("student / pass");
        System.out.println("student2 / pass");
        System.out.println("student3 / pass");
        System.out.println("senior / pass");
        System.out.println("researcher / pass");
        pause();
    }

    private void changePassword(User user) {
        String oldPassword = readText("Old password: ");
        String newPassword = readText("New password: ");
        if (user.changePassword(oldPassword, newPassword)) {
            printSuccess("Password updated successfully.");
        } else {
            printError("Password update failed", new IllegalArgumentException("Old password is incorrect."));
        }
    }

    private void saveData() {
        facade.saveData();
        printSuccess("Saved to " + STORAGE_FILE);
    }

    private void loadData() {
        facade.loadData();
        printSuccess("Loaded from " + STORAGE_FILE);
    }

    private void loadExistingDataIfPresent() {
        if (new File(STORAGE_FILE).exists()) {
            try {
                facade.loadData();
                printSuccess("Loaded existing data from " + STORAGE_FILE);
            } catch (RuntimeException e) {
                printError("Could not load existing storage; default seed will be used", e);
            }
        }
    }

    private void showStartupBanner() {
        ConsoleUI.startupBanner();
        pause();
    }

    private ResearchProfile getOrCreateResearchProfile(User user) {
        if (user instanceof Teacher teacher) {
            if (teacher.getResearchProfile() == null) {
                teacher.setResearchProfile(new ResearchProfile(teacher));
            }
            return teacher.getResearchProfile();
        }
        if (user instanceof Student student) {
            if (student.getResearchProfile() == null) {
                student.setResearchProfile(new ResearchProfile(student));
            }
            return student.getResearchProfile();
        }
        throw new IllegalStateException("This user type does not have a research profile.");
    }

    private List<Student> students() {
        return system.getUsers().stream().filter(Student.class::isInstance).map(Student.class::cast).toList();
    }

    private List<Teacher> teachers() {
        return system.getUsers().stream().filter(Teacher.class::isInstance).map(Teacher.class::cast).toList();
    }

    private List<Manager> managers() {
        return system.getUsers().stream().filter(Manager.class::isInstance).map(Manager.class::cast).toList();
    }

    private List<Employee> employees() {
        return system.getUsers().stream().filter(Employee.class::isInstance).map(Employee.class::cast).toList();
    }

    private List<EmployeeRequest> employeeRequests() {
        List<EmployeeRequest> requests = new ArrayList<>();
        employees().forEach(employee -> requests.addAll(employee.getRequests()));
        return requests;
    }

    private List<Researcher> researchers() {
        List<Researcher> researchers = new ArrayList<>();
        for (User user : system.getUsers()) {
            if (user instanceof Teacher teacher && teacher.getResearchProfile() != null) {
                researchers.add(teacher.getResearchProfile());
            }
            if (user instanceof Student student && student.getResearchProfile() != null) {
                researchers.add(student.getResearchProfile());
            }
            if (user instanceof Researcher researcher) {
                researchers.add(researcher);
            }
        }
        return researchers.stream().distinct().toList();
    }

    private String describeResearcher(Researcher researcher) {
        if (researcher instanceof ResearchProfile profile && profile.getOwner() != null) {
            return profile.getOwner().getLogin();
        }
        return researcher.toString();
    }

    private String researchActor(Researcher researcher) {
        if (researcher instanceof ResearchProfile profile && profile.getOwner() != null) {
            return profile.getOwner().getLogin();
        }
        return "research";
    }

    private void printCourses(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("No courses.");
            pause();
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
            for (Lesson lesson : course.getLessons()) {
                System.out.println("   " + lesson);
            }
        }
        pause();
    }

    private <T> T chooseFromList(String title, List<T> items, java.util.function.Function<T, String> label) {
        printHeader(title);
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            ConsoleUI.option(i + 1, label.apply(items.get(i)));
        }
        ConsoleUI.exitOption("Cancel");
        prompt();
        int choice = readInt();
        if (choice <= 0 || choice > items.size()) {
            return null;
        }
        return items.get(choice - 1);
    }

    private String readText(String promptText) {
        System.out.print(promptText);
        return scanner.nextLine().trim();
    }

    private int readInt(String promptText) {
        while (true) {
            System.out.print(promptText);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return parseIntOrUnknown(input);
            }
        }
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return parseIntOrUnknown(input);
            }
            prompt();
        }
    }

    private int parseIntOrUnknown(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double readDouble(String promptText) {
        System.out.print(promptText);
        String input = scanner.nextLine().trim();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected number.");
        }
    }

    private void printHeader(String title) {
        ConsoleUI.title(title);
    }

    private void prompt() {
        ConsoleUI.prompt();
    }

    private void pause() {
        ConsoleUI.pause();
        scanner.nextLine();
        System.out.println();
    }

    private void warnUnknown() {
        ConsoleUI.warning("Unknown option. Try again.");
    }

    private void printSuccess(String message) {
        ConsoleUI.success(message);
    }

    private void printError(String prefix, RuntimeException e) {
        ConsoleUI.error(prefix + ": " + e.getMessage());
    }
}
