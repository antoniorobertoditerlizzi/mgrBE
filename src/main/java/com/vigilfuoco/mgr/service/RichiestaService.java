package com.vigilfuoco.mgr.service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigilfuoco.mgr.model.Modello;
import com.vigilfuoco.mgr.model.ModelloConJson;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.repository.ModelloRepository;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.token.JwtTokenProvider;

/* 
 * Logica di Business. Dettaglio della logica del singolo servizio.
 * 
 */

	@Service
	public class RichiestaService {

	    @Autowired
	    private final RichiestaRepository repository;
	    
	    @Autowired
	    private final ModelloRepository repositoryModello;

	    
	    @Autowired 
	    private static JwtTokenProvider jwtTokenProvider;
	    
	    @Autowired
	    private static BlacklistServiceImpl blacklistService;
	    

	    
	    private static final Logger logger = LogManager.getLogger(RichiestaService.class);

	    // Costruttore con iniezione di entrambi i repository
	    public RichiestaService(RichiestaRepository repository, ModelloRepository repositoryModello) {
	        this.repository = repository;
	        this.repositoryModello = repositoryModello;
	    }
	    
	    //Salva Richiesta
		public Richiesta salvaRichiesta(Richiesta request) {
	        return repository.save(request);
	    }
		
		
		//VALIDAZIONE AUTORIZZAZIONE DEPRECATA NON PIU USATA
		 public static ResponseEntity<Object> checkAuthorization(Authentication authentication) {
		    	// Controllo se l'utente ha effettuato il login e quindi è autenticato,
			    if (authentication == null || !authentication.isAuthenticated()) {
			        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			    }
		    	// Recupero il token di sessione di quell'utente
				String token = jwtTokenProvider.getToken(authentication.getPrincipal().toString()); 
				logger.debug("Ingresso checkAuthorization - Bearer Token:" + token);
				
		    	// Controllo se ha gia effettuato il logout con quel token e quindi è un token blacklisted
			    if (blacklistService.isTokenBlacklisted(token)) {
			        logger.warn("Token rifiutato in quanto già inserito in blacklist. ", token);
			        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			    }
			    // Permetto di procedere nella richiesta desiderata
				return null;
		 }
		 
		 //TRANSCODIFICA MODELLI JSON
		 @SuppressWarnings("unchecked")
		 public ResponseEntity<ModelloConJson> formModelloByIDModello(Long idModello) throws JsonMappingException, JsonProcessingException {
			 
			 	Modello res = repositoryModello.findByidModello(idModello);
			 	
			 	if (res!=null) {
			        // Conversione da byte a String e parsing JSON
			        String jsonModello = new String(res.getTranscodificaModello(), StandardCharsets.UTF_8);
			        ObjectMapper mapper = new ObjectMapper();
			        
					Map<String, Object> modelloMap = mapper.readValue(jsonModello, Map.class);
					
			        // Creazione di un nuovo oggetto ModelloConJson
			        ModelloConJson modelloConJson = new ModelloConJson(res.getIdModello(), res.getDescrizioneModello(), modelloMap);
			        modelloConJson.setIdModello(res.getIdModello());
			        modelloConJson.setDescrizioneModello( res.getDescrizioneModello());
			        modelloConJson.setAttivo(res.isAttivo());
			        return ResponseEntity.ok(modelloConJson);	
			 	}
			 	return ResponseEntity.ok(null);	
		 }
		 
		 
	}
