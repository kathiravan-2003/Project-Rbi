package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Customer {

        @Id
        private int id;

    private String name;
    private int age;
    private double balance;
    private double salary;
    private int cibil;
    private boolean active;
    private boolean loanActive;
    private double loanAmount;
    private boolean personalLoanActive;
    private double personalLoanAmount;
    private boolean goldLoanActive;
    private double goldLoanAmount;
    private boolean insuranceActive;
    private double insuranceAmount;
    private boolean emiActive;
    private double emiAmount;
    private int remainingEmi;
    private String nomineeName;
    private String nomineeRelationship;
    private String nomineePhone;
    private boolean creditCardActive;
    private double creditCardLimit;
    private double loanInterestRate;
    private double loanInterestAmount;
    private String transactionHistory;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getCibil() {
        return cibil;
    }

    public void setCibil(int cibil) {
        this.cibil = cibil;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLoanActive() {
        return loanActive;
    }

    public void setLoanActive(boolean loanActive) {
        this.loanActive = loanActive;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public boolean isPersonalLoanActive() {
        return personalLoanActive;
    }

    public void setPersonalLoanActive(boolean personalLoanActive) {
        this.personalLoanActive = personalLoanActive;
    }

    public double getPersonalLoanAmount() {
        return personalLoanAmount;
    }

    public void setPersonalLoanAmount(double personalLoanAmount) {
        this.personalLoanAmount = personalLoanAmount;
    }

    public boolean isGoldLoanActive() {
        return goldLoanActive;
    }

    public void setGoldLoanActive(boolean goldLoanActive) {
        this.goldLoanActive = goldLoanActive;
    }

    public double getGoldLoanAmount() {
        return goldLoanAmount;
    }

    public void setGoldLoanAmount(double goldLoanAmount) {
        this.goldLoanAmount = goldLoanAmount;
    }

    public boolean isInsuranceActive() {
        return insuranceActive;
    }

    public void setInsuranceActive(boolean insuranceActive) {
        this.insuranceActive = insuranceActive;
    }

    public double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public boolean isEmiActive() {
        return emiActive;
    }

    public void setEmiActive(boolean emiActive) {
        this.emiActive = emiActive;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public int getRemainingEmi() {
        return remainingEmi;
    }

    public void setRemainingEmi(int remainingEmi) {
        this.remainingEmi = remainingEmi;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeRelationship() {
        return nomineeRelationship;
    }

    public void setNomineeRelationship(String nomineeRelationship) {
        this.nomineeRelationship = nomineeRelationship;
    }

    public String getNomineePhone() {
        return nomineePhone;
    }

    public void setNomineePhone(String nomineePhone) {
        this.nomineePhone = nomineePhone;
    }

    public boolean isCreditCardActive() {
        return creditCardActive;
    }

    public void setCreditCardActive(boolean creditCardActive) {
        this.creditCardActive = creditCardActive;
    }

    public double getCreditCardLimit() {
        return creditCardLimit;
    }

    public void setCreditCardLimit(double creditCardLimit) {
        this.creditCardLimit = creditCardLimit;
    }

    public double getLoanInterestRate() {
        return loanInterestRate;
    }

    public void setLoanInterestRate(double loanInterestRate) {
        this.loanInterestRate = loanInterestRate;
    }

    public double getLoanInterestAmount() {
        return loanInterestAmount;
    }

    public void setLoanInterestAmount(double loanInterestAmount) {
        this.loanInterestAmount = loanInterestAmount;
    }

    public String getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(String transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public Customer() {

    }
    }


