package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.models.CategoriaProduto;
import com.deliciascaseiras.repository.CategoriaProdutoRepository;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        List<String> tagNomes = new AppUtil().stringForList(nome);
        Predicate<CategoriaProduto> filterCategoriaList = categoriaProduto -> new AppUtil().stringCompare(categoriaProduto.getNome_categoria(), tagNomes);
        List<CategoriaProduto> categoriasReturn = allCategorias.stream().filter(filterCategoriaList).collect(Collectors.toList());
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
