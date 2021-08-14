package com.deliciascaseiras.controller.admin;

import com.deliciascaseiras.entity.Usuario;
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
@RequestMapping("admin/api/usuario")
@RestController
@Api(value="API REST - Controle de usuários")
public class UsuarioAdmin {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ComumUtilService comumUtilService;

    @GetMapping(path = "buscarnome")
    @ApiOperation(value="Retorna uma lista que contem o nome informado")
    public ResponseEntity<?> findByName(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.findByName(nome);
        if(usuarios.contains(usuarioService.findByEmail("admin@admin.com"))) //Verificamos se o usuario admin@admin está na lista
            usuarios.remove(usuarioService.findByEmail("admin@admin.com")); //E removemos ele da lista se estiver
        if (usuarios.toArray().length == 0)
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos os usuarios")
    public ResponseEntity<?> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.toArray().length == 1) //Se tiver apenas um usuário cadastrado, ele será o admin@admin porque não é possível excluí-lo
            comumUtilService.noContentException("Sem resultados para exibir."); //Desse modo não exibimos
        usuarios.remove(usuarioService.findByEmail("admin@admin.com")); //Se tiver mais usuários cadastrados, removemos o admin@admin
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um usuario unico com o ID informado")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        if(usuarioService.findById(id).getEmail_usuario().equals("admin@admin.com")) //Se o ID informado for o admin@admin
            throw new ResourceNotFoundException("Não existe o usuário com a ID: " + id); //Não exibimos os detalhes
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value="Atualiza o usuário com o ID informado")
    public ResponseEntity<?> update(@RequestBody @Valid Usuario usuario,
                                    @PathVariable("id") long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        new AppUtil().validUsuario(usuario);
        Usuario usuarioAux = usuarioService.findById(id);
        usuarioAux.setNome_usuario(usuario.getNome_usuario());
        usuarioAux.setEmail_usuario(usuario.getEmail_usuario());
        usuarioAux.setAniversario_usuario(usuario.getAniversario_usuario());
        usuarioAux.setTelefone_usuario(usuario.getTelefone_usuario());
        usuarioAux.setSenha_usuario(new BCryptPasswordEncoder().encode(usuario.getSenha_usuario()));
        usuarioAux.setDataatualizacao_usuario(LocalDate.now());
        usuarioService.save(usuarioAux);
        return new ResponseEntity<>("Usuário atualizado!",HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta o usuário com o ID informado")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        if (usuarioService.findById(id).getEmail_usuario().equals("admin@admin.com"))
            comumUtilService.badRequestException("Não é possível excluir este usuário.");
        if (usuarioService.findById(id).getEmail_usuario().equals(new AppUtil().userDetailUsername()))
            comumUtilService.badRequestException("Faça esta solicitação a outro ADMIN.");
        if (usuarioService.produtoIsPresent(id))
            comumUtilService.badRequestException("Usuário com produtos cadastrados.");
        usuarioService.delete(usuarioService.findById(id));
        return new ResponseEntity<>("Usuário deletado!",HttpStatus.OK);
    }
}
