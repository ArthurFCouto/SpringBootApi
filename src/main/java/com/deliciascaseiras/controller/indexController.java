package com.deliciascaseiras.controller;

import com.deliciascaseiras.entity.Usuario;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class indexController {

    @Autowired
    UsuarioService usuarioService;

    //Requisição para chamar a nossa página de login, para acessar a documentação da API
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    //Caso seja digitado os dados incorretos, somos redirecionados para este método
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginError() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam Optional<String> username,
                              @RequestParam Optional<String> password,
                              HttpSession httpSession,
                              RedirectAttributes attributes) {
        String email = username.orElse(" "); //1 espaço vazio
        String senha = password.orElse(" "); //1 espaço vazio
        if(!email.trim().isEmpty() && !senha.trim().isEmpty()) { //Estou vendo se não estão vazios
            if(usuarioService.emailIsPresent(email)) { //Verificando se existe o email no banco de usuários
                Usuario usuario = usuarioService.findByEmail(email);
                if(new BCryptPasswordEncoder().matches(senha, usuario.getSenha_usuario())) { //Verificando a correspondência das senhas
                    try {
                        httpSession.setAttribute("User", usuario); //Criando a sessão (login) com o usuário
                        System.out.println(AppUtil.userDetailUsername()); //Verificando no console se o usuário está logado
                        return new ModelAndView("redirect:/swagger-ui.html#/"); //Redirecionando para a página de documentação
                    } catch (Exception e) {
                        e.printStackTrace(); //Exibindo no console o erro da criação da sessão
                        attributes.addFlashAttribute("message", "Erro desconhecido ao tentar fazer login."); //Adicionando uma menssagem a ser exibida ao usuário
                        return new ModelAndView("redirect:/login"); //O ModelAndView não permite redirecionar para a raiz, por isso foi preciso criar o método login
                    }
                }
            }
            attributes.addFlashAttribute("message", "USUÁRIO/SENHA incorreto. Caso tenha esquecido, contatar gestor da API."); //Mensagem de aviso caso o usuário ou senha estejam incorretos
            return new ModelAndView("redirect:/login");
        }
        attributes.addFlashAttribute("message", "Favor informar usuário e senha."); //Mensagem de erro caso os dados estejam vazios
        return new ModelAndView("redirect:/login");
    }
}
