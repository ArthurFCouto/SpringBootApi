package com.deliciascaseiras.service.serviceimpl;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.entity.auxEntity.Endereco;
import com.deliciascaseiras.error.ResourceNotFoundException;
import com.deliciascaseiras.repository.EnderecoRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.EnderecoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ComumUtilService comumUtilService;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public List<Endereco> findAll() {
        List<Endereco> enderecoList = enderecoRepository.findAll();
        if (enderecoList.isEmpty())
            comumUtilService.resourceNotFoundException("Sem endereços cadastrados.");
        return enderecoList;
    }

    @Override
    public Endereco findById(long id) {
        if (!enderecoRepository.existsById(id))
            throw new ResourceNotFoundException("Não existe endereço com o ID: " + id);
        return enderecoRepository.findById(id).get();
    }

    @Override
    public Endereco findByUsuarioById(long id) {
        if (!enderecoRepository.existsById(id))
            throw new ResourceNotFoundException("Não existe endereço com o ID: " + id);
        else if (enderecoRepository.findById(id).get().getUsuario_endereco().equals(usuarioService.findByEmail(AppUtil.userDetailUsername())))
            comumUtilService.badRequestException("Não foi possível carregar o endereço (Endereço pertence a outro usuário).");
        return enderecoRepository.findById(id).get();
    }

    @Override
    public List<Endereco> findByUsuario(Usuario usuario) {
        List<Endereco> enderecos = enderecoRepository.findAll();
        Predicate<Endereco> filterEndereco = endereco -> endereco.getUsuario_endereco().equals(usuario);
        List<Endereco> enderecosReturn = enderecos.stream().filter(filterEndereco).collect(Collectors.toList());
        if(enderecosReturn.isEmpty())
            comumUtilService.resourceNotFoundException("Sem resultados para exibir.");
        return enderecosReturn;
    }

    @Override
    public void save(Endereco endereco) {
        enderecoRepository.save(endereco);
    }

    @Override
    public void delete(Endereco endereco) {
        Usuario usuario = usuarioService.findByEmail(AppUtil.userDetailUsername());
        if (!endereco.getUsuario_endereco().equals(usuario))
            if (!usuarioService.verifyIsAdmin(usuario))
                comumUtilService.badRequestException("Não foi possível excluir o endereço (Endereço pertence a outro usuário).");
        enderecoRepository.delete(endereco);
    }
}