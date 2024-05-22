package com.vigilfuoco.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableSwagger2 					//http://localhost:8080/swagger-ui/  -  http://localhost:8080/v2/api-docs
@EnableJpaRepositories(basePackages = "com.vigilfuoco.mgr.repository")
@EntityScan("com.vigilfuoco.mgr.model")
public class MgrApplication {
    public static void main(String[] args) {
        SpringApplication.run(MgrApplication.class, args);
    }
}