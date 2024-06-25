package com.vigilfuoco.mgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vigilfuoco.mgr.model.ModelloConJson;
import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.TipologiaRichiesta;
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
	
	// API Salva Richiesta a DB ---------------------------------------- /api/richiesta/save
    @PostMapping("/save")
    public Richiesta save(@RequestBody Richiesta request, @RequestParam String accountname) {
		logger.debug("Ingresso api /api/richiesta/save");
        Richiesta savedRichiesta = richiestaService.salvaRichiesta(request,accountname);
        if (savedRichiesta != null) {
            return savedRichiesta;
        } else {
            throw new RichiestaException("Errore durante il salvataggio della richiesta");
        }
    }
    
    /*JSON DI ESEMPIO
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
