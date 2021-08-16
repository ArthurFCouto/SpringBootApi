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
    EnderecoRepository enderecoRepository;

    @Autowired
    ComumUtilService comumUtilService;

    @GetMapping
    @ApiOperation(value="Retorna uma lista com todos os endereços cadastrados")
    public ResponseEntity<?> findAll() {
        List<Endereco> enderecoList = enderecoRepository.findAll();
        if(enderecoList.isEmpty())
            comumUtilService.noContentException("Sem resultados para exibir.");
        return new ResponseEntity<>(EnderecoShow.converter(enderecoList), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value="Retorna um endereço unico com o ID informado")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        if(enderecoRepository.existsById(id))
            return new ResponseEntity<>(new EnderecoShow(enderecoRepository.getById(id)), HttpStatus.OK);
        else
            comumUtilService.noContentException("Sem resultados para exibir.");
        return null;
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value="Atualiza o endereço com o ID informado")
    public ResponseEntity<?> update(@RequestBody @Valid EnderecoModel enderecoModel,
                                    @PathVariable("id") long id,
                                    UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        if(!enderecoRepository.existsById(id))
            throw new ResourceNotFoundException("Não existe o endereço com a ID: " + id);
        Endereco endereco = enderecoRepository.getById(id);
        if(!endereco.getUsuario_endereco().equals(usuario))
            comumUtilService.badRequestException("Não foi possível atualizar o endereço (Endereço pertence a outro usuário).");
        endereco.setCep_endereco(enderecoModel.getCep_endereco());
        endereco.setLogradouro_endereco(enderecoModel.getLogradouro_endereco());
        endereco.setNumero_endereco(enderecoModel.getNumero_endereco());
        endereco.setComplemento_endereco(enderecoModel.getComplemento_endereco());
        endereco.setCidade_endereco(enderecoModel.getCidade_endereco());
        endereco.setUf_endereco(enderecoModel.getUf_endereco());
        enderecoRepository.save(endereco);
        URI uri = uriBuilder.path("/api/auth/endereco/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(new EnderecoShow(endereco));
    }

    @PostMapping
    @ApiOperation(value="Salva um novo endereço")
    public ResponseEntity<?> save(@RequestBody @Valid EnderecoModel enderecoModel,
                                    UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        Endereco endereco = enderecoModel.converter(usuario);
        enderecoRepository.save(endereco);
        URI uri = uriBuilder.path("/api/auth/endereco/{id}").buildAndExpand(endereco.getId_endereco()).toUri();
        return ResponseEntity.created(uri).body(new EnderecoShow(endereco));
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value="Deleta um endereço com o id informado")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        if(!enderecoRepository.existsById(id))
            throw new ResourceNotFoundException("Não existe o endereço com a ID: " + id);
        Endereco endereco = enderecoRepository.getById(id);
        if(!endereco.getUsuario_endereco().equals(usuario))
            if(!usuarioService.verifyIsAdmin(usuario))
                comumUtilService.badRequestException("Não foi possível excluir o endereço (Endereço pertence a outro usuário).");
        enderecoRepository.delete(endereco);
        return new ResponseEntity<>("Endereço excluido.", HttpStatus.OK);
    }
}
