package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    // metodi non specificati in quanto JpaRepository fornisce già metodi basici di findAll()
}