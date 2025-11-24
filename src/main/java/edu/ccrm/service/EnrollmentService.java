package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.AcademicSemester;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.util.CustomExceptions;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service to manage enrollments with a simple max-credit-per-semester rule.
 */
public class EnrollmentService {
    private final int maxCreditsPerSemester;

    public EnrollmentService(int maxCreditsPerSemester) {
        this.maxCreditsPerSemester = maxCreditsPerSemester;
    }

    public void enrollStudentToCourse(Student student, Course course, AcademicSemester semester) throws CustomExceptions.MaxCreditLimitExceededException {
        Objects.requireNonNull(student, "student");
        Objects.requireNonNull(course, "course");
        Objects.requireNonNull(semester, "semester");

        // Check duplicate
    boolean duplicate = student.getEnrolledCourses().stream()
        .anyMatch(e -> e.getCourse().getCode().equals(course.getCode()) && semester.equals(e.getSemester()));
    if (duplicate) {
        throw new CustomExceptions.DuplicateEnrollmentException("Student already enrolled in course for semester");
    }

        // Calculate current credits
    int currentCredits = student.getEnrolledCourses().stream()
        .filter(e -> semester.equals(e.getSemester()))
        .mapToInt(e -> e.getCourse().getCredits())
        .sum();

        if (currentCredits + course.getCredits() > maxCreditsPerSemester) {
            throw new CustomExceptions.MaxCreditLimitExceededException(String.format("Enrolling would exceed max credits (%d)", maxCreditsPerSemester));
        }

        Enrollment enrollment = new Enrollment(course, semester, null);
        student.addEnrollment(enrollment);
    }

    public boolean unenrollStudentFromCourse(Student student, Course course, AcademicSemester semester) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(course);
    List<Enrollment> toRemove = student.getEnrolledCourses().stream()
        .filter(e -> e.getCourse().getCode().equals(course.getCode()) && semester.equals(e.getSemester()))
        .collect(Collectors.toList());
    if (toRemove.isEmpty()) return false;
    boolean removed = false;
    for (Enrollment e : toRemove) {
        removed |= student.removeEnrollment(e);
    }
    return removed;
    }
}
