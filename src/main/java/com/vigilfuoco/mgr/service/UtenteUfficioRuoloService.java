package com.vigilfuoco.mgr.service;


import java.util.Calendar;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.model.Ruolo;
import com.vigilfuoco.mgr.model.SettoreUfficio;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.model.UtenteUfficioRuolo;
import com.vigilfuoco.mgr.repository.UtenteUfficioRuoloRepository;

@Service
public class UtenteUfficioRuoloService {

    @Autowired
    private UtenteUfficioRuoloRepository utenteUfficioRuoloRepository;

	//private static final Logger logger = LogManager.getLogger(UtenteController.class);

    public UtenteUfficioRuolo createUtenteUfficioRuolo(Utente utente, Ruolo ruolo, SettoreUfficio settoreUfficio, boolean attivo) {
        
        // Cerca se esiste già un'associazione
        /*List<UtenteUfficioRuolo> associazioniEsistenti = utenteUfficioRuoloRepository.findByUtenteRuoloSettore(
            utente.getIdUtente(), ruolo.getIdRuolo(), settoreUfficio.getIdSettoreUfficio());

        if (!associazioniEsistenti.isEmpty()) {
        	logger.warn("Associazione utente-ruolo-settore ufficio già presente");
            return null;
        }*/
        
    	UtenteUfficioRuolo uuf = new UtenteUfficioRuolo();
        uuf.setUtente(utente);
        uuf.setRuolo(ruolo);
        uuf.setSettoreUfficio(settoreUfficio);
        uuf.setAttivo(attivo);
        uuf.setDataCreazione(new java.util.Date(System.currentTimeMillis()));
        //uuf.setDataDisattivazione(new java.util.Date());
        //Assumo che scade fra 50 anni - In quanto a DB il campo è obbligatorio
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 50);
        uuf.setDataDisattivazione(cal.getTime());

        return utenteUfficioRuoloRepository.save(uuf);

    }
    
    
    public UtenteUfficioRuolo updateAttivoFlag(Utente utente, Ruolo ruolo, SettoreUfficio settoreUfficio, boolean attivo) {
        UtenteUfficioRuolo uuf = utenteUfficioRuoloRepository.findByUtenteAndRuoloAndSettoreUfficio(utente, ruolo, settoreUfficio);

        if (uuf != null) {
            uuf.setAttivo(attivo);
            return utenteUfficioRuoloRepository.save(uuf);
        } else {
            throw new EntityNotFoundException("Combinazione Utente-Ruolo-Settore non trovata.");
        }
    }
    
    public boolean ifExistUtenteUfficioRuolo(Utente utente, Ruolo ruolo, SettoreUfficio settoreUfficio) {
		return utenteUfficioRuoloRepository.existsByUtente_IdUtenteAndRuolo_IdRuoloAndSettoreUfficio_IdSettoreUfficio(utente.getIdUtente(), ruolo.getIdRuolo(), settoreUfficio.getIdSettoreUfficio());
    }
    
}