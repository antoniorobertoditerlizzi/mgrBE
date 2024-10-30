package com.vigilfuoco.mgr;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/* 
 * Configurazione dei parametri per la generazione dello SWAGGER automatico
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
	          .apiInfo(apiInfo());
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
	
}