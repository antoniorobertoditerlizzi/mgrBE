package com.vigilfuoco.mgr.token;


import java.io.IOException;

import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vigilfuoco.mgr.controller.InvalidTokenException;
import com.vigilfuoco.mgr.service.BlacklistService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
    @Value("${expirationTokenTime}")
    private long expirationTokenTime;
    
	private static final Logger logger = LogManager.getLogger(JwtTokenProvider.class);

    private final BlacklistService blacklistService;

    // Method to generate a JWT token for a given username
    public String generateToken(String accountName) throws IOException {
        logger.debug("Secret Key: " + getResources("secret.token.key"));
        // Generate JWT token
        String username = accountName; // Username attuale
        


        logger.debug("Timeout del token impostaot a: " + expirationTokenTime);
        Claims claims = Jwts.claims()
                .setSubject(username)
                .setIssuedAt(new Date());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, getResources("secret.token.key"))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTokenTime))
                .compact();
        
        logger.debug("Il nuovo token generato e': " + token);
        
        return token;
    }

    // Method to validate a JWT token
    public boolean validateToken(HttpServletRequest request) throws InvalidTokenException, UnsupportedJwtException, IllegalArgumentException, IOException {
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            Claims claims = Jwts.parser()
                    .setSigningKey(getResources("secret.token.key"))
                    .parseClaimsJws(token)
                    .getBody();

            // Check if token is valid (not expired)
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                throw new InvalidTokenException("Token expired");
            }

            // Extract user information from claims
            String username = claims.getSubject();

            // Successful token validation
            logger.debug("Token valid for user: " + username);
            
            return true;
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidTokenException("Token expired");
        } catch (SignatureException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidTokenException("Invalid token signature");
        } catch (MalformedJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidTokenException("Malformed token");
        } catch (InvalidTokenException e) {
            throw e; 
        }
    }

    public String getResources(String key) throws IOException {
    	Resource resource = new ClassPathResource("/application.properties");
    	Properties props = PropertiesLoaderUtils.loadProperties(resource);
    	String keyVaule=props.getProperty(key);
	return keyVaule;
    }
    

    public JwtTokenProvider(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public void invalidateToken(String token) {
        blacklistService.addToBlacklist(token);
    }
    
    // Estraggo l'accountdipvvf(username) da un valido JWT token
    public String getToken(String token) {
		return token;
    }

}