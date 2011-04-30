/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.util.Date;

/**
 *
 * @author jhorgemiguel
 */
public abstract class BankAccount {
private Date openDate;
private double initialAmount;
private int accountID;

    public BankAccount(){}

    public BankAccount(Date openDate, double initial, int id){
        setOpenDate(openDate);
        setInitialAmount(initial);
        setAccountID(id);
    }

    //Getters & Setters
    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public int getAccountID(){
        return accountID;
    }

    public void setAccountID(int accountID){
        this.accountID = accountID;
    }
    
    public abstract double getAmount();
    
    public abstract void deposit(double amount);
    
    //public abstract String histMovements(ArrayList<String> hist)
    //        throws IOException;
    
}
