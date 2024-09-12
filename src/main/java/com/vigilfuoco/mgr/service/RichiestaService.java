package com.vigilfuoco.mgr.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigilfuoco.mgr.exception.RichiestaException;
import com.vigilfuoco.mgr.model.Modello;
import com.vigilfuoco.mgr.model.ModelloConJson;
import com.vigilfuoco.mgr.model.ModelloTipologiaRichiesta;
import com.vigilfuoco.mgr.model.Priorita;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.StatoRichiesta;
import com.vigilfuoco.mgr.model.TipologiaRichiesta;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.ModelliTipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.ModelloRepository;
import com.vigilfuoco.mgr.repository.PrioritaRepository;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.repository.StatoRichiestaRepository;
import com.vigilfuoco.mgr.repository.TipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.utility.Utility;

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
	    private final StatoRichiestaRepository repositoryStatoRichiesta;
	    
	    @Autowired 
	    private final ModelliTipologiaRichiestaRepository repositoryModelliTipologiaRichiesta;

	    
	    


	    
	    private static final Logger logger = LogManager.getLogger(RichiestaService.class);

	    // Costruttore con iniezione dei repository
	    public RichiestaService(RichiestaRepository repositoryRichiesta, 
					    		ModelloRepository repositoryModello, 
					    		UtenteRepository repositoryUtente, 
					    		TipologiaRichiestaRepository repositoryTipologiaRichiesta,
					    		PrioritaRepository repositoryPriorita,
					    		StatoRichiestaRepository repositoryStatoRichiesta,
					    		ModelliTipologiaRichiestaRepository repositoryModelliTipologiaRichiesta) {
	        this.repositoryUtente = repositoryUtente;
			this.repositoryRichiesta = repositoryRichiesta;
	        this.repositoryModello = repositoryModello;
	        this.repositoryTipologiaRichiesta = repositoryTipologiaRichiesta;
	        this.repositoryPriorita = repositoryPriorita;
	        this.repositoryStatoRichiesta = repositoryStatoRichiesta;
	        this.repositoryModelliTipologiaRichiesta = repositoryModelliTipologiaRichiesta;

	    }
	    
	    //Salva/Modifica Richiesta
		public Richiesta salvaRichiesta(Richiesta request, String accountname, String numeroRichiesta, String oggetto) {
			//Cerco per accountname
		 	Utente resUtente = repositoryUtente.findByAccount(accountname);
		 	
		 	if (resUtente != null) {
		        request.getUtenteUfficioRuoloStatoIniziale().setUtente(resUtente);
		        request.getUtenteUfficioRuoloStatoCorrente().setUtente(resUtente);	
		        //Se l'oggetto ha un ID nullo o non esistente nel database, verrà eseguita un'operazione di salvataggio.
		        //Se l'oggetto ha un ID che esiste nel database, verrà eseguita un'operazione di aggiornamento.
				
		        // Salva Richiesta
		        Richiesta res = repositoryRichiesta.save(request);
                
		        // Invio Email
		        if (res != null) {
		        	Utility.invioEmailRichiesta(numeroRichiesta, resUtente.getEmailUtente(), oggetto);
			        return res;
		        } else {
			        throw new RichiestaException("Errore durante il salvataggio della richiesta");
			    }
			}
	        return null;
	    }
		
		 //VALIDAZIONE AUTORIZZAZIONE DEPRECATA NON PIU USATA
		 /*public static ResponseEntity<Object> checkAuthorization(Authentication authentication) {
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
		 }*/
		 
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
		 
		//TIPOLOGIA RICHIESTA GET DESCRIZIONE TIPOLOGIA
		 public String descTipologiaRichiesta(Short idTipologiaRichiesta) {
			 return repositoryTipologiaRichiesta.findByIdTipologiaRichiesta(idTipologiaRichiesta).getDescrizioneTipologiaRichiesta();	
		 }
		 
		 //SALVA TIPOLOGIA RICHIESTA
		 public ResponseEntity<TipologiaRichiesta> saveTipologiaRichiesta(TipologiaRichiesta tipologiaRichiesta) {
			
			    // Se viene passato solo l'ID dello stato della richiesta, recupera l'intera entità
			    if (tipologiaRichiesta.getStatoRichiestaPartenza() != null) {
			        Long statoRichiestaId = tipologiaRichiesta.getStatoRichiestaPartenza().getIdStatoRichiesta();
			        StatoRichiesta statoRichiesta = repositoryStatoRichiesta.findById(statoRichiestaId)
			            .orElseThrow(() -> new EntityNotFoundException("StatoRichiesta non trovato con ID: " + statoRichiestaId));
			        tipologiaRichiesta.setStatoRichiestaPartenza(statoRichiesta);
			    }
			 return ResponseEntity.ok(repositoryTipologiaRichiesta.save(tipologiaRichiesta));	
		 }
		 
		 //SALVA MODELLI TIPOLOGIA RICHIESTA
		    public ResponseEntity<ModelloTipologiaRichiesta> saveModelliTipologiaRichiesta(ModelloTipologiaRichiesta modelliTipologieRichieste) {
		        
		    	// Se viene passato solo l'ID del modello, recupera l'intera entità
		        if (modelliTipologieRichieste.getModello() != null) {
		            Long modelloId = modelliTipologieRichieste.getModello().getIdModello();
		            Modello modello = repositoryModello.findById(modelloId)
		                .orElseThrow(() -> new EntityNotFoundException("Modello non trovato con ID: " + modelloId));
		            modelliTipologieRichieste.setModello(modello);
		        }

		        // Se viene passato solo l'ID della tipologia di richiesta, recupera l'intera entità
		        if (modelliTipologieRichieste.getTipologiaRichiesta() != null) {
		            Short tipologiaRichiestaId = modelliTipologieRichieste.getTipologiaRichiesta().getIdTipologiaRichiesta();
		            TipologiaRichiesta tipologiaRichiesta = repositoryTipologiaRichiesta.findByIdTipologiaRichiesta(tipologiaRichiestaId);
		            if (tipologiaRichiesta == null) {
		                throw new EntityNotFoundException("TipologiaRichiesta non trovata con ID: " + tipologiaRichiestaId);
		            }
		            modelliTipologieRichieste.setTipologiaRichiesta(tipologiaRichiesta);
		        }

		        // Verifica se esiste già una combinazione di id_modello e id_tipologia_richiesta con attivo = true
		        List<ModelloTipologiaRichiesta> esistenti = repositoryModelliTipologiaRichiesta
		            .findByModello_IdModelloAndTipologiaRichiesta_IdTipologiaRichiestaAndAttivo(
		                modelliTipologieRichieste.getModello().getIdModello(),
		                modelliTipologieRichieste.getTipologiaRichiesta().getIdTipologiaRichiesta(),
		                true);

		        // Se esistono record già attivi, non salvare
		        if (!esistenti.isEmpty()) {
					logger.warn("Esiste già una combinazione di id_modello e id_tipologia_richiesta con attivo = true");
		            return ResponseEntity.status(HttpStatus.CONFLICT)
		                .body(null); // Restituisce uno stato di conflitto (409)
		        }
		        
		        // Salva e ritorna l'entità
		        return ResponseEntity.ok(repositoryModelliTipologiaRichiesta.save(modelliTipologieRichieste));
		    }

		 
		 
		//Check per verificare se il numero richiesta esiste a DB
		public boolean existsByNumeroRichiesta(String numeroRichiesta) {
		    return repositoryRichiesta.existsByNumeroRichiesta(numeroRichiesta);
		}
		
		//Dettaglio Richiesta per ID
	    public Optional<Richiesta> findById(Long id) {
	        return repositoryRichiesta.findById(id);
	    }
		 //LISTA PRIORITA
		 public ResponseEntity<List<Priorita>> getPriorityList() {
			 return ResponseEntity.ok(repositoryPriorita.findAll());	
		 }
		 
		 // Lista Modelli BY Tipologia
		 public List<Modello> getModelliByTipologia(Short idTipologiaRichiesta) {
		        List<ModelloTipologiaRichiesta> relazioni = repositoryModelliTipologiaRichiesta.findByTipologiaRichiesta_IdTipologiaRichiesta(idTipologiaRichiesta);
		        
		        // Debug
		        logger.debug("Relazioni trovate: " + relazioni.size());
		        for (ModelloTipologiaRichiesta relazione : relazioni) {
		        	logger.debug("Relazione: " + relazione);
		        	logger.debug("Modello: " + relazione.getModello());
		        }

		        return relazioni.stream()
		                .map(ModelloTipologiaRichiesta::getModello)
		                .filter(Objects::nonNull) // Filtra eventuali null
		                .collect(Collectors.toList());
		    }
		 
		 	// Lista Tipologie BY Modello
		    public List<TipologiaRichiesta> getTipologieByModello(Long idModello) {
		        List<ModelloTipologiaRichiesta> relazioni = repositoryModelliTipologiaRichiesta.findByModello_IdModello(idModello);
		        
		        // Debug
		        logger.debug("Relazioni trovate: " + relazioni.size());
		        for (ModelloTipologiaRichiesta relazione : relazioni) {
		        	logger.debug("Relazione: " + relazione);
		        	logger.debug("Tipologia Richiesta: " + relazione.getTipologiaRichiesta());
		        }

		        return relazioni.stream()
		                .map(ModelloTipologiaRichiesta::getTipologiaRichiesta)
		                .filter(Objects::nonNull) // Filtra eventuali null
		                .collect(Collectors.toList());
		    }
	}
