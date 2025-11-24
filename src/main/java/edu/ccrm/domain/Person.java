package edu.ccrm.domain;

/**
 * Abstract base class representing a person in the CCRM domain.
 */
public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;

    public Person(String id, String fullName, String email) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id must not be null or empty");
        }
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return a human-readable summary of this person.
     */
    public abstract String getDetails();
}
