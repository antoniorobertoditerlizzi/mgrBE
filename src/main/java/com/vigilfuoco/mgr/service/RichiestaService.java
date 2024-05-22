package com.vigilfuoco.mgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.repository.RichiestaRepository;

//Logica di Business

	@Service
	public class RichiestaService {

	    @Autowired
	    private final RichiestaRepository repository;

	    
	    //Costruttore
	    public RichiestaService(RichiestaRepository repository) {
	        this.repository = repository;
	    }

	    //Salva Richiesta
		public Richiesta salvaRichiesta(Richiesta request) {
	        return repository.save(request);
	    }
	}
