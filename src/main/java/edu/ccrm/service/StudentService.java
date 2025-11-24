package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.StudentStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simple in-memory student service.
 */
public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        if (s == null) throw new IllegalArgumentException("student must not be null");
        students.add(s);
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    public Optional<Student> findById(String id) {
        return students.stream().filter(st -> st.getId().equals(id)).findFirst();
    }

    public boolean updateStudent(String id, String fullName, String email) {
        Optional<Student> opt = findById(id);
        if (!opt.isPresent()) return false;
        Student s = opt.get();
        if (fullName != null) s.setFullName(fullName);
        if (email != null) s.setEmail(email);
        return true;
    }

    public boolean deactivateStudent(String id) {
        Optional<Student> opt = findById(id);
        if (!opt.isPresent()) return false;
        Student s = opt.get();
        s.setStatus(StudentStatus.INACTIVE);
        return true;
    }
}
