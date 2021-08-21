package com.deliciascaseiras.models.modelsShow;

import com.deliciascaseiras.entity.Produto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProdutoShow{

    private long id_produto;

    private String nome_produto;

    private String sabor_produto;

    private float preco_produto;

    private long categoria_produto;

    private String nomecategoria_produto;

    private boolean disponivel_produto;

    private String detalhe_produto;

    private LocalDate datacriacao_produto;

    private LocalDate dataatualizacao_produto;

    private long usuario_produto;

    private String nomeUsuario_produto;

    public ProdutoShow(Produto produto) {
        this.id_produto = produto.getId_produto();
        this.nome_produto = produto.getNome_produto();
        this.sabor_produto = produto.getSabor_produto();
        this.preco_produto = produto.getPreco_produto();
        this.categoria_produto = produto.getCategoria_produto().getId_categoria();
        this.nomecategoria_produto = produto.getCategoria_produto().getNome_categoria();
        this.disponivel_produto = produto.isDisponivel_produto();
        this.detalhe_produto = produto.getDetalhe_produto();
        this.datacriacao_produto = produto.getData_produto();
        this.dataatualizacao_produto = produto.getDataatualizacao_produto();
        this.usuario_produto = produto.getUsuario_produto().getId_usuario();
        this.nomeUsuario_produto = produto.getUsuario_produto().getNome_usuario();
    }

    public static List<ProdutoShow> converter(List<Produto> produtoList) {
        return produtoList.stream().map(ProdutoShow::new).collect(Collectors.toList());
    }
}
