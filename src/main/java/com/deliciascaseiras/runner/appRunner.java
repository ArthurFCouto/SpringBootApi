package com.deliciascaseiras.runner;

import com.deliciascaseiras.models.Usuario;
import com.deliciascaseiras.models.admModel.Role;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class appRunner implements ApplicationRunner {
    //Este método é executado toda vez que se inicia a aplicação, por isso implementa ApplicationRunner

    private RoleRepository roleRepository;
    private UsuarioRepository usuarioRepository;

    //Dessa vez, usamos um método para criar os nossos pontos de injeção
    @Autowired
    public void DataLoader(RoleRepository roleRepository, UsuarioRepository usuarioRepository) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Neste método verificamos se temos roles cadastradas no banco de dados, se não tiver cadastramos duas
        if (roleRepository.findAll().toArray().length == 0) {
            roleRepository.save(new Role("ROLE_ADMIN","Administrador"));
            roleRepository.save(new Role("ROLE_SELL","Vendedor"));
            roleRepository.save(new Role("ROLE_USER","Usuario"));
        }
        //Neste método verificamos se temos usuários cadastradas no banco de dados, se não tiver cadastramos o principal
        if (usuarioRepository.findAll().toArray().length == 0) {
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById("ROLE_ADMIN").get());
            usuarioRepository.save(new Usuario("admin@admin.com","ADMIN", LocalDate.now(), "12345678", roles));
        }
    }
}
