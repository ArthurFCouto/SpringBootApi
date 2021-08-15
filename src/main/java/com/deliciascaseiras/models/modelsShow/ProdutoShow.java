package com.deliciascaseiras.models.modelsShow;

import com.deliciascaseiras.entity.Produto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoShow{

    private long id_produto;

    private String nome_produto;

    private String sabor_produto;

    private float preco_produto;

    private String categoria_produto;

    private String nomeCategoria_produto;

    private boolean disponivel_produto;

    private String detalhe_produto;

    private LocalDate data_produto;

    private LocalDate dataatualizacao_produto;

    private String usuario_produto;

    private String nomeUsuario_produto;

    public ProdutoShow(Produto produto) {
        this.id_produto = produto.getId_produto();
        this.nome_produto = produto.getNome_produto();
        this.sabor_produto = produto.getSabor_produto();
        this.preco_produto = produto.getPreco_produto();
        this.categoria_produto = "api/user/categoria/"+produto.getCategoria_produto().getNome_categoria();
        this.nomeCategoria_produto = produto.getCategoria_produto().getNome_categoria();
        this.disponivel_produto = produto.isDisponivel_produto();
        this.detalhe_produto = produto.getDetalhe_produto();
        this.data_produto = produto.getData_produto();
        this.dataatualizacao_produto = produto.getDataatualizacao_produto();
        this.usuario_produto = "api/user/produto/"+produto.getUsuario_produto().getId_usuario();
        this.nomeUsuario_produto = produto.getUsuario_produto().getNome_usuario();
    }

    public static List<ProdutoShow> converter(List<Produto> produtoList) {
        return produtoList.stream().map(ProdutoShow::new).collect(Collectors.toList());
    }

    public long getId_produto() {
        return id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public String getSabor_produto() {
        return sabor_produto;
    }

    public float getPreco_produto() {
        return preco_produto;
    }

    public String getCategoria_produto() {
        return categoria_produto;
    }

    public String getNomeCategoria_produto() {
        return nomeCategoria_produto;
    }

    public boolean isDisponivel_produto() {
        return disponivel_produto;
    }

    public String getDetalhe_produto() {
        return detalhe_produto;
    }

    public LocalDate getData_produto() {
        return data_produto;
    }

    public LocalDate getDataatualizacao_produto() {
        return dataatualizacao_produto;
    }

    public String getUsuario_produto() {
        return usuario_produto;
    }

    public String getNomeUsuario_produto() {
        return nomeUsuario_produto;
    }
}
