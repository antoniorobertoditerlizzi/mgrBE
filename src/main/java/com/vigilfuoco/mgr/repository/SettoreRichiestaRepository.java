package com.vigilfuoco.mgr.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.SettoreRichiesta;

@Repository
public interface SettoreRichiestaRepository extends JpaRepository<SettoreRichiesta, Long> {
	
    //True o False se: esiste già la tupla nella tabella Settore con:
    //tabella Settore campo idSettore AND
    //tabella TiplogiaRichiesta campo IdTipologiaRichiesta
    boolean existsBySettore_IdSettoreAndTipologiaRichiesta_IdTipologiaRichiesta(Long idSettore, Short idTipologiaRichiesta);
    
    @Modifying
    @Transactional
    @Query("UPDATE SettoreRichiesta sr SET sr.attivo = :attivo WHERE sr.idSettoreRichiesta = :idSettoreRichiesta")
    int updateAttivoByIdSettoreRichiesta(@Param("idSettoreRichiesta") Long idSettoreRichiesta, @Param("attivo") boolean attivo);
    
    List<SettoreRichiesta> findBySettore_IdSettore(Long idSettore);

    List<SettoreRichiesta> findByTipologiaRichiesta_IdTipologiaRichiesta(Short idTipologiaRichiesta);

    List<SettoreRichiesta> findBySettore_IdSettoreAndTipologiaRichiesta_IdTipologiaRichiesta(Long idSettore, Short idTipologiaRichiesta);
}