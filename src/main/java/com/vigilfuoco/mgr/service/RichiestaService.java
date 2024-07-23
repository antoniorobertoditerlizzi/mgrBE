package com.vigilfuoco.mgr.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
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
import com.vigilfuoco.mgr.model.Priorita;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.TipologiaRichiesta;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.ModelloRepository;
import com.vigilfuoco.mgr.repository.PrioritaRepository;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.repository.TipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.token.JwtTokenProvider;

/* 
 * Logica di Business. Dettaglio della logica del singolo servizio.
 * 
 */

	@Service
	public class RichiestaService {
	    @Autowired
	    private final UtenteRepository repositoryUtente;
	    
	    @Autowired
	    private final RichiestaRepository repositoryRichiesta;
	    
	    @Autowired
	    private final ModelloRepository repositoryModello;
	    
	    @Autowired
	    private final TipologiaRichiestaRepository repositoryTipologiaRichiesta;
	    
	    @Autowired
	    private final PrioritaRepository repositoryPriorita;
	    
	    @Autowired 
	    private static JwtTokenProvider jwtTokenProvider;
	    
	    @Autowired
	    private static BlacklistServiceImpl blacklistService;
	    
	    


	    
	    private static final Logger logger = LogManager.getLogger(RichiestaService.class);

	    // Costruttore con iniezione dei repository
	    public RichiestaService(RichiestaRepository repositoryRichiesta, 
					    		ModelloRepository repositoryModello, 
					    		UtenteRepository repositoryUtente, 
					    		TipologiaRichiestaRepository repositoryTipologiaRichiesta,
					    		PrioritaRepository repositoryPriorita) {
	        this.repositoryUtente = repositoryUtente;
			this.repositoryRichiesta = repositoryRichiesta;
	        this.repositoryModello = repositoryModello;
	        this.repositoryTipologiaRichiesta = repositoryTipologiaRichiesta;
	        this.repositoryPriorita = repositoryPriorita;
	    }
	    
	    //Salva Richiesta
		public Richiesta salvaRichiesta(Richiesta request, String accountname) {
			//Cerco per accountname
		 	Utente resUtente = repositoryUtente.findByAccount(accountname);
		 	
			if (resUtente != null) {
		        request.getUtenteUfficioRuoloStatoIniziale().setUtente(resUtente);
		        request.getUtenteUfficioRuoloStatoCorrente().setUtente(resUtente);	
		        return repositoryRichiesta.save(request);
			}
	        return null;
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
			        
			        //DATABIND STRING-OBJ GENERICO
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
		 
		 
		 //LISTA TIPOLOGIE RICHIESTE
		 public ResponseEntity<List<TipologiaRichiesta>> tipologieRichieste() {
			 return ResponseEntity.ok(repositoryTipologiaRichiesta.findAll());	
		 }
		 
		 //TIPOLOGIA RICHIESTA
		 public ResponseEntity<TipologiaRichiesta> tipologiaRichiesta(Short idTipologiaRichiesta) {
			 return ResponseEntity.ok(repositoryTipologiaRichiesta.findByIdTipologiaRichiesta(idTipologiaRichiesta));	
		 }
		 
		//Check per verificare se il numero richiesta esiste a DB
		public boolean existsByNumeroRichiesta(String numeroRichiesta) {
		    return repositoryRichiesta.existsByNumeroRichiesta(numeroRichiesta);
		}
		
		 //LISTA PRIORITA
		 public ResponseEntity<List<Priorita>> getPriorityList() {
			 return ResponseEntity.ok(repositoryPriorita.findAll());	
		 }
		 
	}
