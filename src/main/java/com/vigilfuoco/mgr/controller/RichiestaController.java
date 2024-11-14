package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import com.vigilfuoco.mgr.model.UfficioRichieste;
import com.vigilfuoco.mgr.model.UtenteUfficioRuolo;
import com.vigilfuoco.mgr.service.RichiestaService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final RichiestaService richiestaService;
    
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
    	return richiestaService.ricerca(id, numeroRichiesta, idStatoRichiesta, descrizioneStatoRichiesta, attivo, idSettoreUfficio, idUfficio, idUtente, idUtenteUfficioRuoloStatoCorrente, idUtenteUfficioRuoloStatoIniziale, descrizioneUfficio);
    }
    
    // API Utente Uffici Richieste  ------------------------------- {{baseUrl}}/api/richiesta/utente/uffici/?idUtente=1
    @GetMapping("/utente/uffici/")
    public ResponseEntity<List<UfficioRichieste>> getUfficiRichieste(@RequestParam Integer idUtente, @RequestParam(required = false) Boolean attivo) {
        logger.debug("/utente/uffici/", idUtente);
        return richiestaService.getUfficiRichiesteWS(idUtente, attivo);
    }

    
    // API Utente Uffici Ruoli ------------------ {{baseUrl}}/api/richiesta/utente/uffici/ruoli/?idUtente=1
    @GetMapping("/utente/uffici/ruoli/")
    public ResponseEntity<List<UtenteUfficioRuolo>> getUtenteUfficiRuoli(
    		@RequestParam(required = false) Integer idUtente,
    		@RequestParam(required = false) Boolean attivo) {
    	return richiestaService.getUtenteUfficiRuoli(idUtente, attivo);
    }
    
	
	// DEPRECATO - API Ricerca tutte le richieste ------------------------------------ /api/richiesta/all
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<Iterable<Richiesta>> listaCompleta(Authentication authentication)
	{
		return richiestaService.listaCompleta(authentication);
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
        return richiestaService.getModelli();
    }
    
	// Salva Form Modello ----------------------------------- http://localhost:8080/api/richiesta/modello/salva
    @PostMapping("/modello/salva")
    public ResponseEntity<Modello> salvaModello(
            @RequestParam("descrizioneModello") String descrizioneModello,
            @RequestParam("transcodificaModello") MultipartFile file,
            @RequestParam("attivo") Boolean attivo) {
    	return richiestaService.salvaModello(descrizioneModello, file, attivo);
    }
    
    //SALVA Modello COMPILATO -------------------------------------- {{baseUrl}}/api/richiesta/modellocompilato/salva
    @PostMapping("/modellocompilato/salva")
    public ResponseEntity<ModelloCompilato> salvaModelloCompilato(
            @RequestParam("fileModello") String fileModello,
            @RequestParam("idModello") Long idModello,
            @RequestParam("idRichiesta") Long idRichiesta,
            @RequestParam("transcodificaModelloCompilato") String transcodificaFile) {
    	return richiestaService.salvaModelloCompilato(fileModello, idModello, idRichiesta, transcodificaFile);
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
        return richiestaService.getModelliCompilati();
    }

    //Leggo il Modello COMPILATO by ID
    @GetMapping("/modellocompilato/{id}")
    public ResponseEntity<ModelloCompilato> getModelloCompilatoById(@PathVariable Long id) {
    	return richiestaService.getModelloCompilatoById(id);
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
	
	
	// API Salva Richiesta a DB ---------------------------- /api/richiesta/save
	@PostMapping("/save")
	public Richiesta save(@RequestBody Richiesta request, @RequestParam String accountname ) {
	    logger.debug("Ingresso api /api/richiesta/save");
	    return richiestaService.saveRequest(request, accountname, richiestaService);
	}
	
	
	// API Modifica Richiesta a DB -------------------------- /api/richiesta/update/1?accountname=antonioroberto.diterlizzi
    @PutMapping("/update/{id}")
    public Richiesta update(@PathVariable Long id, @RequestBody Richiesta updatedRequest, @RequestParam String accountname ) {
        logger.debug("Ingresso api /api/richiesta/update");
        return richiestaService.update(id, updatedRequest, accountname, richiestaService);
    }
    
    
    // API Cancellazione Fisica Richiesta solo se id_stato_richiesta = 1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRichiesta(@PathVariable Long id) {
        logger.debug("Ingresso api /api/richiesta/delete");
        return richiestaService.deleteRichiesta(id);
    }
    
    
    
    // API Cancella tipologia richiesta: valido solo se la tipogia è priva di modello altrimenti cancellare prima il modello e le sue relazioni ----- {{baseUrl}}/api/richiesta/deleteTipologiaRichiesta/22
    @DeleteMapping("/deleteTipologiaRichiesta/{idTipologiaRichiesta}")
    public ResponseEntity<String> deleteTipologiaRichiesta(@PathVariable("idTipologiaRichiesta") Short idTipologiaRichiesta) {
        return richiestaService.deleteTipologiaRichiesta(idTipologiaRichiesta);
    }
    
    
    // API Cancella SettoreUfficio
    @DeleteMapping("/deleteSettoreUfficio/{idSettoreUfficio}")
    public ResponseEntity<String> deleteSettoreUfficio(@PathVariable("idSettoreUfficio") Short idSettoreUfficio) {
        return richiestaService.deleteSettoreUfficio(idSettoreUfficio);
    }
    
    
    // API Cancella Settore
    @DeleteMapping("/deleteSettore/{idSettore}")
    public ResponseEntity<String> deleteSettore(@PathVariable("idSettore") Long idSettore) {
       return richiestaService.deleteSettoreUfficioWs(idSettore);
    }
    
    //API SALVA SETTORE RICHIESTA  -------------- /api/richiesta/saveSettoreRichiesta?idSettore=1&idTipologiaRichiesta=2&attivo=0
    @PostMapping("/saveSettoreRichiesta") 
    public ResponseEntity<?> saveSettoreRichiesta(
            @RequestParam(required = false) Long idSettore,
            @RequestParam(required = false) Short idTipologiaRichiesta,
            @RequestParam(required = false) Boolean attivo) {
    	return richiestaService.saveSettoreRichiestaWs(idSettore, idTipologiaRichiesta, attivo);
    }

    //API LISTA SETTORI RICHIESTA  -------------- /api/richiesta/getSettoriRichiesta
    @GetMapping("/getSettoriRichiesta")
    public ResponseEntity<List<SettoreRichiesta>> getSettoriRichiesta(
            @RequestParam(required = false) Long idSettore,
            @RequestParam(required = false) Short idTipologiaRichiesta) {

    	return richiestaService.getSettoriRichiestaWs(idSettore, idTipologiaRichiesta);
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
    	return richiestaService.getAllStatiRichieste(idStatoRichiesta, descrizioneStatoRichiesta);
    }
    
    //API INSERISCI NUOVO STATO RICHIESTA  ------------- {{baseUrl}}/api/richiesta/saveStatiRichiesta/?descrizioneStatoRichiesta=asd&attivo=1&descrizioneStato=asdasd&percentuale=3&colore=234234
    @PostMapping("/saveStatiRichiesta")
    public ResponseEntity<StatoRichiesta> createStatoRichiesta(
            @RequestParam String descrizioneStatoRichiesta,
            @RequestParam boolean attivo,
            @RequestParam String descrizioneStato,
            @RequestParam int percentuale,
            @RequestParam String colore) {

    	return richiestaService.createStatoRichiesta(descrizioneStatoRichiesta, attivo, descrizioneStato, percentuale, colore);
    }
    
    
    //API Modifica flag in tbl stato richiesta ------------ {{baseUrl}}/api/richiesta/updateStatoRichiesta/?idStatoRichiesta=1&attivo=1
    @PutMapping("/updateTblStatoRichiesta")
    public ResponseEntity<?> updateTblStatoRichiesta(
            @RequestParam Long idStatoRichiesta,
            @RequestParam boolean attivo) {
        
    	return richiestaService.updateTblStatoRichiesta(idStatoRichiesta, attivo);
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
