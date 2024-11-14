package com.vigilfuoco.mgr.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigilfuoco.mgr.exception.InvalidRequestStateException;
import com.vigilfuoco.mgr.exception.NumeroRichiestaDuplicatoException;
import com.vigilfuoco.mgr.exception.ResourceNotFoundException;
import com.vigilfuoco.mgr.exception.RichiestaException;
import com.vigilfuoco.mgr.exception.RichiestaNotFoundException;
import com.vigilfuoco.mgr.model.Modello;
import com.vigilfuoco.mgr.model.ModelloCompilato;
import com.vigilfuoco.mgr.model.ModelloConJson;
import com.vigilfuoco.mgr.model.ModelloTipologiaRichiesta;
import com.vigilfuoco.mgr.model.Priorita;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.RichiestaUpdateDTO;
import com.vigilfuoco.mgr.model.Settore;
import com.vigilfuoco.mgr.model.SettoreRichiesta;
import com.vigilfuoco.mgr.model.SettoreUfficio;
import com.vigilfuoco.mgr.model.StatoRichiesta;
import com.vigilfuoco.mgr.model.TipologiaRichiesta;
import com.vigilfuoco.mgr.model.Ufficio;
import com.vigilfuoco.mgr.model.UfficioRichieste;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.model.UtenteUfficioRuolo;
import com.vigilfuoco.mgr.repository.ModelliTipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.ModelloCompilatoRepository;
import com.vigilfuoco.mgr.repository.ModelloRepository;
import com.vigilfuoco.mgr.repository.PrioritaRepository;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.repository.SettoreRepository;
import com.vigilfuoco.mgr.repository.SettoreRichiestaRepository;
import com.vigilfuoco.mgr.repository.SettoreUfficioRepository;
import com.vigilfuoco.mgr.repository.StatoRichiestaRepository;
import com.vigilfuoco.mgr.repository.TipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.UfficioRepository;
import com.vigilfuoco.mgr.repository.UtenteRepository;
import com.vigilfuoco.mgr.repository.UtenteUfficioRuoloRepository;
import com.vigilfuoco.mgr.specification.RichiestaSpecification;
import com.vigilfuoco.mgr.utility.DateUtil;
import com.vigilfuoco.mgr.utility.Utility;

/* 
 * Logica di Business. Dettaglio della logica del singolo servizio.
 * 
 */

	@Service
	public class RichiestaService {
		
	    
	    @Autowired
	    private ModelloRepository modelloRepository;

	    @Autowired
	    private UtenteUfficioRuoloRepository utenteUfficioRuoloRepository;
	    
	    @Autowired
	    private ModelloCompilatoRepository modelloCompilatoRepository;
	    
	    @Autowired
	    private TipologiaRichiestaRepository tipologiaRichiestaRepository;
	    
	    @Autowired
	    private StatoRichiestaService statoRichiestaService;
	    
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
	    
	    @Autowired
	    private final SettoreUfficioRepository repositorySettoreUfficio;

	    @Autowired
	    private final SettoreRepository repositorySettore;
	    
	    @Autowired
	    private final UtenteUfficioRuoloRepository repositoryUtenteUfficioRuolo;
	    
	    @Autowired
	    private SettoreRichiestaRepository settoreRichiestaRepository;
	    
	    private static final Logger logger = LogManager.getLogger(RichiestaService.class);

	    // Costruttore con iniezione dei repository
	    public RichiestaService(RichiestaRepository repositoryRichiesta, 
					    		ModelloRepository repositoryModello, 
					    		UtenteRepository repositoryUtente, 
					    		TipologiaRichiestaRepository repositoryTipologiaRichiesta,
					    		PrioritaRepository repositoryPriorita,
					    		StatoRichiestaRepository repositoryStatoRichiesta,
					    		ModelliTipologiaRichiestaRepository repositoryModelliTipologiaRichiesta,
					    		UfficioRepository repositoryUfficio,
					    		SettoreUfficioRepository repositorySettoreUfficio,
					    		UtenteUfficioRuoloRepository repositoryUtenteUfficioRuolo,
					    		SettoreRepository repositorySettore) {
	        this.repositoryUtente = repositoryUtente;
			this.repositoryRichiesta = repositoryRichiesta;
	        this.repositoryModello = repositoryModello;
	        this.repositoryTipologiaRichiesta = repositoryTipologiaRichiesta;
	        this.repositoryPriorita = repositoryPriorita;
	        this.repositoryStatoRichiesta = repositoryStatoRichiesta;
	        this.repositoryModelliTipologiaRichiesta = repositoryModelliTipologiaRichiesta;
	        this.repositorySettoreUfficio = repositorySettoreUfficio;
	        this.repositoryUtenteUfficioRuolo = repositoryUtenteUfficioRuolo;
	        this.repositorySettore = repositorySettore;
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
		 
		 //SALVA SETTORI DI COMPETENZA
		 public ResponseEntity<SettoreUfficio> saveSettoriCompetenza(SettoreUfficio settoreUfficio) {
			    
			    // Controlla se il settore è presente
			   /* if (settoreUfficio.getSettore() != null && settoreUfficio.getSettore().getIdSettore() != null) {
			        Long settoreId = settoreUfficio.getSettore().getIdSettore();
			        SettoreUfficio settore = repositorySettoreUfficio.findById(settoreId)
			            .orElseThrow(() -> new EntityNotFoundException("Settore non trovato con ID: " + settoreId));
			        settoreUfficio.setSettore(settore);
			    } else {
			        throw new IllegalArgumentException("ID settore non può essere nullo.");
			    }

			    // Controlla se l'ufficio è presente
			    if (settoreUfficio.getUfficio() != null && settoreUfficio.getUfficio().getIdUfficio() != null) {
			        Long ufficioId = settoreUfficio.getUfficio().getIdUfficio();
			        Ufficio ufficio = repositoryUfficio.findById(ufficioId)
			            .orElseThrow(() -> new EntityNotFoundException("Ufficio non trovato con ID: " + ufficioId));
			        settoreUfficio.setUfficio(ufficio);
			    } else {
			        throw new IllegalArgumentException("ID ufficio non può essere nullo.");
			    }*/

			    // Salva il nuovo SettoreUfficio
			    return ResponseEntity.ok(repositorySettoreUfficio.save(settoreUfficio));
			}
	 
		 //SALVA SETTORI DI COMPETENZA
		 public ResponseEntity<Settore> saveSettori(Settore settore) {
			    return ResponseEntity.ok(repositorySettore.save(settore));
		}
		 
		// Lista settori di competenza
	    public ResponseEntity<List<SettoreUfficio>> getListSettoriCompetenza() {
	        List<SettoreUfficio> settori = repositorySettoreUfficio.findAll();
	        return ResponseEntity.ok(settori);
	    }
	    
		// Lista settori
	    public ResponseEntity<List<Settore>> getListSettori(Long id) {
	        if (id != null) {
	            Optional<Settore> settore = repositorySettore.findById(id);
	            return settore.map(s -> {
	                        List<Settore> singleSettoreList = Collections.singletonList(s);
	                        return ResponseEntity.ok(singleSettoreList);
	                    })
	                    .orElseGet(() -> ResponseEntity.notFound().build());
	        } else {
	            // Altrimenti, restituisci tutti i settori
	            List<Settore> settori = repositorySettore.findAll();
	            return ResponseEntity.ok(settori);
	        }
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
		    
		    

	    public boolean existsBySettoreAndTipologiaRichiesta(Long idSettore, Short idTipologiaRichiesta) {
	        return settoreRichiestaRepository.existsBySettore_IdSettoreAndTipologiaRichiesta_IdTipologiaRichiesta(idSettore, idTipologiaRichiesta);
	    }
	    
	    public SettoreRichiesta saveSettoreRichiesta(SettoreRichiesta settoreRichiesta) {
	        return settoreRichiestaRepository.save(settoreRichiesta);
	    }
	    
	    public boolean updateSettoreRichiestaAttivo(Long idSettoreRichiesta, boolean attivo) {
	        int updatedRows = settoreRichiestaRepository.updateAttivoByIdSettoreRichiesta(idSettoreRichiesta, attivo);
	        return updatedRows > 0; // Restituisce true se è stato aggiornato almeno un record
	    }
	    
	    public boolean updateSettoreAttivo(Long idSettore, boolean attivo) {
	        int updatedRows = repositorySettore.updateAttivoByIdSettore(idSettore, attivo);
	        return updatedRows > 0; // Restituisce true se è stato aggiornato almeno un record
	    }
	    
	    public boolean updateTipologiaRichiestaAttivo(Short idTipologiaRichiesta, boolean attivo) {
	        int updatedRows = repositoryTipologiaRichiesta.updateAttivoByIdTipologiaRichiesta(idTipologiaRichiesta, attivo);
	        return updatedRows > 0; // Restituisce true se è stato aggiornato almeno un record
	    }
	    
	    
	    public List<SettoreRichiesta> getAllSettoreRichiesta() {
	        return settoreRichiestaRepository.findAll();
	    }

	    public List<SettoreRichiesta> getByIdSettore(Long idSettore) {
	        return settoreRichiestaRepository.findBySettore_IdSettore(idSettore);
	    }

	    public List<SettoreRichiesta> getByIdTipologiaRichiesta(Short idTipologiaRichiesta) {
	        return settoreRichiestaRepository.findByTipologiaRichiesta_IdTipologiaRichiesta(idTipologiaRichiesta);
	    }

	    public List<SettoreRichiesta> getByIdSettoreAndIdTipologiaRichiesta(Long idSettore, Short idTipologiaRichiesta) {
	        return settoreRichiestaRepository.findBySettore_IdSettoreAndTipologiaRichiesta_IdTipologiaRichiesta(idSettore, idTipologiaRichiesta);
	    }
	    
	    
	    
	    public Richiesta aggiornaRichiesta(Long idRichiesta, RichiestaUpdateDTO richiestaUpdateDTO) {
	        Richiesta richiesta = repositoryRichiesta.findById(idRichiesta)
	                .orElseThrow(() -> new EntityNotFoundException("Richiesta non trovata con id: " + idRichiesta));

	        if (richiestaUpdateDTO.getNumeroRichiesta() != null) {
	            richiesta.setNumeroRichiesta(richiestaUpdateDTO.getNumeroRichiesta());
	        }

	        if (richiestaUpdateDTO.getIdStatoRichiesta() != null) {
	            StatoRichiesta statoRichiesta = repositoryStatoRichiesta.findById(richiestaUpdateDTO.getIdStatoRichiesta())
	                    .orElseThrow(() -> new EntityNotFoundException("Stato Richiesta non trovato"));
	            richiesta.setStatoRichiesta(statoRichiesta);
	        }

	        if (richiestaUpdateDTO.getIdTipologiaRichiesta() != null) {
	            TipologiaRichiesta tipologiaRichiesta = repositoryTipologiaRichiesta.findById(richiestaUpdateDTO.getIdTipologiaRichiesta())
	                    .orElseThrow(() -> new EntityNotFoundException("Tipologia Richiesta non trovata"));
	            richiesta.setTipologiaRichiesta(tipologiaRichiesta);
	        }

	        if (richiestaUpdateDTO.getRichiestaPersonale() != null) {
	            richiesta.setRichiestaPersonale(richiestaUpdateDTO.getRichiestaPersonale());
	        }

	        if (richiestaUpdateDTO.getIdPriorita() != null) {
	            Priorita priorita = repositoryPriorita.findById(richiestaUpdateDTO.getIdPriorita())
	                    .orElseThrow(() -> new EntityNotFoundException("Priorità non trovata"));
	            richiesta.setPriorita(priorita);
	        }

	        if (richiestaUpdateDTO.getIdUtenteUfficioRuoloStatoCorrente() != null) {
	            UtenteUfficioRuolo utenteCorrente = repositoryUtenteUfficioRuolo.findById(richiestaUpdateDTO.getIdUtenteUfficioRuoloStatoCorrente())
	                    .orElseThrow(() -> new EntityNotFoundException("Utente Ufficio Ruolo Corrente non trovato"));
	            richiesta.setUtenteUfficioRuoloStatoCorrente(utenteCorrente);
	        }

	        if (richiestaUpdateDTO.getIdUtenteUfficioRuoloStatoIniziale() != null) {
	            UtenteUfficioRuolo utenteIniziale = repositoryUtenteUfficioRuolo.findById(richiestaUpdateDTO.getIdUtenteUfficioRuoloStatoIniziale())
	                    .orElseThrow(() -> new EntityNotFoundException("Utente Ufficio Ruolo Iniziale non trovato"));
	            richiesta.setUtenteUfficioRuoloStatoIniziale(utenteIniziale);
	        }

	        if (richiestaUpdateDTO.getIdSettoreUfficio() != null) {
	            SettoreUfficio settoreUfficio = repositorySettoreUfficio.findById(richiestaUpdateDTO.getIdSettoreUfficio())
	                    .orElseThrow(() -> new EntityNotFoundException("Settore Ufficio non trovato"));
	            richiesta.setSettoreUfficio(settoreUfficio);
	        }

	        return repositoryRichiesta.save(richiesta);
	    }
	    
	    
	    
	    public List<UfficioRichieste> getUfficiRichieste(List<UtenteUfficioRuolo> utentiUfficiRuoli) {
	    	
	    	 List<UfficioRichieste> result = new ArrayList<>();
	         for (UtenteUfficioRuolo utenteUfficioRuolo : utentiUfficiRuoli) {
	             Ufficio ufficio = utenteUfficioRuolo.getSettoreUfficio().getUfficio();
	             SettoreUfficio settoreUfficio = utenteUfficioRuolo.getSettoreUfficio();
	             
	             List<Richiesta> richieste = repositoryRichiesta.findAll(Specification
	             	    .where(RichiestaSpecification.hasIdUfficio(ufficio.getIdUfficio()))
	             	    .and(RichiestaSpecification.hasIdSettoreUfficio(settoreUfficio.getIdSettoreUfficio()))
	             	);
	             UfficioRichieste dto = new UfficioRichieste();
	             dto.setIdUfficio(ufficio.getIdUfficio());
	             dto.setDescrizioneUfficio(ufficio.getDescrizioneUfficio());
	             dto.setRichieste(richieste);

	             result.add(dto);
	         }
	         return result;
	    }
	    
	    
	    
	    public Richiesta saveRequest(Richiesta request, String accountname, RichiestaService richiestaService) {
	    	
	    	// Check Stato Richiesta // Controllo su id richiesta se già censita allora modifico altrimenti imposto su inserita
		    if (request.getStatoRichiesta() == null) { // || request.getStatoRichiesta().getIdStatoRichiesta() == 1) {
			    StatoRichiesta statoInserita = new StatoRichiesta();
			    statoInserita.setIdStatoRichiesta(1L); 		// 1 Inserita **********
			    statoInserita.setPercentuale(10);			// 1 Inserita **********
			    statoInserita.setColore("#808080");			// 1 Inserita **********
			    request.setStatoRichiesta(statoInserita);
		    
			    // Verifico che TipologiaRichiesta e SettoreUfficio non siano null
			    TipologiaRichiesta tipoRichiesta = request.getTipologiaRichiesta();
			    Short idTipologiaRichiesta = tipoRichiesta.getIdTipologiaRichiesta();
			    
			    // Recupero la descrizione associata all'idTipologia
			    String descrizioneTipologiaRichiesta = richiestaService.descTipologiaRichiesta(idTipologiaRichiesta);
			    
			    SettoreUfficio settoreUfficio = request.getSettoreUfficio();
			    if (tipoRichiesta == null || settoreUfficio == null || settoreUfficio.getUfficio() == null) {
			        throw new RichiestaException("TipologiaRichiesta, SettoreUfficio, o Ufficio non possono essere null");
			    }
		
			    // Genero codice ISBN Richiesta
			    Long idUtente = request.getUtenteUfficioRuoloStatoCorrente().getIdUtenteUfficioRuolo();
			    Long idUfficio = settoreUfficio.getUfficio().getIdUfficio(); // ????????
			    String numeroRichiesta = Utility.generaNumeroRichiesta(
			    		descrizioneTipologiaRichiesta,
			            idUtente,
			            idUfficio
			    );

			    // Se numero richiesta esiste a DB genero eccezione
			    if (richiestaService.existsByNumeroRichiesta(numeroRichiesta)) {
			        throw new NumeroRichiestaDuplicatoException("Il numero richiesta esiste già: " + numeroRichiesta);
			    }
			    
			    request.setNumeroRichiesta(numeroRichiesta);
			    
			    // Ricavo l'ora corrente nel fuso orario locale
			    LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Rome"));
			    //LocalDateTime now = LocalDateTime.now();
			    request.setDataInserimentoRichiesta(now);
			    request.setDataUltimoStatoRichiesta(now);
			    
			    // STAMPO DATA ATTUALE
			    String formattedDate = DateUtil.format(now);
			    System.out.println("Formatted Date: " + formattedDate);
			    
			    // Salvataggio richiesta
	        	String oggetto = "Salvataggio Richiesta";
			    Richiesta savedRichiesta = richiestaService.salvaRichiesta(request, accountname, numeroRichiesta, oggetto);
			    if (savedRichiesta != null) {
			        return savedRichiesta;
			    } else {
			        throw new RichiestaException("Errore durante il salvataggio della richiesta");
			    }
		    }
			return null;
	    }
	    
	    
		public Richiesta update(Long id, Richiesta updatedRequest, String accountname, RichiestaService richiestaService) {
	    	
	        // Trova la richiesta esistente per ID
	        Richiesta existingRequest = richiestaService.findById(id)
	                .orElseThrow(() -> new RichiestaNotFoundException("Richiesta non trovata con ID: " + id));

	        // Aggiorna i campi della richiesta esistente con i nuovi dati
	        existingRequest.setTipologiaRichiesta(updatedRequest.getTipologiaRichiesta());
	        existingRequest.setSettoreUfficio(updatedRequest.getSettoreUfficio());
	        existingRequest.setDataUltimoStatoRichiesta(LocalDateTime.now(ZoneId.of("Europe/Rome")));
	        
	        // Capire gli altri campi...
	        
	        // Salva la richiesta aggiornata
	    	String oggetto = "Modifica Richiesta";
	        Richiesta savedRequest = richiestaService.salvaRichiesta(existingRequest, accountname, existingRequest.getNumeroRichiesta(), oggetto);
	        
	        if (savedRequest != null) {
	            return savedRequest;
	        } else {
	            throw new RichiestaException("Errore durante l'aggiornamento della richiesta");
	        }
	        
	    }
	
	public ResponseEntity<ModelloCompilato> salvaModelloCompilato(String fileModello, Long idModello, Long idRichiesta, String transcodificaFile) {
        try {
        	
            // Conversione fileModello (JSON) in byte
            byte[] fileModelloBytes = fileModello.getBytes(StandardCharsets.UTF_8);

            // Conversione transcodificaFile (Base64) in byte
            byte[] transcodificaFileBytes = Base64.getDecoder().decode(transcodificaFile);

            // Carico l'entità Modello dal DB
            Modello modello = modelloRepository.findById(idModello)
                    .orElseThrow(() -> new RuntimeException("Modello non trovato"));

            // Carico l'entità Richiesta dal DB
            Richiesta richiesta = repositoryRichiesta.findById(idRichiesta)
                    .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));

            // Creo e popolo l'entità ModelloCompilato
            ModelloCompilato modelloCompilato = new ModelloCompilato();
            modelloCompilato.setFileModello(fileModelloBytes);
            modelloCompilato.setModello(modello);
            modelloCompilato.setRichiesta(richiesta);
            modelloCompilato.setTranscodificaModelloCompilato(transcodificaFileBytes);

            // Salvo l'entità ModelloCompilato nel DB
            ModelloCompilato nuovoModelloCompilato = modelloCompilatoRepository.save(modelloCompilato);
            return ResponseEntity.ok(nuovoModelloCompilato);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
	}


	// Metodo di utilità per convertire un array di byte in MultipartFile
	/*private MultipartFile convertToMultipartFile(byte[] content, String fileName, String contentType) {
	    DiskFileItem fileItem = new DiskFileItem("file", contentType, false, fileName, content.length, null);
	    try {
	        IOUtils.write(content, fileItem.getOutputStream());
	    } catch (IOException e) {
	        throw new RuntimeException("Errore nella creazione del MultipartFile", e);
	    }
	    return new CommonsMultipartFile(fileItem);
	}*/

	public ResponseEntity<Modello> salvaModello(String descrizioneModello, MultipartFile file, Boolean attivo) {

        try {
            // Creare una nuova entità Modello
            Modello modello = new Modello();
            modello.setDescrizioneModello(descrizioneModello);
            modello.setTranscodificaModello(file.getBytes());
            modello.setAttivo(attivo);

            // Salvare l'entità nel database
            Modello nuovoModello = modelloRepository.save(modello);

            // Restituire l'entità salvata come risposta
            return ResponseEntity.ok(nuovoModello);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();  // Gestione dell'errore
        }
		
	}

	public ResponseEntity<List<UtenteUfficioRuolo>> getUtenteUfficiRuoli(Integer idUtente, Boolean attivo) {
	     logger.debug("Chiamata a /utente/uffici/ruoli con idUtente: {}", idUtente, attivo);
	        List<UtenteUfficioRuolo> utentiUfficiRuoli;
	        if (idUtente != null) {
	            utentiUfficiRuoli = utenteUfficioRuoloRepository.findByUtenteIdUtenteAndAttivo(idUtente, attivo);
	        } else {
	            utentiUfficiRuoli = utenteUfficioRuoloRepository.findAll();
	        }
	        if (utentiUfficiRuoli.isEmpty()) {
	            return ResponseEntity.ok().build();
	        }
	        return ResponseEntity.ok(utentiUfficiRuoli);
	}
	


	public ResponseEntity<List<UfficioRichieste>> getUfficiRichiesteWS(Integer idUtente, Boolean attivo) {
		List<UtenteUfficioRuolo> utentiUfficiRuoli = utenteUfficioRuoloRepository.findByUtenteIdUtenteAndAttivo(idUtente, attivo);
        
        if (utentiUfficiRuoli.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<UfficioRichieste> result;
        try {
            result = getUfficiRichieste(utentiUfficiRuoli);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(result);
	}

	public ResponseEntity<String> deleteRichiesta(Long id) {

        // Verifica che la richiesta esista
        Richiesta richiesta = repositoryRichiesta.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Richiesta non trovata con id: " + id));

        // Controlla se lo stato della richiesta è 1
        if (richiesta.getStatoRichiesta().getIdStatoRichiesta() != 1) {
            throw new InvalidRequestStateException("La richiesta non può essere cancellata perché non ha lo stato di bozza ovvero 'Inserita'.");
        }

        // Cancella la richiesta
        repositoryRichiesta.delete(richiesta);

        return ResponseEntity.ok("Richiesta cancellata con successo.");
	}

	public ResponseEntity<String> deleteTipologiaRichiesta(Short idTipologiaRichiesta) {
		try {
            // Verifico se esistono richieste correlate già salvate a DB per quella tipologia richiesta
            if (repositoryRichiesta.existsByTipologiaRichiesta_IdTipologiaRichiesta(idTipologiaRichiesta)) {
                return ResponseEntity.badRequest().body("Impossibile cancellare la tipologia di richiesta: già utilizzata in altre richieste.");
            }

            // Recupero tutti i modelli associati alla tipologia richiesta
            List<ModelloTipologiaRichiesta> modelliTipologia = repositoryModelliTipologiaRichiesta.findByTipologiaRichiesta_IdTipologiaRichiesta(idTipologiaRichiesta);

            // Controllo che non ci siano modelli compilati per i modelli associati a questa tipologia
            boolean hasCompilati = false;
            List<Long> idModelli = new ArrayList<>();

            for (ModelloTipologiaRichiesta modelloTipologia : modelliTipologia) {
                Long idModello = modelloTipologia.getModello().getIdModello();
                idModelli.add(idModello);

                // Verifico se esistono modelli compilati associati a questo modello
                List<ModelloCompilato> modelliCompilati = modelloCompilatoRepository.findByModelloIdAndTipologiaRichiestaIdAndAttivo(idModello, idTipologiaRichiesta);
                if (!modelliCompilati.isEmpty()) {
                    hasCompilati = true;
                    break; // Termino se trovo un modello compilato
                }
            }

            // Se ci sono modelli compilati, restituisci un errore
            if (hasCompilati) {
                return ResponseEntity.badRequest().body("Impossibile cancellare la tipologia di richiesta: presente un modello compilato associato.");
            }

            // Se non ci sono modelli compilati, cancelliamo i modelli associati
            for (Long idModello : idModelli) {
                modelloRepository.deleteById(idModello);
        		logger.debug("Cancellato modello: " + idModello);
            }

            // Cancellazione delle righe associate nella tabella ModelloTipologiaRichiesta
            repositoryModelliTipologiaRichiesta.deleteByTipologiaRichiesta_IdTipologiaRichiesta(idTipologiaRichiesta);
    		logger.debug("Cancellate righe tabella modelli tipologia richiesta aventi idTipologiaRichiesta: " + idTipologiaRichiesta);

            // Cancellazione della tipologia di richiesta
            TipologiaRichiesta tipologiaRichiesta = tipologiaRichiestaRepository.findByIdTipologiaRichiesta(idTipologiaRichiesta);
            if (tipologiaRichiesta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TipologiaRichiesta non trovata con ID: " + idTipologiaRichiesta);
            }
            tipologiaRichiestaRepository.delete(tipologiaRichiesta);
    		logger.debug("Cancellata tipologiaRichiesta: " + idTipologiaRichiesta);
            return ResponseEntity.ok("Tipologia di richiesta cancellata con successo.");

        } catch (DataIntegrityViolationException e) {
            // Gestione dell'eccezione di violazione del vincolo di integrità referenziale
            return ResponseEntity.badRequest().body("Presenti relazioni con la tabella tbl_tipologie_richieste, impossibile procedere con l'eliminazione. Rimuovere prima relazione con tbl_modelli_tipologie_richieste.");

        } catch (Exception e) {
            // Gestione di altre eccezioni generiche
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno durante l'eliminazione della tipologia di richiesta.");
        }
	}

	public ResponseEntity<String> deleteSettoreUfficio(Short idSettoreUfficio) {
		try {
            // Verifica se ci sono richieste correlate
            if (repositoryRichiesta.existsBySettoreUfficio_IdSettoreUfficio(idSettoreUfficio)) {
                return ResponseEntity.badRequest().body("Impossibile cancellare il settore ufficio: già utilizzato in altre richieste.");
            }
            
            // Recupera il SettoreUfficio da cancellare
            SettoreUfficio settoreUfficio = repositorySettoreUfficio.findByIdSettoreUfficio(idSettoreUfficio);
            
            if (settoreUfficio == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SettoreUfficio non trovato con ID: " + idSettoreUfficio);
            }
            
            // Cancellazione del SettoreUfficio
            repositorySettoreUfficio.delete(settoreUfficio);
            return ResponseEntity.ok("SettoreUfficio cancellato con successo.");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Presenti relazioni con altre tabelle, impossibile procedere con l'eliminazione.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno durante l'eliminazione del SettoreUfficio.");
        }
	}

	public ResponseEntity<String> deleteSettoreUfficioWs(Long idSettore) {
		 try {
	            // Recupero del Settore
	            Settore settore = repositorySettore.findById(idSettore)
	                .orElseThrow(() -> new EntityNotFoundException("Settore non trovato con ID: " + idSettore));

	            // Cancellazione del Settore
	            repositorySettore.delete(settore);
	            return ResponseEntity.ok("Settore cancellato con successo.");

	        } catch (DataIntegrityViolationException e) {
	            return ResponseEntity.badRequest().body("Presenti relazioni con altre tabelle, impossibile procedere con l'eliminazione.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno durante l'eliminazione del Settore.");
	        }
	}

	public ResponseEntity<?> saveSettoreRichiestaWs(Long idSettore, Short idTipologiaRichiesta, Boolean attivo) {
	       if (idSettore == null || idTipologiaRichiesta == null || attivo == null) {
	            return ResponseEntity
	                    .badRequest()
	                    .body("Parametri obbligatori mancanti: idSettore, idTipologiaRichiesta, attivo");
	        }

	        try {
	            // Controllo se la tupla esiste già
	            boolean exists = existsBySettoreAndTipologiaRichiesta(idSettore, idTipologiaRichiesta);

	            if (exists) {
	                logger.warn("Tupla per SettoriRichieste già presente a DB.");
	                return ResponseEntity.status(HttpStatus.OK).body("Warning: Tupla già esistente.");
	            }

	            // Creazione dell'entità Settore, SettoreRichiesta e salvataggio
	            Settore settore = new Settore();
	            settore.setIdSettore(idSettore);

	            TipologiaRichiesta tipologiaRichiesta = new TipologiaRichiesta();
	            tipologiaRichiesta.setIdTipologiaRichiesta(idTipologiaRichiesta);

	            SettoreRichiesta settoreRichiesta = new SettoreRichiesta();
	            settoreRichiesta.setSettore(settore);
	            settoreRichiesta.setTipologiaRichiesta(tipologiaRichiesta);
	            settoreRichiesta.setAttivo(attivo);

	            SettoreRichiesta savedSettoreRichiesta = saveSettoreRichiesta(settoreRichiesta);

	            return ResponseEntity.ok(savedSettoreRichiesta);
	        } catch (Exception e) {
	            logger.error("Errore durante il salvataggio di SettoreRichiesta", e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server");
	        }
	}


	public ResponseEntity<List<SettoreRichiesta>> getSettoriRichiestaWs(Long idSettore, Short idTipologiaRichiesta) {
	    List<SettoreRichiesta> result;

        // Filtra in base ai parametri ricevuti
        if (idSettore != null && idTipologiaRichiesta != null) {
            result = getByIdSettoreAndIdTipologiaRichiesta(idSettore, idTipologiaRichiesta);
        } else if (idSettore != null) {
            result = getByIdSettore(idSettore);
        } else if (idTipologiaRichiesta != null) {
            result = getByIdTipologiaRichiesta(idTipologiaRichiesta);
        } else {
            result = getAllSettoreRichiesta();
        }

        return ResponseEntity.ok(result);
	}

	public ResponseEntity<List<StatoRichiesta>> getAllStatiRichieste(Long idStatoRichiesta,
			String descrizioneStatoRichiesta) {
        if (idStatoRichiesta != null) {
            Optional<StatoRichiesta> statoRichiesta = statoRichiestaService.getById(idStatoRichiesta);
            if (statoRichiesta.isPresent()) {
                return ResponseEntity.ok(Collections.singletonList(statoRichiesta.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else if (descrizioneStatoRichiesta != null) {
            List<StatoRichiesta> statiRichiesta = statoRichiestaService.getByDescrizione(descrizioneStatoRichiesta);
            if (!statiRichiesta.isEmpty()) {
                return ResponseEntity.ok(statiRichiesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            List<StatoRichiesta> statiRichiesta = statoRichiestaService.getAll();
            return ResponseEntity.ok(statiRichiesta);
        }
	}

	public ResponseEntity<StatoRichiesta> createStatoRichiesta(String descrizioneStatoRichiesta, boolean attivo, String descrizioneStato, int percentuale, String colore) {
        StatoRichiesta statoRichiesta = new StatoRichiesta();
        statoRichiesta.setDescrizioneStatoRichiesta(descrizioneStatoRichiesta);
        statoRichiesta.setAttivo(attivo);
        statoRichiesta.setDescrizioneStato(descrizioneStato);
        statoRichiesta.setPercentuale(percentuale);
        statoRichiesta.setColore(colore);

        StatoRichiesta savedStatoRichiesta = statoRichiestaService.save(statoRichiesta);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStatoRichiesta);
	}

	public ResponseEntity<?> updateTblStatoRichiesta(Long idStatoRichiesta, boolean attivo) {
        try {
            // Cerca lo stato richiesta tramite id
            Optional<StatoRichiesta> statoRichiestaOptional = statoRichiestaService.getById(idStatoRichiesta);

            if (statoRichiestaOptional.isPresent()) {
                StatoRichiesta statoRichiesta = statoRichiestaOptional.get();
                // Modifica il flag attivo
                statoRichiesta.setAttivo(attivo);
                // Salva lo stato aggiornato
                StatoRichiesta updatedStatoRichiesta = statoRichiestaService.save(statoRichiesta);
                return ResponseEntity.ok(updatedStatoRichiesta);
            } else {
                // Se non trovi lo stato richiesta con l'ID fornito
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StatoRichiesta non trovato con ID: " + idStatoRichiesta);
            }
        } catch (Exception e) {
            // Gestione di eventuali errori
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiornamento dello stato richiesta.");
        }
	}

	public ResponseEntity<List<Richiesta>> ricerca(Long id, String numeroRichiesta, Long idStatoRichiesta, String descrizioneStatoRichiesta, Boolean attivo, Short idSettoreUfficio, Long idUfficio, Long idUtente, Long idUtenteUfficioRuoloStatoCorrente, Long idUtenteUfficioRuoloStatoIniziale, String descrizioneUfficio) {
    	
        List<Richiesta> results = repositoryRichiesta.findAll(Specification
                .where(RichiestaSpecification.hasId(id))
                .and(RichiestaSpecification.hasNumeroRichiesta(numeroRichiesta))
                .and(RichiestaSpecification.hasIdStatoRichiesta(idStatoRichiesta))
                .and(RichiestaSpecification.hasDescrizioneStatoRichiesta(descrizioneStatoRichiesta))
                .and(RichiestaSpecification.isAttivo(attivo))
                .and(RichiestaSpecification.hasIdSettoreUfficio(idSettoreUfficio))
                .and(RichiestaSpecification.hasIdUfficio(idUfficio))
                .and(RichiestaSpecification.hasIdUtente(idUtente))
                .and(RichiestaSpecification.hasIdUtenteUfficioRuoloStatoCorrente(idUtenteUfficioRuoloStatoCorrente))
                .and(RichiestaSpecification.hasIdUtenteUfficioRuoloStatoIniziale(idUtenteUfficioRuoloStatoIniziale))
                .and(RichiestaSpecification.hasDescrizioneUfficio(descrizioneUfficio))
        );

        return ResponseEntity.ok(results);
	}

	public ResponseEntity<Iterable<Richiesta>> listaCompleta(Authentication authentication) {
		// Controlli autorizzativi
		// RichiestaService.checkAuthorization(authentication);
		
		Iterable<Richiesta> res = repositoryRichiesta.findAll();
		for (Richiesta richiesta : res) {
			System.out.println("richiesta.getIdRichiesta(): " + richiesta.getIdRichiesta());
			System.out.println("richiesta.getTipologiaRichiesta(): " + richiesta.getTipologiaRichiesta());
		}
		return new ResponseEntity<Iterable<Richiesta>>(res, HttpStatus.OK);
	}

	public ResponseEntity<List<Modello>> getModelli() {
		List<Modello> modelli = modelloRepository.findAll();
        return ResponseEntity.ok(modelli);
	}

	public ResponseEntity<List<ModelloCompilato>> getModelliCompilati() {
		List<ModelloCompilato> modelliCompilati = modelloCompilatoRepository.findAll();
        return ResponseEntity.ok(modelliCompilati);
	}

	public ResponseEntity<ModelloCompilato> getModelloCompilatoById(Long id) {
        return modelloCompilatoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
	}

}
