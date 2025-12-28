package com.example.demo.model;



import java.math.BigDecimal;

public class Depot extends Transaction {

	public Depot( BigDecimal amount,Compte compte) {
		super(TransType.DEPOT, amount, compte);
	}
	

	@Override
	public void executer() {
		compte.setBalance(compte.getBalance().add(getAmount()));
		compte.addTransaction(this);
		compte.getStatus().setLastd(getAmount().toString());
	}
	
}
