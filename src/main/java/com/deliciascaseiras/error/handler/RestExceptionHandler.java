package com.deliciascaseiras.error.handler;

import com.deliciascaseiras.error.BadRequestException;
import com.deliciascaseiras.error.ForbiddenException;
import com.deliciascaseiras.error.RequestNoContentException;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.error.details.ApiErrorDetails;
import com.deliciascaseiras.error.details.ApiErrorValidation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice //Permite utilizar a camada RestExceptionHandler através das diversas camadas que o Spring oferece
public class RestExceptionHandler extends ResponseEntityExceptionHandler { //Como ela extende, podemos sobreescrever as classes de erro e personalizar

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException rnfException) {
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails(HttpStatus.NOT_FOUND, "Resource not found", rnfException.getClass().getName(), rnfException.getMessage());
        return new ResponseEntity<>(apiErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestNoContentException.class)
    protected ResponseEntity<Object> handleRequestAcceptedException(
            RequestNoContentException rncException) {
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails(HttpStatus.NO_CONTENT, "No content", rncException.getClass().getName(), rncException.getMessage());
        return new ResponseEntity<>(apiErrorDetails, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(
            ForbiddenException frException) {
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails(HttpStatus.UNAUTHORIZED, "Not authorized", frException.getClass().getName(), frException.getMessage());
        return new ResponseEntity<>(apiErrorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(
            BadRequestException brException) {
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails(HttpStatus.BAD_REQUEST, "Bad Request", brException.getClass().getName(), brException.getMessage());
        return new ResponseEntity<>(apiErrorDetails, HttpStatus.BAD_REQUEST);
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

        ApiErrorDetails apiErrorDetails = new ApiErrorDetails(status, "Internal Exception", ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(apiErrorDetails, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiErrorValidation apiErrorValidation = new ApiErrorValidation(HttpStatus.BAD_REQUEST, "Method Argument Not Valid", ex.getClass().getName(), "Erro durante a validação dos campos.");
        for(FieldError field : ex.getBindingResult().getFieldErrors()) {
            apiErrorValidation.addError(field.getField(), field.getDefaultMessage());
        }
        return new ResponseEntity<>(apiErrorValidation, headers, HttpStatus.BAD_REQUEST);
    }
}