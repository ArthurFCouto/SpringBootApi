package com.deliciascaseiras.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class RequestAcceptedException extends RuntimeException{
    public RequestAcceptedException(String message) {
        super(message);
    }
}
