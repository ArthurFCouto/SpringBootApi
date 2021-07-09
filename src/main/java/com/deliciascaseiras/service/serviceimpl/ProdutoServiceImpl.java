package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.models.CategoriaProduto;
import com.deliciascaseiras.models.Produto;
import com.deliciascaseiras.models.Usuario;
import com.deliciascaseiras.repository.ProdutoRepository;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> findByName(String nome) {
        List<Produto> todosProdutos = findAll();
        List<Produto> produtosReturn = new ArrayList<>();
        List<String> tagProdutos = new AppUtil().stringForList(nome);
        for (Produto produtos : todosProdutos) {
            for (String tag_aux : tagProdutos) {
                if (produtos.getNome_produto().toUpperCase().contains(tag_aux.toUpperCase()))
                    produtosReturn.add(produtos);
            }
        }
        return produtosReturn;
    }

    @Override
    public Produto findById(long id) {
        return produtoRepository.findById(id).get();
    }

    @Override
    public List<Produto> findByUsuario(Usuario usuario) {
        List<Produto> produtosReturn = new ArrayList<>();
        List<Produto> allProdutos = findAll();
        for(Produto produto : allProdutos) {
            if (produto.getUsuario_produto().equals(usuario))
                produtosReturn.add(produto);
        }
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
        List<Produto> produtosReturn = new ArrayList<>();
        for (Produto produto : todosProdutos) {
            if (produto.getCategoria_produto().equals(category))
                produtosReturn.add(produto);
        }
        return produtosReturn;
    }
}
