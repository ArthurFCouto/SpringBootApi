package com.deliciascaseiras.controller.admin;

import com.deliciascaseiras.entity.admEntity.Role;
import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "API REST - ADMIN Controler")
public class AdminController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ComumUtilService comumUtilService;

    /*@RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna a solicitação de login")
    public ResponseEntity<?> login() {
        try {
            request.login(cpf, senha);
        } catch (ServletException e) {
                    e.printStackTrace();
                    ModelAndView mv = new ModelAndView("redirect:/login");
                    return new ResponseEntity<>("Houve algum erro.", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Faça uma requisição POST para /login passando 'usuario' e 'senha'.", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/loginerror", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna a solicitação de login caso erre a senha ou usuário")
    public ResponseEntity<?> loginError() {
        return new ResponseEntity<>("Senha ou usuario incorreto.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/loginsuccess", method = RequestMethod.GET)
    @ApiOperation(value = "Informa o sucesso no login")
    public ResponseEntity<?> loginSuccess() {
        //Pegando as informações do usuário logado com o UserDetails
        return new ResponseEntity<>("Login efetuado: " + new AppUtil().userDetailUsername(), HttpStatus.OK);
    }

    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    @ApiOperation(value = "Informa sobre o logout")
    public ResponseEntity<?> loginout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>("Logout efetuado.", HttpStatus.OK);
    }*/

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    @ApiOperation(value = "Informa a falta de permissão para acesso a página")
    public ResponseEntity<?> notAuthorized() {
        comumUtilService.forbiddenException();
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/admin/api/roles", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna a lista de autorizações(roles) cadastradas")
    public ResponseEntity<?> returnRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/api/quantidadeproduto", method = RequestMethod.GET)
    @ApiOperation(value="Retorna a quantidade de produtos cadastrados")
    public ResponseEntity<?> length() {
        return new ResponseEntity<>(produtoService.findAll().toArray().length, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/api/usuariologado", method = RequestMethod.GET)
    @ApiOperation(value="Retorna o usuário logado e sua autorização")
    public ResponseEntity<?> userLogin() {
        String login = new AppUtil().userDetailUsername();
        List<Role> role = usuarioService.findByEmail(login).getRoles();
        return new ResponseEntity<>(login +" - "+role, HttpStatus.OK);
    }
}
