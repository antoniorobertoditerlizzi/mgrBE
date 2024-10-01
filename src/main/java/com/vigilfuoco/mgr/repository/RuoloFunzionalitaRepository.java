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
            "AND uur.ruolo.attivo = true)")
     List<Funzionalita> findFunzionalitaByAccount(String account);
    
}
