package com.vigilfuoco.mgr.token;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vigilfuoco.mgr.controller.InvalidTokenException;

import io.jsonwebtoken.UnsupportedJwtException;

public class JwtTokenFilter extends OncePerRequestFilter {


    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Estrazione JWT token dall'header della request
        String headerToken = request.getHeader("Authorization");

        // Validazione del token ed estrazione delle informazioni utente
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            //String token = jwtTokenProvider.getToken(headerToken.substring(7));
            String token = headerToken; 

            try {
				if (token != null && jwtTokenProvider.validateToken(request)) {
				    // Creazione dell'Authentication object basato sull'username
				    Authentication authentication = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
				    SecurityContextHolder.getContext().setAuthentication(authentication);
				}
            } catch (UnsupportedJwtException | IllegalArgumentException | InvalidTokenException e) {
                logger.error("Invalid JWT token: " + e.getMessage());
                // Set dello stato della response ad unauthorized (401)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // Invio di un messaggio di errore nel response body
                response.getWriter().write("Unauthorized: Invalid or expired token");
            } catch (JwtAuthenticationException e) {
                response.setStatus(e.getStatusCode().value());
                // Invio di un messaggio di errore nel response body
                response.getWriter().write(e.getMessage());
                // Logdel messaggio di errore
                logger.error(e.getMessage());    
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // Continua l'esecuzione del filter solo se il token è valido
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
            }
        }

        // Salta la richiesta se il filtro non va a buon fine
        // filterChain.doFilter(request, response);
    }
    
}