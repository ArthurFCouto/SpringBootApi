package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.repository.ProdutoRepository;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Nesta classe implementamos os métodos da nossa interface, é onde acontece boa parte das regras no projeto
@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ComumUtilServiceImpl comumUtilService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public List<Produto> findAll() {
        if(produtoRepository.findAll().isEmpty())
            comumUtilService.resourceNotFoundException("Sem produtos cadastrados.");
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> findByName(String nome) {
        List<Produto> todosProdutos = findAll();
        List<String> tagProdutos = AppUtil.stringForList(nome);
        Predicate<Produto> filterProdutoList = produto -> AppUtil.stringCompareList(produto.getNome_produto(), tagProdutos);
        List<Produto> produtosReturn = todosProdutos.stream().filter(filterProdutoList).collect(Collectors.toList());
        if(produtosReturn.isEmpty())
            comumUtilService.resourceNotFoundException("Sem resultados para exibir.");
        return produtosReturn;
    }

    @Override
    public Produto findById(long id) {
        comumUtilService.verifyIfProdutoExists(id); //Verificando se existe o produto com a ID
        return produtoRepository.findById(id).get();
    }

    @Override
    public List<Produto> findByUsuario(Usuario usuario) {
        List<Produto> allProdutos = findAll();
        List<Produto> produtosReturn = allProdutos.stream().filter(produto -> produto.getUsuario_produto().equals(usuario)).collect(Collectors.toList());
        if(produtosReturn.isEmpty())
            comumUtilService.resourceNotFoundException("Sem resultados para exibir.");
        return produtosReturn;
    }

    @Override
    public List<Produto> findByCategory(CategoriaProduto category) {
        List<Produto> todosProdutos = findAll();
        List<Produto> produtosReturn = todosProdutos.stream().filter(produto -> produto.getCategoria_produto().equals(category)).collect(Collectors.toList());
        if(produtosReturn.isEmpty())
            comumUtilService.resourceNotFoundException("Sem resultados para exibir.");
        return produtosReturn;
    }

    @Override
    public boolean productIsPresentUser(Usuario usuario) {
        List<Produto> allProdutos = findAll();
        List<Produto> produtos = allProdutos.stream().filter(produto -> produto.getUsuario_produto().equals(usuario)).collect(Collectors.toList());
        if(produtos.isEmpty())
            return false;
        return true;
    }

    @Override
    public void save(Produto produto) {
        produtoRepository.save(produto);
    }

    @Override
    public void delete(Produto produto) {
        Usuario usuarioLogado = usuarioService.findByEmail(AppUtil.userDetailUsername()); //Atribuimos o usuário logado a uma variável
        if (produto.getUsuario_produto() != usuarioLogado) //Verificamos se o usuário do produto é igual ao usuário logado
            if(!usuarioService.verifyIsAdmin(usuarioLogado)) //Se não for, verificamos se é o usuário ADMIN
                comumUtilService.badRequestException("Não foi possível excluir o produto (Pertence a outro usuário)."); //Se não for, não permitimos deletar
                //Mas se o usuário logado for ADMIN pode deletar
        produtoRepository.delete(produto);
    }
}
