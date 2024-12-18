package com.vigilfuoco.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/*
 * Main per il RUN dell'applicativo di BE. REPO: https://github.com/antoniorobertoditerlizzi/mgrBE/
 * git add -f target/mgr-0.0.1-SNAPSHOT.war
 * BE Realizzato da II Antonio Roberto Di Terlizzi
 */
@EnableScheduling
@SpringBootApplication
@EnableSwagger2 					//http://localhost:8080/swagger-ui/  -  http://localhost:8080/v2/api-docs
@EnableJpaRepositories(basePackages = {
	    "com.vigilfuoco.mgr.repository"
})
@EntityScan(basePackages = {
	    "com.vigilfuoco.mgr.model",
	    "com.vigilfuoco.mgr.wauc.model"
	})
public class MgrApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(MgrApplication.class, args);
    }
}