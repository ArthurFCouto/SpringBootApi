package com.deliciascaseiras.service;

import com.deliciascaseiras.models.CategoriaProduto;

import java.util.List;

public interface CategoriaProdutoService{
    List<CategoriaProduto> findAll();
    CategoriaProduto findById(long id);
    List<CategoriaProduto> findByName(String nome);
    void save(CategoriaProduto categoriaProduto);
    void delete(CategoriaProduto categoriaProduto);
}
