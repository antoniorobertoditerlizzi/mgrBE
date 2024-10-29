package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Ruolo;
import com.vigilfuoco.mgr.model.SettoreUfficio;
import com.vigilfuoco.mgr.model.Utente;
import com.vigilfuoco.mgr.model.UtenteUfficioRuolo;

import java.util.List;

@Repository
public interface UtenteUfficioRuoloRepository extends JpaRepository<UtenteUfficioRuolo, Long> {
   List<UtenteUfficioRuolo> findByUtenteIdUtente(Integer idUtente);
   
   List<UtenteUfficioRuolo> findByIdUtenteUfficioRuolo(Long idUtente);
   
   @Query("SELECT uuf FROM UtenteUfficioRuolo uuf "
   		+ "WHERE uuf.utente.idUtente = :idUtente "
   		+ "AND uuf.ruolo.idRuolo = :idRuolo "
   		+ "AND uuf.settoreUfficio.idSettoreUfficio = :idSettoreUfficio")
   List<UtenteUfficioRuolo> findByUtenteRuoloSettore(@Param("idUtente") int idUtente, @Param("idRuolo") Long idRuolo, @Param("idSettoreUfficio") Short idSettoreUfficio);

   UtenteUfficioRuolo findByUtenteAndRuoloAndSettoreUfficio(Utente utente, Ruolo ruolo, SettoreUfficio settoreUfficio);
   
   //Entitu Utente + Entity Ruolo + Entity SettoreUfficio, con rispettivi ID Entity
   boolean existsByUtente_IdUtenteAndRuolo_IdRuoloAndSettoreUfficio_IdSettoreUfficio(Integer idUtente, Long idRuolo, Short idSettoreUfficio);

}

/*SELECT * 
FROM tbl_utenti_uffici_ruoli uuf 
JOIN tbl_utenti u ON uuf.id_utente = u.id_utente
JOIN tbl_ruoli r ON uuf.id_ruolo = r.id_ruolo
JOIN tbl_settori_uffici su ON uuf.id_settore_ufficio = su.id_settore_ufficio
WHERE uuf.id_utente_ufficio_ruolo = 7;
*/


