package com.example.demo.model;

public class LoginReturn {
    private String accountNumber;
    private boolean log;

    public LoginReturn(boolean log , String accountNumber){
        this.accountNumber=accountNumber;
        this.log=log;
    }
    public boolean getLog(){
        return log;
    }

    public String getAccountNumber(){
        return accountNumber;
    }
}
