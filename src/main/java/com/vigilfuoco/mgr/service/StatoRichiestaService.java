package com.vigilfuoco.mgr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.model.StatoRichiesta;
import com.vigilfuoco.mgr.repository.StatoRichiestaRepository;

@Service
public class StatoRichiestaService {

    @Autowired
    private StatoRichiestaRepository statoRichiestaRepository;

    // Ottieni tutti i dati
    public List<StatoRichiesta> getAll() {
        return statoRichiestaRepository.findAll();
    }

    // Ottieni per ID
    public Optional<StatoRichiesta> getById(Long idStatoRichiesta) {
        return statoRichiestaRepository.findById(idStatoRichiesta);
    }

    // Ottieni per descrizione
    public List<StatoRichiesta> getByDescrizione(String descrizioneStatoRichiesta) {
        return statoRichiestaRepository.findByDescrizioneStatoRichiesta(descrizioneStatoRichiesta);
    }
    
    // Salva un nuovo stato richiesta
    public StatoRichiesta save(StatoRichiesta statoRichiesta) {
        return statoRichiestaRepository.save(statoRichiesta);
    }
}