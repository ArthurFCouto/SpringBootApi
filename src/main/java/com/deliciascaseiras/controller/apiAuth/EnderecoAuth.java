package com.deliciascaseiras.controller.apiAuth;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.entity.auxEntity.Endereco;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.models.EnderecoModel;
import com.deliciascaseiras.models.UsuarioModel;
import com.deliciascaseiras.models.modelsShow.EnderecoShow;
import com.deliciascaseiras.models.modelsShow.UsuarioShow;
import com.deliciascaseiras.repository.EnderecoRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.EnderecoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/auth/endereco")
@RestController
@Api(value="API REST - Controle de endereco")
public class EnderecoAuth {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    ComumUtilService comumUtilService;

    @GetMapping
    @ApiOperation(value="Retorna uma lista de endereços cadastrados para o usuário")
    public ResponseEntity<?> findByUsuario() {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        return new ResponseEntity<>(enderecoService.findByUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um endereço unico com o ID informado")
    public ResponseEntity<?> findByUsuarioById(@PathVariable("id") long id) {
        return new ResponseEntity<>(new EnderecoShow(enderecoService.findByUsuarioById(id)), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value="Atualiza o endereço com o ID informado")
    public ResponseEntity<?> update(@RequestBody @Valid EnderecoModel enderecoModel,
                                    @PathVariable("id") long id,
                                    UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        Endereco endereco = enderecoService.findById(id);
        enderecoService.save(enderecoModel.update(endereco, usuario, comumUtilService));
        URI uri = uriBuilder.path("/api/auth/endereco/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(new EnderecoShow(endereco));
    }

    @PostMapping
    @ApiOperation(value="Salva um novo endereço (Máximo - 3)")
    public ResponseEntity<?> save(@RequestBody @Valid EnderecoModel enderecoModel,
                                    UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        Endereco endereco = enderecoModel.converter(usuario, comumUtilService, usuarioService);
        enderecoService.save(endereco);
        URI uri = uriBuilder.path("/api/auth/endereco/{id}").buildAndExpand(endereco.getId_endereco()).toUri();
        return ResponseEntity.created(uri).body(new EnderecoShow(endereco));
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta um endereço com o id informado")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Endereco endereco = enderecoService.findById(id);
        enderecoService.delete(endereco);
        return new ResponseEntity<>("Endereço excluido.", HttpStatus.OK);
    }
}
