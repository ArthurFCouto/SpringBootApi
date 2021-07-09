package com.deliciascaseiras.models;

import com.deliciascaseiras.modelJson.ProdutoJson;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name="produto")
@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id_produto;

    @Size(max = 25, message = "NOME de no máximo 30 caracteres")
    @NotBlank(message = "NOME não pode ser vazio")
    private String nome_produto;

    @Size(max = 25, message = "SABOR de no máximo 35 caracteres")
    @NotBlank(message = "SABOR não pode ser vazio")
    private String sabor_produto;

    @NotNull(message = "PREÇO não pode ser vazio")
    private float preco_produto;

    @ManyToOne //Relação muitos para um com categoria
    @JoinTable(
            name = "produto_categoria",
            joinColumns = @JoinColumn(
                    name = "produto_id", referencedColumnName = "id_produto"),
            inverseJoinColumns = @JoinColumn(
                    name = "categoria_id", referencedColumnName = "id_categoria"))
    private CategoriaProduto categoria_produto;

    @NotNull(message = "DISPONIBILIDADE não pode ser vazio")
    private boolean disponivel_produto;

    @Lob
    @Size(max = 120, message = "DETALHES - Máximo 120 caracteres.")
    private String detalhe_produto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data_produto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataatualizacao_produto;

    @ManyToOne //Relação muitos para um com usuário
    @JoinTable(
            name = "produto_usuario",
            joinColumns = @JoinColumn(
                    name = "produto_id", referencedColumnName = "id_produto"),
            inverseJoinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id_usuario")) //Aqui criamos uma tabela para o relacionamento entre o usuario e os produtos
    private Usuario usuario_produto;

    public Produto() {
    }

    public Produto(ProdutoJson produtoJson, Usuario usuario, CategoriaProduto categoriaProduto) {
        setNome_produto(produtoJson.getNome_produto());
        setSabor_produto(produtoJson.getSabor_produto());
        setPreco_produto(produtoJson.getPreco_produto());
        setDisponivel_produto(produtoJson.isDisponivel_produto());
        setDetalhe_produto(produtoJson.getDetalhe_produto());
        setDataatualizacao_produto(LocalDate.now());
        setUsuario_produto(usuario);
        setCategoria_produto(categoriaProduto);
    }

    public long getId_produto() {
        return id_produto;
    }

    public void setId_produto(long id_produto) {
        this.id_produto = id_produto;
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

    public CategoriaProduto getCategoria_produto() {
        return categoria_produto;
    }

    public void setCategoria_produto(CategoriaProduto categoria_produto) {
        this.categoria_produto = categoria_produto;
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

    public Usuario getUsuario_produto() {
        return usuario_produto;
    }

    public void setUsuario_produto(Usuario usuario_produto) {
        this.usuario_produto = usuario_produto;
    }

    public LocalDate getData_produto() {
        return data_produto;
    }

    public void setData_produto(LocalDate data_produto) {
        this.data_produto = data_produto;
    }

    public LocalDate getDataatualizacao_produto() {
        return dataatualizacao_produto;
    }

    public void setDataatualizacao_produto(LocalDate dataatualizacao_produto) {
        this.dataatualizacao_produto = dataatualizacao_produto;
    }
}
