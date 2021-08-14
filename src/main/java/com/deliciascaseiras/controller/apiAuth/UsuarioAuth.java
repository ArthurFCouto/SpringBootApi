package com.deliciascaseiras.controller.apiAuth;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.error.ResourceNotFoundException;
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

import javax.validation.Valid;
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

    private final String emailAdmin = "admin@admin.com";

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos os usuarios")
    public ResponseEntity<?> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.toArray().length == 1) //Se tiver apenas um usuário cadastrado, ele será o apiAuth@apiAuth porque não é possível excluí-lo
            comumUtilService.noContentException("Sem resultados para exibir."); //Desse modo não exibimos
        usuarios.remove(usuarioService.findByEmail(emailAdmin)); //Se tiver mais usuários cadastrados, removemos o apiAuth@apiAuth
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um usuario unico com o ID informado")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        if(usuarioService.findById(id).getEmail_usuario().equals(emailAdmin)) //Se o ID informado for o apiAuth@apiAuth
            throw new ResourceNotFoundException("Não existe o usuário com a ID: " + id); //Não exibimos os detalhes
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "buscar")
    @ApiOperation(value="Retorna uma lista que contem o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.findByName(nome);
        if(usuarios.contains(usuarioService.findByEmail(emailAdmin))) //Verificamos se o usuario apiAuth@apiAuth está na lista
            usuarios.remove(usuarioService.findByEmail(emailAdmin)); //E removemos ele da lista se estiver
        if (usuarios.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value="Atualiza o usuário com o ID informado (E-mail não pode ser atualizado.)")
    public ResponseEntity<?> update(@RequestBody @Valid Usuario usuario,
                                    @PathVariable("id") long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        new AppUtil().validUsuario(usuario);
        Usuario usuarioAux = usuarioService.findById(id);
        usuarioAux.setNome_usuario(usuario.getNome_usuario());
        usuarioAux.setAniversario_usuario(usuario.getAniversario_usuario());
        usuarioAux.setTelefone_usuario(usuario.getTelefone_usuario());
        usuarioAux.setSenha_usuario(new BCryptPasswordEncoder().encode(usuario.getSenha_usuario()));
        usuarioAux.setDataatualizacao_usuario(LocalDate.now());
        usuarioService.save(usuarioAux);
        return new ResponseEntity<>("Usuário atualizado.",HttpStatus.OK);
    }

    //Apenas para teste (SpringSecurity) em ambiente de desenvolvimento
    @GetMapping(value = "usuariologado")
    @ApiOperation(value="Retorna o usuário logado e sua autorização")
    public ResponseEntity<?> userLogin() {
        String login = new AppUtil().userDetailUsername();
        List<Role> role = usuarioService.findByEmail(login).getRoles();
        return new ResponseEntity<>(login +" - "+role, HttpStatus.OK);
    }
}
