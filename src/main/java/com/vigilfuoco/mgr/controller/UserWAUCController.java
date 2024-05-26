package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.service.UtenteWAUC_to_Utente_Service;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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
@RequestMapping("/api/utenteWAUC/")
public class UserWAUCController {
			
	@Value("${api.wauc.basepath}")
    private String waucBasePath;
	
	@Value("${api.wauc.personale}")
    private String waucPersonale;
	
	@Value("${api.wauc.anagraficapersonale}")
    private String anagraficaPersonale;
	
	private static final Logger logger = LogManager.getLogger(UserWAUCController.class);

	private UtenteWAUC_to_Utente_Service utenteWAUC_to_Utente_Service = new UtenteWAUC_to_Utente_Service();
	
	// API Dettaglio Utente WAUC WS Personale ------------------------------------ /api/utenteWAUC/userWAUCDetails?accountName=antonioroberto.diterlizzi
	@GetMapping("userWAUCDetails")
	public ResponseEntity<List<UtenteWAUC>> getUserWAUCDetails(@RequestParam String accountName) throws IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		logger.debug("Ingresso api " + url);
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
		return ResponseEntity.ok(utentiList);
	}
	
	// API Ricerca dettagli utente per Nome e Cognome WS Personale ------------------------------- /api/utenteWAUC/dettagliopersonale?nome=antonio&cognome=di terlizzi
    @GetMapping("dettagliopersonale")
	public ResponseEntity<List<UtenteWAUC>> getUtenteDetailedData(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) throws IOException, JsonProcessingException {
    	String decodedNome = URLEncoder.encode(nome, "UTF-8");
        String decodedCognome = URLEncoder.encode(cognome, "UTF-8");
		String url = waucBasePath + waucPersonale + "?nome=" + decodedNome + "&cognome=" + decodedCognome;
		logger.debug("Ingresso api " + url);
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
	

	// API Ricerca utente per Nome e/o Cognome WS AnagraficaPersonale ---------------------------------------- /api/utenteWAUC/anagraficapersonale?nome=antonio&cognome=di terlizzi
    @GetMapping("anagraficapersonale")
	public ResponseEntity<List<UtenteWAUC>> getUtenteData(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) throws IOException, JsonProcessingException {
    	//String decodedNome = URLDecoder.decode(nome, "UTF-8");
        //String decodedCognome = URLDecoder.decode(cognome, "UTF-8");
    	String decodedNome = URLEncoder.encode(nome, "UTF-8");
        String decodedCognome = URLEncoder.encode(cognome, "UTF-8");
		String url = waucBasePath + anagraficaPersonale + "?nome=" + decodedNome + "&cognome=" + decodedCognome;
		logger.debug("Ingresso api " + url);
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);

	}

	// API Ricerca utente per CF WS AnagraficaPersonale ------------------------------------------------------ /api/utenteWAUC/anagraficapersonale/DTRNNR85E11L109U
    @GetMapping("anagraficapersonale/{cf}")
    public ResponseEntity<List<UtenteWAUC>> getUtenteCF(@PathVariable String cf) throws IOException, JsonProcessingException {
		String url = waucBasePath + anagraficaPersonale + "?codiciFiscali=" + cf;
		logger.debug("Ingresso api " + url);
		List<UtenteWAUC> utentiList = utenteWAUC_to_Utente_Service.parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
    
		
}
