package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.repository.CategoriaProdutoRepository;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ComumUtilService;
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

    @Autowired
    ComumUtilService comumUtilService;

    @Override
    public List<CategoriaProduto> findAll() {
        if(categoriaProdutoRepository.findAll().isEmpty())
            comumUtilService.resourceNotFoundException("Sem categorias cadastradas.");
        return categoriaProdutoRepository.findAll();
    }

    @Override
    public CategoriaProduto findById(long id) {
        comumUtilService.verifyIfCategoriaExists(id);
        return categoriaProdutoRepository.findById(id).get();
    }

    @Override
    public List<CategoriaProduto> findByName(String nome) {
        List<CategoriaProduto> allCategorias = findAll();
        List<String> tagNomes = AppUtil.stringForList(nome);
        Predicate<CategoriaProduto> filterCategoriaList = categoriaProduto -> AppUtil.stringCompareList(categoriaProduto.getNome_categoria(), tagNomes);
        List<CategoriaProduto> categoriasReturn = allCategorias.stream().filter(filterCategoriaList).collect(Collectors.toList());
        if(categoriasReturn.isEmpty())
            comumUtilService.resourceNotFoundException("Sem resultados para exibir.");
        return categoriasReturn;
    }

    @Override
    public void save(CategoriaProduto categoriaProduto) {
        categoriaProdutoRepository.save(categoriaProduto);
    }

    @Override
    public void delete(CategoriaProduto categoriaProduto) {
        comumUtilService.verifyIfBeingUsedCategory(categoriaProduto.getId_categoria()); //Verifica se a categoria está sendo utilizada
        categoriaProdutoRepository.delete(categoriaProduto);
    }
}
