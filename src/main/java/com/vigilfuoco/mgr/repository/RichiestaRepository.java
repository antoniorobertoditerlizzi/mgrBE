package com.vigilfuoco.mgr.repository;


import com.vigilfuoco.mgr.model.Richiesta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//Operazioni CRUD DB

@Repository
public interface RichiestaRepository extends JpaRepository<Richiesta, Long> {

	List<Richiesta> findByDescrizione(String descrizione);
    
    //Query
	@Query(value = "SELECT * FROM RICHIESTA WHERE descrizione LIKE :ds", nativeQuery = true)
	List<Richiesta> SelByDescrizioneLike(@Param("ds") String ds);
	
	
	//Query JPQL
	/*@Query(value = "FROM RICHIESTA WHERE descrizione LIKE :ds")
	List<Richiesta> SelByDescrizioneLikeJPQL(@Param("ds") String ds);
	
	
	//necessità di modificare il db, va startata una transazione
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM RICHIESTA WHERE descrizione = :ds", nativeQuery = true)
	void DelRowDescrizione(@Param("ds") String ds);*/
	    
}
