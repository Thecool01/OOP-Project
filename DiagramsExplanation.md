# 1. Общая идея проекта

Наш проект — это **консольная информационная система исследовательского университета**.

В системе есть несколько главных ролей:

- `Student`
- `Teacher`
- `Manager`
- `Admin`
- `Researcher`

Система должна поддерживать:

- авторизацию пользователей;
- регистрацию студентов на курсы;
- назначение преподавателей на курсы;
- выставление оценок;
- просмотр transcript;
- создание академических отчётов;
- работу с research papers;
- работу с research projects;
- назначение research supervisor;
- сохранение и загрузку данных;
- работу через сервисы и Facade.

Проект разделён на несколько модулей:

| Модуль | Цвет в диаграмме | Ответственный | Основная задача |
| --- | --- | --- | --- |
| Users / People | зелёный | общая часть | пользователи и роли |
| Academic Module | голубой | Искандер | курсы, уроки, регистрация, transcript |
| Teaching & Management | жёлтый / оранжевый | Томирис | оценки, отчёты, новости, requests |
| Research Module | розовый / фиолетовый | Айгерим | researchers, papers, projects |
| Platform / Services / Storage | синий | Мейрамбек | auth, storage, services, facade |
| Enums | бежевый | общая часть | перечисления |
| Exceptions | красный | общая часть | собственные ошибки |

---

# 2. Use Case Diagram

Use Case Diagram показывает, **какие пользователи взаимодействуют с системой и какие действия они могут выполнять**.

---

## 2.1. Actors

В диаграмме есть основные actors:

```
User
Student
Employee
Teacher
Manager
Admin
Researcher
Storage System
```

---

## 2.2. Actor inheritance

В Use Case Diagram используется наследование actors.

```
Student ───▷ User
Employee ───▷ User
Teacher ───▷ Employee
Manager ───▷ Employee
Admin ───▷ Employee
```

Это означает:

- `Student` является разновидностью `User`;
- `Employee` является разновидностью `User`;
- `Teacher`, `Manager`, `Admin` являются разновидностями `Employee`.

То есть все пользователи системы могут выполнять базовые действия, например:

```
Login
Logout
View Profile
Change Password
```

А конкретные роли имеют дополнительные действия.

---

# 3. Use Case Block: User

## Actor

```
User
```

## Что может делать User

```
Login
Logout
View Profile
Change Password
```

## Объяснение

`User` — это общий пользователь системы. Все остальные роли наследуют его базовые возможности.

Любой пользователь должен сначала войти в систему через authentication.

## Как объяснить на защите

```
User is a general actor. All system users must log in before accessing the system. Student, Teacher, Manager and Admin inherit basic user actions.
```

---

# 4. Use Case Block: Student

## Actor

```
Student
```

## Что может делать Student

```
View Courses
Register for Course
View Marks
View Transcript
```

## Объяснение

`Student` работает с учебной частью системы.

Он может:

1. Смотреть доступные курсы.
2. Подавать заявку на регистрацию на курс.
3. Смотреть свои оценки.
4. Смотреть transcript.

## Важная include-связь

```
Register for Course ..> Check Credit Limit : <<include>>
```

Это значит, что при регистрации на курс система обязательно проверяет credit limit.

## Почему используется `<<include>>`

`Check Credit Limit` — обязательный шаг. Нельзя зарегистрировать студента на курс без проверки количества кредитов.

## Бизнес-правило

```
Student cannot register for more than 21 credits.
```

---

# 5. Use Case Block: Teacher

## Actor

```
Teacher
```

## Что может делать Teacher

```
View Assigned Courses
View Students
Put Marks
Send Request
```

## Объяснение

`Teacher` отвечает за преподавание и выставление оценок.

Teacher может:

1. Смотреть назначенные ему курсы.
2. Смотреть студентов на курсе.
3. Ставить оценки студентам.
4. Отправлять request / complaint.

## Важная include-связь

```
Put Marks ..> Validate Mark : <<include>>
```

## Почему используется `<<include>>`

Перед сохранением оценки система должна проверить, что оценка корректная.

Например:

```
firstAttestation >= 0
secondAttestation >= 0
finalExam >= 0
total <= maximum allowed score
```

## Бизнес-правило

```
Mark consists of first attestation, second attestation and final exam.
```

---

# 6. Use Case Block: Manager

## Actor

```
Manager
```

## Что может делать Manager

```
Approve Course Registration
Assign Teacher to Course
Create Academic Report
Manage News
Assign Research Supervisor
```

## Объяснение

`Manager` управляет учебным процессом.

Manager может:

1. Одобрять регистрацию студентов на курсы.
2. Назначать преподавателей на курсы.
3. Создавать академические отчёты.
4. Управлять новостями.
5. Назначать research supervisor.

## Include-связь для отчётов

```
Create Academic Report ..> Calculate Statistics : <<include>>
```

## Почему используется `<<include>>`

Чтобы создать отчёт, система должна обязательно посчитать статистику.

Например:

```
average GPA
average mark
course performance
student performance
```

## Include-связь для supervisor

```
Assign Research Supervisor ..> Check Supervisor H-Index : <<include>>
```

## Почему используется `<<include>>`

Перед назначением supervisor система должна проверить, что h-index supervisor не меньше 3.

## Бизнес-правило

```
Only 4th year students must have supervisor.
Supervisor h-index must be >= 3.
```

---

# 7. Use Case Block: Admin

## Actor

```
Admin
```

## Что может делать Admin

```
Manage Users
View Logs
Save Data
Load Data
```

## Объяснение

`Admin` отвечает за пользователей и техническую часть системы.

Admin может:

1. Добавлять пользователей.
2. Удалять пользователей.
3. Обновлять пользователей.
4. Смотреть логи.
5. Сохранять данные.
6. Загружать данные.

## Include-связи

```
Manage Users ..> Add User : <<include>>
Manage Users ..> Remove User : <<include>>
Manage Users ..> Update User : <<include>>
```

## Почему используется `<<include>>`

`Manage Users` — это общий use case, который включает конкретные действия:

```
Add User
Remove User
Update User
```

---

# 8. Use Case Block: Researcher

## Actor

```
Researcher
```

## Что может делать Researcher

```
Add Research Paper
Join Research Project
Print Research Papers Sorted
View Top Cited Researcher
```

## Объяснение

`Researcher` работает с research-модулем.

Researcher может:

1. Добавлять научные статьи.
2. Участвовать в research project.
3. Печатать свои papers в отсортированном виде.
4. Смотреть top cited researcher.

## Include-связь для проекта

```
Join Research Project ..> Check Researcher Status : <<include>>
```

## Почему используется `<<include>>`

В research project может вступить только пользователь, который является researcher.

## Include-связь для сортировки

```
Print Research Papers Sorted ..> Choose Sorting Type : <<include>>
```

## Почему используется `<<include>>`

Перед выводом papers нужно выбрать способ сортировки:

```
by citations
by publication date
by pages
```

---

# 9. Use Case Block: Storage System

## Actor

```
Storage System
```

## Что делает Storage System

```
Save Data
Load Data
```

## Объяснение

`Storage System` — это внешний или технический actor, который показывает, что система сохраняет и загружает данные.

Это связано с требованием:

```
properly working serialization
```

---

# 10. Class Diagram

Class Diagram показывает **структуру системы**:

- классы;
- поля;
- методы;
- наследование;
- интерфейсы;
- связи;
- multiplicity;
- зависимости;
- паттерны проектирования.

---

# 11. Class Diagram Block: Users / People

## Классы блока

```
User
Employee
Student
Teacher
Manager
Admin
```

---

## 11.1. User

`User` — базовый класс для всех пользователей.

### Поля

```
- id: String
- username: String
- password: String
- fullName: String
- email: String
- role: UserRole
- status: AccountStatus
```

### Методы

```
+ login(username: String, password: String): boolean
+ logout(): void
+ viewProfile(): void
+ changePassword(newPassword: String): void
+ equals(obj: Object): boolean
+ hashCode(): int
+ toString(): String
```

### Зачем нужен User

Все пользователи имеют общие данные:

```
id
username
password
fullName
email
role
status
```

Поэтому эти поля вынесены в общий родительский класс `User`.

---

## 11.2. Employee

`Employee` наследуется от `User`.

### Поля

```
- employeeId: String
- salary: double
- messages: List<Message>
- requests: List<EmployeeRequest>
```

### Методы

```
+ sendMessage(receiver: Employee, text: String): void
+ sendRequest(text: String): EmployeeRequest
```

### Зачем нужен Employee

`Teacher`, `Manager`, `Admin` — это сотрудники университета. У них есть общие employee-данные и общие employee-действия.

---

## 11.3. Student

`Student` наследуется от `User`.

### Поля

```
- studentId: String
- year: int
- major: String
- gpa: double
- credits: int
- transcript: Transcript
- researchProfile: ResearchProfile
- supervisor: Researcher
- failCount: int
```

### Методы

```
+ viewCourses(courses: List<Course>): void
+ registerForCourse(course: Course): RegistrationRequest
+ viewMarks(): List<Mark>
+ viewTranscript(): Transcript
+ rateTeacher(teacher: Teacher, rating: int): void
+ assignSupervisor(supervisor: Researcher): void
```

### Основная роль Student

Student связан с:

```
Course
RegistrationRequest
Transcript
Mark
ResearchProfile
Researcher
```

Он может регистрироваться на курсы, смотреть оценки, иметь transcript и research supervisor.

---

## 11.4. Teacher

`Teacher` наследуется от `Employee`.

### Поля

```
- teacherId: String
- title: TeacherTitle
- assignedCourses: List<Course>
- researchProfile: ResearchProfile
```

### Методы

```
+ viewAssignedCourses(): List<Course>
+ viewStudents(course: Course): List<Student>
+ putMark(student: Student, course: Course, mark: Mark): void
+ manageCourse(course: Course): void
+ isProfessor(): boolean
```

### Основная роль Teacher

Teacher:

- ведёт курсы;
- смотрит студентов;
- ставит оценки;
- может быть researcher;
- если `TeacherTitle = PROFESSOR`, то обязан иметь `ResearchProfile`.

---

## 11.5. Manager

`Manager` наследуется от `Employee`.

### Поля

```
- managerType: ManagerType
```

### Методы

```
+ addCourseForRegistration(course: Course): void
+ approveRegistration(request: RegistrationRequest): void
+ rejectRegistration(request: RegistrationRequest): void
+ assignTeacher(course: Course, teacher: Teacher): void
+ createReport(students: List<Student>): Report
+ manageNews(news: News): void
+ viewEmployeeRequests(): List<EmployeeRequest>
```

### Основная роль Manager

Manager:

- одобряет регистрации;
- назначает teachers на courses;
- создаёт reports;
- управляет news;
- смотрит employee requests;
- может назначать research supervisor.

---

## 11.6. Admin

`Admin` наследуется от `Employee`.

### Методы

```
+ addUser(user: User): void
+ removeUser(user: User): void
+ updateUser(user: User): void
+ viewLogs(): List<LogEntry>
```

### Основная роль Admin

Admin управляет пользователями и смотрит логи действий.

---

# 12. Связи в Users / People Block

## Generalization

```
Student ───▷ User
Employee ───▷ User
Teacher ───▷ Employee
Manager ───▷ Employee
Admin ───▷ Employee
```

## Почему используется Generalization

Generalization используется, когда один класс является разновидностью другого.

Например:

```
Student is a User.
Teacher is an Employee.
Manager is an Employee.
Admin is an Employee.
```

Это позволяет не дублировать общие поля и методы.

---

# 13. Class Diagram Block: Academic Module

## Классы блока

```
Course
Lesson
RegistrationRequest
Transcript
```

`Mark` тоже тесно связан с Academic Module, но по цвету он относится к Teaching & Management, потому что оценки ставит Teacher.

---

## 13.1. Course

`Course` — главный класс учебного модуля.

### Поля

```
- courseId: String
- title: String
- description: String
- credits: int
- major: String
- year: int
- status: CourseStatus
- instructors: List<Teacher>
- students: List<Student>
- lessons: List<Lesson>
```

### Методы

```
+ addInstructor(teacher: Teacher): void
+ removeInstructor(teacher: Teacher): void
+ addStudent(student: Student): void
+ removeStudent(student: Student): void
+ addLesson(lesson: Lesson): void
+ getTotalStudents(): int
+ toString(): String
```

### Основная роль Course

Course связан с:

```
Student
Teacher
Lesson
RegistrationRequest
Mark
```

Курс содержит уроки, имеет преподавателей и студентов.

---

## 13.2. Lesson

`Lesson` — конкретное занятие курса.

### Поля

```
- lessonId: String
- topic: String
- type: LessonType
- dateTime: LocalDateTime
- room: String
```

### Методы

```
+ reschedule(newDateTime: LocalDateTime): void
+ toString(): String
```

### Важное требование

```
Lesson type must be LECTURE or PRACTICE.
```

Это реализуется через enum:

```
LessonType
```

---

## 13.3. RegistrationRequest

`RegistrationRequest` — заявка студента на регистрацию на курс.

### Поля

```
- requestId: String
- student: Student
- course: Course
- status: RegistrationStatus
- createdAt: LocalDateTime
- approvedBy: Manager
```

### Методы

```
+ approve(manager: Manager): void
+ reject(manager: Manager): void
+ isPending(): boolean
```

### Основная роль

Student не попадает на курс напрямую. Сначала создаётся `RegistrationRequest`, потом Manager её одобряет или отклоняет.

---

## 13.4. Transcript

`Transcript` хранит оценки студента.

### Поля

```
- student: Student
- marks: List<Mark>
```

### Методы

```
+ addMark(mark: Mark): void
+ calculateGPA(): double
+ printTranscript(): void
+ getMarks(): List<Mark>
```

### Основная роль

Transcript нужен, чтобы Student мог смотреть свои marks и общий academic performance.

# 14. Связи в Academic Module

## Course — Lesson

```
Course 1 ◆── 0..* Lesson
```

### Тип связи

```
Composition
```

### Почему Composition

Lesson является частью Course. Если Course удаляется, его Lessons тоже теряют смысл.

Ромбик должен быть возле `Course`.

---

## Student — Course

```
Student 0..* ── 0..* Course
```

### Тип связи

```
Association
```

### Почему Association

Student и Course существуют независимо.

Один Student может быть записан на много Courses.

Один Course может иметь много Students.

---

## Course — Teacher

```
Course 0..* ◇── 1..* Teacher
```

### Тип связи

```
Aggregation
```

### Почему Aggregation

Course имеет Teachers, но Teacher существует отдельно от Course.

Если Course удаляется, Teacher не удаляется.

---

## Student — RegistrationRequest

```
Student 1 ◆── 0..* RegistrationRequest
```

### Тип связи

```
Composition
```

### Почему Composition

RegistrationRequest создаётся студентом и принадлежит процессу его регистрации.

---

## RegistrationRequest — Course

```
RegistrationRequest 0..* ── 1 Course
```

### Тип связи

```
Association
```

### Почему Association

Request связан с Course, но Course не является частью Request.

---

## RegistrationRequest — Manager

```
RegistrationRequest 0..* ── 0..1 Manager
```

### Тип связи

```
Association
```

### Почему Association

Manager только обрабатывает request.

`0..1` используется потому, что request может быть ещё не обработан.

---

## Student — Transcript

```
Student 1 ◆── 1 Transcript
```

### Тип связи

```
Composition
```

### Почему Composition

Transcript принадлежит конкретному Student.

---

## Transcript — Mark

```
Transcript 1 ◆── 0..* Mark
```

### Тип связи

```
Composition
```

### Почему Composition

Mark является частью Transcript.

---

# 15. Class Diagram Block: Teaching & Management

## Классы блока

```
Mark
Report
News
Message
EmployeeRequest
```

---

## 15.1. Mark

`Mark` — оценка студента по конкретному курсу.

### Поля

```
- markId: String
- firstAttestation: double
- secondAttestation: double
- finalExam: double
- total: double
- course: Course
- student: Student
```

### Методы

```
+ calculateTotal(): double
+ isPassed(): boolean
+ compareTo(other: Mark): int
+ toString(): String
```

### Важное требование

```
Mark consists of first attestation, second attestation and final exam.
```

---

## 15.2. Report

`Report` — академический отчёт.

### Поля

```
- reportId: String
- type: ReportType
- createdAt: LocalDateTime
- content: String
```

### Методы

```
+ print(): void
+ exportAsText(): String
```

### Основная роль

Report используется Manager для анализа успеваемости.

---

## 15.3. News

`News` — новость системы.

### Поля

```
- newsId: String
- title: String
- text: String
- createdAt: LocalDateTime
- author: Manager
```

### Методы

```
+ publish(): void
+ edit(text: String): void
```

### Основная роль

Manager может создавать и редактировать новости.

---

## 15.4. Message

`Message` — сообщение между employees.

### Поля

```
- messageId: String
- sender: Employee
- receiver: Employee
- text: String
- sentAt: LocalDateTime
```

### Методы

```
+ read(): void
```

### Основная роль

Employees могут отправлять сообщения друг другу.

---

## 15.5. EmployeeRequest

`EmployeeRequest` — запрос или complaint от employee.

### Поля

```
- requestId: String
- sender: Employee
- text: String
- status: RequestStatus
- signedBy: String
```

### Методы

```
+ approve(): void
+ reject(): void
```

### Основная роль

Employees могут отправлять requests, которые потом обрабатываются management.

---

# 16. Связи в Teaching & Management

## Teacher — Mark

```
Teacher 1 ── 0..* Mark
```

### Тип связи

```
Association
```

### Почему Association

Teacher ставит Mark, но Mark не является частью Teacher.

---

## Student — Mark

```
Student 1 ── 0..* Mark
```

### Тип связи

```
Association
```

### Почему Association

Mark относится к Student, но хранится в Transcript, поэтому здесь достаточно обычной связи.

---

## Course — Mark

```
Course 1 ── 0..* Mark
```

### Тип связи

```
Association
```

### Почему Association

Mark ставится по конкретному Course.

---

## Manager — Report

```
Manager 1 ── 0..* Report
```

### Тип связи

```
Association
```

### Почему Association

Manager создаёт Reports, но Report не является частью Manager.

---

## Manager — News

```
Manager 1 ◆── 0..* News
```

### Тип связи

```
Composition
```

### Почему Composition

News создаются и управляются через management. В учебной диаграмме это допустимо.

---

## Employee — Message

```
Employee 1 ── 0..* Message
```

### Тип связи

```
Association
```

### Почему Association

Employee может отправлять и получать сообщения.

---

## Employee — EmployeeRequest

```
Employee 1 ── 0..* EmployeeRequest
```

### Тип связи

```
Association
```

### Почему Association

Employee создаёт requests, но они обрабатываются отдельно.

---

# 17. Class Diagram Block: Research Module

## Классы блока

```
Researcher
ResearchProfile
ResearchPaper
ResearchProject
PaperCitationComparator
PaperDateComparator
PaperPagesComparator
```

---

## 17.1. Researcher

`Researcher` — interface.

### Методы

```
+ getHIndex(): int
+ getResearchPapers(): List<ResearchPaper>
+ getResearchProjects(): List<ResearchProject>
+ printPapers(comparator: Comparator<ResearchPaper>): void
+ calculateTotalCitations(): int
```

### Почему Researcher — interface

Researcher может быть:

```
Student
Teacher
Employee
```

Если сделать `Researcher` обычным родительским классом, возникнут проблемы с множественным наследованием.

Поэтому лучше:

```
Researcher = interface
ResearchProfile implements Researcher
Student / Teacher / Employee can have ResearchProfile
```

---

## 17.2. ResearchProfile

`ResearchProfile` реализует `Researcher`.

### Поля

```
- hIndex: int
- papers: List<ResearchPaper>
- projects: List<ResearchProject>
```

### Методы

```
+ addPaper(paper: ResearchPaper): void
+ addProject(project: ResearchProject): void
+ printPapers(comparator: Comparator<ResearchPaper>): void
+ calculateTotalCitations(): int
```

### Основная роль

ResearchProfile хранит research-информацию пользователя.

---

## 17.3. ResearchPaper

`ResearchPaper` — научная статья.

### Поля

```
- paperId: String
- title: String
- authors: List<String>
- journal: String
- pages: int
- publicationDate: LocalDate
- doi: String
- citations: int
- publisher: String
```

### Методы

```
+ compareTo(other: ResearchPaper): int
+ getLength(): int
+ toString(): String
```

### Основная роль

ResearchPaper используется для хранения научных публикаций researcher.

---

## 17.4. ResearchProject

`ResearchProject` — исследовательский проект.

### Поля

```
- projectId: String
- topic: String
- participants: List<Researcher>
- publishedPapers: List<ResearchPaper>
- status: ResearchProjectStatus
```

### Методы

```
+ addParticipant(researcher: Researcher): void
+ addPaper(paper: ResearchPaper): void
+ removeParticipant(researcher: Researcher): void
+ printProjectInfo(): void
```

### Основная роль

ResearchProject объединяет researchers и research papers.

---

## 17.5. Comparators

Классы:

```
PaperCitationComparator
PaperDateComparator
PaperPagesComparator
```

### Методы

```
+ compare(p1: ResearchPaper, p2: ResearchPaper): int
```

### Основная роль

Comparators используются для сортировки research papers:

```
by citations
by date
by pages
```

# 18. Связи в Research Module

## ResearchProfile — Researcher

```
ResearchProfile - - -▷ Researcher
```

### Тип связи

```
Realization
```

### Почему Realization

ResearchProfile реализует interface Researcher.

---

## Student / Teacher / Employee — ResearchProfile

```
Student 1 ◇── 0..1 ResearchProfile
Teacher 1 ◇── 0..1 ResearchProfile
Employee 1 ◇── 0..1 ResearchProfile
```

### Тип связи

```
Aggregation
```

### Почему Aggregation

Student, Teacher и Employee могут иметь ResearchProfile, но ResearchProfile не является обязательной частью каждого пользователя.

---

## Student — Researcher

```
Student 0..* ── 0..1 Researcher
```

Role name:

```
supervisor
```

### Тип связи

```
Association
```

### Почему Association

Supervisor — это отдельный Researcher, который связан со Student.

Он не является частью Student.

---

## ResearchProfile — ResearchPaper

```
ResearchProfile 1 ◆── 0..* ResearchPaper
```

### Тип связи

```
Composition
```

### Почему Composition

ResearchProfile содержит papers researcher.

---

## ResearchProfile — ResearchProject

```
ResearchProfile 0..* ── 0..* ResearchProject
```

### Тип связи

```
Association
```

### Почему Association

Researcher может участвовать во многих projects, а project может иметь много researchers.

---

## ResearchProject — ResearchPaper

```
ResearchProject 1 ◇── 0..* ResearchPaper
```

### Тип связи

```
Aggregation
```

### Почему Aggregation

Project имеет published papers, но paper может существовать отдельно.

---

## Comparators — ResearchPaper

```
PaperCitationComparator ..> ResearchPaper
PaperDateComparator ..> ResearchPaper
PaperPagesComparator ..> ResearchPaper
```

### Тип связи

```
Dependency
```

### Почему Dependency

Comparator только временно использует `ResearchPaper` в методе `compare()`.

Он не хранит papers внутри себя.

---

# 19. Class Diagram Block: Platform / Services / Storage

## Классы блока

```
UniversitySystem
UniversityFacade
AuthService
UserService
CourseRegistrationService
MarkService
ResearchService
ReportService
DataStore
FileDataStore
UserFactory
```

---

## 19.1. UniversitySystem

`UniversitySystem` — главный объект системы.

### Поля

```
- users: List<User>
- courses: List<Course>
- registrationRequests: List<RegistrationRequest>
- researchProjects: List<ResearchProject>
- news: List<News>
- logs: List<LogEntry>
```

### Методы

```
+ getInstance(): UniversitySystem
+ addUser(user: User): void
+ addCourse(course: Course): void
+ save(): void
+ load(): void
```

### Основная роль

UniversitySystem хранит главные коллекции системы.

Также он показывает идею Singleton.

---

## 19.2. UniversityFacade

`UniversityFacade` — класс паттерна Facade.

### Поля

```
- authService: AuthService
- userService: UserService
- registrationService: CourseRegistrationService
- markService: MarkService
- researchService: ResearchService
- reportService: ReportService
```

### Методы

```
+ login(username: String, password: String): User
+ registerForCourse(student: Student, course: Course): RegistrationRequest
+ putMark(teacher: Teacher, student: Student, course: Course, mark: Mark): void
+ addResearchPaper(researcher: Researcher, paper: ResearchPaper): void
+ joinResearchProject(researcher: Researcher, project: ResearchProject): void
+ createAcademicReport(students: List<Student>): Report
+ saveData(): void
+ loadData(): void
```

### Основная роль

Main / Demo не должен напрямую работать со всеми сервисами.

Вместо этого он обращается к одному классу:

```
UniversityFacade
```

---

## 19.3. AuthService

### Методы

```
+ login(username: String, password: String): User
+ logout(user: User): void
+ checkAccess(user: User, role: UserRole): boolean
```

### Основная роль

AuthService отвечает за вход, выход и проверку доступа.

---

## 19.4. UserService

### Методы

```
+ addUser(user: User): void
+ removeUser(user: User): void
+ updateUser(user: User): void
+ findUserById(id: String): User
```

### Основная роль

UserService управляет пользователями.

---

## 19.5. CourseRegistrationService

### Методы

```
+ register(student: Student, course: Course): RegistrationRequest
+ approve(request: RegistrationRequest, manager: Manager): void
+ reject(request: RegistrationRequest, manager: Manager): void
+ checkCreditLimit(student: Student, course: Course): boolean
```

### Основная роль

CourseRegistrationService отвечает за регистрацию студентов на курсы.

---

## 19.6. MarkService

### Методы

```
+ putMark(teacher: Teacher, student: Student, course: Course, mark: Mark): void
+ validateMark(mark: Mark): boolean
```

### Основная роль

MarkService отвечает за выставление и проверку оценок.

---

## 19.7. ResearchService

### Методы

```
+ addPaper(researcher: Researcher, paper: ResearchPaper): void
+ joinProject(researcher: Researcher, project: ResearchProject): void
+ assignSupervisor(student: Student, supervisor: Researcher): void
+ printAllPapers(comparator: Comparator<ResearchPaper>): void
+ findTopCitedResearcher(): Researcher
```

### Основная роль

ResearchService отвечает за research-логику:

```
papers
projects
supervisors
top cited researcher
```

---

## 19.8. ReportService

### Методы

```
+ generateAcademicReport(students: List<Student>): Report
+ calculateAverageGPA(students: List<Student>): double
+ calculateAverageMark(course: Course): double
```

### Основная роль

ReportService создаёт академические отчёты.

---

## 19.9. DataStore

`DataStore` — interface для сохранения и загрузки данных.

### Методы

```
+ save(system: UniversitySystem): void
+ load(): UniversitySystem
```

### Основная роль

DataStore позволяет не зависеть от конкретного способа хранения данных.

---

## 19.10. FileDataStore

`FileDataStore` реализует `DataStore`.

### Поля

```
- filePath: String
```

### Методы

```
+ save(system: UniversitySystem): void
+ load(): UniversitySystem
```

### Основная роль

FileDataStore отвечает за сохранение данных в файл.

---

## 19.11. UserFactory

### Метод

```
+ createUser(role: UserRole): User
```

### Основная роль

UserFactory создаёт нужный тип пользователя по `UserRole`.

---

# 20. Связи в Platform / Services / Storage

## UniversityFacade — Services

```
UniversityFacade ◆── AuthService
UniversityFacade ◆── UserService
UniversityFacade ◆── CourseRegistrationService
UniversityFacade ◆── MarkService
UniversityFacade ◆── ResearchService
UniversityFacade ◆── ReportService
```

### Тип связи

```
Composition
```

### Почему Composition

Facade содержит сервисы и управляет доступом к ним.

---

## FileDataStore — DataStore

```
FileDataStore - - -▷ DataStore
```

### Тип связи

```
Realization
```

### Почему Realization

FileDataStore реализует interface DataStore.

---

## Services — Model Classes

Примеры:

```
AuthService ..> User
UserService ..> User
CourseRegistrationService ..> Student
CourseRegistrationService ..> Course
MarkService ..> Mark
ResearchService ..> Researcher
ReportService ..> Report
```

### Тип связи

```
Dependency
```

### Почему Dependency

Services используют model classes в своих методах, но не являются их частью.

Например:

```
MarkService uses Mark.
ResearchService uses Researcher.
AuthService uses User.
```

---

# 21. Enums Block

Enums нужны для фиксированных наборов значений.

## Enums

```
UserRole
AccountStatus
TeacherTitle
ManagerType
LessonType
CourseStatus
RegistrationStatus
RequestStatus
ReportType
ResearchProjectStatus
```

## Примеры использования

```
User.role: UserRole
User.status: AccountStatus
Teacher.title: TeacherTitle
Lesson.type: LessonType
Course.status: CourseStatus
RegistrationRequest.status: RegistrationStatus
Report.type: ReportType
ResearchProject.status: ResearchProjectStatus
```

---

# 22. Exceptions Block

Exceptions нужны для ошибок бизнес-логики.

## Exceptions

```
UniversityException
InvalidLoginException
AccessDeniedException
UserNotFoundException
DuplicateUserException
CreditLimitExceededException
CourseAlreadyRegisteredException
InvalidMarkException
LowHIndexSupervisorException
NotResearcherException
DataStorageException
```

## Для чего они нужны

| Exception | Когда используется |
| --- | --- |
| `InvalidLoginException` | неправильный login/password |
| `AccessDeniedException` | нет прав на действие |
| `UserNotFoundException` | пользователь не найден |
| `DuplicateUserException` | пользователь уже существует |
| `CreditLimitExceededException` | студент превысил 21 кредит |
| `CourseAlreadyRegisteredException` | студент уже зарегистрирован на курс |
| `InvalidMarkException` | некорректная оценка |
| `LowHIndexSupervisorException` | supervisor h-index < 3 |
| `NotResearcherException` | non-researcher пытается вступить в project |
| `DataStorageException` | ошибка save/load |

---

# 23. Design Patterns в диаграмме

## 23.1. Facade

```
UniversityFacade
```

### Зачем нужен

Чтобы Main / Demo работал с одним классом, а не со всеми сервисами напрямую.

---

## 23.2. Singleton

```
UniversitySystem
+ getInstance()
```

### Зачем нужен

Чтобы в системе был один главный объект, который хранит основные коллекции.

---

## 23.3. Factory

```
UserFactory
+ createUser(role: UserRole): User
```

### Зачем нужен

Чтобы создавать нужного пользователя по роли:

```
STUDENT
TEACHER
MANAGER
ADMIN
```

---

## 23.4. Strategy / Comparator

```
PaperCitationComparator
PaperDateComparator
PaperPagesComparator
```

### Зачем нужен

Чтобы менять алгоритм сортировки papers:

```
by citations
by date
by pages
```

---

## 23.5. Storage abstraction

```
DataStore
FileDataStore
```

### Зачем нужен

Чтобы система зависела от интерфейса, а не от конкретного способа хранения.

Позже можно добавить:

```
DatabaseDataStore
CloudDataStore
```

и не ломать систему.