package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Minimal Enrollment record linking a Student to a Course with optional grade and semester.
 */
public class Enrollment {
    private final Course course;
    private final AcademicSemester semester;
    private final LocalDate enrolledOn;
    private Grade grade;

    public Enrollment(Course course, AcademicSemester semester, LocalDate enrolledOn) {
        if (course == null) throw new IllegalArgumentException("course must not be null");
        this.course = course;
        this.semester = semester;
        this.enrolledOn = enrolledOn == null ? LocalDate.now() : enrolledOn;
    }

    public Course getCourse() {
        return course;
    }

    public AcademicSemester getSemester() {
        return semester;
    }

    public LocalDate getEnrolledOn() {
        return enrolledOn;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
