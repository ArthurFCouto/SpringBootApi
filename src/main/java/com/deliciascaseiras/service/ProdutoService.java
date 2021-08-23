package com.deliciascaseiras.service;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.entity.Usuario;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

//É recomendável não utilizamos o repository diretamente, pra isso criamos nossa interface de service
public interface ProdutoService {
    //Buscar todos os produtos
    List<Produto> findAll();
    //Buscar produtos por nome
    List<Produto> findByName(String nome);
    //Buscar produtos por categoria
    List<Produto> findByCategory(CategoriaProduto category);
    //Buscar um produto pela id
    Produto findById(long id);
    //Buscar produtos pelo usuario
    List<Produto> findByUsuario(Usuario usuario);
    //Verifica se existe produtos para o usuário
    boolean productIsPresentUser(Usuario usuario);
    //Salvar um produto
    void save(Produto produto);
    //Deletar um produto
    @Async //Informando que o método será assincrono
    void delete(Produto produto);
}
