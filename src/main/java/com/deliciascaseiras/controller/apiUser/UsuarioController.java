package com.deliciascaseiras.controller.apiUser;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.models.UsuarioModel;
import com.deliciascaseiras.models.modelsShow.UsuarioShow;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/user/usuario")
@RestController
@Api(value="API REST - Informações de usuários")
public class UsuarioController {

    private final String urlPath = "/api/auth/usuario/{id}";

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ComumUtilService comumUtilService;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos* os usuários")
    public ResponseEntity<?> findAllNoAdmin() {
        List<Usuario> usuarios = usuarioService.findAllNoAdmin();
        return new ResponseEntity<>(UsuarioShow.converter(usuarios), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um usuário unico com o ID informado")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        return new ResponseEntity<>(new UsuarioShow(usuarioService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(path = "buscar")
    @ApiOperation(value="Retorna uma lista que o nome contém o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.findByName(nome);
        return new ResponseEntity<>(UsuarioShow.converter(usuarios), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value="Salva um usuario")
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioModel usuarioModel,
                                  UriComponentsBuilder uriBuilder) {

        Usuario usuario = usuarioModel.converter(comumUtilService, roleRepository, usuarioService);
        usuarioService.save(usuario);
        URI uri = uriBuilder.path(urlPath).buildAndExpand(usuario.getId_usuario()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioShow(usuario));
    }
}
