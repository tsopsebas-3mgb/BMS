
package com.example.demo.model;



import java.math.BigDecimal;

public class Transfert extends Transaction{
	
	private Compte source;
	private Compte destinataire;
	
	public Transfert(BigDecimal amount, Compte source,Compte destinaitaire) {
		super(TransType.TRANSFERT, amount, source);
		this.source=source;
		this.destinataire=destinaitaire;
	}

	@Override
	public void executer() {	
		
		if(source.getBalance().compareTo(amount)>=0) {
			source.setBalance(compte.getBalance().subtract(getAmount()));
			source.addTransaction(this);
			source.getStatus().setLastr(getAmount().toString());

			destinataire.setBalance(destinataire.getBalance().add(amount));
		destinataire.addTransaction(this);
		destinataire.getStatus().setLastd(amount.toString());
		}else{
			throw new IllegalArgumentException("Solde insuffisant pour le retrait.");
		}	
	}
	
}
