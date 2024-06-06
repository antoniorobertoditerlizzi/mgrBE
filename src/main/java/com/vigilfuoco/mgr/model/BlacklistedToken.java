package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_blacklisted_tokens")
public class BlacklistedToken {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idToken;
	
	@Column(name = "token", nullable = false, length = 250)
	private String token;

    public Long getIdToken() {
		return idToken;
	}

	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public String toString() {
		return "BlacklistedToken [idToken=" + idToken + ", token=" + token + "]";
	}

	
    public BlacklistedToken(String token) {
        this.token = token;
    }


}
