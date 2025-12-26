package com.workintech.twitter.exception;

import com.workintech.twitter.dto.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> {
                    ErrorResponse r = new ErrorResponse();
                    r.setMessage(err.getField() + ": " + err.getDefaultMessage());
                    r.setTimestamp(OffsetDateTime.now());
                    r.setStatus(HttpStatus.BAD_REQUEST.value());
                    return r;
                })
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        ErrorResponse r = new ErrorResponse();
        r.setMessage(ex.getMessage());
        r.setTimestamp(OffsetDateTime.now());
        r.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(r);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex){
        ErrorResponse r = new ErrorResponse();
        r.setMessage(ex.getMessage());
        r.setTimestamp(OffsetDateTime.now());
        r.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex){
        ErrorResponse r = new ErrorResponse();
        r.setMessage(ex.getMessage());
        r.setTimestamp(OffsetDateTime.now());
        r.setStatus(HttpStatus.FORBIDDEN.value());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(r);
    }
}
