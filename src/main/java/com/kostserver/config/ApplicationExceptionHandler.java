package com.kostserver.config;

import com.kostserver.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class ApplicationExceptionHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleInvalidArgument(MethodArgumentNotValidException e){
        ErrorResponseDto errorMap = new ErrorResponseDto();
        List<Map<String, String>> errorListField = new ArrayList<>();
        errorMap.setStatus(HttpStatus.BAD_REQUEST.toString());
        errorMap.setMessage("");
        e.getBindingResult().getFieldErrors().forEach(error ->{
            Map<String, String> errorField = new LinkedHashMap<>();
            errorField.put("field",error.getField());
            errorField.put("message",error.getDefaultMessage());
            errorListField.add(errorField);
        });
        errorMap.setError(errorListField);

        return errorMap;
    }

}

