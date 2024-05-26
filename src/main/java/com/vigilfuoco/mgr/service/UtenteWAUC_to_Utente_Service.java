package com.vigilfuoco.mgr.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vigilfuoco.mgr.controller.UserWAUCController;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.UtenteWAUCRepository;
import com.vigilfuoco.mgr.utility.ExternalAPIClient;
import com.vigilfuoco.mgr.wauc.mapper.UtenteWAUCMapper;
import com.vigilfuoco.mgr.wauc.model.UtenteWAUC;

/* 
 * Logica di Business. Dettaglio della logica del singolo servizio.
 * 
 */
	@Service
	public class UtenteWAUC_to_Utente_Service {
		
		private static final Logger logger = LogManager.getLogger(UserWAUCController.class);


	    // API Parsing Response Chiamata API WAUC
		public List<UtenteWAUC> parsingResponseWAUC(String url) throws IOException, JsonProcessingException  {
			String jsonResponse = ExternalAPIClient.getJsonData(url);
			System.out.println(jsonResponse);
			logger.debug("Ingresso api " + url + "response: "+ jsonResponse);
		    UtenteWAUCMapper mapper = new UtenteWAUCMapper();
		    List<UtenteWAUC> utentiList = mapper.mapJsonToPersonaList(jsonResponse);
			return utentiList;
		}
		
	    // Prendo dal JSON WAUC i dati che mi servono e SALVO Utente MGR a DB MYSQL
	    public Utente salvaUtenteTrovato(UtenteWAUC request, UtenteWAUCRepository utenteWAUCRepository2) {
			  Utente utente = new Utente();

	    	  if (request == null) {
	    		    throw new IllegalArgumentException("Utente non può essere nullo!");
	    		  }
	    		  if (request.getAccountDipvvf() != null) {
	    			  utente.setAccountDipvvf(request.getAccountDipvvf());
	    			  utente.setEmailVigilfuoco(request.getEmailVigilfuoco());
	    			  utente.setUsername(request.getAccountDipvvf());
	    			  utente.setPassword(bcrypt(request.getAccountDipvvf()));
	    			  utente.setRole("ROLE_ADMIN");
	    			  utente.setEnabled(true);
	    		  }
	    	//Salvo a DB l'utente trovato
	        return utenteWAUCRepository2.save(utente);
	    }
	    
	    public String bcrypt(String password) {
	    	/**
	    	 * BCrypt: Include una funzione di crypt che aumenta la sicurezza e rende più difficile il calcolo di hash simili da password diverse. Inoltre, è configurabile con un parametro di costo che regola la forza di hash.
	 		 * SHA-256 o 512: Più indicato per i digest dei messaggi e i controlli di integrità dei file. il BCript offre una sicurezza superiore per le password.
			 * MD5: Non include una funzione di crypt integrata ed è vulnerabile a collisioni.
			 * 
			 * */
	         String hash = BCrypt.hashpw(password, BCrypt.gensalt());
	         return hash;
	    }
	    
	}
