package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.exception.InvalidTokenException;
import com.vigilfuoco.mgr.model.JwtResponse;
import com.vigilfuoco.mgr.model.Ufficio;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.service.UtenteService;

import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* 
 * Definizione di tutte le API che contraddistinguono l'Utente
 * @Autowired indica a Spring di inserire un'istanza della JwtTokenProviderclasse nel controller o nel servizio.
 * Ciò garantisce che stai utilizzando la stessa JwtTokenProvideristanza che Spring ha già creato e gestito, promuovendo la coerenza ed evitando potenziali problemi con più istanze.
 * 
 */

@RestController
@RequestMapping("/api/utente/")
public class UtenteController {
	
	@Value("${api.wauc.basepath}")
    private String waucBasePath;
	
	@Value("${api.wauc.personale}")
    private String waucPersonale;
	
	@Value("${api.wauc.anagraficapersonale}")
    private String anagraficaPersonale;
	
    @Autowired
    private UtenteRepository utenteRepository;
    
    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }
    
	private static final Logger logger = LogManager.getLogger(UtenteController.class);

	

	// API LoginCheck ----------------------------------------------------------- /api/utente/login?accountName=antonioroberto.diterlizzi
	@GetMapping("login")
	public ResponseEntity<JwtResponse> login(@RequestParam String accountName) throws IllegalArgumentException, IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		logger.debug("Ingresso api /api/utente/loginCheck?accountName=" +accountName);
		return utenteService.login(accountName, url);
	}
	
	// API Refresh Token --------------------------------------------------- /api/utente/refresh-token
	@PostMapping("/refresh-token")
	public ResponseEntity<JwtResponse> refresh(@RequestHeader("Authorization") String authorizationHeader) throws InvalidTokenException, IOException {
	    String refreshToken = authorizationHeader.split(" ")[1];
	    logger.debug("Rinnovo del token con refresh token: " + refreshToken);
	    return utenteService.refreshToken(refreshToken);
	}
	
	// API Loginout ----------------------------------------------------------- /api/utente/logout
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) throws InvalidTokenException, UnsupportedJwtException, IllegalArgumentException, IOException {
	    String token = authorizationHeader.split(" ")[1];
	    logger.debug("Invalido il token: " + token);
        return utenteService.logout(token);
	}
	
	
	// API Ricerca tutti gli utenti salvati a DB ----------------------------- /api/utente/accounts
    @GetMapping("accounts")
	public ResponseEntity<Iterable<Utente>> getAccounts() throws IOException, JsonProcessingException {
    	String url = waucBasePath + waucPersonale + "/accounts";
		logger.debug("Ingresso api" + url );
		
		Iterable<Utente> res = utenteRepository.findAll();
		
		for (Utente utente : res) {
			logger.debug("richiesta.getId():"+utente.getAccount());
			logger.debug("richiesta.getId():"+utente.getEmailUtente());
		}

		return new ResponseEntity<Iterable<Utente>>(res, HttpStatus.OK);
	}
    

	// API Ricerca utente per accountName --------------------------------- /api/utente/account/antonioroberto.diterlizzi
    @GetMapping("account/{accountName}")
    public ResponseEntity<Utente> getAccount(@PathVariable String accountName) throws IOException, JsonProcessingException {
    	String url = waucBasePath + waucPersonale + "/account/" + accountName;
		logger.debug("Ingresso api" + url );
		Utente res = utenteRepository.findByAccount(accountName);
		return new ResponseEntity<Utente>(res, HttpStatus.OK);
	}
    
    
    // API MENU PER ACCOUNT NAME [MENU SX] ------------------------------- /api/utente/menuByAccountName?accountName=antonioroberto.diterlizzi
    @GetMapping("/menuByAccountName")
    @PreAuthorize("isAuthenticated()") 
    public ResponseEntity<List<Object>> getMenuByAccountName(@RequestParam String accountName) throws IOException {
    	List<Object> jsonData = utenteService.getMenuByRoleFromAccount(accountName);
    	return ResponseEntity.ok(jsonData);
    }
    
    
	// API MENU PER ID [MENU SX] ------------------------------------------ /api/utente/menu?roleId=2
   @GetMapping("/menu")
   @PreAuthorize("isAuthenticated()") 
   public ResponseEntity<List<Object>> getMenuByRole(@RequestParam int roleId) throws IOException {
	     List<Object> jsonData = utenteService.getMenuByRole_OBJ(roleId);
	    return ResponseEntity.ok(jsonData);
   }
	
	// API MENU UTENTE LOGGATO [SMALL MENU DX] ---------------------------- /api/utente/menuUser
   @GetMapping("/menuUser")
   @PreAuthorize("isAuthenticated()") 
   public ResponseEntity<List<Object>> getMenuUser() throws IOException {
	     List<Object> jsonData = utenteService.getMenuUser();
	    return ResponseEntity.ok(jsonData);
   }
   
	// API MENU UTENTE NON LOGGATO ---------------------------------------- /api/utente/menuUserNotLogged
   @GetMapping("/menuUserNotLogged")
   public ResponseEntity<List<Object>> getMenuUserNotLogged() throws IOException {
	     List<Object> jsonData = utenteService.getMenuUserNotLogged();
	    return ResponseEntity.ok(jsonData);
   }
    
   // API LISTA UFFICI UTENTE -------------------------------------------- /api/utente/getUfficiUtente?idUtente=1
   @GetMapping("/getUfficiUtente")
   public ResponseEntity<List<Ufficio>> getUfficiUtente(
           @RequestParam(required = false) int idUtente) throws IOException {
	   		//Tabelle impattate: tbl_utenti, tbl_utenti_uffici_ruoli, tbl_settori_uffici, tbl_settori, tbl_uffici
	     List<Ufficio> uffici = utenteService.getUfficiUtente(idUtente);
	    return ResponseEntity.ok(uffici);
   }
   
	// API MENU ----------------------------------------------------------- /api/utente/menu?roleId=2
    /*@GetMapping("/menu")
    public ResponseEntity<String> getMenuByID(@RequestParam int roleId) throws IOException {
		return utenteService.getMenuByRoleWS(roleId);
	}*/
    
}
