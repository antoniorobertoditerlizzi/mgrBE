package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.UtenteWAUCRepository;
import com.vigilfuoco.mgr.service.UtenteWAUC_to_Utente_Service;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@CrossOrigin(origins = "http://localhost:3000") // URL del frontend React
public class UserWAUCController {
			
	@Value("${api.wauc.basepath}")
    private String waucBasePath;
	
	@Value("${api.wauc.personale}")
    private String waucPersonale;
	
	@Value("${api.wauc.anagraficapersonale}")
    private String anagraficaPersonale;
    
	@Autowired
    private UtenteWAUCRepository utenteWAUCRepository;
	
	private static final Logger logger = LogManager.getLogger(UserWAUCController.class);

	private UtenteWAUC_to_Utente_Service utenteWAUC_to_Utente_Service = new UtenteWAUC_to_Utente_Service();
	
	// API Login ------------------------------------ /api/utente/userWAUCDetails?accountName=antonioroberto.diterlizzi
	@GetMapping("userWAUCDetails")
	public ResponseEntity<List<UtenteWAUC>> getUserWAUCDetails(@RequestParam String accountName) throws IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
		return ResponseEntity.ok(utentiList);
	}
	
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
    
    
	// API Ricerca dettagli utente per accountName ------------------------------------ /api/utente/personale?accountName=antonioroberto.diterlizzi
    @GetMapping("personale")
	public ResponseEntity<List<UtenteWAUC>> getAccountName(@RequestParam String accountName) throws IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
    

	// API Ricerca dettagli utente per Nome e/o Cognome ------------------------------- /api/dettagliopersonale?nome=antonio&cognome=di terlizzi
    @GetMapping("dettagliopersonale")
	public ResponseEntity<List<UtenteWAUC>> getUtenteDetailedData(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) throws IOException, JsonProcessingException {
    	String decodedNome = URLEncoder.encode(nome, "UTF-8");
        String decodedCognome = URLEncoder.encode(cognome, "UTF-8");
		String url = waucBasePath + waucPersonale + "?nome=" + decodedNome + "&cognome=" + decodedCognome;
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
	

	// API Ricerca utente per Nome e/o Cognome ---------------------------------------- /api/utente/anagraficapersonale?nome=antonio&cognome=di terlizzi
    @GetMapping("anagraficapersonale")
	public ResponseEntity<List<UtenteWAUC>> getUtenteData(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) throws IOException, JsonProcessingException {
    	//String decodedNome = URLDecoder.decode(nome, "UTF-8");
        //String decodedCognome = URLDecoder.decode(cognome, "UTF-8");
    	String decodedNome = URLEncoder.encode(nome, "UTF-8");
        String decodedCognome = URLEncoder.encode(cognome, "UTF-8");
		String url = waucBasePath + anagraficaPersonale + "?nome=" + decodedNome + "&cognome=" + decodedCognome;
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);

	}

	// API Ricerca utente per CF ------------------------------------------------------ /api/utente/anagraficapersonale/DTRNNR85E11L109U
    @GetMapping("anagraficapersonale/{cf}")
    public ResponseEntity<List<UtenteWAUC>> getUtenteCF(@PathVariable String cf) throws IOException, JsonProcessingException {
		String url = waucBasePath + anagraficaPersonale + "?codiciFiscali=" + cf;
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
    
    
		
}
