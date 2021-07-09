package com.deliciascaseiras.controller.api;

import com.deliciascaseiras.models.CategoriaProduto;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ComumUtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("api/categoriaproduto")
@RestController
@Api(value="API REST - Informações de categorias")
public class CategoriaProdutoController {

    @Autowired
    CategoriaProdutoService categoriaProdutoService;

    @Autowired
    ComumUtilService comumUtilService;

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todas as categorias")
    public ResponseEntity<?> findAll() {
        List<CategoriaProduto> categoriaProdutos = categoriaProdutoService.findAll();
        if(categoriaProdutos.toArray().length==0)
            comumUtilService.acceptedException("Sem resultados para exibir.");
        return new ResponseEntity<>(categoriaProdutos, HttpStatus.OK);
    }

    @GetMapping(path = "buscarnome")
    @ApiOperation(value="Retorna uma lista que contem o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<CategoriaProduto> categoriaProdutos = categoriaProdutoService.findByName(nome);
        if(categoriaProdutos.toArray().length==0)
            comumUtilService.acceptedException("Sem resultados para exibir.");
        return new ResponseEntity<>(categoriaProdutos, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna uma categoria unica com o ID informado")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        comumUtilService.verifyIfCategoriaExists(id);
        return new ResponseEntity<>(categoriaProdutoService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "quantidacategoria")
    @ApiOperation(value="Retorna a quantidade de categorias cadastradas")
    public ResponseEntity<?> length() {
        return new ResponseEntity<>(categoriaProdutoService.findAll().toArray().length, HttpStatus.OK);
    }
}
