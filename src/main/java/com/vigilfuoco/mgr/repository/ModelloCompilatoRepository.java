package com.vigilfuoco.mgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.ModelloCompilato;

@Repository
public interface ModelloCompilatoRepository extends JpaRepository<ModelloCompilato, Long> {

	// Query per trovare ModelloCompilato in base all'id del modello
    @Query("SELECT mc FROM ModelloCompilato mc " +
           "WHERE mc.modello.idModello = :idModello " +
           "AND EXISTS (SELECT mtr FROM ModelloTipologiaRichiesta mtr " +
           "WHERE mtr.modello.idModello = mc.modello.idModello " +
           "AND mtr.tipologiaRichiesta.idTipologiaRichiesta = :idTipologiaRichiesta " +
           "AND mtr.attivo = true)") 
    List<ModelloCompilato> findByModelloIdAndTipologiaRichiestaIdAndAttivo(
            @Param("idModello") Long idModello,
            @Param("idTipologiaRichiesta") Short idTipologiaRichiesta);
    
    List<ModelloCompilato> findByRichiesta_IdRichiesta(Long idRichiesta);

}
