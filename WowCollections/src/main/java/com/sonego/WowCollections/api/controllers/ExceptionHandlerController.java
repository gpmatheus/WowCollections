package com.sonego.WowCollections.api.controllers;

import com.sonego.WowCollections.api.models.ErrorModel;
import com.sonego.WowCollections.business.exceptions.RequestErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(RequestErrorException.class)
    public ResponseEntity<ErrorModel> requestErrorExceptionController(RequestErrorException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrorModel());
    }
}
