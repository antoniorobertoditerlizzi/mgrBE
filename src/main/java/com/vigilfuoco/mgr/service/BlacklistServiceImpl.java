package com.vigilfuoco.mgr.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.model.BlacklistedToken;
import com.vigilfuoco.mgr.repository.BlacklistRepository;

@Service
public class BlacklistServiceImpl implements BlacklistService {

	private static final Logger logger = LogManager.getLogger(BlacklistService.class);
    
    @Autowired
    private BlacklistRepository blacklistRepository;
    
    @Value("${spring.scheduling.fixedRate}")
    private String fixedRate;
    
    @Value("${expirationTokenTime}")
    private long expirationTokenTime;
    
    @Override
    public void addToBlacklist(String token) {
        if (token != null) {
            BlacklistedToken blacklistedToken = blacklistRepository.findByToken(token);
            if (blacklistedToken == null) {
                blacklistedToken = new BlacklistedToken(token);
            }
            blacklistRepository.save(blacklistedToken);
        } else {
            logger.warn("Tentativo di aggiungere token vuoto o nullo alla blacklist");
            return;
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        // Rimuovo il "Bearer " prefisso se esiste
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        // Utilizzo la stringa token per il controllo della blacklist
        return blacklistRepository.existsByToken(token);
    }

    @Scheduled(fixedRateString="${spring.scheduling.fixedRate}")
    public void removeExpiredTokens() {
        //long retentionPeriod = 60 * 60 * 24; // 24 hours in seconds
    	long retentionPeriod = expirationTokenTime;
    	
        List<BlacklistedToken> blacklistedTokens = blacklistRepository.findAll();

        for (BlacklistedToken token : blacklistedTokens) {
            long currentTime = System.currentTimeMillis();

            // Controllo se il periodo di conservazione è trascorso dal blacklistTime
            if (currentTime - expirationTokenTime > retentionPeriod) {
                // Rimuovo il token da blacklist
                blacklistRepository.delete(token);
                logger.info("Token {} removed from blacklist due to expiration", token.getToken());
            }
        }
    }
}