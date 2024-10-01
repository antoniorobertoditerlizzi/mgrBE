package com.vigilfuoco.mgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.model.Funzionalita;
import com.vigilfuoco.mgr.model.Ruolo;
import com.vigilfuoco.mgr.model.RuoloFunzionalita;
import com.vigilfuoco.mgr.repository.FunzionalitaRepository;
import com.vigilfuoco.mgr.repository.RuoloFunzionalitaRepository;

@Service
public class FunzionalitaService {

    @Autowired
    private FunzionalitaRepository funzionalitaRepository;

    @Autowired
    private RuoloFunzionalitaRepository ruoloFunzionalitaRepository;
    
    public Funzionalita saveFunzionalita(Funzionalita funzionalita) {
        if (funzionalitaRepository.existsByCodiceFunzionalita(funzionalita.getCodiceFunzionalita())) {
            throw new RuntimeException("Una funzionalità con questo codice esiste già!");
        }

        if (funzionalitaRepository.existsByUrl(funzionalita.getUrl())) {
            throw new RuntimeException("Una funzionalità con questo URL esiste già!");
        }

        return funzionalitaRepository.save(funzionalita);
    }
    
    
    public RuoloFunzionalita saveRuoloFunzionalita(Ruolo ruolo, Funzionalita funzionalita, boolean attivo) {
        if (ruoloFunzionalitaRepository.existsByRuoloAndFunzionalita(ruolo, funzionalita)) {
            throw new RuntimeException("Questa combinazione di ruolo e funzionalità esiste già!");
        }

        RuoloFunzionalita ruoloFunzionalita = new RuoloFunzionalita();
        ruoloFunzionalita.setRuolo(ruolo);
        ruoloFunzionalita.setFunzionalita(funzionalita);
        ruoloFunzionalita.setAttivo(attivo);

        return ruoloFunzionalitaRepository.save(ruoloFunzionalita);
    }
    
    
    public RuoloFunzionalita updateAttivo(Long idRuoloFunzionalita, boolean attivo) {
        RuoloFunzionalita ruoloFunzionalita = ruoloFunzionalitaRepository.findById(idRuoloFunzionalita)
                .orElseThrow(() -> new RuntimeException("Associazione Ruolo-Funzionalità non trovata!"));

        ruoloFunzionalita.setAttivo(attivo);
        return ruoloFunzionalitaRepository.save(ruoloFunzionalita);
    }
    

}