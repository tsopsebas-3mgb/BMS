package com.example.demo.model;

import java.math.BigDecimal;

public class TransactionReq {


    private String accountNumber;
    private String destinatorAccountNumber;
    private BigDecimal amount;
    private String type;
    private String pass;


    public String getAccountNumber() {
        return accountNumber;
    }   
    public BigDecimal getAmount() {
        return amount;
    }
    public String getType() {
        return type;
    }
    public String getPass() {
        return pass;
    }
    public String getDestinatorAccountNumber() {
        return destinatorAccountNumber;
    }

}