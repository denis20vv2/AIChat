package com.example.AIChat.Group.Error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<EntityArgumentNotValid> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {

        EntityArgumentNotValid entityArgumentNotValid = new EntityArgumentNotValid(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                request.getDescription(false)

        );

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        entityArgumentNotValid.setFieldErrors(fieldErrors);

        return new ResponseEntity<>(entityArgumentNotValid, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleExceptionNotParametersException(IllegalArgumentException exception, WebRequest request) {

        Error error = new Error(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(EntityNotFoundException exception, WebRequest request) {


        Error error = new Error(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Entity Not Found",
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
