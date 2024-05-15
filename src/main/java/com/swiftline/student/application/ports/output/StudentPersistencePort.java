package com.swiftline.student.application.ports.output;

import com.swiftline.student.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentPersistencePort {

    Optional<Student> findById(long id);
    List<Student> findAll();
    Student save(Student student);
    void deleteById(long id);
}
