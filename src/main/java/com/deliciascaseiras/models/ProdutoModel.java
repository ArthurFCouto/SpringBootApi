package com.deliciascaseiras.models;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.entity.Usuario;

import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProdutoModel{

    @Size(max = 25, message = "NOME - No máximo 25 caracteres")
    @NotBlank(message = "NOME - Não pode ser vazio")
    private String nome_produto;

    @Size(max = 25, message = "SABOR - No máximo 25 caracteres")
    @NotBlank(message = "SABOR - Não pode ser vazio")
    private String sabor_produto;

    @Min(value = 1, message = "PREÇO - Não pode ser vazio.")
    @Max(value = 999, message = "PREÇO - No máximo R$ 999.")
    private float preco_produto;

    private boolean disponivel_produto;

    @Lob
    @Size(max = 120, message = "DETALHES - Máximo 120 caracteres.")
    private String detalhe_produto;

    public Produto converter(Usuario usuario, CategoriaProduto categoriaProduto) {
        return new Produto(nome_produto, sabor_produto, categoriaProduto, preco_produto, disponivel_produto, detalhe_produto, usuario);
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getSabor_produto() {
        return sabor_produto;
    }

    public void setSabor_produto(String sabor_produto) {
        this.sabor_produto = sabor_produto;
    }

    public float getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(float preco_produto) {
        this.preco_produto = preco_produto;
    }

    public boolean isDisponivel_produto() {
        return disponivel_produto;
    }

    public void setDisponivel_produto(boolean disponivel_produto) {
        this.disponivel_produto = disponivel_produto;
    }

    public String getDetalhe_produto() {
        return detalhe_produto;
    }

    public void setDetalhe_produto(String detalhe_produto) {
        this.detalhe_produto = detalhe_produto;
    }
}
