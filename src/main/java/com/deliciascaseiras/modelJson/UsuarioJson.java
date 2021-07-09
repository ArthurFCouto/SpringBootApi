package com.deliciascaseiras.modelJson;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UsuarioJson {

    @Email(message = "Digite um E-mail válido")
    @Size(max = 60, message = "E-MAIL - Máximo 60 caracteres")
    @NotBlank(message = "E-MAIL - Não pode ser vazio")
    private String email_usuario;

    @Size(max = 42, message = "NOME - Máximo 42 caracteres")
    @NotBlank(message = "NOME - Não pode ser vazio")
    private String nome_usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotBlank(message = "ANIVERSÁRIO - Não pode ser vazio")
    private LocalDate aniversario_usuario;

    @Size(min=11, max = 11, message = "TELEFONE - Informe o DDD e o telefone")
    @NotNull(message = "TELEFONE - Não pode ser vazio")
    private long telefone_usuario;

    @Size(min = 8, message = "SENHA - Mínimo 8 caracteres")
    @NotBlank(message = "SENHA - Não pode ser vazio")
    private String senha_usuario;

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public LocalDate getAniversario_usuario() {
        return aniversario_usuario;
    }

    public void setAniversario_usuario(LocalDate aniversario_usuario) {
        this.aniversario_usuario = aniversario_usuario;
    }

    public long getTelefone_usuario() {
        return telefone_usuario;
    }

    public void setTelefone_usuario(long telefone_usuario) {
        this.telefone_usuario = telefone_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

}