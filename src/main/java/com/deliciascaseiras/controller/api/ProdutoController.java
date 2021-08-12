package com.deliciascaseiras.controller.api;

import com.deliciascaseiras.models.Produto;
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
@RequestMapping("api/produto")
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

    @GetMapping(path = "buscarnome")
    @ApiOperation(value="Retorna uma lista que contem o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Produto> produtos = produtoService.findByName(nome);
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping(path = "buscarcategoria")
    @ApiOperation(value="Retorna uma lista que contem a categoria(id) informada")
    public ResponseEntity<?> findByCategoria(@RequestParam String idCategoria) {
        comumUtilService.verifyIfCategoriaExists(Long.parseLong(idCategoria));
        List<Produto> produtos = produtoService.findByCategory(categoriaProdutoService.findById(Long.parseLong(idCategoria)));
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping(path = "buscarusuario")
    @ApiOperation(value="Retorna uma lista que contem o usuário(id) informado")
    public ResponseEntity<?> findByUsuario(@RequestParam String idUsuario) {
        comumUtilService.verifyIfUsuarioExists(Long.parseLong(idUsuario));
        List<Produto> produtos = produtoService.findByUsuario(usuarioService.findById(Long.parseLong(idUsuario)));
        if(produtos.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping(path = "quantidadeproduto")
    @ApiOperation(value="Retorna a quantidade de produtos cadastrados")
    public ResponseEntity<?> length() {
        return new ResponseEntity<>(produtoService.findAll().toArray().length, HttpStatus.OK);
    }

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
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        comumUtilService.verifyIfProdutoExists(id);
        return new ResponseEntity<>(produtoService.findById(id), HttpStatus.OK);
    }
}
