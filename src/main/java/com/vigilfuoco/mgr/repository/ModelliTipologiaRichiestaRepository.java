package com.vigilfuoco.mgr.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.ModelloTipologiaRichiesta;


@Repository
public interface ModelliTipologiaRichiestaRepository extends JpaRepository<ModelloTipologiaRichiesta, Long> {
	
	//Modello è l'entità collegata e IdModello che è la proprietà di quella entità, idem per TipologiaRichiesta. (_ utilizzati quando si sta attraversando una relazione tra entità)
    List<ModelloTipologiaRichiesta> findByModello_IdModelloAndTipologiaRichiesta_IdTipologiaRichiestaAndAttivo(Long idModello, Short idTipologiaRichiesta, Boolean attivo);

    List<ModelloTipologiaRichiesta> findByTipologiaRichiesta_IdTipologiaRichiesta(Short idTipologiaRichiesta);

    List<ModelloTipologiaRichiesta> findByModello_IdModello(Long idModello);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM ModelloTipologiaRichiesta mtr WHERE mtr.tipologiaRichiesta.idTipologiaRichiesta = :idTipologiaRichiesta")
    void deleteByTipologiaRichiesta_IdTipologiaRichiesta(@Param("idTipologiaRichiesta") Short idTipologiaRichiesta);
}
