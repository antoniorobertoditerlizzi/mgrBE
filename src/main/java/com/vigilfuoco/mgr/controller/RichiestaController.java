package com.vigilfuoco.mgr.controller;

import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.repository.RichiestaRepository;
import com.vigilfuoco.mgr.service.RichiestaService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private RichiestaRepository repository;
	
	// API Ricerca per id richiesta ------------------------------------ /api/richiesta/id/2
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Optional<Richiesta>> ricercaPerId(@PathVariable("id") Long id)
	
	{
		logger.debug("Ingresso api /api/richiesta/id");
		Optional<Richiesta> res = repository.findById(id);
		return new ResponseEntity<Optional<Richiesta>>(res, HttpStatus.OK);
	}
	
	
	// API Ricerca per descrizione ------------------------------------ /api/richiesta/cerca/prima richiesta
	@RequestMapping(value = "/cerca/{descrizione}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Richiesta>> ricercaDescrizione(@PathVariable("descrizione") String descrizione)
	
	{
		logger.debug("Ingresso api /api/richiesta/cerca/{descrizione}" + "descrizione:"+ descrizione);
		List<Richiesta> res = repository.findByDescrizione(descrizione);
		return new ResponseEntity<List<Richiesta>>(res, HttpStatus.OK);
	}

	
	
	// API Ricerca tutte le richieste ------------------------------------ /api/richiesta/all
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Iterable<Richiesta>> listaCompleta()
	{
		logger.debug("Ingresso api /api/richiesta/ALL");
		Iterable<Richiesta> res = repository.findAll();
		for (Richiesta richiesta : res) {
			System.out.println("richiesta.getId():"+richiesta.getId());
			System.out.println("richiesta.getDescrizione():"+richiesta.getDescrizione());
		}
		return new ResponseEntity<Iterable<Richiesta>>(res, HttpStatus.OK);
	}
	 
	// API Salva Richiesta a DB ---------------- /api/richiesta/save
    @PostMapping("/save")
    public Richiesta createRequest(@RequestBody Richiesta request) {
		logger.debug("Ingresso api /api/richiesta/save");
        RichiestaService richiestaService = new RichiestaService(repository);
        Richiesta savedRichiesta = richiestaService.salvaRichiesta(request);
        if (savedRichiesta != null) {
            return savedRichiesta;
        } else {
            throw new RichiestaException("Errore durante il salvataggio della richiesta");
        }
    }
    
    /*JSON DI ESEMPIO
     {
        "nome": "Richiesta di esempio",
        "descrizione": "Questa è una richiesta di esempio per il testing",
        "tipologia": "MANUTENZIONE",
        "priorità": "ALTA",
        "dataInizio": "2024-05-10",
        "dataFine": "2024-05-15",
        "stato": "APERTA",
        "utente": {
            "id": 1,
            "nome": "Mario Rossi",
            "email": "mariorossi@vigilfuoco.com"
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
