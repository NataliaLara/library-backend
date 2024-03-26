package com.crud.backend.config;

import com.crud.backend.exceptions.BadRequestException;
import com.crud.backend.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseBody
    public ResponseEntity<String> notFound(EntityNotFoundException notFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(notFoundException.getMessage());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseBody
    public ResponseEntity<String> badRequest(BadRequestException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(exception.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<String> validationError(ConstraintViolationException exception){
        var errorMessage = exception.getConstraintViolations()
                .stream().findFirst()
                .map(ConstraintViolation::getMessage)
                .orElseGet(exception::getMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(errorMessage);
    }
}
