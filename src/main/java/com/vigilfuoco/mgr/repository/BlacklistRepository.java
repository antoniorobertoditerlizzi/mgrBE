package com.vigilfuoco.mgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vigilfuoco.mgr.model.BlacklistedToken;

@Repository
public interface BlacklistRepository extends JpaRepository<BlacklistedToken, Long> {

	//Query di ricerca per token
    BlacklistedToken findByToken(String token);
    
    //Query di check esistenza token
    boolean existsByToken(String token);

}

