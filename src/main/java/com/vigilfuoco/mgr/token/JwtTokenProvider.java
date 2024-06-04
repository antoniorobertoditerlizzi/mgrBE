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

    // Metodo per generare un token JWT per un determinato username
    public String generateToken(String accountName) throws IOException {
        logger.debug("Secret Key: " + getResources("secret.token.key"));
        // Generazone del JWT token
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

    // Metodo per la validazione del JWT token
    public boolean validateToken(HttpServletRequest request) throws InvalidTokenException, UnsupportedJwtException, IllegalArgumentException, IOException {
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            Claims claims = Jwts.parser()
                    .setSigningKey(getResources("secret.token.key"))
                    .parseClaimsJws(token)
                    .getBody();

            // Controllo se il token è valido (non scaduto)
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                throw new InvalidTokenException("Token scaduto");
            }

            // Estraggo le user information dal claims
            String username = claims.getSubject();

            // Token validato con successo
            logger.debug("Token valido per l'utente: " + username);
            
            return true;
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidTokenException("Token scaduto");
        } catch (SignatureException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidTokenException("Firma del token non valida");
        } catch (MalformedJwtException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidTokenException("Token non valido");
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
    
    public String getUsernameFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, IOException {
	  // Parsing del JWT token per accedere ai claims
	  Claims claims = Jwts.parser()
	      .setSigningKey(getResources("secret.token.key"))
	      .parseClaimsJws(token)
	      .getBody();

	  // Recupero l'username claim dal token
	  String username = claims.getSubject();

	  // Restituisco l'username
	  return username;
	}

}