package com.example.demo.service;
import com.example.demo.model.Compte;
import com.example.demo.model.Types;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import com.example.demo.model.Transaction;
import com.example.demo.model.Depot;
import com.example.demo.model.LoginReturn;
import com.example.demo.model.Retrait;
import com.example.demo.model.Transfert;

import org.springframework.stereotype.Service;


@Service
public class Banque {
    private List<Compte> comptes = new ArrayList<>();

   public Compte creerCompte(String nom,String niu,String telephone,Types type,String password){
	   for(Compte c:comptes) {
		   if(c.getNiu().equals(niu)) {
			   return null;
		   }
	   }
	   String numero = accountNumber();
	   Compte compte = new Compte (nom,niu,telephone,type,numero,password);
	   comptes.add(compte);
	   return compte;
   }
   			
   			public Transaction initiateTransaction(String accountNumber,String destinatorAccountNumber,BigDecimal amount, String type) {
	        Compte compte = getCompteById(accountNumber);
	        Compte destinatorCompte = getCompteById(destinatorAccountNumber);
		   	Transaction transaction;
	        switch (type.toUpperCase()) {
	            case "DEPOT":
	                transaction = new Depot(amount, compte);
	                break;
	            case "RETRAIT":
	                transaction = new Retrait(amount, compte);
	                break;
				case "TRANSFERT":
				transaction = new Transfert(amount, compte, destinatorCompte);
				break;
	            default:
	                return null;
	        }
	        transaction.executer();
	        return transaction;

			}

   			public Compte getCompteById(String id) {
	        for (Compte c : comptes) {
	            if (c.getAccountNumber().equals(id)) {
	                return c;
	            }
	        }
	        return null;
	    }
	    private String accountNumber() {
		    long number;
		    boolean exists;

		    do {
		        number = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		        exists = false;

		        for (Compte c : comptes) {
		            if (c.getAccountNumber().equals(number)) {
		                exists = true;
		                break;
		            }
		        }
		    } while (exists);


		    return String.valueOf(number);
		}

		public LoginReturn logIn(String num,String tel,String pass){
			for (Compte c : comptes) {
		            if (c.getAccountNumber().equals(num) && c.getTelephone().equals(tel) && c.getPassword().equals(pass) ) {
						LoginReturn logReturn = new LoginReturn(true,c.getAccountNumber());
						return logReturn;
		        }
		}

		throw new IllegalAccessError("Account not Found");
		
	}

	public Compte fetchCompte(boolean log,String accountNumber){
		if(log == false){
				return null;
		}
		Compte c = getCompteById(accountNumber);
		return c;
	}

		
		

	    public List<Compte> getComptes() {
	        return comptes;
	    }
   
}
