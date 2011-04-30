/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.util.Date;

/**
 *
 * @author Flávio
 */
public class SavingsAccount {

    //properties
    private int savingsAccountID;
    private int currentAccountID;
    private int savingsAccountTypeID;
    private Date openDate;
    private double initialAmount;

    //obj builders
    public SavingsAccount(){

    }
    
    public SavingsAccount(int theSavingsAccountID, int theCurrentAccountID,
            int theSavingsAccountTypeID, Date theOpenDate, double theInitialAmount){

        setSavingsAccountID(theSavingsAccountID);
        setCurrentAccountID(theCurrentAccountID);
        setSavingsAccountTypeID(theSavingsAccountTypeID);
        setOpenDate(theOpenDate);
        setInitialAmount(theInitialAmount);

    }
    //getters
    /**
     * @return the savingsAccountID
     */
    public int getSavingsAccountID() {
        return savingsAccountID;
    }

    /**
     * @return the currentAccountID
     */
    public int getCurrentAccountID() {
        return currentAccountID;
    }

    /**
     * @return the savingsAccountTypeID
     */
    public int getSavingsAccountTypeID() {
        return savingsAccountTypeID;
    }

    /**
     * @return the openDate
     */
    public Date getOpenDate() {
        return openDate;
    }
    

    /**
     * @return the initialAmount
     */
    public double getInitialAmount() {
        return initialAmount;
    }

    //Setters

    /**
     * @param savingsAccountID the savingsAccountID to set
     */
    public void setSavingsAccountID(int savingsAccountID) {
        this.savingsAccountID = savingsAccountID;
    }

    /**
     * @param currentAccountID the currentAccountID to set
     */
    public void setCurrentAccountID(int currentAccountID) {
        this.currentAccountID = currentAccountID;
    }

    /**
     * @param savingsAccountTypeID the savingsAccountTypeID to set
     */
    public void setSavingsAccountTypeID(int savingsAccountTypeID) {
        this.savingsAccountTypeID = savingsAccountTypeID;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * @param initialAmount the inicialAmount to set
     */
    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    
    //outputString generator
    @Override
    public String toString(){


        String toReturn = "";

        toReturn += "savingsAccountID: " + getSavingsAccountID();
        toReturn += " currentAccountID: " + getCurrentAccountID();
        toReturn += " savingsAccountTypeID: " + getSavingsAccountTypeID();
        toReturn += " openDate: " + getOpenDate();
        toReturn += " initialAmount: " + getInitialAmount();

        return toReturn;
    }
    
}

