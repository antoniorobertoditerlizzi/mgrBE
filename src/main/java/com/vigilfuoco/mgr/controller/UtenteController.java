package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.model.JwtResponse;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.repository.UtenteWAUCRepository;
import com.vigilfuoco.mgr.service.BlacklistServiceImpl;
import com.vigilfuoco.mgr.service.UtenteWAUC_to_Utente_Service;
import com.vigilfuoco.mgr.token.JwtTokenProvider;
import com.vigilfuoco.mgr.utility.GetMenuByRole;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/* 
 * Definizione di tutte le API che contraddistinguono l'Utente
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
    private UtenteWAUCRepository utenteWAUCRepository;
	
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private BlacklistServiceImpl blacklistService;
    
    //@Autowired indica a Spring di inserire un'istanza della JwtTokenProviderclasse nel controller o nel servizio.
    //Ciò garantisce che stai utilizzando la stessa JwtTokenProvideristanza che Spring ha già creato e gestito, promuovendo la coerenza ed evitando potenziali problemi con più istanze.
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
	private static final Logger logger = LogManager.getLogger(UtenteController.class);

	private UtenteWAUC_to_Utente_Service utenteWAUC_to_Utente_Service = new UtenteWAUC_to_Utente_Service();

	private ResourceLoader resourceLoader;
	
    public UtenteController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
	
    
	// API LoginCheck -------------------------------- /api/utente/login?accountName=antonioroberto.diterlizzi
	@GetMapping("login")
	public ResponseEntity<JwtResponse> login(@RequestParam String accountName) throws IllegalArgumentException, IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		// recupero i dati dell'utente da salvare nella tabella Utente
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
		logger.debug("Ingresso api /api/utente/loginCheck?accountName=" +accountName + " lista utenti: " + utentiList);
		Utente savedUser = new Utente();

	    if (!utentiList.isEmpty()) {
		    try {
		      // genero token di sessione
		      //JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
		      String token = jwtTokenProvider.generateToken(accountName);
		      
		      if (token!= null) {
			  	  // scrivo a db l'utente trovato
			      savedUser = utenteWAUC_to_Utente_Service.salvaUtenteTrovato(utentiList.get(0),utenteWAUCRepository);

				      if (savedUser != null) {
				    	// restituisco al WS in output i dati salvati e genero anche il token di sessione
						try {
					        //return ResponseEntity.ok(savedUser);
					    	return ResponseEntity.ok(new JwtResponse(savedUser, token));
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Errore nella generazione del token: " + e.toString());
						}
				      } 
		      }
		    } catch (IllegalArgumentException e) {
		      logger.error("Error saving user: " + e.getMessage());
		      throw new RichiestaException("Errore durante il salvataggio della richiesta: " + e.getMessage());
		    }
		}
    	// restituisco al WS in output i dati salvati
		return null;
	}
    
	// API Loginout -------------------------------- /api/utente/logout
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) throws InvalidTokenException, UnsupportedJwtException, IllegalArgumentException, IOException {
	    String token = authorizationHeader.split(" ")[1];
	    System.out.println("Invalido il token " + token);
	    
        if (blacklistService.isTokenBlacklisted(token)) {
            logger.warn("Token presente nella Blacklist. ", token);
        } else {
        	jwtTokenProvider.invalidateToken(token); 	// Invalido il token
        }
        
        return ResponseEntity.ok("Logout effettuato con successo"); 
	}
	
	
	// API Ricerca tutti gli utenti salvati a DB -------------------------- /api/utente/accounts
    @GetMapping("accounts")
	public ResponseEntity<Iterable<Utente>> getAccounts() throws IOException, JsonProcessingException {
    	String url = waucBasePath + waucPersonale + "/accounts";
		logger.debug("Ingresso api" + url );
		
		Iterable<Utente> res = utenteRepository.findAll();
		
		for (Utente utente : res) {
			logger.debug("richiesta.getId():"+utente.getAccountDipvvf());
			logger.debug("richiesta.getId():"+utente.getEmailVigilfuoco());
		}

		return new ResponseEntity<Iterable<Utente>>(res, HttpStatus.OK);
	}
    

	// API Ricerca utente per accountName --------------------------------- /api/utente/account/antonioroberto.diterlizzi
    @GetMapping("account/{accountName}")
    public ResponseEntity<List<Utente>> getAccount(@PathVariable String accountName) throws IOException, JsonProcessingException {
    	String url = waucBasePath + waucPersonale + "/account/" + accountName;
		logger.debug("Ingresso api" + url );
		List<Utente> res = utenteRepository.findByAccountDipvvf(accountName);
		return new ResponseEntity<List<Utente>>(res, HttpStatus.OK);
	}
    

    
	// API MENU --------------------------------- /api/utente/menu?roleId=2
    @GetMapping("/menu")
    public ResponseEntity<String> getMenu(@RequestParam int roleId) throws IOException {
        // Replace with actual ResourceLoader from your Spring Boot application
        String strutturaMenu = "";

        //Eventualmente si salva il json al DB e non piu su file
        Resource path = resourceLoader.getResource("classpath:menu.json");
        
        JSONObject menuJson = GetMenuByRole.getMenuByRole(roleId, path);
        if (menuJson != null) {
            if (!menuJson.isEmpty()) {
                strutturaMenu = menuJson.toString();
            } else {
                strutturaMenu = "Menu vuoto";
            }
        } else {
            strutturaMenu = "Nessun Menu trovato appartenente al ruolo " + roleId + " richiesto";
        }
        
        logger.debug(menuJson);
		return new ResponseEntity<String>(strutturaMenu, HttpStatus.OK);

	}
    

    
		
}
