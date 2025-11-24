package edu.ccrm.domain;

import java.util.Objects;

/**
 * Immutable value class combining a Semester (term) and a year.
 */
public final class AcademicSemester {
    private final Semester term;
    private final int year;

    public AcademicSemester(Semester term, int year) {
        this.term = Objects.requireNonNull(term);
        this.year = year;
    }

    public Semester getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicSemester that = (AcademicSemester) o;
        return year == that.year && term == that.term;
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, year);
    }

    @Override
    public String toString() {
        return term + " " + year;
    }
}
