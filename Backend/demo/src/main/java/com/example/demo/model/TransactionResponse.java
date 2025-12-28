package com.example.demo.model;


import java.math.BigDecimal;
import com.example.demo.model.Transaction;

public class TransactionResponse {
    private BigDecimal amount;
    private TransType type;
    private String accountNumber;
    private BigDecimal balance;

    public TransactionResponse(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.accountNumber = transaction.getCompte().getAccountNumber();
        this.balance=transaction.getCompte().getBalance();
    }

    public BigDecimal getAmount() { return amount; }
    public TransType getType() { return type; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance(){return balance;}
}
