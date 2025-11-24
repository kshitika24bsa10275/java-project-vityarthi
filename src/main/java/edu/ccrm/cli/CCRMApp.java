package edu.ccrm.cli;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.AcademicSemester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.io.IOUtils;
import edu.ccrm.io.FileBackupService;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the CCRM console application with a small interactive menu.
 */
public class CCRMApp {
    public static void main(String[] args) {
        System.out.println("CCRM Application starting...");

        StudentService studentService = new StudentService();
        EnrollmentService enrollmentService = new EnrollmentService(30); // max 30 credits per semester

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            do {
            System.out.println("\n=== CCRM Menu ===");
            System.out.println("1) Add student");
            System.out.println("2) List students");
            System.out.println("3) Enroll student to course");
            System.out.println("4) Unenroll student from course");
            System.out.println("5) Import students from CSV");
            System.out.println("6) Export students to CSV");
            System.out.println("7) Backup data folder");
            System.out.println("0) Exit");
            System.out.print("Choose option: ");

            String choice = readLine(scanner);
            if (choice == null) { System.out.println("Input closed."); break; }
            choice = choice.trim();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("id: ");
                        String id = readLine(scanner);
                        if (id == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("full name: ");
                        String name = readLine(scanner);
                        if (name == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("email: ");
                        String email = readLine(scanner);
                        if (email == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("regNo: ");
                        String regNo = readLine(scanner);
                        if (regNo == null) { System.out.println("Input closed."); running = false; break; }
                        Student s = new Student(id, name, email, regNo, null, LocalDate.now());
                        studentService.addStudent(s);
                        System.out.println("Student added.");
                    }
                    case "2" -> {
                        List<Student> all = studentService.listStudents();
                        if (all.isEmpty()) System.out.println("No students.");
                        for (Student s : all) System.out.println(s.getDetails());
                    }
                    case "3" -> {
                        System.out.print("student id: ");
                        String sid = readLine(scanner);
                        if (sid == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("course code (e.g., CS101): ");
                        String code = readLine(scanner);
                        if (code == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("course title: ");
                        String title = readLine(scanner);
                        if (title == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("credits: ");
                        String creditsLine = readLine(scanner);
                        if (creditsLine == null) { System.out.println("Input closed."); running = false; break; }
                        int credits = Integer.parseInt(creditsLine);
                        System.out.print("instructor: ");
                        String instr = readLine(scanner);
                        if (instr == null) { System.out.println("Input closed."); running = false; break; }

                        System.out.print("semester (SPRING/SUMMER/FALL): ");
                        String semLine = readLine(scanner);
                        if (semLine == null) { System.out.println("Input closed."); running = false; break; }
                        Semester semEnum = Semester.valueOf(semLine.trim());
                        System.out.print("year (e.g., 2025): ");
                        String yearLine = readLine(scanner);
                        if (yearLine == null) { System.out.println("Input closed."); running = false; break; }
                        int year = Integer.parseInt(yearLine.trim());
                        AcademicSemester sem = new AcademicSemester(semEnum, year);

                        Student st = studentService.findById(sid).orElse(null);
                        if (st == null) {
                            System.out.println("Student not found.");
                            break;
                        }
                        Course course = new Course(new CourseCode(code), title, credits, instr);
                        enrollmentService.enrollStudentToCourse(st, course, sem);
                        System.out.println("Enrolled.");
                    }
                    case "4" -> {
                        System.out.print("student id: ");
                        String sid = readLine(scanner);
                        if (sid == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("course code: ");
                        String code = readLine(scanner);
                        if (code == null) { System.out.println("Input closed."); running = false; break; }
                        System.out.print("semester (SPRING/SUMMER/FALL): ");
                        String semLine2 = readLine(scanner);
                        if (semLine2 == null) { System.out.println("Input closed."); running = false; break; }
                        Semester semEnum2 = Semester.valueOf(semLine2.trim());
                        System.out.print("year (e.g., 2025): ");
                        String yearLine2 = readLine(scanner);
                        if (yearLine2 == null) { System.out.println("Input closed."); running = false; break; }
                        int year2 = Integer.parseInt(yearLine2.trim());
                        AcademicSemester sem = new AcademicSemester(semEnum2, year2);

                        Student st = studentService.findById(sid).orElse(null);
                        if (st == null) { System.out.println("Student not found."); break; }
                        Course dummy = new Course(new CourseCode(code), "", 0, "");
                        boolean ok = enrollmentService.unenrollStudentFromCourse(st, dummy, sem);
                        System.out.println(ok ? "Unenrolled." : "Not enrolled.");
                    }
                    case "5" -> {
                        System.out.print("CSV file path to import: ");
                        String path = readLine(scanner);
                        if (path == null) { System.out.println("Input closed."); running = false; break; }
                        List<Student> imported = IOUtils.importStudentsFromCSV(path);
                        imported.forEach(studentService::addStudent);
                        System.out.println("Imported " + imported.size() + " students.");
                    }
                    case "6" -> {
                        System.out.print("CSV file path to export: ");
                        String path = readLine(scanner);
                        if (path == null) { System.out.println("Input closed."); running = false; break; }
                        IOUtils.exportStudentsToCSV(studentService.listStudents(), path);
                        System.out.println("Exported.");
                    }
                    case "7" -> {
                        System.out.print("Directory to backup: ");
                        String dir = readLine(scanner);
                        if (dir == null) { System.out.println("Input closed."); running = false; break; }
                        Path backup = FileBackupService.backupData(Path.of(dir));
                        long size = FileBackupService.calculateDirectorySize(backup);
                        System.out.println("Backup created at: " + backup + " (" + size + " bytes)");
                    }
                    case "0" -> running = false;
                    default -> System.out.println("Unknown option");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace(System.out);
            }

            } while (running);
        }

        System.out.println("Exiting CCRM.");
    }

    private static String readLine(Scanner scanner) {
        try {
            if (!scanner.hasNextLine()) return null;
            return scanner.nextLine();
        } catch (Exception ex) {
            return null;
        }
    }
}

