
package com.example.demo.model;



import java.math.BigDecimal;

public class Retrait extends Transaction{
	

	public Retrait(BigDecimal amount, Compte compte) {
		super(TransType.RETRAIT, amount,  compte);
	}

	@Override
	public void executer() {
		if(compte.getBalance().compareTo(amount)>=0) {
			compte.setBalance(compte.getBalance().subtract(getAmount()));
			compte.addTransaction(this);
			compte.getStatus().setLastr(getAmount().toString());
		}else {
			throw new IllegalArgumentException("Solde insuffisant pour le retrait.");
		}
	}
	
}
