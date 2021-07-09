package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.models.CategoriaProduto;
import com.deliciascaseiras.repository.CategoriaProdutoRepository;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaProdutoImpl implements CategoriaProdutoService {

    @Autowired
    CategoriaProdutoRepository categoriaProdutoRepository;

    @Override
    public List<CategoriaProduto> findAll() {
        return categoriaProdutoRepository.findAll();
    }

    @Override
    public CategoriaProduto findById(long id) {
        return categoriaProdutoRepository.findById(id).get();
    }

    @Override
    public List<CategoriaProduto> findByName(String nome) {
        List<CategoriaProduto> allCategorias = findAll();
        List<CategoriaProduto> categoriasReturn = new ArrayList<>();
        List<String> tagNomes = new AppUtil().stringForList(nome);
        for (CategoriaProduto categoriaProduto : allCategorias) { //Percorrendo a lista de todas as categorias
            for (String tag_aux : tagNomes) { //Percorrendo a lista de nomes
                if (categoriaProduto.getNome_categoria().toUpperCase().contains(tag_aux.toUpperCase())) //Verificando se a categoria contém o nome da lista
                    categoriasReturn.add(categoriaProduto); //Adicionando a categoria a lista de retorno caso contenha o nome da lista de nomes
            }
        }
        return categoriasReturn;
    }

    @Override
    public void save(CategoriaProduto categoriaProduto) {
        categoriaProdutoRepository.save(categoriaProduto);
    }

    @Override
    public void delete(CategoriaProduto categoriaProduto) {
        categoriaProdutoRepository.delete(categoriaProduto);
    }
}
