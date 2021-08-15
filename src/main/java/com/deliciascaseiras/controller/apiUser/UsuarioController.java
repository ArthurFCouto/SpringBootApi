package com.deliciascaseiras.controller.apiUser;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.models.UsuarioModel;
import com.deliciascaseiras.models.modelsShow.UsuarioShow;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RequestMapping("api/user/usuario")
@RestController
@Api(value="API REST - Informações de usuários")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ComumUtilService comumUtilService;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping
    @ApiOperation(value="Salva um usuario")
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioModel usuarioModel,
                                  UriComponentsBuilder uriBuilder) {
        if (usuarioModel.getEmail_usuario().toUpperCase().contains(AppUtil.emailAdmin().toUpperCase()))
            comumUtilService.badRequestException("Informe um e-mail válido.");
        if (usuarioService.emailIsPresent(usuarioModel.getEmail_usuario()))
            comumUtilService.badRequestException("Email já cadastrado! Informe outro e-mail.");
        Usuario usuario = usuarioModel.converter(roleRepository);
        usuario.setData_usuario(LocalDate.now());
        usuario.setDataatualizacao_usuario(LocalDate.now());
        usuarioService.save(usuario);
        URI uri = uriBuilder.path("/api/auth/usuario/{id}").buildAndExpand(usuario.getId_usuario()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioShow(usuario));
    }
}
