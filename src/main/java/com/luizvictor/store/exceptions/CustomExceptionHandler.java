package com.luizvictor.store.exceptions;

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

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardException> unauthorizedException(UnauthorizedException exception, HttpServletRequest request) {
        String error = "Unauthorized";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardException standardException = new StandardException(
                status.value(),
                error,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardException);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardException> unprocessableException(UnprocessableEntityException exception, HttpServletRequest request) {
        String error = "Unprocessable Content";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardException standardException = new StandardException(
                status.value(),
                error,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardException);
    }
}
