package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.repository.UtenteWAUCRepository;
import com.vigilfuoco.mgr.service.UtenteWAUC_to_Utente_Service;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    
	private static final Logger logger = LogManager.getLogger(UtenteController.class);

	private UtenteWAUC_to_Utente_Service utenteWAUC_to_Utente_Service = new UtenteWAUC_to_Utente_Service();
	
	
	// API LoginCheck -------------------------------- /api/utente/login?accountName=antonioroberto.diterlizzi
	@GetMapping("login")
	public ResponseEntity<Utente> getLogin(@RequestParam String accountName) throws IllegalArgumentException, IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		// recupero i dati dell'utente da salvare nella tabella Utente
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
		logger.debug("Ingresso api /api/utente/loginCheck?accountName=" +accountName + " lista utenti: " + utentiList);
		Utente savedUser = new Utente();
		
		if (!utentiList.isEmpty()) {
			  if (!utentiList.isEmpty()) {
				    try {
				  	  // scrivo a db l'utente trovato
				      savedUser = utenteWAUC_to_Utente_Service.salvaUtenteTrovato(utentiList.get(0),utenteWAUCRepository);
				      if (savedUser != null) {
				    	// restituisco al WS in output i dati salvati
				        return ResponseEntity.ok(savedUser);
				      } 
				    } catch (IllegalArgumentException e) {
				      logger.error("Error saving user: " + e.getMessage());
				      throw new RichiestaException("Errore durante il salvataggio della richiesta: " + e.getMessage());
				    }
				  }
		}
    	// restituisco al WS in output i dati salvati
		return ResponseEntity.ok(savedUser);
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
    
    
		
}
