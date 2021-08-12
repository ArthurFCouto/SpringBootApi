package com.deliciascaseiras.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class RequestNoContentException extends RuntimeException{
    public RequestNoContentException(String message) {
        super(message);
    }
}
