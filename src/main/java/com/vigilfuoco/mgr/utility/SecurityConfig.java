package com.vigilfuoco.mgr.utility;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//Certificare che tutte le chiamate ai vari controller dell'aplicativo di BE arrivino esclusivamente dai segueinti URL
        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList("http://localhost:8080", 
								            		"https://localhost:443", 
								            		"http://localhost:3000", 
								            		"https://localhost:3000", 
								            		"http://*.dipvvf.it", 
								            		"https://*.dipvvf.it"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            return config;
        });

        //Gestire le autorizzazioni agli accessi dei singoli endpoint
        http.authorizeRequests()
            .antMatchers("/api/utente/login").permitAll() // Allow login without authentication
            .antMatchers("/api/utente/accounts").hasRole("ADMIN") // Admin-only access for accounts
            .antMatchers("/api/richiesta/id/**").authenticated() // Require authentication for all endpoints
            .antMatchers("/api/richiesta/cerca/**", "/api/richiesta/all", "/api/richiesta/save", "/api/richiesta/ciao", "/api/richiesta/test").hasAnyRole("USER", "ADMIN")
            .anyRequest().denyAll() // Deny all other requests by default
        .and()
        .formLogin()
        .and()
        .httpBasic(); 
    }

}
