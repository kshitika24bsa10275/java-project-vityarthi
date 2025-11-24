CCRM (Course & CRM) Java Project

Overview
--------
A small console-based Java application demonstrating domain modeling, services, I/O, and utility patterns.

Evolution of Java (high-level bullets)
-------------------------------------
- Java 1.0 (1996): Initial release — Applets and early class libraries.
- Java 2 (J2SE, J2EE, J2ME): Modularization of editions; major additions to the standard libraries.
- Java 5 (2004): Generics, annotations, enums, enhanced for-loop — major language improvements.
- Java 8 (2014): Lambda expressions, Streams API, java.time (new date/time API).
- Java 9+: Modular system (JPMS), continued performance and API improvements.
- Modern Java (11, 17, LTS): Long-term support releases; improvements in GC, performance, and language features.

Java ME vs SE vs EE (comparison)
--------------------------------
- Java ME (Micro Edition): Targeted at constrained devices (embedded, mobile); lightweight runtime and APIs.
- Java SE (Standard Edition): Core Java platform for desktop and general-purpose server apps. Contains core libraries (collections, concurrency, I/O, JDBC, etc.).
- Java EE / Jakarta EE (Enterprise Edition): Built on Java SE, adds enterprise APIs (Servlets, JPA, JMS, EJB, JAX-RS) for large distributed applications.

JDK vs JRE vs JVM
-----------------
- JVM (Java Virtual Machine): The runtime that executes Java bytecode. Provides memory management and execution environment.
- JRE (Java Runtime Environment): Contains the JVM and standard libraries required to run Java applications.
- JDK (Java Development Kit): Superset of the JRE; includes compiler (javac), debugging tools, and development utilities.

Syllabus Topic -> File/Class/Method mapping
------------------------------------------
| Topic | File / Class | Notable Methods |
|---|---|---|
| OOP & Inheritance | `edu.ccrm.domain.Person`, `edu.ccrm.domain.Student` | `Person.getDetails()`, `Student.getDetails()` |
| Enums & Value types | `edu.ccrm.domain.Grade`, `edu.ccrm.domain.Semester`, `edu.ccrm.domain.CourseCode` | `Grade.getGradePoints()` |
| Collections | `edu.ccrm.service.StudentService` | `addStudent()`, `listStudents()` |
| File I/O (NIO.2) | `edu.ccrm.io.IOUtils`, `edu.ccrm.io.FileBackupService` | `importStudentsFromCSV()`, `exportStudentsToCSV()`, `backupData()` |
| Exception handling | `edu.ccrm.util.CustomExceptions` | `MaxCreditLimitExceededException`, `DuplicateEnrollmentException` |
| Design Patterns | `edu.ccrm.config.AppConfig` (Singleton), `edu.ccrm.config.CourseBuilder` (Builder) | `AppConfig.getInstance()`, `CourseBuilder.Builder.build()` |
| Business Logic / Services | `edu.ccrm.service.EnrollmentService` | `enrollStudentToCourse()`, `unenrollStudentFromCourse()` |

How to build & run
-------------------
1. Compile from project root (PowerShell):

```powershell
javac -d out src\main\java\edu\ccrm\domain\*.java src\main\java\edu\ccrm\service\*.java src\main\java\edu\ccrm\io\*.java src\main\java\edu\ccrm\config\*.java src\main\java\edu\ccrm\cli\*.java src\main\java\edu\ccrm\util\*.java
```

2. Run the console app:

```
java -cp out edu.ccrm.cli.CCRMApp
```

Notes & next steps
------------------
- CSV parsing is basic; for production use consider a robust CSV library.
- Add unit tests (JUnit) and a build tool (Maven/Gradle) for better reproducibility.
- Consider persisting data (file-based or DB) instead of in-memory lists.
