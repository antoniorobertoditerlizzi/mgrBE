package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.UtenteUfficioRuolo;

import java.util.List;

@Repository
public interface UtenteUfficioRuoloRepository extends JpaRepository<UtenteUfficioRuolo, Long> {
   List<UtenteUfficioRuolo> findByUtenteIdUtente(Integer idUtente);
   
   List<UtenteUfficioRuolo> findByIdUtenteUfficioRuolo(Long idUtente);
}