package com.swiftline.student.infrastructure.adapters.output.persistence.repository;

import com.swiftline.student.domain.model.Student;
import com.swiftline.student.infrastructure.adapters.output.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
