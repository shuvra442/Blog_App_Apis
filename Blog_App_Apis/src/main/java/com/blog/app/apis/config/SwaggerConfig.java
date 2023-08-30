package com.blog.app.apis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys(){
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }

    private List<SecurityContext> securityContext(){
        return Arrays.asList( SecurityContext.builder().securityReferences(securityReference()).build());
    }

    private List<SecurityReference> securityReference(){
        AuthorizationScope scope=new AuthorizationScope("Global","Access Everything");
        return Arrays.asList( new SecurityReference("JWT", new AuthorizationScope[]{ scope }));
    }


    @Bean
    public Docket api(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContext())
                .securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo getInfo(){

        return new ApiInfo("Blogging Application : Backend Course","This project is developed by Shuvra Patra","1.0","No terms of service",new Contact("Shuvra","https://github.com/shuvra442","shuvrapatra6@gmail.com"),"For Learning purpose you can used this APIS","Api licence URL", Collections.emptyList());
    }
}
