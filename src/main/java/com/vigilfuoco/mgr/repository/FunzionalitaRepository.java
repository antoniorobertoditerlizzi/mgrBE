package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Funzionalita;

@Repository
public interface FunzionalitaRepository extends JpaRepository<Funzionalita, Long> {

    boolean existsByCodiceFunzionalita(String codiceFunzionalita);
    
    boolean existsByUrl(String url);
}
