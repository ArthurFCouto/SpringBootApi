package com.deliciascaseiras.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //Habilitando a configuração de segurança
public class SecurityConfig extends WebSecurityConfigurerAdapter { //Extendendo a classe de configurção, para que póssamos utilizar os usuários cadastrados para fazer login

    @Autowired
    private ImplementsUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                .antMatchers("/admin/api/**").authenticated() //Todas as requisiçoes que precisam ser autenticadas
                .antMatchers("/admin/api/roles").hasRole("ROLE_ADMIN") //Estamos informando que determinada URL só pode ser acessada por usuários ADMIN
                .antMatchers(HttpMethod.DELETE, "/admin/api/usuario/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/admin/api/categoriaproduto").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/admin/api/categoriaproduto/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/admin/api/categoriaproduto/**").hasRole("ADMIN")
                .anyRequest().permitAll() //Caminhos que não precisam de autenticação (o restante exceto o listado acima)
                .and().formLogin().permitAll()//Permitindo o acesso ao FormLogin por todos
                .loginPage("/login").failureForwardUrl("/loginerror").successForwardUrl("/loginsuccess") //Chama minha requisição de login ao invés da padrão, e o login erro e sucesso (GET)
                .usernameParameter("usuario") //Ao fazer um POST para login, estou informando o nome da variável com o usuario
                .passwordParameter("senha") //Informando o nome da variável com a senha
                .and().httpBasic() //Método de segurança, autenticação básica
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/loginout") //link para encerrar a autenticação e a página de logout
                .and().exceptionHandling().accessDeniedPage("/403"); //Chamando meu método de acesso negado
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder()); //Configurando a criptografia da senha para comparação
    }
}