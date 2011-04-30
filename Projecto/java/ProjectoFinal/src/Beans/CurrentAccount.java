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
public class CurrentAccount {

    //properties
    private int currentAccountID;
    private int bankID;
    private Date openDate;
    private double currentAmount;
    private double inicialAmount;

    //obj builders
    public CurrentAccount(){

    }
    
    public CurrentAccount(int theCurrentAccountID, int theBankID, 
            Date theOpenDate, double theCurrentAmount, double theInicialAmount){
        
        setCurrentAccountID(theCurrentAccountID);
        setBankID(theBankID);
        setOpenDate(theOpenDate);
        setCurrentAmount(theCurrentAmount);
        setInitialAmount(theInicialAmount);

    }
    //getters
    /**
     * @return the currentAccountID
     */
    public int getCurrentAccountID() {
        return currentAccountID;
    }

    /**
     * @return the bankID
     */
    public int getBankID() {
        return bankID;
    }

    /**
     * @return the openDate
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * @return the currentAmount
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /**
     * @return the inicialAmount
     */
    public double getInitialAmount() {
        return inicialAmount;
    }

    //setters

    /**
     * @param currentAccountID the currentAccountID to set
     */
    public void setCurrentAccountID(int currentAccountID) {
        this.currentAccountID = currentAccountID;
    }

    /**
     * @param bankID the bankID to set
     */
    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * @param currentAmount the currentAmount to set
     */
    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * @param inicialAmount the inicialAmount to set
     */
    public void setInitialAmount(double inicialAmount) {
        this.inicialAmount = inicialAmount;
    }

    //outputString generator
    @Override
    public String toString(){


        String toReturn = "";

        toReturn += "currentAccountID: " + getCurrentAccountID();
        toReturn += " bankID: " + getBankID();
        toReturn += " openDate: " + getOpenDate();
        toReturn += " currentAmount: " + getCurrentAmount();
        toReturn += " inicialAmount: " + getInitialAmount();

        return toReturn;
    }
}
