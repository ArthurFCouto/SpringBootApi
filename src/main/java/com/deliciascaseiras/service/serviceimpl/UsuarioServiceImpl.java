package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.repository.UsuarioRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    ComumUtilService comumUtilService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() { //Método para retornar todos os usuários cadastrados
        List<Usuario> usuarioList = usuarioRepository.findAll();
        if (usuarioList.isEmpty())
            comumUtilService.resourceNotFoundException("Sem usuários cadastrados.");
        return usuarioList;
    }

    @Override
    public List<Usuario> findAllNoAdmin() { //Método para retornar todos os usuários, exceto o ADMIM
        //Com o predicate criamos a condição para o filtro da lista
        Predicate<Usuario> filterUsuarioList = usuario -> !verifyIsAdmin(usuario); //Condição para verificar se o usuário é ADMIN
        //No método abaixo, primeiro criamos uma strem a partir de todos os produtos
        //Depois filtramos de acordo com nossas condições
        //Depois criamos uma nova coleção com os produtos filtrados.
        List<Usuario> usuarioList = usuarioRepository.findAll().stream().filter(filterUsuarioList).collect(Collectors.toList()); //Filtrando a lista de todos os usuários
        if (usuarioList.isEmpty())
            comumUtilService.resourceNotFoundException("Sem usuários cadastrados*.");
        return usuarioList;
    }

    @Override
    public List<Usuario> findByName(String nome) {
        List<Usuario> todosUsuarios = findAllNoAdmin(); //Ao pesquisar um usuário, removemos o ADMIN da lista
        List<String> tagUsuarios = AppUtil.stringForList(nome); //Separando o 'nome' em lista
        Predicate<Usuario> filterUsuarioList = usuario -> AppUtil.stringCompareList(usuario.getNome_usuario(), tagUsuarios); //Comparando o nome do usuário com a lista passada pelo usuário
        List<Usuario> usuariosReturn = todosUsuarios.stream().filter(filterUsuarioList).collect(Collectors.toList());
        if (usuariosReturn.isEmpty())
            comumUtilService.resourceNotFoundException("Sem resultados para exibir.");
        return usuariosReturn;
    }

    @Override
    public Usuario findById(long id) {
        comumUtilService.verifyIfUsuarioExists(id); //Verificando se existe o usuário com a ID
        Usuario usuario = usuarioRepository.findById(id).get();
        if(usuario.getEmail_usuario().equals(AppUtil.emailAdmin())) //Se o ID informado for o ADMIN
            comumUtilService.badRequestException("Erro ao carregar dados do usuário com ID: " + id); //Não exibimos os detalhes
        return usuario;
    }

    @Override
    public Usuario findByEmail(String email) {
        for(Usuario usuario : usuarioRepository.findAll()) { //Ao pesquisar um usuário por e-mail, preciso percorrer toda a lista, incluindo o ADMIN
            if (usuario.getEmail_usuario().equals(email.toLowerCase())) //O email já é salvo com toLowerCase()
                return usuario;
        }
        throw new ResourceNotFoundException("Erro ao pesquisar usuário com o e-mail: " + email); //Se não encontrar o e-mail nos usuários, lançamos uma exceção
    }

    @Override
    public boolean emailIsPresent(String email) {
        for(Usuario usuario : usuarioRepository.findAll()) { //Ao conferir se existe o e-mail, preciso percorrer toda a lista, incluindo o ADMIN
            if (usuario.getEmail_usuario().equals(email.toLowerCase()))
                return true;
        }
        return false;
    }

    @Override
    public boolean produtoIsPresent(Usuario usuario) {
        return produtoService.productIsPresentUser(usuario);
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
        if (usuario.getEmail_usuario().equals(AppUtil.userDetailUsername())) //Verificando se vou excluir o usuário logado
            comumUtilService.badRequestException("Faça esta solicitação a outro ADMIN.");
        if (usuario.getEmail_usuario().equals(AppUtil.emailAdmin())) //Verificando se vou excluir o ADMIN
            comumUtilService.badRequestException("Não é possível excluir este usuário.");
        if (produtoIsPresent(usuario)) //Verificando se o usuário tem produtos cadastrados
            comumUtilService.badRequestException("Usuário com produtos cadastrados.");
        usuarioRepository.delete(usuario);
    }
}
