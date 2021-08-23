package com.deliciascaseiras.error.details;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//Classe de personalização do objeto JSON a ser retornado para mostrar os detalhes dos erros
@Getter //Geração dos Getters para as variáveis
@Setter //Geração dos Setters para as variáveis
public class ApiErrorDetails {

    //Variáveis que serão exibidas quando o erro acontecer
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String title;
    private String developerMessage;
    private String error;

    //Definindo a data e hora no momento de geração do erro
    private ApiErrorDetails() {
        timestamp = LocalDateTime.now();
    }

    //Construtor a ser chamado para definir as variáveis
    public ApiErrorDetails(HttpStatus status, String title, String developerMessage, String error) {
        this(); //Chamando o construtor vazio antes de prosseguir
        this.status = status;
        this.title = title;
        this.developerMessage = developerMessage;
        this.error = error;
    }

}
