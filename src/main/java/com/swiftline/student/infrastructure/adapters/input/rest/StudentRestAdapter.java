package com.swiftline.student.infrastructure.adapters.input.rest;

import com.swiftline.student.application.ports.input.StudentServicePort;
import com.swiftline.student.infrastructure.adapters.input.rest.mapper.StudentRestMapper;
import com.swiftline.student.infrastructure.adapters.input.rest.model.request.StudentCreateRequest;
import com.swiftline.student.infrastructure.adapters.input.rest.model.response.StudentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentRestAdapter {

    private final StudentServicePort servicePort;
    private final StudentRestMapper restMapper;

    public StudentRestAdapter(StudentServicePort servicePort,
                              StudentRestMapper mapper) {
        this.servicePort = servicePort;
        this.restMapper = mapper;
    }

    @GetMapping("v1/api")
    public ResponseEntity<List<StudentResponse>> getStudents() {

        return ResponseEntity.ok(restMapper.toStudentResponseList(servicePort.findAll()));
    }

    @GetMapping("v1/api/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(restMapper.toStudentResponse(servicePort.findById(id)));
    }

    @PostMapping("v1/api")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest request) {

        return new ResponseEntity<>(restMapper.toStudentResponse(servicePort.save(restMapper.toStudent(request))), HttpStatus.CREATED);
    }

    @PutMapping("v1/api/{id}")
    public ResponseEntity<StudentResponse> createStudent(@PathVariable Long id, @Valid @RequestBody StudentCreateRequest request) {

        return ResponseEntity.ok(restMapper.toStudentResponse(servicePort.update(id, restMapper.toStudent(request))));
    }

    @DeleteMapping("v1/api/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
