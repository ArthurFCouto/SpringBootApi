package com.deliciascaseiras.repository;

import com.deliciascaseiras.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
