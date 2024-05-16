package com.swiftline.student.infrastructure.adapters.input.rest;

import com.swiftline.student.domain.model.ErrorResponse;
import com.swiftline.student.utils.ErrorCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalControllerAdviceTests {

    @InjectMocks
    private GlobalControllerAdvice globalControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleStudentNotFoundException() {
        ErrorResponse response = globalControllerAdvice.handleStudentNotFoundException();

        assertNotNull(response);
        assertEquals(ErrorCatalog.STUDENT_NOT_FOUND.getCode(), response.getCode());
        assertEquals(ErrorCatalog.STUDENT_NOT_FOUND.getMessage(), response.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("student", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ErrorResponse response = globalControllerAdvice.handleMethodArgumentNotValidException(ex);

        assertNotNull(response);
        assertEquals(ErrorCatalog.INVALID_STUDENT.getCode(), response.getCode());
        assertEquals(ErrorCatalog.INVALID_STUDENT.getMessage(), response.getMessage());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertNotNull(response.getTimestamp());
        assertEquals(1, response.getDetails().size());
        assertEquals("must not be null", response.getDetails().get(0));
    }

    @Test
    void handleGenericException() {
        Exception ex = new Exception("Internal Server Error");

        ErrorResponse response = globalControllerAdvice.handleGenericException(ex);

        assertNotNull(response);
        assertEquals(ErrorCatalog.GENERIC_ERROR.getCode(), response.getCode());
        assertEquals(ErrorCatalog.GENERIC_ERROR.getMessage(), response.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertNotNull(response.getTimestamp());
        assertEquals(1, response.getDetails().size());
        assertEquals("Internal Server Error", response.getDetails().get(0));
    }
}
