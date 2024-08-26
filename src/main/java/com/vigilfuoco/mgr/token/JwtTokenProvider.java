package com.vigilfuoco.mgr.token;


import java.io.IOException;

import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import com.vigilfuoco.mgr.exception.InvalidTokenException;
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

	/* Tempo di scadenza del tokenJWT (JSON Web Token) staccato in fase di Login. Dopo che il token scade l'utente deve riloggarsi*/
    @Value("${expirationTokenTime}")
    private long expirationTokenTime;

    /* Tempo di scadenza del refresh token. Il refresh token viene utilizzato per ottenere nuovi token di accesso 
     senza dover re-autenticare l'utente. La durata del refresh token è tipicamente più lunga rispetto al token di accesso. 
     Quando il token di accesso scade, il refresh token può essere usato per richiederne uno nuovo, 
     una volta che anche il refresh token scade, l'utente deve effettuare nuovamente l'accesso.*/
    @Value("${refreshTokenExpirationTime}")
    private long refreshTokenExpirationTime;

    private final BlacklistService blacklistService;

    public JwtTokenProvider(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    // Metodo per generare un access token JWT per un determinato username
    public String generateToken(String accountName) throws IOException {
        Claims claims = Jwts.claims().setSubject(accountName).setIssuedAt(new Date());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, getResources("secret.token.key"))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTokenTime))
                .compact();
    }

    // Metodo per generare un refresh token JWT per un determinato username
    public String generateRefreshToken(String accountName) throws IOException {
        Claims claims = Jwts.claims().setSubject(accountName).setIssuedAt(new Date());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, getResources("secret.token.key"))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .compact();
    }

    // Metodo per la validazione del JWT token
    public boolean validateToken(HttpServletRequest request) throws InvalidTokenException, IOException {
        String token = extractToken(request);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getResources("secret.token.key"))
                    .parseClaimsJws(token)
                    .getBody();

            // Controllo se il token è valido (non scaduto)
            if (claims.getExpiration().before(new Date())) {
                throw new InvalidTokenException("Token scaduto");
            }

            return true;
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token scaduto");
        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidTokenException("Token non valido");
        }
    }

    // Metodo per validare il refresh token
    public boolean validateRefreshToken(String token) throws InvalidTokenException, IOException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getResources("secret.token.key"))
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new InvalidTokenException("Refresh token scaduto");
            }

            return true;
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Refresh token scaduto");
        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidTokenException("Refresh token non valido");
        }
    }

    // Metodo per estrarre il token dalla richiesta
    private String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization").split(" ")[1];
    }

    public String getResources(String key) throws IOException {
        Resource resource = new ClassPathResource("/application.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        return props.getProperty(key);
    }

    public void invalidateToken(String token) {
        blacklistService.addToBlacklist(token);
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