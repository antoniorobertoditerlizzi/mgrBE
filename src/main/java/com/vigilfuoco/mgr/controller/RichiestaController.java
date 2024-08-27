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
import com.vigilfuoco.mgr.model.Priorita;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.SettoreUfficio;
import com.vigilfuoco.mgr.model.StatoRichiesta;
import com.vigilfuoco.mgr.model.TipologiaRichiesta;
import com.vigilfuoco.mgr.repository.ModelloCompilatoRepository;
import com.vigilfuoco.mgr.repository.ModelloRepository;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.service.RichiestaService;
import com.vigilfuoco.mgr.specification.RichiestaSpecification;
import com.vigilfuoco.mgr.utility.DateUtil;
import com.vigilfuoco.mgr.utility.Utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ModelloRepository modelloRepository;

    @Autowired
    public RichiestaController(RichiestaService richiestaService) {
        this.richiestaService = richiestaService;
    }
    
    @Autowired
    private ModelloCompilatoRepository modelloCompilatoRepository;

    // API Ricerca Richiesta -------------------------------- es. /api/richiesta/cerca?idSettoreUfficio=2&idUfficio=1
    @GetMapping("/cerca")
    public ResponseEntity<List<Richiesta>> ricerca(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String numeroRichiesta,
            @RequestParam(required = false) Long idStatoRichiesta,
            @RequestParam(required = false) String descrizioneStatoRichiesta,
            @RequestParam(required = false) Boolean attivo,
            @RequestParam(required = false) Long idSettoreUfficio,
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
    


    /*TEST
    @GetMapping("/cerca")
    public ResponseEntity<String> ricercaPerId(@RequestParam long id) throws IOException {

    	return new ResponseEntity<String>("CIAO", HttpStatus.OK);
	}*/
	
	
	// API Ricerca per descrizione ------------------------------------ /api/richiesta/cerca/prima richiesta
	/*@RequestMapping(value = "/cerca/{descrizione}", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<List<Richiesta>> ricercaDescrizione(@PathVariable("descrizione") String descrizione, Authentication authentication)
	
	{
		// Controlli autorizzativi
		RichiestaService.checkAuthorization(authentication);
	    
		logger.debug("Ingresso api /api/richiesta/cerca/{descrizione}" + "descrizione:"+ descrizione);
		List<Richiesta> res = repositoryRichiesta.findByDescrizione(descrizione);
		return new ResponseEntity<List<Richiesta>>(res, HttpStatus.OK);
	}*/

	
	
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
	    
	    // Check Stato Richiesta // Controllo su id richiesta se già censita allora modifico altrimenti imposto su inserita
	    if (request.getStatoRichiesta() == null) { // || request.getStatoRichiesta().getIdStatoRichiesta() == 1) {
		    StatoRichiesta statoInserita = new StatoRichiesta();
		    statoInserita.setIdStatoRichiesta(1L); // Es. 1 Inserita **********
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
    
    /*JSON DI ESEMPIO
     * 
     * {
    
		  "tipologiaRichiesta": {
		    "idTipologiaRichiesta": 1,
		    "descrizioneTipologiaRichiesta": "Acquisti"
		  },
		  "richiestaPersonale": false,
		  "priorita": {
		    "idPriorita": 1,
		    "descrizionePriorita": "Alta"
		  },
		  "utenteUfficioRuoloStatoCorrente": {
		    "idUtenteUfficioRuolo": 1
		  },
		  "utenteUfficioRuoloStatoIniziale": {
		    "idUtenteUfficioRuolo": 1
		  },
	      "settoreUfficio": {
		    "idSettoreUfficio": 1,
		    "settore": {
		      "idSettore": 1
		    },
		    "ufficio": {
		      "idUfficio": 1
		    }
		  }
    }
		*/
    
    
    
    /* DEPRECATO JSON DI ESEMPIO
		{
		  "idRichiesta": null,
		  "numeroRichiesta": "123",
		  "statoRichiesta": {
		    "idStatoRichiesta": 1,
		    "descrizioneStatoRichiesta": "Inserita"
		  },
		  "tipologiaRichiesta": {
		    "idTipologiaRichiesta": 1,
		    "descrizioneTipologiaRichiesta": "Acquisti"
		  },
		  "richiestaPersonale": false,
		  "priorita": {
		    "idPriorita": 1,
		    "descrizionePriorita": "Alta"
		  },
		  "dataInserimentoRichiesta": "2024-06-11T07:38:00.000Z",
		  "dataUltimoStatoRichiesta": "2024-06-11T07:38:00.000Z",
		  "utenteUfficioRuoloStatoCorrente": {
		    "idUtenteUfficioRuolo": 1
		  },
		  "utenteUfficioRuoloStatoIniziale": {
		    "idUtenteUfficioRuolo": 1
		  },
		  "settoreUfficio": {
		    "idSettoreUfficio": 1
		  }
		}
    }*/
	
    
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
