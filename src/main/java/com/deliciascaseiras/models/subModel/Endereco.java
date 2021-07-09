package com.deliciascaseiras.models.subModel;

import com.deliciascaseiras.models.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Table(name="endereco")
@Entity
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_endereco;

    @Size(max = 45, message = "LOGRADOURO - Máximo 45 caracteres")
    @NotEmpty(message = "LOGRADOURO - Não pode ser vazio")
    private String logradouro_endereco;

    @Size(max = 8, message = "NÚMERO - Máximo 8 caracteres")
    @NotEmpty(message = "NÚMERO - Não pode ser vazio")
    private String numero_endereco;

    @Size(max = 36, message = "BAIRRO/COMPLEMENTO - Máximo 36 caracteres")
    private String complemento_endereco;

    @Size(max = 10, message = "CEP - Insira apenas números")
    @NotEmpty(message = "CEP - Não pode ser vazio")
    private String cep_endereco;

    @NotBlank(message = "CIDADE - Não pode ser vazio")
    private String cidade_endereco;

    @NotBlank(message = "UF - Não pode ser vazio")
    private String uf_endereco;

    @JsonIgnore
    @ManyToOne
    @JoinTable(
            name = "endereco_usuario",
            joinColumns = @JoinColumn(
                    name = "endereco_id", referencedColumnName = "id_endereco"),
            inverseJoinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id_usuario"))
    private Usuario usuario_endereco;

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
