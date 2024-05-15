package com.swiftline.student.application.service;

import com.swiftline.student.application.ports.input.StudentServicePort;
import com.swiftline.student.application.ports.output.StudentPersistencePort;
import com.swiftline.student.domain.exception.StudentNotFoundException;
import com.swiftline.student.domain.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements StudentServicePort {

    private final StudentPersistencePort persistencePort;

    public StudentService(StudentPersistencePort persistencePort) {

        this.persistencePort = persistencePort;
    }

    @Override
    public Student findById(Long id) {

        return persistencePort.findById(id)
                .orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public List<Student> findAll() {

        return persistencePort.findAll();
    }

    @Override
    public Student save(Student student) {

        return persistencePort.save(student);
    }

    @Override
    public Student update(Long id, Student student) {

        return persistencePort.findById(id)
                .map(savedStudent -> {
                    savedStudent.setFirstName(student.getFirstName());
                    savedStudent.setLastName(student.getLastName());
                    savedStudent.setAge(student.getAge());
                    savedStudent.setAddress(savedStudent.getAddress());
                    return persistencePort.save(savedStudent);
                })
                .orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public void delete(Long id) {

        if(persistencePort.findById(id).isEmpty())
            throw new StudentNotFoundException();

        persistencePort.deleteById(id);
    }
}
