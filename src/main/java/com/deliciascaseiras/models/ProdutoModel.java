package com.deliciascaseiras.models;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class ProdutoModel{

    @Size(max = 25, message = "NOME - No máximo 25 caracteres")
    @NotBlank(message = "NOME - Não pode ser vazio")
    private String nome;

    @Size(max = 25, message = "SABOR - No máximo 25 caracteres")
    @NotBlank(message = "SABOR - Não pode ser vazio")
    private String sabor;

    @Min(value = 1, message = "PREÇO - Não pode ser vazio.")
    @Max(value = 999, message = "PREÇO - No máximo R$ 999.")
    private float preco;

    private boolean disponivel;

    @Lob
    @Size(max = 120, message = "DETALHES - Máximo 120 caracteres.")
    private String detalhe;

    public Produto converter(Usuario usuario, CategoriaProduto categoriaProduto, UsuarioService usuarioService, ComumUtilService comumUtilService) {
        if(usuarioService.verifyIsAdmin(usuario))
            comumUtilService.badRequestException("Não foi possível cadastrar o produto (Não autorizado para o perfil do usuário).");
        Produto produto = new Produto(nome, sabor, categoriaProduto, preco, disponivel, detalhe, usuario);
        produto.setData_produto(LocalDate.now());
        produto.setDataatualizacao_produto(LocalDate.now());
        return produto;
    }

    public Produto update(Produto produto, Usuario usuario, CategoriaProduto categoriaProduto, ComumUtilService comumUtilService) {
        if (produto.getUsuario_produto() != usuario)
            comumUtilService.badRequestException("Não foi possível atualizar o produto (Pertence a outro usuário).");
        produto.setDetalhe_produto(getDetalhe());
        produto.setNome_produto(getNome());
        produto.setPreco_produto(getPreco());
        produto.setSabor_produto(getSabor());
        produto.setCategoria_produto(categoriaProduto);
        produto.setDisponivel_produto(isDisponivel());
        produto.setDataatualizacao_produto(LocalDate.now());
        return produto;
    }
}
