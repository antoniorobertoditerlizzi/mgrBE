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

import com.vigilfuoco.mgr.exception.InvalidTokenException;

import io.jsonwebtoken.UnsupportedJwtException;

public class JwtTokenFilter extends OncePerRequestFilter {


    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	  // Extract JWT token from request header
    	  String headerToken = request.getHeader("Authorization");

    	  // Validazione del token ed estrazione delle informazioni utente
    	  if (headerToken != null && headerToken.startsWith("Bearer ")) {
    		String token = headerToken.substring(7);
    		try {
    		  if (token != null && jwtTokenProvider.validateToken(request)) {
	    		  // Extract username from token using jwtTokenProvider
	    		  String username = jwtTokenProvider.getUsernameFromToken(token);
	    		  // Creazione dell'Authentication object basato sull'username
	              Authentication authentication = new UsernamePasswordAuthenticationToken(
	                  username, null, Collections.emptyList());
	              SecurityContextHolder.getContext().setAuthentication(authentication);
			  } else {
	              SecurityContextHolder.clearContext();
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
    	  } else {
    	        SecurityContextHolder.clearContext();
    	  }

    	  filterChain.doFilter(request, response);
    }
    
}