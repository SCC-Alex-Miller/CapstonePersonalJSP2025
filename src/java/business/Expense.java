/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.time.LocalDate;

/**
 *
 * @author kj418824
 */
public class Expense {
    
    
    private int expenseID;
    private String expenseName;
    private double amount;
    private LocalDate transactionDate;
    private int frequency;
    private int fkCategoryID;
    private int fkAccountID;
    
    public Expense(){}
    
    public Expense(int expenseID, String expenseName, double amount, LocalDate transactionDate,int frequency, int fkCategoryID, int fkAccountID){
        this.expenseID = expenseID;
        this.expenseName = expenseName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.frequency = frequency;
        this.fkCategoryID = fkCategoryID;
        this.fkAccountID = fkAccountID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }
    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFkCategoryID() {
        return fkCategoryID;
    }

    public void setFkCategoryID(int fkCategoryID) {
        this.fkCategoryID = fkCategoryID;
    }

    public int getFkAccountID() {
        return fkAccountID;
    }

    public void setFkAccountID(int fkAccountID) {
        this.fkAccountID = fkAccountID;
    }
    
}
