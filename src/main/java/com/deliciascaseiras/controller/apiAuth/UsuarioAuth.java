package com.deliciascaseiras.controller.apiAuth;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.models.UsuarioModel;
import com.deliciascaseiras.models.modelsShow.UsuarioShow;
import com.deliciascaseiras.service.ComumUtilService;
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
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/auth/usuario")
@RestController
@Api(value="API REST - Controle de usuários")
public class UsuarioAuth {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ComumUtilService comumUtilService;

    @PutMapping(value = "{id}")
    @ApiOperation(value="Atualiza o usuário com o ID informado")
    public ResponseEntity<?> update(@RequestBody @Valid UsuarioModel usuarioModel,
                                    @PathVariable("id") long id,
                                    UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioModel.update(usuarioService.findById(id), usuarioService, comumUtilService); //Envio usuário para atualização na classe usuarioModel
        usuarioService.save(usuario);
        URI uri = uriBuilder.path("/api/auth/usuario/{id}").buildAndExpand(usuario.getId_usuario()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioShow(usuario));
    }

    //Apenas para teste (SpringSecurity) em ambiente de desenvolvimento
    @GetMapping(value = "usuariologado")
    @ApiOperation(value="Retorna o usuário logado e sua autorização")
    public ResponseEntity<?> userLogin() {
        List<Role> role = usuarioService.findByEmail(AppUtil.userDetailUsername()).getRoles();
        return new ResponseEntity<>(AppUtil.userDetailUsername() +" - "+role, HttpStatus.OK);
    }
}
