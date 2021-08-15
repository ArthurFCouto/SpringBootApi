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
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RequestMapping(value = "api/auth/produto", consumes = "application/json")
@RestController
@Api(value="API REST - Controle de produtos")
public class ProdutoAuth {

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
        comumUtilService.verifyIfCategoriaExists(idCategoria);
        Usuario usuarioLogado = usuarioService.findByEmail(new AppUtil().userDetailUsername());
        if(usuarioService.verifyIsAdmin(usuarioLogado))
            comumUtilService.badRequestException("Não foi possível cadastrar o produto (Não autorizado para o perfil do usuário).");
        CategoriaProduto categoriaProduto = categoriaProdutoService.findById(idCategoria);
        Produto produto = produtoModel.converter(usuarioLogado, categoriaProduto);
        produto.setData_produto(LocalDate.now());
        produto.setDataatualizacao_produto(LocalDate.now());
        produtoService.save(produto);
        URI uri = uriBuilder.path("/api/user/produto/{id}").buildAndExpand(produto.getId_produto()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoShow(produto));
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value="Atualiza o produto com o id informado")
    public ResponseEntity<?> update(@RequestBody @Valid ProdutoModel produtoModel,
                                    @RequestParam long idCategoria,
                                    @PathVariable("id") long idProduto,
                                    UriComponentsBuilder uriBuilder) {
        comumUtilService.verifyIfProdutoExists(idProduto);
        comumUtilService.verifyIfCategoriaExists(idCategoria);
        Usuario usuarioLogado = usuarioService.findByEmail(new AppUtil().userDetailUsername());
        if (produtoService.findById(idProduto).getUsuario_produto() != usuarioLogado)
            comumUtilService.badRequestException("Não foi possível atualizar o produto (Pertence a outro usuário).");
        CategoriaProduto categoriaProduto = categoriaProdutoService.findById(idCategoria);
        Produto produto = produtoModel.converter(usuarioLogado, categoriaProduto);
        produto.setId_produto(idProduto);
        produto.setData_produto(produtoService.findById(idProduto).getData_produto());
        produto.setDataatualizacao_produto(LocalDate.now());
        produtoService.save(produto);
        URI uri = uriBuilder.path("/api/user/produto/{id}").buildAndExpand(produto.getId_produto()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoShow(produto));
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta um produto com o id informado")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        comumUtilService.verifyIfProdutoExists(id);
        Usuario usuarioLogado = usuarioService.findByEmail(new AppUtil().userDetailUsername());
        if (produtoService.findById(id).getUsuario_produto() != usuarioLogado) //Verificamos se o usuário do produto é igual ao usuário logado
            if(!usuarioService.verifyIsAdmin(usuarioLogado)) //Se não for, verificamos se é o usuário apiAuth
                comumUtilService.badRequestException("Não foi possível excluir o produto (Pertence a outro usuário)."); //Se não for, não permitimos deletar
        produtoService.delete(produtoService.findById(id));
        return new ResponseEntity<>("Produto excluido.", HttpStatus.OK);
    }
}
