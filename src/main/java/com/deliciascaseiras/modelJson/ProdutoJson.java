package com.deliciascaseiras.modelJson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProdutoJson {

    @Size(max = 25, message = "NOME de no máximo 25 caracteres")
    @NotBlank(message = "NOME não pode ser vazio")
    private String nome_produto;

    @Size(max = 25, message = "SABOR de no máximo 25 caracteres")
    @NotBlank(message = "SABOR não pode ser vazio")
    private String sabor_produto;

    @NotNull(message = "PREÇO não pode ser vazio")
    private float preco_produto;

    @NotNull(message = "DISPONIBILIDADE não pode ser vazio")
    private boolean disponivel_produto;

    @Size(max = 120, message = "DETALHES - Máximo 120 caracteres.")
    private String detalhe_produto;

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
