package com.deliciascaseiras.service;

import com.deliciascaseiras.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    List<Usuario> findAllNoAdmin();
    List<Usuario> findByName(String nome);
    Usuario findById(long id);
    Usuario findByEmail(String email);
    boolean emailIsPresent(String email);
    boolean produtoIsPresent(Usuario usuario);
    boolean verifyIsAdmin(Usuario usuario);
    void save(Usuario usuario);
    void delete(Usuario usuario);
}
