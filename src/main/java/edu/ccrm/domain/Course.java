package edu.ccrm.domain;

/**
 * Represents a course offering.
 */
public class Course {
    private final CourseCode code;
    private String title;
    private int credits;
    private String instructor;

    public Course(CourseCode code, String title, int credits, String instructor) {
        if (code == null) throw new IllegalArgumentException("code must not be null");
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
    }

    public CourseCode getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
