package com.sonego.WowCollections.business.exceptions;

import com.sonego.WowCollections.api.models.ErrorModel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestErrorException extends RuntimeException {

    private ErrorModel errorModel;
    private HttpStatus statusCode;

    public RequestErrorException(String message, ErrorModel errorModel, HttpStatus statusCode) {
        super(message);
        this.errorModel = errorModel;
        this.statusCode = statusCode;
    }

    public RequestErrorException(ErrorModel errorModel, HttpStatus statusCode) {
        this.errorModel = errorModel;
        this.statusCode = statusCode;
    }
}
