package com.deliciascaseiras.models;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.auxEntity.Endereco;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EnderecoModel{

    @Size(max = 45, message = "LOGRADOURO - Máximo 45 caracteres")
    @NotBlank(message = "LOGRADOURO - Não pode ser vazio")
    private String logradouro;

    @Size(max = 8, message = "NÚMERO - Máximo 8 caracteres")
    @NotBlank(message = "NÚMERO - Não pode ser vazio")
    private String numero;

    @Size(max = 35, message = "BAIRRO - Máximo 35 caracteres")
    @NotBlank(message = "BAIRRO - Não pode ser vazio")
    private String bairro;

    @Size(max = 45, message = "COMPLEMENTO - Máximo 45 caracteres")
    private String complemento;

    @Size(max = 10, message = "CEP - Insira apenas números")
    @NotBlank(message = "CEP - Não pode ser vazio")
    private String cep;

    @Size(max = 40, message = "CIDADE - Máximo 40 caracteres")
    @NotBlank(message = "CIDADE - Não pode ser vazio")
    private String cidade;

    @Size(max = 2, message = "UF - Informe abreviado (XX)")
    @NotBlank(message = "UF - Não pode ser vazio")
    private String uf;

    public Endereco converter(Usuario usuario, ComumUtilService comumUtilService, UsuarioService usuarioService){
        if(usuario.getEnderecoList().toArray().length == 3)
            comumUtilService.badRequestException("Não é possível cadastrar mais endereços (Máximo permitido: 3)");
        Endereco endereco = new Endereco(logradouro, numero, bairro, complemento, cep, cidade, uf, usuario);
        return endereco;
    }

    public Endereco update(Endereco endereco, Usuario usuario, ComumUtilService comumUtilService){
        if(!endereco.getUsuario_endereco().equals(usuario))
            comumUtilService.badRequestException("Não foi possível atualizar o endereço (Pertence a outro usuário).");
        endereco.setCep_endereco(getCep());
        endereco.setLogradouro_endereco(getLogradouro());
        endereco.setNumero_endereco(getNumero());
        endereco.setBairro_endereco(getBairro());
        endereco.setComplemento_endereco(getComplemento());
        endereco.setCidade_endereco(getCidade());
        endereco.setUf_endereco(getUf());
        return endereco;
    }

}
