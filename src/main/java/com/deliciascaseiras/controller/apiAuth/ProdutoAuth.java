package com.deliciascaseiras.controller.apiAuth;

import com.deliciascaseiras.entity.CategoriaProduto;
import com.deliciascaseiras.entity.Produto;
import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.models.ProdutoModel;
import com.deliciascaseiras.models.modelsShow.ProdutoShow;
import com.deliciascaseiras.service.CategoriaProdutoService;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "*")
@RequestMapping(value = "api/auth/produto")
@RestController
@Api(value="API REST - Controle de produtos")
public class ProdutoAuth {

    private final String urlPath = "/api/user/produto/{id}";

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CategoriaProdutoService categoriaProdutoService;

    @Autowired
    ComumUtilService comumUtilService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    @ApiOperation(value="Salva um produto")
    public ResponseEntity<?> save(@RequestBody @Valid ProdutoModel produtoModel,
                                  @RequestParam long idCategoria,
                                  UriComponentsBuilder uriBuilder) {
        CategoriaProduto categoriaProduto = categoriaProdutoService.findById(idCategoria);
        Usuario usuarioLogado = usuarioService.findByEmail(AppUtil.userDetailUsername());
        Produto produto = produtoModel.converter(usuarioLogado, categoriaProduto, usuarioService, comumUtilService);
        produtoService.save(produto);
        URI uri = uriBuilder.path(urlPath).buildAndExpand(produto.getId_produto()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoShow(produto));
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value="Atualiza o produto com o id informado")
    public ResponseEntity<?> update(@RequestBody @Valid ProdutoModel produtoModel,
                                    @RequestParam long idCategoria,
                                    @PathVariable("id") long idProduto,
                                    UriComponentsBuilder uriBuilder) {
        CategoriaProduto categoriaProduto = categoriaProdutoService.findById(idCategoria);
        Usuario usuarioLogado = usuarioService.findByEmail(AppUtil.userDetailUsername());
        Produto produto = produtoModel.update(produtoService.findById(idProduto), usuarioLogado, categoriaProduto, comumUtilService);
        produtoService.save(produto);
        URI uri = uriBuilder.path(urlPath).buildAndExpand(produto.getId_produto()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoShow(produto));
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta um produto com o id informado")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        produtoService.delete(produtoService.findById(id));
        return new ResponseEntity<>("Produto excluido.", HttpStatus.OK);
    }
}
