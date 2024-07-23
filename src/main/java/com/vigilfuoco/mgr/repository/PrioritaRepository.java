package com.vigilfuoco.mgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.Priorita;


@Repository
public interface PrioritaRepository extends JpaRepository<Priorita, Long> {

	Priorita findById(long id);
	
	Priorita findByIdPriorita(Short descrizionePriorita);
	
	List<Priorita> findAll();

}
