package com.vigilfuoco.mgr.utility;

import java.util.Arrays;

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
        	.antMatchers("/login", "/logout").permitAll() // Permetto a tutti l'accesso all'invocazione delle api di login e logout
            .antMatchers("/api/utente/login").permitAll() // Accesso a tutti senza autenticazione
            .antMatchers("/api/utente/accounts").hasRole("ADMIN") // Accesso endpoint solo da ADMIN user
            .antMatchers("/api/adminpanel/").hasRole("ADMIN") // L'Admin vedrà tutto cio che c'è dopo l'admin panel url
            .antMatchers("/api/richiesta/id/**").authenticated() // Richiesta di autenticazione per tutti gli endpoints
            .antMatchers("/api/richiesta/cerca/**", "/api/richiesta/all", "/api/richiesta/save", "/api/richiesta/ciao", "/api/richiesta/test").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated() // Nega tutte le altre richieste
        .and()
        .formLogin().disable() //Disabilito pagina login di default di spring security dato che abbiamo frontend in REACT
        .logout().disable()	   //Disabilito pagina logout di default di spring security dato che abbiamo frontend in REACT
        .exceptionHandling().accessDeniedPage("/403") 
        .and()
        .httpBasic(); 
    }

    
}
