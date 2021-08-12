package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.error.BadRequestException;
import com.deliciascaseiras.error.RequestNoContentException;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.error.UnauthorizedException;
import com.deliciascaseiras.models.Produto;
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
            throw new ResourceNotFoundException("Não existe o produto com a ID: " + id); //Chama a classe de erro customizada
            //Se chamar a classe de erro já encerra o método automáticamente
        }
    }

    @Override
    public void verifyIfUsuarioExists(Long id){
        if(!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não existe o usuário com a ID: " + id); //Chama a classe de erro customizada
            //Se chamar a classe de erro já encerra o método automáticamente
        }
    }

    @Override
    public void noContentException(String message){
        throw new RequestNoContentException(message);
    }

    @Override
    public void badRequestException(String message){
        throw new BadRequestException(message);
    }

    @Override
    public void unauthotizedException(){
        throw new UnauthorizedException();
    }

    @Override
    public void verifyIfBeingUsedCategory(Long id) {
        List<Produto> produtoList = produtoRepository.findAll();
        for(Produto produto : produtoList) {
            if(produto.getCategoria_produto().getId_categoria() == id)
                throw new BadRequestException("Categoria em uso.");
        }
    }
}
