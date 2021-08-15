package com.deliciascaseiras.entity;

import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.entity.auxEntity.Endereco;
import com.deliciascaseiras.util.AppUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Table(name="usuario")
@Entity
public class Usuario implements Serializable, UserDetails {
    //Implementando a Serializable para que o Spring gere os ID do usuário automáticamente
    //implementando o UserDetails para que possamos fazer login com os usuarios cadastrados

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_usuario;

    @Column(unique = true)
    private String email_usuario;

    private String nome_usuario;

    private LocalDate aniversario_usuario;

    private long telefone_usuario;

    @JsonIgnore
    private String senha_usuario;

    private LocalDate data_usuario;

    private LocalDate dataatualizacao_usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario_produto")
    private List<Produto> produtoList;

    //@OneToMany(mappedBy = "usuario_endereco")
    @OneToOne
    //private List<Endereco> enderecoList;
    private Endereco enderecoList;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "nome_role")) //Primeira coluna
    private List<Role> roles;

    public Usuario() {
    }

    public Usuario(String email_usuario, String nome_usuario, LocalDate data_usuario, String senha_usuario, List<Role> roles) {
        setEmail_usuario(email_usuario);
        setNome_usuario(nome_usuario);
        setAniversario_usuario(data_usuario);
        setTelefone_usuario(Long.parseLong("12345678901"));
        setSenha_usuario(new BCryptPasswordEncoder().encode(senha_usuario));
        setData_usuario(data_usuario);
        setDataatualizacao_usuario(data_usuario);
        setRoles(roles);
    }

    public Usuario(String email_usuario, String nome_usuario, LocalDate aniversario_usuario, long telefone_usuario, String senha_usuario, List<Role> roles) {
        this.email_usuario = email_usuario;
        this.nome_usuario = nome_usuario;
        this.aniversario_usuario = aniversario_usuario;
        this.telefone_usuario = telefone_usuario;
        this.senha_usuario = new BCryptPasswordEncoder().encode(senha_usuario);
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{" +
                "id_usuario: " + id_usuario +
                ", email_usuario: " + email_usuario +
                ", nome_usuario: " + nome_usuario +
                ", aniversario_usuario: " + aniversario_usuario +
                ", telefone_usuario: " + telefone_usuario +
                ", produtoList: "+AppUtil.baseURL()+"api/user/produto/usuario/"+id_usuario +
                ", data_usuario: " + data_usuario +
                ", dataatualizacao_usuario: " + dataatualizacao_usuario +
                ", roles: " + roles +
                '}';
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario.toLowerCase();
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

    public LocalDate getData_usuario() {
        return data_usuario;
    }

    public void setData_usuario(LocalDate data_usuario) {
        this.data_usuario = data_usuario;
    }

    public LocalDate getDataatualizacao_usuario() {
        return dataatualizacao_usuario;
    }

    public void setDataatualizacao_usuario(LocalDate dataatualizacao_usuario) {
        this.dataatualizacao_usuario = dataatualizacao_usuario;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    public Endereco getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(Endereco enderecoList) {
        this.enderecoList = enderecoList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return this.senha_usuario;
    } //Definindo no UserDetails qual será a senha utilizada

    @Override
    public String getUsername() {
        return this.email_usuario;
    } //Informando qual será o login(userName) utilizado

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } //Informando que a conta nunca expira

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //Informando que a conta não está bloqueada

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } //Informando que as credenciais não expiram

    @Override
    public boolean isEnabled() {
        return true;
    } //Informando que a conta está ativa

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Trabalhar a questão das Roles
        return (Collection<? extends GrantedAuthority>) this.roles;
    }
}