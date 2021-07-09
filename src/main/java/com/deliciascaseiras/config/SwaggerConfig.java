package com.deliciascaseiras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.*;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2 //Habilitando o swagger2 no projeto
@Configuration
public class SwaggerConfig {

    //Para acessar digite: localhost:8080/swagger-ui.html

    //Estamos usando uma classe externa a nossa aplicação, por isso o Bean
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deliciascaseiras.controller")) //Mapeando os pacotes com URL para documentação
                //.apis(RequestHandlerSelectors.any()) //Mapeando todos os pacotes para documentação
                //.paths(regex("/api/.*")) //Mapeamento quais URL serão documentadas
                .paths(PathSelectors.any()) //Mapeamento quais URL serão documentadas, neste caso, todas
                .build()
                .apiInfo(metaInfo()); //Descrição personalizada da API
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "DeliciasGourmet API REST",
                "API REST de cadastro de produtos caseiros.",
                "1.0",
                "Terms of Service",
                new Contact("Arthur Couto", "https://github.com/ArthurFCouto",
                        "arthurfcouto@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );
        return apiInfo;
    }

}
