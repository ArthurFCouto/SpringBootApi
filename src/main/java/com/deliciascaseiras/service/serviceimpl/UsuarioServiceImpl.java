package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.models.Usuario;
import com.deliciascaseiras.models.admModel.Role;
import com.deliciascaseiras.repository.UsuarioRepository;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> findByName(String nome) {
        List<Usuario> todosUsuarios = findAll();
        List<Usuario> usuariosReturn = new ArrayList<>();
        List<String> tagUsuarios = new AppUtil().stringForList(nome);
        for (Usuario usuario : todosUsuarios) {
            for (String tag_aux : tagUsuarios) {
                if (usuario.getNome_usuario().toUpperCase().contains(tag_aux.toUpperCase()))
                    usuariosReturn.add(usuario);
            }
        }
        return usuariosReturn;
    }

    @Override
    public Usuario findById(long id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario findByEmail(String email) {
        for(Usuario usuario : findAll()) {
            if (usuario.getEmail_usuario().equals(email.toLowerCase())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public boolean emailIsPresent(String email) {
        for(Usuario usuario : findAll()) {
            if (usuario.getEmail_usuario().equals(email.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean produtoIsPresent(long id) {
        return produtoService.findByUsuario(findById(id)).toArray().length > 0;
    }

    @Override
    public boolean verifyIsAdmin(Usuario usuario) {
        List<Role> rolesUsuario = usuario.getRoles();
        for(Role role : rolesUsuario)
            if(role.getNome_role().toUpperCase().contains("ADMIN"))
                return true;
        return false;
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
}
