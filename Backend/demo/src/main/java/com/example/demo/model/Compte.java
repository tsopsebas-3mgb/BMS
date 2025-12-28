package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.model.Status;

@Entity
@Table(name = "comptes")
public class Compte {
    private Types type;
    private BigDecimal balance;
    private String accountNumber;
    private String nom;
    private String niu;
    private Status status;
    private String telephone;
    private String password;
    private List<TransactionResponse> transactions = new ArrayList<TransactionResponse>();

    public Compte(final String nom, final String niu, final String telephone, Types type, String accountNumber,
            String password) {

        this.balance = new BigDecimal("0.00");
        this.type = type;
        this.accountNumber = accountNumber;
        this.niu = niu;
        this.nom = nom;
        this.telephone = telephone;
        this.status = new Status();
        this.password = password;

    }

    public Types getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public String getNiu() {
        return niu;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction trans) {
        transactions.add(new TransactionResponse(trans));
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void afficherInfos() {
        System.out.println("Compte " + type + " - Solde: " + balance + " - Titulaire: " + nom);
    }

    public Status getStatus() {
        return status;
    }
}
