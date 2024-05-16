package com.swiftline.student.application.service;

import com.swiftline.student.application.ports.output.StudentPersistencePort;
import com.swiftline.student.domain.exception.StudentNotFoundException;
import com.swiftline.student.domain.model.Student;
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

public class StudentServiceTest {

    @Mock
    private StudentPersistencePort persistencePort;

    @InjectMocks
    private StudentService studentService;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        easyRandom = new EasyRandom();
    }

    @Test
    void testFindById_Success() {
        Student student = easyRandom.nextObject(Student.class);
        when(persistencePort.findById(student.getId())).thenReturn(Optional.of(student));

        Student foundStudent = studentService.findById(student.getId());

        assertNotNull(foundStudent);
        assertEquals(student.getId(), foundStudent.getId());
        verify(persistencePort, times(1)).findById(student.getId());
    }

    @Test
    void testFindById_StudentNotFoundException() {
        Long id = easyRandom.nextLong();
        when(persistencePort.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.findById(id));
        verify(persistencePort, times(1)).findById(id);
    }

    @Test
    void testFindAll() {
        List<Student> students = easyRandom.objects(Student.class, 5).toList();
        when(persistencePort.findAll()).thenReturn(students);

        List<Student> foundStudents = studentService.findAll();

        assertNotNull(foundStudents);
        assertEquals(5, foundStudents.size());
        verify(persistencePort, times(1)).findAll();
    }

    @Test
    void testSave() {
        Student student = easyRandom.nextObject(Student.class);
        when(persistencePort.save(student)).thenReturn(student);

        Student savedStudent = studentService.save(student);

        assertNotNull(savedStudent);
        assertEquals(student.getId(), savedStudent.getId());
        verify(persistencePort, times(1)).save(student);
    }

    @Test
    void testUpdate_Success() {
        Student student = easyRandom.nextObject(Student.class);
        Student updatedStudent = easyRandom.nextObject(Student.class);
        updatedStudent.setId(student.getId());
        when(persistencePort.findById(student.getId())).thenReturn(Optional.of(student));
        when(persistencePort.save(any(Student.class))).thenReturn(updatedStudent);

        Student result = studentService.update(student.getId(), updatedStudent);

        assertNotNull(result);
        assertEquals(updatedStudent.getFirstName(), result.getFirstName());
        verify(persistencePort, times(1)).findById(student.getId());
        verify(persistencePort, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdate_StudentNotFoundException() {
        Long id = easyRandom.nextLong();
        Student student = easyRandom.nextObject(Student.class);
        when(persistencePort.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.update(id, student));
        verify(persistencePort, times(1)).findById(id);
    }

    @Test
    void testDelete_Success() {
        Student student = easyRandom.nextObject(Student.class);
        when(persistencePort.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.delete(student.getId());

        verify(persistencePort, times(1)).findById(student.getId());
        verify(persistencePort, times(1)).deleteById(student.getId());
    }

    @Test
    void testDelete_StudentNotFoundException() {
        Long id = easyRandom.nextLong();
        when(persistencePort.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.delete(id));
        verify(persistencePort, times(1)).findById(id);
    }
}
