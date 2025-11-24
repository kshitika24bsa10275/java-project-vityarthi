package edu.ccrm.config;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;

/**
 * Builder utility to create Course instances.
 */
public final class CourseBuilder {
    private CourseBuilder() { }

    public static class Builder {
        private CourseCode code;
        private String title;
        private int credits;
        private String instructor;

        public Builder withCode(CourseCode code) {
            this.code = code; return this;
        }

        public Builder withTitle(String title) {
            this.title = title; return this;
        }

        public Builder withCredits(int credits) {
            this.credits = credits; return this;
        }

        public Builder withInstructor(String instructor) {
            this.instructor = instructor; return this;
        }

        public Course build() {
            return new Course(code, title, credits, instructor);
        }
    }
}
