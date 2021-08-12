package com.deliciascaseiras.controller.api;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<?> save(@RequestBody Usuario usuario) {
        System.out.println(usuario.getNome_usuario()+" - "+usuario.getAniversario_usuario());
        new AppUtil().validUsuario(usuario);
        if (usuarioService.emailIsPresent(usuario.getEmail_usuario()))
            comumUtilService.badRequestException("Email já cadastrado! Informe outro e-mail.");
        String senha_usuario = usuario.getSenha_usuario();
        usuario.setSenha_usuario(new BCryptPasswordEncoder().encode(senha_usuario));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById("ROLE_USER").get());
        usuario.setRoles(roles);
        usuario.setData_usuario(LocalDate.now());
        usuario.setDataatualizacao_usuario(LocalDate.now());
        usuarioService.save(usuario);
        return new ResponseEntity<>("Usuário salvo!",HttpStatus.OK);
    }
}
