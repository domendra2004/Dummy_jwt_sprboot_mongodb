package com.domen.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.*;

@EnableSwagger2
@Configuration    //extends WebMvcConfigurerAdapter
public class SwaggerConfig  {
    @Bean
    public Docket SwaggerConfig(){
        /*return  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.domen.controller"))
                .build().apiInfo(getApinInfo())
                .useDefaultResponseMessages(false)//
                .securitySchemes(new ArrayList<>(Arrays.asList(new ApiKey("%token", "Authorization", "Header"))))//
                .genericModelSubstitutes(Optional.class);
    }*/
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.domen.controller"))
                .build().apiInfo(getApinInfo())
                .directModelSubstitute(LocalDate.class, Date.class)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", In.HEADER.name())))
                .securityContexts(Collections.singletonList(SecurityContext.builder()
                .securityReferences(Collections.singletonList(SecurityReference.builder().reference("JWT")
                                        .scopes(new AuthorizationScope[0])
                                        .build()))
                .build()))
                .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();}

    private ApiInfo getApinInfo() {
        return  new ApiInfoBuilder()
                .title("Assignment project")
                .description("Documentation Demo")
                .contact(new Contact("Domendra Suktel","url@xyz","domendra2014@gmail.com"))
                .license("License xxxxxx")
                .licenseUrl("License Url xxxxxxx")
                .version("Version v x.xx")
                .build();
    }

}
