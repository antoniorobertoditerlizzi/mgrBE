package com.vigilfuoco.mgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    // metodi non specificati in quanto JpaRepository fornisce già metodi basici di findAll()
	
	
    // Trovo i ruoli distinti associati all'utente tramite ID utente, filtrati per ruoli attivi
    @Query("SELECT DISTINCT uur.ruolo FROM UtenteUfficioRuolo uur " +
           "WHERE uur.utente.idUtente = :idUtente " +
           "AND uur.attivo = true")
    List<Ruolo> findDistinctRuoliByIdUtente(int idUtente);

    // Trovo i ruoli distinti associati all'utente tramite account name, filtrati per ruoli attivi
    @Query("SELECT DISTINCT uur.ruolo FROM UtenteUfficioRuolo uur " +
           "WHERE uur.utente.account = :account " +
           "AND uur.attivo = true")
    List<Ruolo> findDistinctRuoliByAccount(String account);
    
}