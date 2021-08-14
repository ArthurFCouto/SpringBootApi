package com.deliciascaseiras.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //Habilitando a configuração de segurança
public class SecurityConfig extends WebSecurityConfigurerAdapter { //Extendendo a classe de configurção, para que póssamos utilizar os usuários cadastrados para fazer login

    @Autowired
    private ImplementsUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http.authorizeRequests()
        http.csrf().disable().authorizeRequests()//Habilitanto os methodos além do GET
                .antMatchers("/api/admin/**").hasRole("ADMIN") //Estamos informando que determinada URL só pode ser acessada por usuários ADMIN
                .antMatchers("/api/auth/**").authenticated() //Todas as requisiçoes que precisam ser autenticadas
                .anyRequest().permitAll() //Caminhos que não precisam de autenticação (o restante exceto o listado acima)
                /*.and()
                .formLogin().permitAll()//Permitindo o acesso ao FormLogin por todos
                .loginPage("/login").failureForwardUrl("/loginerror").successForwardUrl("/loginsuccess") //Chama minha requisição de login ao invés da padrão, e o login erro e sucesso (GET)
                .usernameParameter("username") //Ao fazer um POST para login, estou informando o nome da variável com o usuario
                .passwordParameter("password") //Informando o nome da variável com a senha
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/loginout") //link para encerrar a autenticação e a página de logout
                 //Chamando meu método de acesso negado
                */
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Método para informar que não quer o estado da aplicação, não armazena a authenticação em cookie, em cada requisição é preciso passar a autenticação, cada requisição é uma nova seção.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder()); //Configurando a criptografia da senha para comparação
    }
}