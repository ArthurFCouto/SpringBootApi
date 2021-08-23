package com.deliciascaseiras.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Iformando a qual status esta classe se refere
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException { //Quando extendemos ao RunTimeException, estamos dizendo que será em tempo de execução

    public BadRequestException() {
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
