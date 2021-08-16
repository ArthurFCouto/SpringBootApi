package com.deliciascaseiras.entity.auxEntity;

import com.deliciascaseiras.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="endereco")
@Entity
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_endereco;

    private String logradouro_endereco;

    private String numero_endereco;

    private String complemento_endereco;

    private String cep_endereco;

    private String cidade_endereco;

    private String uf_endereco;

    @JsonIgnore
    /*@ManyToOne
    @JoinTable(
            name = "endereco_usuario",
            joinColumns = @JoinColumn(
                    name = "endereco_id", referencedColumnName = "id_endereco"),
            inverseJoinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id_usuario"))*/
    @OneToOne
    private Usuario usuario_endereco;

    public Endereco() {
    }

    public Endereco(String logradouro_endereco, String numero_endereco, String complemento_endereco, String cep_endereco, String cidade_endereco, String uf_endereco, Usuario usuario_endereco) {
        this.logradouro_endereco = logradouro_endereco;
        this.numero_endereco = numero_endereco;
        this.complemento_endereco = complemento_endereco;
        this.cep_endereco = cep_endereco;
        this.cidade_endereco = cidade_endereco;
        this.uf_endereco = uf_endereco;
        this.usuario_endereco = usuario_endereco;
    }

    public long getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(long id_endereco) {
        this.id_endereco = id_endereco;
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

    public Usuario getUsuario_endereco() {
        return usuario_endereco;
    }

    public void setUsuario_endereco(Usuario usuario_endereco) {
        this.usuario_endereco = usuario_endereco;
    }
}
