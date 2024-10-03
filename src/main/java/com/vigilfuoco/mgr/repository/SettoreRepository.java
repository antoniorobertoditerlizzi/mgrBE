package com.vigilfuoco.mgr.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Settore;

@Repository
public interface SettoreRepository extends JpaRepository<Settore, Long> {
	
	//boolean existsBySettore_Id----(Long idSettore);
	
    @Modifying
    @Transactional
    @Query("UPDATE Settore sr SET sr.attivo = :attivo WHERE sr.idSettore = :idSettore")
    int updateAttivoByIdSettore(@Param("idSettore") Long idSettore, @Param("attivo") boolean attivo);
    
    
}