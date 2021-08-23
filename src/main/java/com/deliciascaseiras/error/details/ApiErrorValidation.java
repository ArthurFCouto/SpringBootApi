package com.deliciascaseiras.error.details;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

//Classe de personalização do objeto JSON a ser retornado para mostrar os detalhes dos erros
//Essa classe será específica para a exception MethodArgumentNotValidException, que são as validações dos campos de cadastro
public class ApiErrorValidation extends ApiErrorDetails { //Ela extende, portando herda seus métodos e argumentos
    private List<ErrorFields> errorList = new ArrayList<ErrorFields>(); //Criamos uma nova variável para ser exibida ao chamar esta classe
    //MethodArgumentNotValidException retorna uma lista de erros, não apenas uma string

    public ApiErrorValidation(HttpStatus status, String title, String developerMessage, String error) {
        super(status, title, developerMessage, error);
    } //Como ela extende, o construtos deverá passar os mesmos parâmetros

    public void addError(String fieldName, String message) {
        this.errorList.add(new ErrorFields(fieldName, message));
    } //Métodos para preenchermos nossa lista de erros

    public List<ErrorFields> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorFields> errorList) {
        this.errorList = errorList;
    }
}
