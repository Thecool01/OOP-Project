# Project Guide as of 27.04.2026

## UML Guide

Our project is a **Research-Oriented University System**. The UML diagram is divided into several logical parts: users, academic module, research module, storage/authentication, and exceptions.

The repository follows a similar package structure:

- [`academic`](src/oopproject/academic)
- [`app`](src/oopproject/app)
- [`auth`](src/oopproject/auth)
- [`enums`](src/oopproject/enums)
- [`exceptions`](src/oopproject/exceptions)
- [`research`](src/oopproject/research)
- [`storage`](src/oopproject/storage)
- [`users`](src/oopproject/users)

## 1. Users Block

The main idea is that all users inherit from the base class [`User`](src/oopproject/users/User.java).

```text
User
 |-- Student
 `-- Employee
      |-- Teacher
      |-- Manager
      `-- Admin
```

[`User`](src/oopproject/users/User.java) stores common data: `id`, `login`, `password`, `firstName`, `lastName`.

[`Student`](src/oopproject/users/Student.java) is responsible for course registration, viewing marks, and transcript-related behavior.

[`Employee`](src/oopproject/users/Employee.java) is the common base class for university employees.

[`Teacher`](src/oopproject/users/Teacher.java) teaches courses and assigns marks.

[`Manager`](src/oopproject/users/Manager.java) approves student registration and assigns teachers to courses.

[`Admin`](src/oopproject/users/Admin.java) manages users and views logs.

These classes are located in the [`users`](src/oopproject/users) package:

- [`Admin.java`](src/oopproject/users/Admin.java)
- [`Employee.java`](src/oopproject/users/Employee.java)
- [`Manager.java`](src/oopproject/users/Manager.java)
- [`Student.java`](src/oopproject/users/Student.java)
- [`Teacher.java`](src/oopproject/users/Teacher.java)
- [`User.java`](src/oopproject/users/User.java)

## 2. Academic Block

Main classes:

```text
Course
Lesson
Mark
```

[`Course`](src/oopproject/academic/Course.java) represents a course. It has a course name, credits, and a list of instructors.

[`Lesson`](src/oopproject/academic/Lesson.java) represents a course lesson. The lesson type is taken from [`LessonType`](src/oopproject/enums/LessonType.java): `LECTURE` or `PRACTICE`.

[`Mark`](src/oopproject/academic/Mark.java) represents a student's mark for a course. It consists of `firstAttestation`, `secondAttestation`, and `finalExam`.

Main relationships:

```text
Student <-> Course — a student registers for courses
Teacher <-> Course — a teacher teaches courses
Course -> Lesson — a course consists of lessons
Student -> Mark — a student receives marks
Teacher -> Mark — a teacher assigns marks
Course -> Mark — a mark belongs to a specific course
```

These classes are located in the [`academic`](src/oopproject/academic) package:

- [`Course.java`](src/oopproject/academic/Course.java)
- [`Lesson.java`](src/oopproject/academic/Lesson.java)
- [`Mark.java`](src/oopproject/academic/Mark.java)

## 3. Research Block

The research module represents the scientific part of the university system.

Main classes:

```text
Researcher
ResearcherDecorator
ResearchPaper
ResearchProject
```

[`Researcher`](src/oopproject/research/Researcher.java) is an interface for research-related behavior.

[`ResearcherDecorator`](src/oopproject/research/ResearcherDecorator.java) adds researcher functionality to a user. This is an example of the **Decorator Pattern**.

[`ResearchPaper`](src/oopproject/research/ResearchPaper.java) stores paper information: `title`, `authors`, `journal`, `pages`, `datePublished`, `citations`, `doi`.

[`ResearchProject`](src/oopproject/research/ResearchProject.java) stores the project topic, participants, and published papers.

Main relationships:

```text
Researcher <-> ResearchPaper — a researcher writes papers
ResearchProject <-> Researcher — a project has participants
ResearchProject -> ResearchPaper — a project produces papers
ResearcherDecorator -> Researcher — the decorator adds research behavior
```

Research classes are located in the [`research`](src/oopproject/research) package:

- [`ResearchPaper.java`](src/oopproject/research/ResearchPaper.java)
- [`ResearchProject.java`](src/oopproject/research/ResearchProject.java)
- [`Researcher.java`](src/oopproject/research/Researcher.java)
- [`ResearcherDecorator.java`](src/oopproject/research/ResearcherDecorator.java)

## 4. Storage And Authentication

[`DataStorage`](src/oopproject/storage/DataStorage.java) is the central data storage. It stores users, courses, research projects, and logs.

[`LogEntry`](src/oopproject/storage/LogEntry.java) is used to store user actions.

[`AuthService`](src/oopproject/auth/AuthService.java) is responsible for system login. It checks `login` and `password` through [`DataStorage`](src/oopproject/storage/DataStorage.java).

Main relationships:

```text
AuthService -> DataStorage — authentication uses stored users
DataStorage -> User — stores users
DataStorage -> Course — stores courses
DataStorage -> ResearchProject — stores projects
DataStorage -> LogEntry — stores logs
Main -> AuthService — starts the login process
Main -> DataStorage — works with system data
DemoDataLoader -> DataStorage — loads test data
```

Files in this block:

- [`AuthService.java`](src/oopproject/auth/AuthService.java)
- [`DataStorage.java`](src/oopproject/storage/DataStorage.java)
- [`LogEntry.java`](src/oopproject/storage/LogEntry.java)
- [`Main.java`](src/oopproject/app/Main.java)
- [`DemoDataLoader.java`](src/oopproject/app/DemoDataLoader.java)

## 5. Enums

Enums are used to avoid random manually written strings.

```text
TeacherTitle: TUTOR, LECTOR, PROFESSOR
ManagerType: OR, DEPARTMENT
LessonType: LECTURE, PRACTICE
```

They are located in the [`enums`](src/oopproject/enums) package:

- [`TeacherTitle.java`](src/oopproject/enums/TeacherTitle.java)
- [`ManagerType.java`](src/oopproject/enums/ManagerType.java)
- [`LessonType.java`](src/oopproject/enums/LessonType.java)

## 6. Exceptions

Exceptions are used for system errors.

```text
UniversitySystemException — base exception
AuthenticationException — login/password error
RegistrationException — course registration error
MarkException — invalid mark error
ResearchException — research rule violation
StorageException — save/load error
```

All exception classes are located in the [`exceptions`](src/oopproject/exceptions) package:

- [`UniversitySystemException.java`](src/oopproject/exceptions/UniversitySystemException.java)
- [`AuthenticationException.java`](src/oopproject/exceptions/AuthenticationException.java)
- [`RegistrationException.java`](src/oopproject/exceptions/RegistrationException.java)
- [`MarkException.java`](src/oopproject/exceptions/MarkException.java)
- [`ResearchException.java`](src/oopproject/exceptions/ResearchException.java)
- [`StorageException.java`](src/oopproject/exceptions/StorageException.java)

## 7. Quick Navigation

Main entry points:

- [`Main.java`](src/oopproject/app/Main.java) — starts the demo scenario
- [`DemoDataLoader.java`](src/oopproject/app/DemoDataLoader.java) — loads test data
- [`DataStorage.java`](src/oopproject/storage/DataStorage.java) — central data storage
- [`AuthService.java`](src/oopproject/auth/AuthService.java) — authentication

Main domain classes:

- [`User.java`](src/oopproject/users/User.java)
- [`Student.java`](src/oopproject/users/Student.java)
- [`Teacher.java`](src/oopproject/users/Teacher.java)
- [`Manager.java`](src/oopproject/users/Manager.java)
- [`Admin.java`](src/oopproject/users/Admin.java)
- [`Course.java`](src/oopproject/academic/Course.java)
- [`Mark.java`](src/oopproject/academic/Mark.java)
- [`ResearcherDecorator.java`](src/oopproject/research/ResearcherDecorator.java)
- [`ResearchProject.java`](src/oopproject/research/ResearchProject.java)
