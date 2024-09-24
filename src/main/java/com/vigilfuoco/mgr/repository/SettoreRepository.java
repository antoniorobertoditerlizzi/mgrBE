package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Settore;

@Repository
public interface SettoreRepository extends JpaRepository<Settore, Long> {
	
	//boolean existsBySettore_Id----(Long idSettore);
}