package com.deliciascaseiras.models.modelsShow;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.entity.auxEntity.Endereco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioShow {

    private long id_usuario;

    private String nome_usuario;

    private String email_usuario;

    private LocalDate aniversario_usuario;

    private LocalDate data_usuario;

    private LocalDate dataatualizacao_usuario;

    private String produtoList;

    private List<String> roles;

    public UsuarioShow(Usuario usuario) {
        this.id_usuario = usuario.getId_usuario();
        this.nome_usuario = usuario.getNome_usuario();
        this.email_usuario = usuario.getEmail_usuario();
        this.aniversario_usuario = usuario.getAniversario_usuario();
        this.data_usuario = usuario.getData_usuario();
        this.dataatualizacao_usuario = usuario.getDataatualizacao_usuario();
        this.produtoList = "api/user/produto/usuario/"+usuario.getId_usuario();
        List<String> roles = new ArrayList<>();
        for (Role role: usuario.getRoles())
            roles.add(role.getApelido_role());
        this.roles = roles;
    }

    public static List<UsuarioShow> converter(List<Usuario> usuarioList) {
        return usuarioList.stream().map(UsuarioShow::new).collect(Collectors.toList());
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public LocalDate getAniversario_usuario() {
        return aniversario_usuario;
    }

    public LocalDate getData_usuario() {
        return data_usuario;
    }

    public LocalDate getDataatualizacao_usuario() {
        return dataatualizacao_usuario;
    }

    public String getProdutoList() {
        return produtoList;
    }

    public List<String> getRoles() {
        return roles;
    }
}