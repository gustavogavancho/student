package com.swiftline.student.infrastructure.adapters.output.persistence;

import com.swiftline.student.application.ports.output.StudentPersistencePort;
import com.swiftline.student.domain.model.Student;
import com.swiftline.student.infrastructure.adapters.output.persistence.mapper.StudentPersistenceMapper;
import com.swiftline.student.infrastructure.adapters.output.persistence.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentPersistenceAdapter implements StudentPersistencePort {

    private final StudentRepository repository;
    private final StudentPersistenceMapper mapper;

    public StudentPersistenceAdapter(StudentRepository repository,
                                     StudentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Student> findById(long id) {

        return repository.findById(id).map(mapper::toStudent);
    }

    @Override
    public List<Student> findAll() {

        return mapper.toStudentList(repository.findAll());
    }

    @Override
    public Student save(Student student) {

        return mapper.toStudent(repository.save(mapper.toStudentEntity(student)));
    }

    @Override
    public void deleteById(long id) {

        repository.deleteById(id);
    }
}
