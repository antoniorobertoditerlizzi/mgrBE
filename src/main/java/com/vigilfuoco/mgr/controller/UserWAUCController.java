package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.mapper.UtenteMapper;
import com.vigilfuoco.mgr.utility.ExternalAPIClient;
import com.vigilfuoco.mgr.wauc.model.Utente;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/utente/")
@CrossOrigin(origins = "http://localhost:3000") // URL del frontend React
public class UserWAUCController {
		
	private static final Logger logger = LogManager.getLogger(UserWAUCController.class);
	
	@Value("${api.wauc.basepath}")
    private String waucBasePath;
	
	@Value("${api.wauc.personale}")
    private String waucPersonale;
	
	@Value("${api.wauc.anagraficapersonale}")
    private String anagraficaPersonale;
    
	
	// Login ------------------------------------ /api/utente/login?accountName=antonioroberto.diterlizzi
	@GetMapping("login")
	public ResponseEntity<List<Utente>> getLogin(@RequestParam String accountName) throws IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		List<Utente> utentiList = parsingResponseWAUC(url);
		return ResponseEntity.ok(utentiList);
	}
	
	// LoginCheck -------------------------------- /api/utente/loginCheck?accountName=antonioroberto.diterlizzi
	@GetMapping("loginCheck")
	public ResponseEntity<String> getLoginCheck(@RequestParam String accountName) throws IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		List<Utente> utentiList = parsingResponseWAUC(url);
		logger.debug("Ingresso api /api/utente/loginCheck?accountName=" +accountName + " lista utenti: " + utentiList);
		if (!utentiList.isEmpty()) {
			//scrivo a db l'utente: accountname
			return ResponseEntity.ok("OK");
		}
		return ResponseEntity.ok("KO");
	}
    
    
	// Ricerca dettagli utente per accountName ------------------------------------ /api/utente/personale?accountName=antonioroberto.diterlizzi
    @GetMapping("personale")
	public ResponseEntity<List<Utente>> getAccountName(@RequestParam String accountName) throws IOException, JsonProcessingException {
    	String decodedAccountName = URLEncoder.encode(accountName, "UTF-8");
		String url = waucBasePath + waucPersonale + "?accountName=" + decodedAccountName;
		List<Utente> utentiList = parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
    

	// Ricerca dettagli utente per Nome e/o Cognome ------------------------------- /api/dettagliopersonale?nome=antonio&cognome=di terlizzi
    @GetMapping("dettagliopersonale")
	public ResponseEntity<List<Utente>> getUtenteDetailedData(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) throws IOException, JsonProcessingException {
    	String decodedNome = URLEncoder.encode(nome, "UTF-8");
        String decodedCognome = URLEncoder.encode(cognome, "UTF-8");
		String url = waucBasePath + waucPersonale + "?nome=" + decodedNome + "&cognome=" + decodedCognome;
		List<Utente> utentiList = parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
	

	// Ricerca utente per Nome e/o Cognome ---------------------------------------- /api/utente/anagraficapersonale?nome=antonio&cognome=di terlizzi
    @GetMapping("anagraficapersonale")
	public ResponseEntity<List<Utente>> getUtenteData(@RequestParam(required = false) String nome, @RequestParam(required = false) String cognome) throws IOException, JsonProcessingException {
    	//String decodedNome = URLDecoder.decode(nome, "UTF-8");
        //String decodedCognome = URLDecoder.decode(cognome, "UTF-8");
    	String decodedNome = URLEncoder.encode(nome, "UTF-8");
        String decodedCognome = URLEncoder.encode(cognome, "UTF-8");
		String url = waucBasePath + anagraficaPersonale + "?nome=" + decodedNome + "&cognome=" + decodedCognome;
		List<Utente> utentiList = parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);

	}

	// Ricerca utente per CF ------------------------------------------------------ /api/utente/anagraficapersonale/DTRNNR85E11L109U
    @GetMapping("anagraficapersonale/{cf}")
    public ResponseEntity<List<Utente>> getUtenteCF(@PathVariable String cf) throws IOException, JsonProcessingException {
		String url = waucBasePath + anagraficaPersonale + "?codiciFiscali=" + cf;
		List<Utente> utentiList = parsingResponseWAUC(url);
	    return ResponseEntity.ok(utentiList);
	}
    
    
    // Parsing Response Chiamata API WAUC
	private List<Utente> parsingResponseWAUC(String url) throws IOException, JsonProcessingException  {
		String jsonResponse = ExternalAPIClient.getJsonData(url);
		System.out.println(jsonResponse);
		logger.debug("Ingresso api " + url + "response: "+ jsonResponse);
	    UtenteMapper mapper = new UtenteMapper();
	    List<Utente> utentiList = mapper.mapJsonToPersonaList(jsonResponse);
		return utentiList;
	}
		
}
