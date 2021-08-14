package com.deliciascaseiras.controller.apiUser;

import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/user/produto")
@RestController
@Api(value="API REST - Informações de produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CategoriaProdutoService categoriaProdutoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ComumUtilService comumUtilService;

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos os produtos")
    public ResponseEntity<?> findAll() {
        List<Produto> produtos = produtoService.findAll();
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um produto unico com o id informado")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        comumUtilService.verifyIfProdutoExists(id);
        return new ResponseEntity<>(produtoService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "buscar")
    @ApiOperation(value="Retorna uma lista que contem o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Produto> produtos = produtoService.findByName(nome);
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping(path = "categoria/{idCategoria}")
    @ApiOperation(value="Retorna uma lista que contem a categoria(id) informada")
    public ResponseEntity<?> findByCategoria(@PathVariable("idCategoria") long idCategoria) {
        comumUtilService.verifyIfCategoriaExists(idCategoria);
        List<Produto> produtos = produtoService.findByCategory(categoriaProdutoService.findById(idCategoria));
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping(path = "usuario/{idUsuario}")
    @ApiOperation(value="Retorna uma lista que contem o usuário(id) informado")
    public ResponseEntity<?> findByUsuario(@PathVariable("idUsuario") long idUsuario) {
        comumUtilService.verifyIfUsuarioExists(idUsuario);
        List<Produto> produtos = produtoService.findByUsuario(usuarioService.findById(idUsuario));
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
}
