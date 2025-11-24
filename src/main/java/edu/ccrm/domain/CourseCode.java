package edu.ccrm.domain;

/**
 * Immutable value class for a course code.
 */
public final class CourseCode {
    private final String value;

    public CourseCode(String value) {
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("CourseCode value must not be empty");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCode that = (CourseCode) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
