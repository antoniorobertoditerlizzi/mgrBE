package com.vigilfuoco.mgr.service;

public interface BlacklistService {
	
    void addToBlacklist(String token);

	boolean isTokenBlacklisted(String token);
}