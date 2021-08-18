package com.deliciascaseiras.models;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.repository.RoleRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioModel {

    @Size(max = 45, message = "NOME - Máximo 45 caracteres")
    @NotBlank(message = "NOME - Não pode ser vazio")
    private String nome_usuario;

    @Email(message = "E-MAIL - Digite um E-mail válido")
    @Size(max = 45, message = "E-MAIL - Máximo 45 caracteres")
    @NotBlank(message = "E-MAIL - Não pode ser vazio")
    private String email_usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "ANIVERSÁRIO - Deve ser anterior a hoje")
    private LocalDate aniversario_usuario;

    @DecimalMin(value = "10000000000", inclusive = false, message = "TELEFONE - Informe o DDD e o telefone (Somente numeros)")
    @Digits(integer=11, fraction=0, message = "TELEFONE - Informe o DDD e o telefone (Somente numeros)")
    private long telefone_usuario;

    @Size(min = 8, max = 12, message = "SENHA - Entre 8 e 12 caracteres")
    @NotBlank(message = "SENHA - Não pode ser vazio")
    private String senha_usuario;

    public Usuario converter(RoleRepository roleRepository) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById("ROLE_USER").get());
        return new Usuario(email_usuario, nome_usuario, aniversario_usuario, telefone_usuario, senha_usuario, roles);
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
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