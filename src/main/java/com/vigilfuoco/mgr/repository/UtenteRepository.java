package com.vigilfuoco.mgr.repository;

import com.vigilfuoco.mgr.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/* 
 * Interfaccia che estende JPA REPOSITORY, libreria che si occupa di effettuare in automatico le operazioni CRUD DB
 * richiamando l'interfaccia repository possiamo richiamare tutti i metodo offerti da JpaRepository tipo il save 
 * per salvare i dati a DB ecc.
 * In questa interfaccia possiamo definire query semplicemente inserendo nel nome del metodo l'operazione di find a db, 
 * oppure possiamo eseguire query puntuali inline o query in JPQL.
 * 
 */

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Utente findByAccount(String accountDipvvf);
	
    @Query("SELECT u.idUtente FROM Utente u WHERE u.account = :accountName")
    int findIdUtenteByAccount(@Param("accountName") String accountName);

	//List<Utente> findByAccount(String accountDipvvf);
	    
    //Query
	/*@Query(value = "SELECT * FROM Utente WHERE accountDipvvf LIKE :ds", nativeQuery = true)
	List<Utente> SelByAccountDipvvfLike(@Param("accountDipvvf") String accounDipvvf);*/
	
	
	//Query JPQL
	/*@Query(value = "FROM RICHIESTA WHERE descrizione LIKE :ds")
	List<Richiesta> SelByDescrizioneLikeJPQL(@Param("ds") String ds);
	
	
	//necessità di modificare il db, va startata una transazione
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM RICHIESTA WHERE descrizione = :ds", nativeQuery = true)
	void DelRowDescrizione(@Param("ds") String ds);*/
	    
}
