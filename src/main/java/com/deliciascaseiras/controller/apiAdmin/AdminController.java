package com.deliciascaseiras.controller.apiAdmin;

import com.deliciascaseiras.repository.RoleRepository;
import com.deliciascaseiras.service.ComumUtilService;
import com.deliciascaseiras.service.ProdutoService;
import com.deliciascaseiras.service.UsuarioService;
import com.deliciascaseiras.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "Retorna uma mensagem personalizada para a falta de autorização.")
    public ResponseEntity<?> notAuthorized() {
        comumUtilService.forbiddenException();
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/api/admin/roles", method = RequestMethod.GET)
    @ApiOperation(value = "Retorna a lista de autorizações(roles) cadastradas")
    public ResponseEntity<?> returnRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/quantidadeproduto", method = RequestMethod.GET)
    @ApiOperation(value="Retorna a quantidade de produtos cadastrados")
    public ResponseEntity<?> lengthProducts() {
        return new ResponseEntity<>(produtoService.findAll().toArray().length, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/quantidadeusuarios", method = RequestMethod.GET)
    @ApiOperation(value="Retorna a quantidade de usuários cadastrados")
    public ResponseEntity<?> lengthUsers() {
        return new ResponseEntity<>(usuarioService.findAll().toArray().length, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/usuario/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value="Deleta o usuário com o ID informado")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        comumUtilService.verifyIfUsuarioExists(id);
        if (usuarioService.findById(id).getEmail_usuario().equals(AppUtil.userDetailUsername()))
            comumUtilService.badRequestException("Faça esta solicitação a outro ADMIN.");
        if (usuarioService.findById(id).getEmail_usuario().equals("admin@admin.com"))
            comumUtilService.badRequestException("Não é possível excluir este usuário.");
        if (usuarioService.produtoIsPresent(id))
            comumUtilService.badRequestException("Usuário com produtos cadastrados.");
        try {
            usuarioService.delete(usuarioService.findById(id));
        } catch (Exception exception) {
            comumUtilService.badRequestException("Erro ao excluir usuário.\nDetails: "+exception);
        }
        return new ResponseEntity<>("Usuário deletado!",HttpStatus.OK);
    }
}
