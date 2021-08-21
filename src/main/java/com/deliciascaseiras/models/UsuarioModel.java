package com.deliciascaseiras.models;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UsuarioModel {

    @Size(max = 45, message = "NOME - Máximo 45 caracteres")
    @NotBlank(message = "NOME - Não pode ser vazio")
    private String nome;

    @Column(unique = true)
    @Email(message = "E-MAIL - Digite um E-mail válido")
    @Size(max = 45, message = "E-MAIL - Máximo 45 caracteres")
    @NotBlank(message = "E-MAIL - Não pode ser vazio")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "ANIVERSÁRIO - Deve ser anterior a hoje")
    private LocalDate aniversario;

    @DecimalMin(value = "10000000000", inclusive = false, message = "TELEFONE - Informe o DDD e o telefone (Somente numeros)")
    @Digits(integer=11, fraction=0, message = "TELEFONE - Informe o DDD e o telefone (Somente numeros)")
    private long telefone;

    @Size(min = 8, max = 12, message = "SENHA - Entre 8 e 12 caracteres")
    @NotBlank(message = "SENHA - Não pode ser vazio")
    private String senha;

    public Usuario converter(ComumUtilService comumUtilService, RoleRepository roleRepository, UsuarioService usuarioService) {
        if (getEmail().toLowerCase().contains(AppUtil.emailAdmin()))
            comumUtilService.badRequestException("Informe um e-mail válido.");
        if (usuarioService.emailIsPresent(getEmail()))
            comumUtilService.badRequestException("Email já cadastrado! Informe outro e-mail.");
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById("ROLE_USER").get());
        Usuario usuario = new Usuario(email, nome, aniversario, telefone, senha, roles);
        usuario.setData_usuario(LocalDate.now());
        usuario.setDataatualizacao_usuario(LocalDate.now());
        return usuario;
    }

    public Usuario update(Usuario usuario, UsuarioService usuarioService, ComumUtilService comumUtilService) {
        if(!usuario.getEmail_usuario().equals(getEmail())) { //Se o usuário estiver atualizando o e-mail fazemos essa verificação, se o e-mail for o mesmo não
            if (getEmail().toLowerCase().contains(AppUtil.emailAdmin()))
                comumUtilService.badRequestException("Informe um e-mail válido.");
            if (usuarioService.emailIsPresent(getEmail()))
                comumUtilService.badRequestException("Email já cadastrado! Informe outro e-mail.");
        }
        usuario.setEmail_usuario(getEmail());
        usuario.setNome_usuario(getNome());
        usuario.setAniversario_usuario(getAniversario());
        usuario.setTelefone_usuario(getTelefone());
        usuario.setSenha_usuario(new BCryptPasswordEncoder().encode(usuario.getSenha_usuario()));
        usuario.setDataatualizacao_usuario(LocalDate.now());
        return usuario;
    }
}