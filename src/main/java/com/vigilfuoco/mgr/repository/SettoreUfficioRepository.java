package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.SettoreUfficio;

@Repository
public interface SettoreUfficioRepository extends JpaRepository<SettoreUfficio, Long> {
	
	SettoreUfficio findByIdSettoreUfficio(Short idSettoreUfficio);

}