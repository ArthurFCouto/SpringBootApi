package com.deliciascaseiras.config;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {
    //Estamos implementando para que os usuários cadastrados sejam configurados como UserDetail para ter acesso as autenticações do programa

    //Criando um ponto de injeção para termos acesso aos métodos do service
    @Autowired
    UsuarioService usuarioService;

    //Substituindo o método que loga o usuário padrão do sistema, e retornando o usuário cadastrado no banco de dados.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario;
        if (usuarioService.emailIsPresent(email)) {
            usuario = usuarioService.findByEmail(email);
            return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
    }

}