package com.deliciascaseiras.entity.admEntity;

import com.deliciascaseiras.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Table(name="roles") //Informando o nome da tabela no banco de dados
@Entity //Informando que esta classe será uma entidade do banco de dados
public class Role implements GrantedAuthority{

    @Id
    private String nome_role; //ID da Role

    @NotEmpty
    private String apelido_role; //Nome para identificação da Role

    @JsonIgnore //Se voltarmos um dados da entidade diretamente para o usuário, este será ignorado e não será exibido
    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    public Role() {
        //Sempre precisamos de um contrutor vazio em uma Entity quando criamos um outro construtor.
    }

    public Role(String nome_role, String apelido_role) {
        this.nome_role = nome_role;
        this.apelido_role = apelido_role;
    }

    @Override
    public String toString() {
        return "{" +
                "nome_role='" + nome_role + '\'' +
                ", apelido_role='" + apelido_role + '\'' +
                '}';
    }

    public String getNome_role() {
        return nome_role;
    }

    public void setNome_role(String nome_role) {
        this.nome_role = nome_role.toUpperCase();
    }

    public String getApelido_role() {
        return apelido_role;
    }

    public void setApelido_role(String apelido_role) {
        this.apelido_role = apelido_role;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String getAuthority() {
        return this.nome_role;
    }
}