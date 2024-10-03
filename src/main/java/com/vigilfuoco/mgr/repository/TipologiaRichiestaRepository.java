package com.vigilfuoco.mgr.repository;

import com.vigilfuoco.mgr.model.TipologiaRichiesta;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TipologiaRichiestaRepository extends JpaRepository<TipologiaRichiesta, Long> {

	//Richiesta findById(long id);
	
	//TipologiaRichiestaRepository findById(long id);
	
	TipologiaRichiesta findByIdTipologiaRichiesta(Short idTipologiaRichiesta);
	
	List<TipologiaRichiesta> findAll();
    
    @Modifying
    @Transactional
    @Query("UPDATE TipologiaRichiesta sr SET sr.attivo = :attivo WHERE sr.idTipologiaRichiesta = :idTipologiaRichiesta")
    int updateAttivoByIdTipologiaRichiesta(@Param("idTipologiaRichiesta") Short idTipologiaRichiesta, @Param("attivo") boolean attivo);
    
    
}
