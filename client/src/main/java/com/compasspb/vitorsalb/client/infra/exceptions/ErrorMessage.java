package com.compasspb.vitorsalb.client.infra.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorMessage {

    private int statusCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ErrorMessage(HttpStatus status, String message, String path) {
        this.statusCode = status.value();
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
