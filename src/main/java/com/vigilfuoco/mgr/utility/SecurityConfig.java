package com.vigilfuoco.mgr.utility;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.vigilfuoco.mgr.token.JwtTokenFilter;
import com.vigilfuoco.mgr.token.JwtTokenProvider;
import com.vigilfuoco.mgr.token.UnauthorizedHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${secret.token.key}")
    private String secret;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//Certificare che tutte le chiamate ai vari controller dell'aplicativo di BE arrivino esclusivamente dai segueinti URL
        http.cors()
        	.configurationSource(request -> {
		            CorsConfiguration config = new CorsConfiguration();
				            config.setAllowedOrigins(Arrays.asList("http://localhost:8080", 
												            		"https://localhost:443", 
												            		"http://localhost:3000", 
												            		"https://localhost:3000", 
												            		"http://*.dipvvf.it", 
												            		"https://*.dipvvf.it"));
				            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				            config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
				    return config;})
        	.and()
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeRequests()
	            .antMatchers("/api/utente/login").permitAll()
	            .antMatchers("/api/utente/**").authenticated()
	            .antMatchers("/api/richiesta/**").authenticated()
	            //.anyRequest().authenticated()
	         .and()
	         .addFilterAfter(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // Place after login filter
	         .exceptionHandling().authenticationEntryPoint(unauthorizedHandler());
    }

    @Bean
    public UnauthorizedHandler unauthorizedHandler() {
        return new UnauthorizedHandler() {

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Unauthorized\"}");
            }
        };
    }
}