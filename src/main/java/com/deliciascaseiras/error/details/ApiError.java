package com.deliciascaseiras.error.details;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//Classe de personalização do objeto JSON a ser retornado para mostrar os detalhes dos erros
public class ApiError {

    //Variáveis que serão exibidas quando o erro acontecer
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String title;
    private String message;
    private String debugMessage;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    //Definindo a data e hora no momento de geração do erro
    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    //Construtor a ser chamado para definir as variáveis
    public ApiError(HttpStatus status, String title, Throwable ex) {
        this();
        this.status = status;
        this.title = title;
        this.debugMessage = ex.getClass().getName();
        this.message = ex.getMessage();
    }
}
