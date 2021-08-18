package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.repository.ProdutoRepository;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ComumUtilServiceImpl comumUtilService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public List<Produto> findAll() {
        if(produtoRepository.findAll().isEmpty())
            comumUtilService.noContentException("Sem resultados para exibir.");
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> findByName(String nome) {
        List<Produto> todosProdutos = findAll();
        List<String> tagProdutos = AppUtil.stringForList(nome);
        //Com o predicate criamos a condição para o filtro da lista
        Predicate<Produto> filterProdutoList = produto -> AppUtil.stringCompareList(produto.getNome_produto(), tagProdutos);
        List<Produto> produtosReturn = todosProdutos.stream().filter(filterProdutoList).collect(Collectors.toList());
        if(produtosReturn.isEmpty())
            comumUtilService.noContentException("Sem resultados para exibir.");
        return produtosReturn;
    }

    @Override
    public Produto findById(long id) {
        comumUtilService.verifyIfProdutoExists(id);
        return produtoRepository.findById(id).get();
    }

    @Override
    public List<Produto> findByUsuario(Usuario usuario) {
        List<Produto> allProdutos = findAll();
        //No método abaixo, primeiro criamos uma strem a partir de todos os produtos
        //Depois filtramos de acordo com nossas condições
        //Depois criamos uma nova coleção com os produtos filtrados.
        List<Produto> produtosReturn = allProdutos.stream().filter(produto -> produto.getUsuario_produto().equals(usuario)).collect(Collectors.toList());
        if(produtosReturn.isEmpty())
            comumUtilService.noContentException("Sem resultados para exibir.");
        return produtosReturn;
    }

    @Override
    public void save(Produto produto) {
        produtoRepository.save(produto);
    }

    @Override
    public void delete(Produto produtos) {
        produtoRepository.delete(produtos);
    }

    @Override
    public List<Produto> findByCategory(CategoriaProduto category) {
        List<Produto> todosProdutos = produtoRepository.findAll();
        List<Produto> produtosReturn = todosProdutos.stream().filter(produto -> produto.getCategoria_produto().equals(category)).collect(Collectors.toList());
        /*for (Produto produto : todosProdutos) {
            if (produto.getCategoria_produto().equals(category))
                produtosReturn.add(produto);
        }*/
        if(produtosReturn.isEmpty())
            comumUtilService.noContentException("Sem resultados para exibir.");
        return produtosReturn;
    }
}
