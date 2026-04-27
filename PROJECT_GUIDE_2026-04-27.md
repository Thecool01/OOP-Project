# Гайд по проекту на 27.04.2026

## Гайд по UML

Наш проект — **Research-Oriented University System**. UML разделена на несколько логических частей: пользователи, академический модуль, research-модуль, storage/authentication и exceptions.

В репозитории код разделён по похожим пакетам:

- [`academic`](src/oopproject/academic)
- [`app`](src/oopproject/app)
- [`auth`](src/oopproject/auth)
- [`enums`](src/oopproject/enums)
- [`exceptions`](src/oopproject/exceptions)
- [`research`](src/oopproject/research)
- [`storage`](src/oopproject/storage)
- [`users`](src/oopproject/users)

## 1. Users Block

Главная идея: все пользователи наследуются от базового класса [`User`](src/oopproject/users/User.java).

```text
User
 ├── Student
 └── Employee
      ├── Teacher
      ├── Manager
      └── Admin
```

[`User`](src/oopproject/users/User.java) хранит общие данные: `id`, `login`, `password`, `firstName`, `lastName`.

[`Student`](src/oopproject/users/Student.java) отвечает за регистрацию на курсы, просмотр оценок и transcript.

[`Employee`](src/oopproject/users/Employee.java) — общий базовый класс для сотрудников университета.

[`Teacher`](src/oopproject/users/Teacher.java) ведёт курсы и ставит оценки.

[`Manager`](src/oopproject/users/Manager.java) подтверждает регистрацию студентов и назначает преподавателей на курсы.

[`Admin`](src/oopproject/users/Admin.java) управляет пользователями и смотрит логи.

В коде эти классы лежат в пакете [`users`](src/oopproject/users):

- [`Admin.java`](src/oopproject/users/Admin.java)
- [`Employee.java`](src/oopproject/users/Employee.java)
- [`Manager.java`](src/oopproject/users/Manager.java)
- [`Student.java`](src/oopproject/users/Student.java)
- [`Teacher.java`](src/oopproject/users/Teacher.java)
- [`User.java`](src/oopproject/users/User.java)

## 2. Academic Block

Главные классы:

```text
Course
Lesson
Mark
```

[`Course`](src/oopproject/academic/Course.java) — курс, у него есть название, кредиты и список преподавателей.

[`Lesson`](src/oopproject/academic/Lesson.java) — занятие курса, тип занятия берётся из [`LessonType`](src/oopproject/enums/LessonType.java): `LECTURE` или `PRACTICE`.

[`Mark`](src/oopproject/academic/Mark.java) — оценка студента по курсу, состоит из `firstAttestation`, `secondAttestation` и `finalExam`.

Основные связи:

```text
Student ↔ Course — студент регистрируется на курсы
Teacher ↔ Course — преподаватель ведёт курсы
Course → Lesson — курс состоит из занятий
Student → Mark — студент получает оценки
Teacher → Mark — преподаватель ставит оценки
Course → Mark — оценка относится к конкретному курсу
```

В коде эти классы находятся в пакете [`academic`](src/oopproject/academic):

- [`Course.java`](src/oopproject/academic/Course.java)
- [`Lesson.java`](src/oopproject/academic/Lesson.java)
- [`Mark.java`](src/oopproject/academic/Mark.java)

## 3. Research Block

Research-модуль показывает научную часть университета.

Главные классы:

```text
Researcher
ResearcherDecorator
ResearchPaper
ResearchProject
```

[`Researcher`](src/oopproject/research/Researcher.java) — interface для research-функционала.

[`ResearcherDecorator`](src/oopproject/research/ResearcherDecorator.java) добавляет пользователю возможность быть исследователем. Это пример **Decorator Pattern**.

[`ResearchPaper`](src/oopproject/research/ResearchPaper.java) хранит информацию о статье: `title`, `authors`, `journal`, `pages`, `datePublished`, `citations`, `doi`.

[`ResearchProject`](src/oopproject/research/ResearchProject.java) хранит тему проекта, участников и опубликованные статьи.

Основные связи:

```text
Researcher ↔ ResearchPaper — researcher writes papers
ResearchProject ↔ Researcher — project has participants
ResearchProject → ResearchPaper — project produces papers
ResearcherDecorator → Researcher — decorator adds research behavior
```

В коде research-классы лежат в пакете [`research`](src/oopproject/research):

- [`ResearchPaper.java`](src/oopproject/research/ResearchPaper.java)
- [`ResearchProject.java`](src/oopproject/research/ResearchProject.java)
- [`Researcher.java`](src/oopproject/research/Researcher.java)
- [`ResearcherDecorator.java`](src/oopproject/research/ResearcherDecorator.java)

## 4. Storage And Authentication

[`DataStorage`](src/oopproject/storage/DataStorage.java) — центральное хранилище данных. Оно хранит пользователей, курсы, research projects и logs.

[`LogEntry`](src/oopproject/storage/LogEntry.java) нужен для сохранения действий пользователей.

[`AuthService`](src/oopproject/auth/AuthService.java) отвечает за вход в систему: проверяет `login` и `password` через [`DataStorage`](src/oopproject/storage/DataStorage.java).

Основные связи:

```text
AuthService → DataStorage — authentication uses stored users
DataStorage → User — stores users
DataStorage → Course — stores courses
DataStorage → ResearchProject — stores projects
DataStorage → LogEntry — stores logs
Main → AuthService — starts login process
Main → DataStorage — works with system data
DemoDataLoader → DataStorage — loads test data
```

Файлы этого блока:

- [`AuthService.java`](src/oopproject/auth/AuthService.java)
- [`DataStorage.java`](src/oopproject/storage/DataStorage.java)
- [`LogEntry.java`](src/oopproject/storage/LogEntry.java)
- [`Main.java`](src/oopproject/app/Main.java)
- [`DemoDataLoader.java`](src/oopproject/app/DemoDataLoader.java)

## 5. Enums

Enums нужны, чтобы не писать случайные строки вручную.

```text
TeacherTitle: TUTOR, LECTOR, PROFESSOR
ManagerType: OR, DEPARTMENT
LessonType: LECTURE, PRACTICE
```

Они находятся в пакете [`enums`](src/oopproject/enums):

- [`TeacherTitle.java`](src/oopproject/enums/TeacherTitle.java)
- [`ManagerType.java`](src/oopproject/enums/ManagerType.java)
- [`LessonType.java`](src/oopproject/enums/LessonType.java)

## 6. Exceptions

Exceptions нужны для ошибок системы.

```text
UniversitySystemException — base exception
AuthenticationException — login/password error
RegistrationException — course registration error
MarkException — invalid mark error
ResearchException — research rule violation
StorageException — save/load error
```

Все exception-классы лежат в пакете [`exceptions`](src/oopproject/exceptions):

- [`UniversitySystemException.java`](src/oopproject/exceptions/UniversitySystemException.java)
- [`AuthenticationException.java`](src/oopproject/exceptions/AuthenticationException.java)
- [`RegistrationException.java`](src/oopproject/exceptions/RegistrationException.java)
- [`MarkException.java`](src/oopproject/exceptions/MarkException.java)
- [`ResearchException.java`](src/oopproject/exceptions/ResearchException.java)
- [`StorageException.java`](src/oopproject/exceptions/StorageException.java)

## 7. Quick Navigation

Основные входные точки:

- [`Main.java`](src/oopproject/app/Main.java) — запуск demo-сценария
- [`DemoDataLoader.java`](src/oopproject/app/DemoDataLoader.java) — тестовые данные
- [`DataStorage.java`](src/oopproject/storage/DataStorage.java) — центральное хранилище
- [`AuthService.java`](src/oopproject/auth/AuthService.java) — авторизация

Основные доменные классы:

- [`User.java`](src/oopproject/users/User.java)
- [`Student.java`](src/oopproject/users/Student.java)
- [`Teacher.java`](src/oopproject/users/Teacher.java)
- [`Manager.java`](src/oopproject/users/Manager.java)
- [`Admin.java`](src/oopproject/users/Admin.java)
- [`Course.java`](src/oopproject/academic/Course.java)
- [`Mark.java`](src/oopproject/academic/Mark.java)
- [`ResearcherDecorator.java`](src/oopproject/research/ResearcherDecorator.java)
- [`ResearchProject.java`](src/oopproject/research/ResearchProject.java)
