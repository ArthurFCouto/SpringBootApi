package com.deliciascaseiras.controller.apiAuth;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.error.ResourceNotFoundException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
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

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos os usuários")
    public ResponseEntity<?> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.toArray().length == 1) //Se tiver apenas um usuário cadastrado, ele será o apiAuth@apiAuth porque não é possível excluí-lo
            comumUtilService.noContentException("Sem resultados para exibir."); //Desse modo não exibimos
        usuarios.remove(usuarioService.findByEmail(AppUtil.emailAdmin())); //Se tiver mais usuários cadastrados, removemos o apiAuth@apiAuth
        return new ResponseEntity<>(UsuarioShow.converter(usuarios), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um usuário unico com o ID informado")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        if(usuarioService.findById(id).getEmail_usuario().equals(AppUtil.emailAdmin())) //Se o ID informado for o apiAuth@apiAuth
            throw new ResourceNotFoundException("Não existe o usuário com a ID: " + id); //Não exibimos os detalhes
        return new ResponseEntity<>(new UsuarioShow(usuarioService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(path = "buscar")
    @ApiOperation(value="Retorna uma lista que o nome contém o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.findByName(nome);
        if(usuarios.contains(usuarioService.findByEmail(AppUtil.emailAdmin()))) //Verificamos se o usuario apiAuth@apiAuth está na lista
            usuarios.remove(usuarioService.findByEmail(AppUtil.emailAdmin())); //E removemos ele da lista se estiver
        if (usuarios.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(UsuarioShow.converter(usuarios), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value="Atualiza o usuário com o ID informado")
    public ResponseEntity<?> update(@RequestBody @Valid UsuarioModel usuarioModel,
                                    @PathVariable("id") long id,
                                    UriComponentsBuilder uriBuilder) {
        comumUtilService.verifyIfUsuarioExists(id);
        Usuario usuario = usuarioService.findById(id);
        usuario.setEmail_usuario(usuarioModel.getEmail_usuario());
        usuario.setNome_usuario(usuarioModel.getNome_usuario());
        usuario.setAniversario_usuario(usuarioModel.getAniversario_usuario());
        usuario.setTelefone_usuario(usuarioModel.getTelefone_usuario());
        usuario.setSenha_usuario(new BCryptPasswordEncoder().encode(usuario.getSenha_usuario()));
        usuario.setDataatualizacao_usuario(LocalDate.now());
        usuarioService.save(usuario);
        URI uri = uriBuilder.path("/api/auth/usuario/{id}").buildAndExpand(usuario.getId_usuario()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioShow(usuario));
    }

    //Apenas para teste (SpringSecurity) em ambiente de desenvolvimento
    @GetMapping(value = "usuariologado")
    @ApiOperation(value="Retorna o usuário logado e sua autorização")
    public ResponseEntity<?> userLogin() {
        String login = AppUtil.userDetailUsername();
        List<Role> role = usuarioService.findByEmail(login).getRoles();
        return new ResponseEntity<>(login +" - "+role, HttpStatus.OK);
    }
}
