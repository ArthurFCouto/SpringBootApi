package com.deliciascaseiras.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Table(name="categoriaproduto") //Informando qual será o nome da tabela
@Entity //Informando que esta classe será uma entidade no banco de dados
public class CategoriaProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id //Informando quem será o ID da tabela
    @GeneratedValue(strategy = GenerationType.AUTO) //Gerando o ID automáticamente
    private long id_categoria;

    @Column(unique = true) //Valor único para a coluna, como se fosse um ID
    @Size(max = 25, message = "NOME - No máximo 25 caracteres") //Tamanho máximo, 25 caracteres
    @NotBlank //Não pode ser vazio ou nulo
    private String nome_categoria;

    @JsonIgnore //Essa informação será ignorada nas solicitações
    @OneToMany(mappedBy = "categoria_produto") //Relação de um para muitos com o produto
    private List<Produto> produtos_categoria;

    public CategoriaProduto() {

    }

    @Override
    public String toString() {
        return "{" +
                "id_categoria=" + id_categoria +
                ", nome_categoria='" + nome_categoria + '\'' +
                '}';
    }

    public CategoriaProduto(String nome_categoria) {
        setNome_categoria(nome_categoria);
    }

    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        nome_categoria = nome_categoria.toLowerCase(); //Deixando todas as letras em minúsculo
        nome_categoria = nome_categoria.substring(0,1).toUpperCase().concat(nome_categoria.substring(1)); //Tornando apenas a primeira letra maiúscula
        this.nome_categoria = nome_categoria;
    }

}
