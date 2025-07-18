package com.erp.userservice.middlewares;


import com.erp.commonlib.exceptions.ApiExceptions;
import com.erp.commonlib.exceptions.ErrorDetails;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiExceptions.class)
    public ResponseEntity<ErrorDetails> handleApiExceptions(ApiExceptions exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(
                        exception.getMessage(),
                        request.getDescription(false),
                        HttpStatus.valueOf(exception.getStatus()).name(),
                        LocalDateTime.now()
                ),
                HttpStatus.valueOf(exception.getStatus())
        );
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        var errorList = ex.getAllErrors();
        errorList.forEach(error -> {
            String filedName = ((FieldError) error).getField();
            String FiledValue = error.getDefaultMessage();
            errors.put(filedName, FiledValue);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("errors", errors);
        return new ResponseEntity<>(problemDetail, headers, status);
    }
}
