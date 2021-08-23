package com.deliciascaseiras.controller.apiAdmin;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ComumUtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*") //Deixando a API disponível para acesso em qualquer dominio
@RequestMapping(value = "api/admin/categoriaproduto")  //Mapeamento da URL para entrar no controller
@RestController //Informando que os dados trocados nas requisições serão Json
@Api(value="API REST - Controle de categorias") //Informações sobre essa API, no caso, essa requisição (Breve descrição)
public class CategoriaProdutoAdmin {

    @Autowired
    CategoriaProdutoService categoriaProdutoService;

    @Autowired
    ComumUtilService comumUtilService;

    @PostMapping
    @ApiOperation(value="Salva uma categoria") //Detalhando o método no swagger
    public ResponseEntity<?> save(@RequestBody @Valid CategoriaProduto categoria) {
        try {
            categoriaProdutoService.save(categoria);
        } catch (Exception exception) {
            comumUtilService.badRequestException("Erro durante o cadastro. "+exception.getMessage());
        }
        return new ResponseEntity<>("Categoria cadastrada.", HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value="Atualiza a categoria com o ID informado")
    public ResponseEntity<?> update(@RequestBody @Valid CategoriaProduto categoria,
                                    @PathVariable("id") long id) {
        comumUtilService.verifyIfCategoriaExists(id);
        categoria.setId_categoria(id);
        try {
            categoriaProdutoService.save(categoria);
        } catch (Exception exception) {
            comumUtilService.badRequestException("Erro durante a atualização. "+exception.getMessage());
        }
        return new ResponseEntity<>("Categoria atualizada.", HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta a categoria com o ID informado")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            categoriaProdutoService.delete(categoriaProdutoService.findById(id));
        } catch (Exception exception) {
            comumUtilService.badRequestException("Erro ao deletar categoria. "+exception.getMessage());
        }
        return new ResponseEntity<>("Categoria deletada.", HttpStatus.OK);
    }

    @GetMapping(path = "quantidade")
    @ApiOperation(value="Retorna a quantidade de categorias cadastradas")
    public ResponseEntity<?> length() {
        return new ResponseEntity<>(categoriaProdutoService.findAll().size(), HttpStatus.OK);
    }
}