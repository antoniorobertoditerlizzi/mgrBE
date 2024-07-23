package com.vigilfuoco.mgr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JacksonConfig {
    //Per la serializzazione del proxy Hibernate Lazy Initialization che Jackson non saprebbe come serializzare. 
	//Metodo per evitare la conversione dell entità in DTO
	@Bean
	public ObjectMapper objectMapper() {
	    ObjectMapper objectMapper = new ObjectMapper();
	
	    // Configura il modulo Hibernate
	    Hibernate5Module hibernate5Module = new Hibernate5Module();
	    hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
	    objectMapper.registerModule(hibernate5Module);
	
	    // Configura il modulo JavaTime
	    JavaTimeModule javaTimeModule = new JavaTimeModule();
	    objectMapper.registerModule(javaTimeModule);
	    
	    // Disabilita la serializzazione delle date come timestamp
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	
	    return objectMapper;
	}
}