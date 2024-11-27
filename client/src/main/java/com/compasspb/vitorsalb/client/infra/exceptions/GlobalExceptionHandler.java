package com.compasspb.vitorsalb.client.infra.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        String path = request.getDescription(false).replace("uri=", "");
        String message = errors.values().stream().findFirst().orElse("Validation error occurred");

        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                message,
                path
        );
        errorMessage.setErrors(errors);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignException(FeignException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateException(DuplicateException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

}