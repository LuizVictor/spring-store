package com.luizvictor.course.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardException> notFound(NotFoundException exception, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardException standardException = new StandardException(
                status.value(),
                error,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardException);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardException> databaseException(DatabaseException exception, HttpServletRequest request) {
        String error = "Database erro";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardException standardException = new StandardException(
                status.value(),
                error,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardException);
    }
}
