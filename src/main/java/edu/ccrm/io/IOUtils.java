package edu.ccrm.io;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.StudentStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility for importing/exporting students from/to CSV using NIO.2.
 */
public final class IOUtils {
    private IOUtils() { }

    public static List<Student> importStudentsFromCSV(String filePath) throws IOException {
        Path p = Path.of(filePath);
        List<Student> result = new ArrayList<>();
        try (var lines = Files.lines(p, StandardCharsets.UTF_8)) {
            result = lines
                    .filter(l -> !l.isBlank())
                    .map(String::trim)
                    .map(l -> l.split(","))
                    .map(parts -> {
                        // expected: id,fullName,email,regNo,status,enrollmentDate(YYYY-MM-DD)
                        String id = parts.length > 0 ? parts[0] : "";
                        String fullName = parts.length > 1 ? parts[1] : "";
                        String email = parts.length > 2 ? parts[2] : "";
                        String regNo = parts.length > 3 ? parts[3] : "";
                        StudentStatus status = StudentStatus.ACTIVE;
                        if (parts.length > 4) {
                            try { status = StudentStatus.valueOf(parts[4]); } catch (Exception ex) { }
                        }
                        LocalDate date = parts.length > 5 ? LocalDate.parse(parts[5]) : LocalDate.now();
                        return new Student(id, fullName, email, regNo, status, date);
                    })
                    .collect(Collectors.toList());
        }
        return result;
    }

    public static void exportStudentsToCSV(List<Student> students, String filePath) throws IOException {
        Path p = Path.of(filePath);
        List<String> lines = new ArrayList<>();
        for (Student s : students) {
            String line = String.join(",",
                    s.getId(),
                    s.getFullName() == null ? "" : s.getFullName(),
                    s.getEmail() == null ? "" : s.getEmail(),
                    s.getRegNo(),
                    s.getStatus() == null ? "" : s.getStatus().name(),
                    s.getEnrollmentDate() == null ? "" : s.getEnrollmentDate().toString()
            );
            lines.add(line);
        }
        Files.write(p, lines, StandardCharsets.UTF_8);
    }
}
