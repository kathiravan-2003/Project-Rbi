package com.example.demo.dto;

public class DepositRequest {
    double amount;
    public void setAmount(double amount){
        this.amount=amount;
    }
    public double getAmount(){
        return this.amount;
    }
    public DepositRequest(){

    }
}
