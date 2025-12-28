
package com.example.demo.model;



import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public abstract class Transaction {
    private TransType type;
    protected BigDecimal amount;
    private Date date;
    protected Compte compte;
    private UUID id;

    public Transaction(TransType type, BigDecimal amount,Compte compte) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
        this.compte=compte;
        this.id=UUID.randomUUID();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public TransType getType() { return type; }
    public UUID getID() { return id; }
    public Compte getCompte() { return compte; }
    public BigDecimal getAmount() { return amount; }
    public Date getDate() { return date; }

    public void setType(TransType type) {
        this.type = type;
    }
    public abstract void executer();
}
