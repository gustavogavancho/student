package com.swiftline.student.infrastructure.adapters.output.persistence;

import com.swiftline.student.domain.model.Student;
import com.swiftline.student.infrastructure.adapters.output.persistence.entity.StudentEntity;
import com.swiftline.student.infrastructure.adapters.output.persistence.mapper.StudentPersistenceMapper;
import com.swiftline.student.infrastructure.adapters.output.persistence.repository.StudentRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentPersistenceAdapterTests {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentPersistenceMapper mapper;

    @InjectMocks
    private StudentPersistenceAdapter adapter;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        easyRandom = new EasyRandom();
    }

    @Test
    void testFindById_Success() {
        Student student = easyRandom.nextObject(Student.class);
        StudentEntity studentEntity = easyRandom.nextObject(StudentEntity.class);
        when(repository.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));
        when(mapper.toStudent(studentEntity)).thenReturn(student);

        Optional<Student> foundStudent = adapter.findById(studentEntity.getId());

        assertTrue(foundStudent.isPresent());
        assertEquals(student.getId(), foundStudent.get().getId());
        verify(repository, times(1)).findById(studentEntity.getId());
        verify(mapper, times(1)).toStudent(studentEntity);
    }

    @Test
    void testFindById_Empty() {
        Long id = easyRandom.nextLong();
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Student> foundStudent = adapter.findById(id);

        assertFalse(foundStudent.isPresent());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(0)).toStudent(any());
    }

    @Test
    void testFindAll() {
        List<Student> students = easyRandom.objects(Student.class, 5).toList();
        List<StudentEntity> studentEntities = easyRandom.objects(StudentEntity.class, 5).toList();
        when(repository.findAll()).thenReturn(studentEntities);
        when(mapper.toStudentList(studentEntities)).thenReturn(students);

        List<Student> foundStudents = adapter.findAll();

        assertNotNull(foundStudents);
        assertEquals(5, foundStudents.size());
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toStudentList(studentEntities);
    }

    @Test
    void testSave() {
        Student student = easyRandom.nextObject(Student.class);
        StudentEntity studentEntity = easyRandom.nextObject(StudentEntity.class);
        when(mapper.toStudentEntity(student)).thenReturn(studentEntity);
        when(repository.save(studentEntity)).thenReturn(studentEntity);
        when(mapper.toStudent(studentEntity)).thenReturn(student);

        Student savedStudent = adapter.save(student);

        assertNotNull(savedStudent);
        assertEquals(student.getId(), savedStudent.getId());
        verify(repository, times(1)).save(studentEntity);
        verify(mapper, times(1)).toStudentEntity(student);
        verify(mapper, times(1)).toStudent(studentEntity);
    }

    @Test
    void testDeleteById() {
        Long id = easyRandom.nextLong();

        adapter.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
