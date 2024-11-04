package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Ufficio;

import java.util.List;

@Repository
public interface UfficioRepository extends JpaRepository<Ufficio, Long> {
	//JPQL che restituisce oggetti di tipo u Ufficio
	
	/*select * from tbl_uffici u
	join tbl_settori_uffici su ON u.id_ufficio = su.id_ufficio
    join tbl_utenti_uffici_ruoli uur ON su.id_settore_ufficio = uur.id_settore_ufficio
	where uur.id_utente=1;*/
	
    @Query("SELECT DISTINCT u FROM Ufficio u "
    		+ "JOIN SettoreUfficio su ON u.idUfficio = su.ufficio.idUfficio "
    		+ "JOIN UtenteUfficioRuolo uur ON su.idSettoreUfficio = uur.settoreUfficio.idSettoreUfficio "
    		+ "WHERE uur.utente.idUtente = :idUtente")
    List<Ufficio> findUfficiByUtenteId(int idUtente);
    
    
    //List<Ufficio> findByUtenteId(Long idUtente);


}