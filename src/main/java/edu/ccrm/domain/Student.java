package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Student entity extending Person.
 */
public class Student extends Person {
    private final String regNo;
    private StudentStatus status;
    private final List<Enrollment> enrolledCourses = new ArrayList<>();
    private final LocalDate enrollmentDate;

    public Student(String id, String fullName, String email, String regNo, StudentStatus status, LocalDate enrollmentDate) {
        super(id, fullName, email);
        if (regNo == null || regNo.isEmpty()) {
            throw new IllegalArgumentException("regNo must not be null or empty");
        }
        this.regNo = regNo;
        this.status = status == null ? StudentStatus.ACTIVE : status;
        this.enrollmentDate = enrollmentDate == null ? LocalDate.now() : enrollmentDate;
    }

    public String getRegNo() {
        return regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public List<Enrollment> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }

    public void addEnrollment(Enrollment e) {
        if (e != null) enrolledCourses.add(e);
    }

    /**
     * Remove an enrollment from this student. Returns true if removed.
     */
    public boolean removeEnrollment(Enrollment e) {
        if (e == null) return false;
        return enrolledCourses.remove(e);
    }

    @Override
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student: ").append(fullName).append(" (" ).append(regNo).append(")\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Enrollment Date: ").append(enrollmentDate).append("\n");
        sb.append("Courses:\n");
        if (enrolledCourses.isEmpty()) {
            sb.append("  (none)\n");
        } else {
            for (Enrollment en : enrolledCourses) {
                sb.append("  - ").append(en.getCourse().getCode().getValue())
                  .append(" : ").append(en.getCourse().getTitle())
                  .append(" (Grade: ").append(en.getGrade() == null ? "N/A" : en.getGrade())
                  .append(")\n");
            }
        }
        return sb.toString();
    }
}
