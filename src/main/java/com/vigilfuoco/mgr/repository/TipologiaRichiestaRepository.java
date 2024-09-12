package com.vigilfuoco.mgr.repository;


import com.vigilfuoco.mgr.model.Richiesta;
import com.vigilfuoco.mgr.model.TipologiaRichiesta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipologiaRichiestaRepository extends JpaRepository<TipologiaRichiesta, Long> {

	//Richiesta findById(long id);
	
	//TipologiaRichiestaRepository findById(long id);
	
	TipologiaRichiesta findByIdTipologiaRichiesta(Short idTipologiaRichiesta);
	
	List<TipologiaRichiesta> findAll();

}
