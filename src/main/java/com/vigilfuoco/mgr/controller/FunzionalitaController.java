package com.vigilfuoco.mgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vigilfuoco.mgr.model.Funzionalita;
import com.vigilfuoco.mgr.model.Ruolo;
import com.vigilfuoco.mgr.model.RuoloFunzionalita;
import com.vigilfuoco.mgr.repository.FunzionalitaRepository;
import com.vigilfuoco.mgr.repository.RuoloFunzionalitaRepository;
import com.vigilfuoco.mgr.repository.RuoloRepository;
import com.vigilfuoco.mgr.service.FunzionalitaService;

@RestController
@RequestMapping("/api/funzionalita")
public class FunzionalitaController {

    @Autowired
    private FunzionalitaService funzionalitaService;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private FunzionalitaRepository funzionalitaRepository;
    
    @Autowired
    private RuoloFunzionalitaRepository ruoloFunzionalitaRepository;
    

    @PostMapping("/saveFunzionalita")
    public ResponseEntity<?> createFunzionalita(
            @RequestParam String codiceFunzionalita,
            @RequestParam String descrizioneFunzionalita,
            @RequestParam String titolo,
            @RequestParam String url) {

        try {
            Funzionalita funzionalita = new Funzionalita();
            funzionalita.setCodiceFunzionalita(codiceFunzionalita);
            funzionalita.setDescrizioneFunzionalita(descrizioneFunzionalita);
            funzionalita.setTitolo(titolo);
            funzionalita.setUrl(url);

            Funzionalita savedFunzionalita = funzionalitaService.saveFunzionalita(funzionalita);

            return ResponseEntity.ok(savedFunzionalita);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    
    @PostMapping("/saveRuoloFunzionalita")
    public ResponseEntity<?> createRuoloFunzionalita(
            @RequestParam Long idRuolo,
            @RequestParam Long idFunzionalita,
            @RequestParam boolean attivo) {

        try {
            Ruolo ruolo = ruoloRepository.findById(idRuolo)
                    .orElseThrow(() -> new RuntimeException("Ruolo non trovato!"));

            Funzionalita funzionalita = funzionalitaRepository.findById(idFunzionalita)
                    .orElseThrow(() -> new RuntimeException("Funzionalità non trovata!"));

            RuoloFunzionalita savedRuoloFunzionalita = funzionalitaService.saveRuoloFunzionalita(ruolo, funzionalita, attivo);

            return ResponseEntity.ok(savedRuoloFunzionalita);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    
    @PostMapping("/updateRuoloFunzionalita")
    public ResponseEntity<?> updateAttivo(
            @RequestParam Long idRuoloFunzionalita,
            @RequestParam boolean attivo) {

        try {
            RuoloFunzionalita updatedRuoloFunzionalita = funzionalitaService.updateAttivo(idRuoloFunzionalita, attivo);
            return ResponseEntity.ok(updatedRuoloFunzionalita);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    

    
}