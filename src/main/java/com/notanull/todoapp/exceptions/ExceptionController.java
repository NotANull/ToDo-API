package com.notanull.todoapp.exceptions;

import com.notanull.todoapp.dto.response.ExceptionDto;
import com.notanull.todoapp.dto.response.ResponseErrorValidationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> idNotFound(IdNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(404, ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationFail(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(field -> errors.put(field.getField(), field.getDefaultMessage()));
        return new ResponseEntity<>(new ResponseErrorValidationDto(400, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OverdueTaskException.class)
    public ResponseEntity<?> overdueTask(OverdueTaskException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(403, ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }
}
