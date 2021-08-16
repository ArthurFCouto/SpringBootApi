package com.deliciascaseiras.models;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.auxEntity.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class EnderecoModel{

    @Size(max = 45, message = "LOGRADOURO - Máximo 45 caracteres")
    @NotBlank(message = "LOGRADOURO - Não pode ser vazio")
    private String logradouro_endereco;

    @Size(max = 8, message = "NÚMERO - Máximo 8 caracteres")
    @NotBlank(message = "NÚMERO - Não pode ser vazio")
    private String numero_endereco;

    @Size(max = 35, message = "BAIRRO/COMPLEMENTO - Máximo 70 caracteres")
    private String complemento_endereco;

    @Size(max = 10, message = "CEP - Insira apenas números")
    @NotBlank(message = "CEP - Não pode ser vazio")
    private String cep_endereco;

    @Size(max = 40, message = "CIDADE - Máximo 40 caracteres")
    @NotBlank(message = "CIDADE - Não pode ser vazio")
    private String cidade_endereco;

    @Size(max = 2, message = "UF - Informe abreviado (XX)")
    @NotBlank(message = "UF - Não pode ser vazio")
    private String uf_endereco;

    public Endereco converter(Usuario usuario){
        return new Endereco(logradouro_endereco, numero_endereco, complemento_endereco, cep_endereco, cidade_endereco, uf_endereco, usuario);
    }

    public String getLogradouro_endereco() {
        return logradouro_endereco;
    }

    public void setLogradouro_endereco(String logradouro_endereco) {
        this.logradouro_endereco = logradouro_endereco;
    }

    public String getNumero_endereco() {
        return numero_endereco;
    }

    public void setNumero_endereco(String numero_endereco) {
        this.numero_endereco = numero_endereco;
    }

    public String getComplemento_endereco() {
        return complemento_endereco;
    }

    public void setComplemento_endereco(String complemento_endereco) {
        this.complemento_endereco = complemento_endereco;
    }

    public String getCep_endereco() {
        return cep_endereco;
    }

    public void setCep_endereco(String cep_endereco) {
        this.cep_endereco = cep_endereco;
    }

    public String getCidade_endereco() {
        return cidade_endereco;
    }

    public void setCidade_endereco(String cidade_endereco) {
        this.cidade_endereco = cidade_endereco;
    }

    public String getUf_endereco() {
        return uf_endereco;
    }

    public void setUf_endereco(String uf_endereco) {
        this.uf_endereco = uf_endereco;
    }
}
