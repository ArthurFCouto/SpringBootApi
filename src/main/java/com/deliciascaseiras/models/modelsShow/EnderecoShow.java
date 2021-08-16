package com.deliciascaseiras.models.modelsShow;

import com.deliciascaseiras.entity.auxEntity.Endereco;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoShow {
    private long id_endereco;

    private String logradouro_endereco;

    private String numero_endereco;

    private String complemento_endereco;

    private String cep_endereco;

    private String cidade_endereco;

    private String uf_endereco;

    private String usuario_endereco;

    private String nomeUsuario_endereco;

    public EnderecoShow(Endereco endereco) {
        this.id_endereco = endereco.getId_endereco();
        this.cep_endereco = endereco.getCep_endereco();
        this.logradouro_endereco = endereco.getLogradouro_endereco();
        this.numero_endereco = endereco.getNumero_endereco();
        this.complemento_endereco = endereco.getComplemento_endereco();
        this.cidade_endereco = endereco.getCidade_endereco();
        this.uf_endereco = endereco.getUf_endereco();
        this.usuario_endereco = "api/auth/usuario"+endereco.getUsuario_endereco().getId_usuario();
        this.nomeUsuario_endereco = endereco.getUsuario_endereco().getNome_usuario();
    }

    public static List<EnderecoShow> converter(List<Endereco> enderecoList) {
        return enderecoList.stream().map(EnderecoShow::new).collect(Collectors.toList());
    }

    public long getId_endereco() {
        return id_endereco;
    }

    public String getLogradouro_endereco() {
        return logradouro_endereco;
    }

    public String getNumero_endereco() {
        return numero_endereco;
    }

    public String getComplemento_endereco() {
        return complemento_endereco;
    }

    public String getCep_endereco() {
        return cep_endereco;
    }

    public String getCidade_endereco() {
        return cidade_endereco;
    }

    public String getUf_endereco() {
        return uf_endereco;
    }

    public String getUsuario_endereco() {
        return usuario_endereco;
    }

    public String getNomeUsuario_endereco() {
        return nomeUsuario_endereco;
    }
}
