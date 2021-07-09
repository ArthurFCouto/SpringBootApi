package com.deliciascaseiras.controller.api;

import com.deliciascaseiras.modelJson.UsuarioJson;
import com.deliciascaseiras.models.Usuario;
import com.deliciascaseiras.models.admModel.Role;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/usuario")
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
    public ResponseEntity<?> save(@RequestBody UsuarioJson usuarioJson) {
        new AppUtil().validUsuarioJson(usuarioJson);
        if (usuarioService.emailIsPresent(usuarioJson.getEmail_usuario()))
            comumUtilService.badRequestException("Email já cadastrado! Informe outro e-mail.");
        Usuario usuario = new Usuario(usuarioJson);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById("ROLE_USER").get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity<>("Usuário salvo!",HttpStatus.OK);
    }
}
