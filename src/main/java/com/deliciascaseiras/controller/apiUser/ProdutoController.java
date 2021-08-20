package com.deliciascaseiras.controller.apiUser;

import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.models.modelsShow.ProdutoShow;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.ComparatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos os produtos(Nome, preço ou data)")
    public ResponseEntity<?> findAll(@Param("order") Optional<String> ordem) {
        String ordenacao = ordem.orElse("default");
        List<Produto> produtos = produtoService.findAll();
        switch (ordenacao.toLowerCase()) {
            case "nome" : produtos.sort(new ComparatorUtil.ProdutoNameComparator());
            case "preco" : produtos.sort(new ComparatorUtil.ProdutoPrecoComparator());
            case "data" : produtos.sort(new ComparatorUtil.ProdutoDataComparator());
        }
        return new ResponseEntity<>(ProdutoShow.converter(produtos), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um produto unico com o id informado")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        return new ResponseEntity<>(new ProdutoShow(produtoService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(path = "buscar")
    @ApiOperation(value="Retorna uma lista que contem o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Produto> produtos = produtoService.findByName(nome);
        return new ResponseEntity<>(ProdutoShow.converter(produtos), HttpStatus.OK);
    }

    @GetMapping(path = "categoria/{idCategoria}")
    @ApiOperation(value="Retorna uma lista que contem a categoria(id) informada")
    public ResponseEntity<?> findByCategoria(@PathVariable("idCategoria") long idCategoria) {
        List<Produto> produtos = produtoService.findByCategory(categoriaProdutoService.findById(idCategoria));
        return new ResponseEntity<>(ProdutoShow.converter(produtos), HttpStatus.OK);
    }

    @GetMapping(path = "usuario/{idUsuario}")
    @ApiOperation(value="Retorna uma lista que contem os produtos do usuario(id) informado.")
    public ResponseEntity<?> findByUsuario(@PathVariable("idUsuario") long idUsuario) {
        List<Produto> produtos = produtoService.findByUsuario(usuarioService.findById(idUsuario));
        return new ResponseEntity<>(ProdutoShow.converter(produtos), HttpStatus.OK);
    }
}
