package com.vigilfuoco.mgr.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.StatoRichiesta;



@Repository
public interface StatoRichiestaRepository extends JpaRepository<StatoRichiesta, Long> {



}
