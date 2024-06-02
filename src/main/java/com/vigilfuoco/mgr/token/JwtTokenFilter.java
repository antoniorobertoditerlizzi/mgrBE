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

    @Autowired  // Or @Inject depending on your framework
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract JWT token from request header
        String headerToken = request.getHeader("Authorization");

        // Validate token and extract user information
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            //String token = jwtTokenProvider.getToken(headerToken.substring(7));
            String token = headerToken; 

            try {
				if (token != null && jwtTokenProvider.validateToken(request)) {
				    // Create Authentication object based on username
				    Authentication authentication = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
				    SecurityContextHolder.getContext().setAuthentication(authentication);
				}
            } catch (UnsupportedJwtException | IllegalArgumentException | InvalidTokenException e) {
                logger.error("Invalid JWT token: " + e.getMessage());
                // Set response status to unauthorized (401)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // Optionally, send an error message in the response body
                response.getWriter().write("Unauthorized: Invalid or expired token");
            } catch (JwtAuthenticationException e) {
                response.setStatus(e.getStatusCode().value());
                // Optionally set response body with error message (consider security implications)
                response.getWriter().write(e.getMessage());
                // Alternatively, consider logging the error message instead
                logger.error(e.getMessage());    
            } catch (IOException e) {
                // Handle other IOExceptions (e.g., from writing to response)
                e.printStackTrace();
            }
            
            // Only continue down the filter chain if the token is valid
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
            }
        }

        // Pass request down the filter chain
        filterChain.doFilter(request, response);
    }
    
    

    
}