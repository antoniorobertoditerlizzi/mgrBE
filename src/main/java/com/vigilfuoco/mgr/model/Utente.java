package com.vigilfuoco.mgr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

	public Utente() {
		super();
		this.accountDipvvf = accountDipvvf;
		this.emailVigilfuoco = emailVigilfuoco;
	}

	@Override
	public String toString() {
		return "UtenteMGR [accountDipvvf=" + accountDipvvf + ", emailVigilfuoco=" + emailVigilfuoco + "]";
	}

	

}


