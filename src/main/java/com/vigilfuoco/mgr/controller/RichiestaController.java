package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import com.vigilfuoco.mgr.model.UtenteUfficioRuolo;
import com.vigilfuoco.mgr.repository.ModelliTipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.ModelloCompilatoRepository;
import com.vigilfuoco.mgr.repository.ModelloRepository;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.repository.SettoreRepository;
import com.vigilfuoco.mgr.repository.SettoreUfficioRepository;
import com.vigilfuoco.mgr.repository.TipologiaRichiestaRepository;
import com.vigilfuoco.mgr.repository.UtenteUfficioRuoloRepository;
import com.vigilfuoco.mgr.service.RichiestaService;
import com.vigilfuoco.mgr.service.StatoRichiestaService;
import com.vigilfuoco.mgr.specification.RichiestaSpecification;
import com.vigilfuoco.mgr.utility.DateUtil;
import com.vigilfuoco.mgr.utility.Utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* 
 * Definizione di tutte le API che contraddistinguono la Richiesta
 * 
 */

@RestController
@RequestMapping("/api/richiesta/")
public class RichiestaController {
	
    private static final Logger logger = LogManager.getLogger(RichiestaController.class);

    @Autowired
    private RichiestaRepository repositoryRichiesta;
    
    private final RichiestaService richiestaService;
    
    @Autowired
    private SettoreUfficioRepository repositorySettoreUfficio;
    
    @Autowired
    private SettoreRepository repositorySettore;
    
    @Autowired
    private ModelloRepository modelloRepository;
    
    @Autowired
    private ModelliTipologiaRichiestaRepository repositoryModelliTipologiaRichiesta;

    @Autowired
    private UtenteUfficioRuoloRepository utenteUfficioRuoloRepository;
    
    @Autowired
    private ModelloCompilatoRepository modelloCompilatoRepository;
    
    @Autowired
    private TipologiaRichiestaRepository tipologiaRichiestaRepository;
    
    @Autowired
    private StatoRichiestaService statoRichiestaService;
    
    @Autowired
    public RichiestaController(RichiestaService richiestaService) {
        this.richiestaService = richiestaService;
    }

    // API Ricerca Richiesta -------------------------------- es. /api/richiesta/cerca?idSettoreUfficio=2&idUfficio=1
    @GetMapping("/cerca")
    public ResponseEntity<List<Richiesta>> ricerca(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String numeroRichiesta,
            @RequestParam(required = false) Long idStatoRichiesta,
            @RequestParam(required = false) String descrizioneStatoRichiesta,
            @RequestParam(required = false) Boolean attivo,
            @RequestParam(required = false) Short idSettoreUfficio,
            @RequestParam(required = false) Long idUfficio,
            @RequestParam(required = false) Long idUtente,
            @RequestParam(required = false) Long idUtenteUfficioRuoloStatoCorrente,
            @RequestParam(required = false) Long idUtenteUfficioRuoloStatoIniziale,
            @RequestParam(required = false) String descrizioneUfficio) throws IOException {
    	
    	logger.debug("Ingresso api /api/richiesta/cerca");
    	
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
    
    // API Utente Uffici Richieste  ------------------------------- {{baseUrl}}/api/richiesta/utente/uffici/?idUtente=1
    @GetMapping("/utente/uffici/")
    public ResponseEntity<List<UfficioRichieste>> getUfficiRichieste(@RequestParam Integer idUtente) {
        logger.debug("/utente/uffici/", idUtente);
        
        List<UtenteUfficioRuolo> utentiUfficiRuoli = utenteUfficioRuoloRepository.findByUtenteIdUtente(idUtente);
        
        if (utentiUfficiRuoli.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<UfficioRichieste> result;
        try {
            result = richiestaService.getUfficiRichieste(utentiUfficiRuoli);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(result);
    }

    
    // API Utente Uffici Ruoli ------------------ {{baseUrl}}/api/richiesta/utente/uffici/ruoli/?idUtente=1
    @GetMapping("/utente/uffici/ruoli/")
    public ResponseEntity<List<UtenteUfficioRuolo>> getUtenteUfficiRuoli(@RequestParam(required = false) Integer idUtente) {
        logger.debug("Chiamata a /utente/uffici/ruoli con idUtente: {}", idUtente);
        List<UtenteUfficioRuolo> utentiUfficiRuoli;
        if (idUtente != null) {
            utentiUfficiRuoli = utenteUfficioRuoloRepository.findByUtenteIdUtente(idUtente);
        } else {
            utentiUfficiRuoli = utenteUfficioRuoloRepository.findAll();
        }
        if (utentiUfficiRuoli.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utentiUfficiRuoli);
    }
    
	
	// DEPRECATO - API Ricerca tutte le richieste ------------------------------------ /api/richiesta/all
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<Iterable<Richiesta>> listaCompleta(Authentication authentication)
	{
		// Controlli autorizzativi
		// RichiestaService.checkAuthorization(authentication);
		
		Iterable<Richiesta> res = repositoryRichiesta.findAll();
		for (Richiesta richiesta : res) {
			System.out.println("richiesta.getIdRichiesta(): " + richiesta.getIdRichiesta());
			System.out.println("richiesta.getTipologiaRichiesta(): " + richiesta.getTipologiaRichiesta());
		}
		return new ResponseEntity<Iterable<Richiesta>>(res, HttpStatus.OK);
	}

	
	// API Visualizza Form Modello ------------------------------------ /api/richiesta/visualizzaFormModello/2
	@RequestMapping(value = "/visualizzaFormModello/{idModello}", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<ModelloConJson> visualizzaFormRichiesta(@PathVariable("idModello") Long idModello, Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api /api/richiesta/visualizzaFormModello/" + " idModello: " + idModello);
		return richiestaService.formModelloByIDModello(idModello);
	}
	
    //Leggo tutti i Modelli ------------------------------------ {{baseUrl}}/api/richiesta/modello/list
    @GetMapping("/modello/list")
    public ResponseEntity<List<Modello>> getModelli() {
        List<Modello> modelli = modelloRepository.findAll();
        return ResponseEntity.ok(modelli);
    }
    
	// Salva Form Modello ----------------------------------- http://localhost:8080/api/richiesta/modello/salva
    @PostMapping("/modello/salva")
    public ResponseEntity<Modello> salvaModello(
            @RequestParam("descrizioneModello") String descrizioneModello,
            @RequestParam("transcodificaModello") MultipartFile file,
            @RequestParam("attivo") Boolean attivo) {
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
    
    //SALVA Modello COMPILATO -------------------------------------- {{baseUrl}}/api/richiesta/modellocompilato/salva
    @PostMapping("/modellocompilato/salva")
    public ResponseEntity<ModelloCompilato> salvaModelloCompilato(
            @RequestParam("fileModello") MultipartFile fileModello,
            @RequestParam("idModello") Long idModello,
            @RequestParam("idRichiesta") Long idRichiesta,
            @RequestParam("transcodificaModelloCompilato") MultipartFile transcodificaFile) {
        try {
            // Carico l'entità Modello dal DB
            Modello modello = modelloRepository.findById(idModello)
                    .orElseThrow(() -> new RuntimeException("Modello non trovato"));

            // Carico l'entità Richiesta dal DB
            Richiesta richiesta = repositoryRichiesta.findById(idRichiesta)
                    .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));

            // Creo e popolo l'entità ModelloCompilato
            ModelloCompilato modelloCompilato = new ModelloCompilato();
            modelloCompilato.setFileModello(fileModello.getBytes());
            modelloCompilato.setModello(modello);
            modelloCompilato.setRichiesta(richiesta);
            modelloCompilato.setTranscodificaModelloCompilato(transcodificaFile.getBytes());

            // Salvo l'entità ModelloCompilato nel DB
            ModelloCompilato nuovoModelloCompilato = modelloCompilatoRepository.save(modelloCompilato);
            return ResponseEntity.ok(nuovoModelloCompilato);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    /*	Tipo Body: form-data
		Parametri Body:
		fileModello (tipo: file)
		idModello (tipo: text)
		idRichiesta (tipo: text)
		transcodificaModelloCompilato (tipo: file)*/
    
    //Leggo tutti i Modelli COMPILATI
    @GetMapping("/modellocompilato/list")
    public ResponseEntity<List<ModelloCompilato>> getModelliCompilati() {
        List<ModelloCompilato> modelliCompilati = modelloCompilatoRepository.findAll();
        return ResponseEntity.ok(modelliCompilati);
    }

    //Leggo il Modello COMPILATO by ID
    @GetMapping("/modellocompilato/{id}")
    public ResponseEntity<ModelloCompilato> getModelloCompilatoById(@PathVariable Long id) {
        return modelloCompilatoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
	// API Tipologie Richieste ---------------------------------------- /api/richiesta/tipologieRichieste/
	@RequestMapping(value = "/tipologieRichieste/", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<List<TipologiaRichiesta>> tiploigieRichieste(Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api /api/richiesta/tipologieRichieste/");
		return richiestaService.tipologieRichieste();
	}
	
	// API Tipologia Richiesta ---------------------------------------- /api/richiesta/tipologiaRichiesta/?idTipologiaRichiesta=1
	@RequestMapping(value = "/tipologiaRichiesta/", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<TipologiaRichiesta> tiploigiaRichiesta(@RequestParam("idTipologiaRichiesta") Short idTipologiaRichiesta, Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api /api/richiesta/tipologieRichieste/");
		return richiestaService.tipologiaRichiesta(idTipologiaRichiesta);
	}
	
	// API SALVA Tipologia Richiesta ---------------------------------- /api/richiesta/tipologiaRichiesta/save
    @PostMapping(value = "/tipologiaRichiesta/save", consumes = "application/json", produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<TipologiaRichiesta> saveTiploigiaRichiesta(
		@RequestBody TipologiaRichiesta tipologiaRichiesta,
		Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api SAVE /api/richiesta/tipologieRichieste/save");
		return richiestaService.saveTipologiaRichiesta(tipologiaRichiesta);
	}

	// API SALVA Settori Competenza ---------------------------------- /api/richiesta/settoriCompetenza/save
    @PostMapping(value = "/settoriCompetenza/save", consumes = "application/json", produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<SettoreUfficio> saveSettoriCompetenza(
		@RequestBody SettoreUfficio settoreUfficio,
		Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api SAVE /api/richiesta/settoriCompetenza/save");
		return richiestaService.saveSettoriCompetenza(settoreUfficio);
	}
    /*  
    {
	    "settore": {
	        "idSettore": 1 
	    },
	    "ufficio": {
	        "idUfficio": 1  
	    },
	    "attivo": true 
	}
    */
    
	// API List getListSettoriCompetenza tbl_settori_uffici --------------------------- /api/richiesta/getListSettoriCompetenza
    @GetMapping("/getListSettoriCompetenza")
    public ResponseEntity<ResponseEntity<List<SettoreUfficio>>> getListSettoriCompetenza() {
        return ResponseEntity.ok(richiestaService.getListSettoriCompetenza());
    }
    
	// API SALVA Modelli Tipologia Richiesta --------------------------- /api/richiesta/modelliTipologiaRichiesta/save
    @PostMapping(value = "/modelliTipologiaRichiesta/save", consumes = "application/json", produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<ModelloTipologiaRichiesta> saveModelliTipologieRichieste(
		@RequestBody ModelloTipologiaRichiesta modelloTipologiaRichiesta,
		Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api SAVE /api/richiesta/modelliTipologiaRichiesta/save");
		return richiestaService.saveModelliTipologiaRichiesta(modelloTipologiaRichiesta);
	}
	
    /*  RAW  * {
	   "descrizioneTipologiaRichiesta": "Automezzo",
	   "attivo": true,
	   "statoRichiestaPartenza": {
	       "idStatoRichiesta": 999
	   }
	}*/
    
	// API List getListSettori ---------------------------------- /api/richiesta/getListSettori
    @GetMapping("/getListSettori")
    public ResponseEntity<ResponseEntity<List<Settore>>> getListSettori(@RequestParam(required = false) Long idSettore) {
        return ResponseEntity.ok(richiestaService.getListSettori(idSettore));
    }
    
	// API SALVA Settori  ---------------------------------- /api/richiesta/settori/save
    @PostMapping(value = "/settori/save", consumes = "application/json", produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<Settore> saveSettori(
		@RequestBody Settore settore,
		Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api SAVE /api/richiesta/settori/save");
		return richiestaService.saveSettori(settore);
	}
    /*
     {
	    "descrizioneSettore": "Settore Esempio",
	    "emailSettore": "esempio@aaaa.com",
	    "attivo": true
	 }
     */
    
    // DATO ID TIPOLOGIA RESTITUISCE LA LISTA DEI MODELLI ASSOCIATI ---------------------------- /api/richiesta/modelli?idTipologiaRichiesta=20
    @GetMapping("/modelli")
    public ResponseEntity<List<Modello>> getModelliByTipologia(
            @RequestParam Short idTipologiaRichiesta) {
        List<Modello> modelli = richiestaService.getModelliByTipologia(idTipologiaRichiesta);
        return ResponseEntity.ok(modelli);
    }
    
    // DATO ID MODELLO RESTITUISCE LA LISTA TIPOLOGIE ASSOCIATE -------------------------------------- /api/richiesta/tipologia?idModello=0
    @GetMapping("/tipologia")
    public ResponseEntity<List<TipologiaRichiesta>> getTipologieByModello(
            @RequestParam Long idModello) {
        List<TipologiaRichiesta> tipologie = richiestaService.getTipologieByModello(idModello);
        return ResponseEntity.ok(tipologie);
    }
    

	// API Lista Priorita ---------------------------------------- /api/richiesta/getPriorityList/
	@RequestMapping(value = "/getPriorityList/", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<List<Priorita>> getPriorityList(Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api /api/richiesta/getPriorityList/");
		return richiestaService.getPriorityList();
	}
	
	
	// API Salva Richiesta a DB ---------------------------------------- /api/richiesta/save
	@PostMapping("/save")
	public Richiesta save(@RequestBody Richiesta request, @RequestParam String accountname ) {
	    logger.debug("Ingresso api /api/richiesta/save");
	    return richiestaService.saveRequest(request, accountname, richiestaService);
	}
	
	
	// API Modifica Richiesta a DB ---------------------------------------- /api/richiesta/update/1?accountname=antonioroberto.diterlizzi
    @PutMapping("/update/{id}")
    public Richiesta update(@PathVariable Long id, @RequestBody Richiesta updatedRequest, @RequestParam String accountname ) {
        logger.debug("Ingresso api /api/richiesta/update");

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
    
    // API Cancellazione Fisica Richiesta solo se id_stato_richiesta = 1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRichiesta(@PathVariable Long id) {
        logger.debug("Ingresso api /api/richiesta/delete");

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
    
    
    
    // API Cancella tipologia richiesta: valido solo se la tipogia è priva di modello altrimenti cancellare prima il modello e le sue relazioni ----- {{baseUrl}}/api/richiesta/deleteTipologiaRichiesta/22
    @DeleteMapping("/deleteTipologiaRichiesta/{idTipologiaRichiesta}")
    public ResponseEntity<String> deleteTipologiaRichiesta(@PathVariable("idTipologiaRichiesta") Short idTipologiaRichiesta) {
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
    
    
    // API Cancella SettoreUfficio
    @DeleteMapping("/deleteSettoreUfficio/{idSettoreUfficio}")
    public ResponseEntity<String> deleteSettoreUfficio(@PathVariable("idSettoreUfficio") Short idSettoreUfficio) {
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
    
    
    // API Cancella Settore
    @DeleteMapping("/deleteSettore/{idSettore}")
    public ResponseEntity<String> deleteSettore(@PathVariable("idSettore") Long idSettore) {
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
    
    //API SALVA SETTORE RICHIESTA  -------------- /api/richiesta/saveSettoreRichiesta?idSettore=1&idTipologiaRichiesta=2&attivo=0
    @PostMapping("/saveSettoreRichiesta") 
    public ResponseEntity<?> saveSettoreRichiesta(
            @RequestParam(required = false) Long idSettore,
            @RequestParam(required = false) Short idTipologiaRichiesta,
            @RequestParam(required = false) Boolean attivo) {

        if (idSettore == null || idTipologiaRichiesta == null || attivo == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Parametri obbligatori mancanti: idSettore, idTipologiaRichiesta, attivo");
        }

        try {
            // Controllo se la tupla esiste già
            boolean exists = richiestaService.existsBySettoreAndTipologiaRichiesta(idSettore, idTipologiaRichiesta);

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

            SettoreRichiesta savedSettoreRichiesta = richiestaService.saveSettoreRichiesta(settoreRichiesta);

            return ResponseEntity.ok(savedSettoreRichiesta);
        } catch (Exception e) {
            logger.error("Errore durante il salvataggio di SettoreRichiesta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server");
        }
    }

    //API LISTA SETTORI RICHIESTA  -------------- /api/richiesta/getSettoriRichiesta
    @GetMapping("/getSettoriRichiesta")
    public ResponseEntity<List<SettoreRichiesta>> getSettoriRichiesta(
            @RequestParam(required = false) Long idSettore,
            @RequestParam(required = false) Short idTipologiaRichiesta) {

        List<SettoreRichiesta> result;

        // Filtra in base ai parametri ricevuti
        if (idSettore != null && idTipologiaRichiesta != null) {
            result = richiestaService.getByIdSettoreAndIdTipologiaRichiesta(idSettore, idTipologiaRichiesta);
        } else if (idSettore != null) {
            result = richiestaService.getByIdSettore(idSettore);
        } else if (idTipologiaRichiesta != null) {
            result = richiestaService.getByIdTipologiaRichiesta(idTipologiaRichiesta);
        } else {
            result = richiestaService.getAllSettoreRichiesta();
        }

        return ResponseEntity.ok(result);
    }


    
    // API per aggiornare il campo attivo di SettoreRichiesta ------------------------------------ /api/richieste/updateSettoreRichiesta
    @PutMapping("/updateSettoreRichiesta")
    public ResponseEntity<String> updateSettoreRichiesta(
            @RequestParam Long idSettoreRichiesta,
            @RequestParam boolean attivo) {

        boolean success = richiestaService.updateSettoreRichiestaAttivo(idSettoreRichiesta, attivo);
        
        if (success) {
            return ResponseEntity.ok("Campo attivo aggiornato correttamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun record trovato con l'id specificato.");
        }
    }
    
    // API per aggiornare il campo attivo di Settore ------------------------------------ /api/richieste/updateSettore
    @PutMapping("/updateSettore")
    public ResponseEntity<String> updateSettore(
            @RequestParam Long idSettore,
            @RequestParam boolean attivo) {

        boolean success = richiestaService.updateSettoreAttivo(idSettore, attivo);
        
        if (success) {
            return ResponseEntity.ok("Campo attivo aggiornato correttamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun record trovato con l'id specificato.");
        }
    }
    
    // API per aggiornare il campo attivo di TipplogiaRichiesta ------------------------------------ /api/richieste/updateTipologiaRichiesta
    @PutMapping("/updateTipologiaRichiesta")
    public ResponseEntity<String> updateTipologiaRichiesta(
            @RequestParam Short idTipologiaRichiesta,
            @RequestParam boolean attivo) {

        boolean success = richiestaService.updateTipologiaRichiestaAttivo(idTipologiaRichiesta, attivo);
        
        if (success) {
            return ResponseEntity.ok("Campo attivo aggiornato correttamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun record trovato con l'id specificato.");
        }
    }
    
    
    // API per ottenere tutti gli STATI RICHIESTA o filtrare per ID o descrizione  --- {{baseUrl}}/api/richiesta/getStatiRichiesta/
    @GetMapping("/getStatiRichiesta")
    public ResponseEntity<List<StatoRichiesta>> getAllStatiRichieste(
            @RequestParam(required = false) Long idStatoRichiesta,
            @RequestParam(required = false) String descrizioneStatoRichiesta) {

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
    
    //API INSERISCI NUOVO STATO RICHIESTA  ------------- {{baseUrl}}/api/richiesta/saveStatiRichiesta/?descrizioneStatoRichiesta=asd&attivo=1&descrizioneStato=asdasd&percentuale=3&colore=234234
    @PostMapping("/saveStatiRichiesta")
    public ResponseEntity<StatoRichiesta> createStatoRichiesta(
            @RequestParam String descrizioneStatoRichiesta,
            @RequestParam boolean attivo,
            @RequestParam String descrizioneStato,
            @RequestParam int percentuale,
            @RequestParam String colore) {

        StatoRichiesta statoRichiesta = new StatoRichiesta();
        statoRichiesta.setDescrizioneStatoRichiesta(descrizioneStatoRichiesta);
        statoRichiesta.setAttivo(attivo);
        statoRichiesta.setDescrizioneStato(descrizioneStato);
        statoRichiesta.setPercentuale(percentuale);
        statoRichiesta.setColore(colore);

        StatoRichiesta savedStatoRichiesta = statoRichiestaService.save(statoRichiesta);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStatoRichiesta);
    }
    
    
    //API Modifica flag in tbl stato richiesta ------------ {{baseUrl}}/api/richiesta/updateStatoRichiesta/?idStatoRichiesta=1&attivo=1
    @PutMapping("/updateTblStatoRichiesta")
    public ResponseEntity<?> updateTblStatoRichiesta(
            @RequestParam Long idStatoRichiesta,
            @RequestParam boolean attivo) {
        
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
    
    
    //API Modifica PARZIALE dei campi richiesta senza passare l'intero oggetto  ------------ {{baseUrl}}/api/richiesta/updateRichiesta/58/
    @PatchMapping("/updateRichiesta/{idRichiesta}")
    public ResponseEntity<Richiesta> aggiornaRichiesta(@PathVariable Long idRichiesta,
                                                       @RequestBody RichiestaUpdateDTO richiestaUpdateDTO) {
        Richiesta richiestaAggiornata = richiestaService.aggiornaRichiesta(idRichiesta, richiestaUpdateDTO);
        return ResponseEntity.ok(richiestaAggiornata);
    }
	/*
	 {
	  "numeroRichiesta": "RICH-001",
	  "idStatoRichiesta": 2,
	  "idTipologiaRichiesta": 3,
	  "richiestaPersonale": true,
	  "idPriorita": 1,
	  "idUtenteUfficioRuoloStatoCorrente": 5,
	  "idUtenteUfficioRuoloStatoIniziale": 4,
	  "idSettoreUfficio": 7
	}
	*/
    
    
	// API Test 1------------------- /api/richiesta/ciao
	@RequestMapping(value = "/api/richiesta/ciao", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> helloWorld()  
	{
		logger.debug("Ingresso api /api/richiesta/ciao");
		return new ResponseEntity<String>("\"ciao\"", HttpStatus.OK);
	}
	
	// API Test 2------------------- /api/richiesta/test
    @GetMapping("/test")
    public String testRequest() {
		logger.debug("Ingresso api /api/richiesta/test");
        return "TEST";
    }
    

    
}
