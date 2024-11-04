package com.vigilfuoco.mgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vigilfuoco.mgr.model.Funzionalita;
import com.vigilfuoco.mgr.model.Ruolo;
import com.vigilfuoco.mgr.model.RuoloFunzionalita;

public interface RuoloFunzionalitaRepository extends JpaRepository<RuoloFunzionalita, Long> {

    boolean existsByRuoloAndFunzionalita(Ruolo ruolo, Funzionalita funzionalita);

    
    @Query("SELECT DISTINCT rf.funzionalita FROM RuoloFunzionalita rf " +
            "WHERE rf.ruolo.idRuolo IN (" +
            "SELECT uur.ruolo.idRuolo FROM UtenteUfficioRuolo uur " +
            "WHERE uur.utente.account = :account " +
            "AND uur.attivo = 1)" +
            "AND rf.attivo = 1")
     List<Funzionalita> findFunzionalitaByAccount(String account);
    
    /*USE prggestionerichieste;
    SELECT DISTINCT * 
    FROM tbl_ruoli_funzionalita rf 
    JOIN tbl_ruoli ru ON ru.id_ruolo = rf.id_ruolo
    WHERE rf.id_ruolo IN (
        SELECT uur.id_ruolo 
        FROM tbl_utenti_uffici_ruoli uur
        JOIN tbl_utenti u ON uur.id_utente = u.id_utente
        WHERE u.account = 'alessandro.pece'
          AND uur.attivo = true
    );*/

}
