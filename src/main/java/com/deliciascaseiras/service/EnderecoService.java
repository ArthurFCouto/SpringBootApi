package com.deliciascaseiras.service;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.auxEntity.Endereco;

import java.util.List;

public interface EnderecoService {
    List<Endereco> findAll();
    Endereco findById(long id);
    Endereco findByUsuarioById(long id);
    List<Endereco> findByUsuario(Usuario usuario);
    void save(Endereco endereco);
    void delete(Endereco endereco);
}
