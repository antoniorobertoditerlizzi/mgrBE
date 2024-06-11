package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vigilfuoco.mgr.model.ModelloConJson;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.service.RichiestaService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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
    public RichiestaController(RichiestaService richiestaService) {
        this.richiestaService = richiestaService;
    }
    
	// API Ricerca per id richiesta ------------------------------------ /api/richiesta/cerca?id=2
    @GetMapping("/cerca")
    public ResponseEntity<Richiesta> ricercaPerId(@RequestParam long id) throws IOException {
		logger.debug("Ingresso api /api/richiesta/cerca?id=" + id);
		Richiesta res = repositoryRichiesta.findById(id);
		return new ResponseEntity<Richiesta>(res, HttpStatus.OK);
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

	
	
	// API Ricerca tutte le richieste ------------------------------------ /api/richiesta/all
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<Iterable<Richiesta>> listaCompleta(Authentication authentication)
	{
		// Controlli autorizzativi
		RichiestaService.checkAuthorization(authentication);
		
		Iterable<Richiesta> res = repositoryRichiesta.findAll();
		for (Richiesta richiesta : res) {
			System.out.println("richiesta.getIdRichiesta(): " + richiesta.getIdRichiesta());
			System.out.println("richiesta.getTipologiaRichiesta(): " + richiesta.getTipologiaRichiesta());
		}
		return new ResponseEntity<Iterable<Richiesta>>(res, HttpStatus.OK);
	}



	// API Salva Richiesta a DB ---------------- /api/richiesta/save
    @PostMapping("/save")
    public Richiesta save(@RequestBody Richiesta request) {
		logger.debug("Ingresso api /api/richiesta/save");
        RichiestaService richiestaService = new RichiestaService(repositoryRichiesta, null);
        Utente utente = new Utente();
        utente.setNome("Antonio");
        utente.setCognome("Di Terlizzi");
        request.getUtenteUfficioRuoloStatoIniziale().setUtente(utente);
        request.getUtenteUfficioRuoloStatoCorrente().setUtente(utente);
        Richiesta savedRichiesta = richiestaService.salvaRichiesta(request);
        if (savedRichiesta != null) {
            return savedRichiesta;
        } else {
            throw new RichiestaException("Errore durante il salvataggio della richiesta");
        }
    }
    
    /*JSON DI ESEMPIO
		{
		  "idRichiesta": null,
		  "numeroRichiesta": "RCH-2024-06-11-001",
		  "statoRichiesta": {
		    "idStatoRichiesta": 1,
		    "descrizioneStatoRichiesta": "Inserita"
		  },
		  "tipologiaRichiesta": {
		    "idTipologiaRichiesta": 2,
		    "descrizioneTipologiaRichiesta": "Assistenza Tecnica"
		  },
		  "richiestaPersonale": false,
		  "priorita": {
		    "idPriorita": 3,
		    "descrizionePriorita": "Alta"
		  },
		  "dataInserimentoRichiesta": "2024-06-11T07:38:00.000Z",
		  "dataUltimoStatoRichiesta": "2024-06-11T07:38:00.000Z",
		  "utenteUfficioRuoloStatoCorrente": {
		    "idUtenteUfficioRuolo": 4
		  },
		  "utenteUfficioRuoloStatoIniziale": {
		    "idUtenteUfficioRuolo": 5
		  },
		  "settoreUfficio": {
		    "idSettoreUfficio": 6
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
    

   
	// API Ricerca per descrizione ------------------------------------ /api/richiesta/visualizzaFormRichiesta/2
	@RequestMapping(value = "/visualizzaFormRichiesta/{idModello}", method = RequestMethod.GET, produces = "application/json")
	@PreAuthorize("isAuthenticated()") 
	public ResponseEntity<ModelloConJson> visualizzaFormRichiesta(@PathVariable("idModello") Long idModello, Authentication authentication) throws JsonMappingException, JsonProcessingException{
		logger.debug("Ingresso api /api/richiesta/visualizzaFormRichiesta" + " idModello: " + idModello);
		return richiestaService.formModelloByIDModello(idModello);
	}
	
    
    
}
