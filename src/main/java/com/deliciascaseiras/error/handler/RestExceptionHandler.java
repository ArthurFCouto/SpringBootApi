package com.deliciascaseiras.error.handler;

import com.deliciascaseiras.error.BadRequestException;
import com.deliciascaseiras.error.ForbiddenException;
import com.deliciascaseiras.error.RequestNoContentException;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.error.details.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //Permite utilizar a camada RestExceptionHandler através das diversas camadas que o Spring oferece
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException rnfException) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Resource not found", rnfException);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestNoContentException.class)
    protected ResponseEntity<Object> handleRequestAcceptedException(
            RequestNoContentException rncException) {
        ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, "No content", rncException);
        return new ResponseEntity<>(apiError, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(
            ForbiddenException frException) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Not authorized", frException);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(
            BadRequestException brException) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Bad Request", brException);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //O método abaixo é chamado por todos os exceptions não informados aqui no hendler.
    //Isso só é possível porque esta classe foi extendida
    //Todos os métodos que chamarem esse exception exibirão a mensagem de erro conforme definimos abaixo
    @Override //Override sobrescreve o método padrão, o sistema utilizará esse
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        ApiError apiError = new ApiError(status, "Internal Exception", ex);
        return new ResponseEntity<>(apiError, headers, status);
    }
}