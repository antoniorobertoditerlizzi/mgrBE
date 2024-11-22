package com.vigilfuoco.mgr;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/* 
 * Configurazione dei parametri per la generazione dello SWAGGER automatico. Per le chiamate autorizzate aggiungere "Bearer " nello swagger
 * 
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	    @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build()
	          .apiInfo(apiInfo())
	          .securitySchemes(Collections.singletonList(apiKey())) // Supporto per il Bearer Token
	          .securityContexts(Collections.singletonList(securityContext())); // Configurazione di sicurezza
	    }
	    
	    private ApiInfo apiInfo() {
	        return new ApiInfo(
	          "REST API - Modello Gestione Richieste", 
	          "Definizione delle API.", 
	          "API TOS", 
	          "Termini del servizio", 
	          new Contact("Vigili del Fuoco", "https:\\www.vigilfuoco.it", "antonioroberto.diterlizzi@vigilfuoco.it"),
	          "License of API", "API license URL", Collections.emptyList());
	    }
	
	    // Definizione dell'ApiKey per il Bearer Token
	    private ApiKey apiKey() {
	        return new ApiKey("Bearer Token", "Authorization", "header");
	    }

	    // Configurazione del contesto di sicurezza
	    private SecurityContext securityContext() {
	        return SecurityContext.builder()
	                .securityReferences(defaultAuth())
	                .build();
	    }

	    private List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
	        return Collections.singletonList(new SecurityReference("Bearer Token", authorizationScopes));
	    }
	}