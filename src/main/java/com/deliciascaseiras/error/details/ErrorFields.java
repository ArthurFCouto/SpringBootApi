package com.deliciascaseiras.error.details;

import lombok.Getter;
import lombok.Setter;

//Classe para criarmos o objeto que será a nossa lista de erros
@Getter
@Setter
public class ErrorFields {
    private String field;
    private String message;

    public ErrorFields() {
    }

    public ErrorFields(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
