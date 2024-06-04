package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Utente {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
	
	@Id
    @Column(nullable = false) 
    private String accountDipvvf;
    
    @Column(nullable = false) 
    private String emailVigilfuoco;
    
    @Column(nullable = false) 
    private String username;
    
    @Column(nullable = false) 
    private String password;
    
    @Column(nullable = false) 
    private String role;
    
    @Column(nullable = false) 
    private Boolean enabled;
    
    @Column(nullable = false) 
    private int ruoloID;

    
	public int getRuoloID() {
		return ruoloID;
	}

	public void setRuoloID(int ruoloID) {
		this.ruoloID = ruoloID;
	}

	public String getAccountDipvvf() {
		return accountDipvvf;
	}

	public void setAccountDipvvf(String accountDipvvf) {
		this.accountDipvvf = accountDipvvf;
	}

	public String getEmailVigilfuoco() {
		return emailVigilfuoco;
	}

	public void setEmailVigilfuoco(String emailVigilfuoco) {
		this.emailVigilfuoco = emailVigilfuoco;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	
	
	public Utente() {
		super();
		this.accountDipvvf = accountDipvvf;
		this.emailVigilfuoco = emailVigilfuoco;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.ruoloID = ruoloID;
	}

	@Override
	public String toString() {
		return "Utente [accountDipvvf=" + accountDipvvf + ", emailVigilfuoco=" + emailVigilfuoco + ", username="
				+ username + ", password=" + password + ", role=" + role + ", enabled=" + enabled + ", ruoloID=" + ruoloID
				+ "]";
	}



}


