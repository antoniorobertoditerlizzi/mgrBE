package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    private long blacklistTime;

    public BlacklistedToken(String token) {
        this.token = token;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getBlacklistTime() {
		return blacklistTime;
	}

	public void setBlacklistTime(long blacklistTime) {
		this.blacklistTime = blacklistTime;
	}

	@Override
	public String toString() {
		return "BlacklistedToken [id=" + id + ", token=" + token + ", blacklistTime=" + blacklistTime + "]";
	}


}
