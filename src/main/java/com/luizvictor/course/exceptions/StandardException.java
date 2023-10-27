package com.luizvictor.course.exceptions;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class StandardException implements Serializable {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

    public StandardException(Integer status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
