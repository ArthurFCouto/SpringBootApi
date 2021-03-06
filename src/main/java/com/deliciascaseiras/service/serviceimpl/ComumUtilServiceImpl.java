package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.error.BadRequestException;
import com.deliciascaseiras.error.ForbiddenException;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.repository.CategoriaProdutoRepository;
import com.deliciascaseiras.repository.ProdutoRepository;
import com.deliciascaseiras.repository.UsuarioRepository;
import com.deliciascaseiras.service.ComumUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComumUtilServiceImpl implements ComumUtilService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void verifyIfCategoriaExists(Long id){
        if(!categoriaProdutoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não existe a categoria com a ID: " + id); //Chama a classe de erro customizada
            //Se chamar a classe de erro já encerra o método automáticamente
        }
    }

    @Override
    public void verifyIfProdutoExists(Long id){
        if(!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não existe o produto com a ID: " + id);
        }
    }

    @Override
    public void verifyIfUsuarioExists(Long id){
        if(!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não existe o usuário com a ID: " + id);
        }
    }

    @Override
    public void resourceNotFoundException(String message){
        throw new ResourceNotFoundException(message);
    }

    @Override
    public void badRequestException(String message){
        throw  new BadRequestException(message);
    }

    @Override
    public void forbiddenException(){
        throw new ForbiddenException();
    }

    @Override
    public void verifyIfBeingUsedCategory(Long id) {
        List<Produto> produtoList = produtoRepository.findAll(); //Criamos um lista com todos os produtos
        if (!produtoList.isEmpty()) { //Verificamos se a lista está vazia
            for(Produto produto : produtoList) { //Percorremos a lista
                if(produto.getCategoria_produto().getId_categoria() == id) //Verificamos se a ID da categoria do produto é igual a ID informada
                    throw new BadRequestException("Categoria em uso."); //Se for, laçamos uma exceção
            }
        }
    }
}
