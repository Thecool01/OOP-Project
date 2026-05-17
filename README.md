# Research-Oriented University System

Console Java application for managing a university workflow. The project was built for an OOP course and demonstrates a layered object-oriented design with users, academic courses, registration, marks, research activity, news, requests, logging, and file-based persistence.

## Team

1. Ishutin Nikolay - Team Leader
2. Zhazykbayeva Tomiris
3. Sabalakov Iskander
4. Shointay Aigerim
5. Zhalgasbek Meirambek

## Main Features

- Role-based workspaces for `Admin`, `Manager`, `Teacher`, and `Student`.
- Course catalog, teacher assignment, registration requests, marks, and transcripts.
- Research module with papers, projects, supervisors, citations, h-index, and sorting strategies.
- News, employee requests, messages, reports, and system logs.
- Demo data loader with ready-to-use accounts and courses.
- Serialization-based storage in `university-system.ser`.

## Project Structure

```text
src/oopproject/
  app/          Main entry point and demo data loader
  users/        User hierarchy: admin, manager, teacher, student, employee
  academic/     Courses, lessons, marks, transcripts, registration requests
  research/     Research profiles, papers, projects, comparators
  services/     Business logic for users, marks, reports, research, registration
  facade/       Unified API for the console application
  storage/      Data storage and logs
  exceptions/   Custom domain exceptions
  enums/        Domain statuses, roles, and types
```

Additional documentation is available in `Diagrams.md` and `DiagramsExplanation.md`.

## How to Run

The project does not require an external build tool. Compile and run it from the project root:

```bash
javac -d out $(find src -name "*.java")
java -cp out oopproject.app.Main
```

On Windows PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out oopproject.app.Main
```

## Demo Accounts

All demo accounts use the password `pass`.

| Role | Login examples |
| --- | --- |
| Admin | `admin`, `sysadmin` |
| Manager | `manager`, `dean` |
| Teacher | `professor`, `lecturer`, `tutor` |
| Student | `student`, `student2`, `student3`, `senior`, `researcher` |

