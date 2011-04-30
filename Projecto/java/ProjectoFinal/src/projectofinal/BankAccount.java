/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author jhorgemiguel
 */
public abstract class BankAccount {
    private GregorianCalendar openDate;
    private double initialAmount;
    private int accountID;

    public BankAccount(Date openDate, double initial, int id){
        this.openDate.setGregorianChange(openDate);
        initialAmount = initial;
        accountID = id;
    }

    //Getters & Setters
    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public GregorianCalendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(GregorianCalendar openDate) {
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
    
    public abstract void close() throws Throwable;
    
    public abstract String histMovements(ArrayList<String> hist)
            throws IOException;
    
}
