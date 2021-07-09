package com.deliciascaseiras.controller.admin;

import com.deliciascaseiras.models.CategoriaProduto;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*") //Deixando a API disponível para acesso em qualquer dominio
@RequestMapping("admin/api/categoriaproduto")  //Mapeamento da URL para entrar no controller
@RestController
@Api(value="API REST - Controle de categorias") //Passando informações sobre essa API (Breve descrição)
public class CategoriaProdutoAdmin {

    @Autowired
    CategoriaProdutoService categoriaProdutoService;

    @Autowired
    ComumUtilService comumUtilService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    @ApiOperation(value="Salva uma categoria") //Detalhando o método no swagger
    public ResponseEntity<?> save(@RequestBody @Valid CategoriaProduto categoria) {
        new AppUtil().validCategoria(categoria);
        try {
            categoriaProdutoService.save(categoria);
        } catch (Exception exception) {
            comumUtilService.badRequestException("Cadastro não efetuado! Erro: "+exception);
        }
        return new ResponseEntity<>("Categoria cadastrada", HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value="Atualiza a categoria com o ID informado")
    public ResponseEntity<?> update(@Valid @RequestBody CategoriaProduto categoria,
                                    @PathVariable("id") Long id) {
        comumUtilService.verifyIfCategoriaExists(id);
        new AppUtil().validCategoria(categoria);
        categoria.setId_categoria(id);
        categoriaProdutoService.save(categoria);
        return new ResponseEntity<>("Categoria atualizada.", HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta a categoria com o ID informado")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        comumUtilService.verifyIfCategoriaExists(id);
        comumUtilService.verifyIfBeingUsed(id);
        categoriaProdutoService.delete(categoriaProdutoService.findById(id));
        return new ResponseEntity<>("Categoria deletada.", HttpStatus.OK);
    }
}
