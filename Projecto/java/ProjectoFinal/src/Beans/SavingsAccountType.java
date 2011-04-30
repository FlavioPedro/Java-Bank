/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

/**
 *
 * @author Flávio
 */
public class SavingsAccountType {

    //properties
    private int savingsAccountTypeID;
    private String type;
    private double interestRate;
    private int duration;

    //obj builders
    public SavingsAccountType(){

    }
    
    public SavingsAccountType(int theTypeID, String theType,
            double theInterestRate, int theDuration){

        setSavingsAccountTypeID(theTypeID);
        setType(theType);
        setInterestRate(theInterestRate);
        setDuration(theDuration);

    }
    //getters
    /**
     * @return the savingsAccountTypeID
     */
    public int getSavingsAccountTypeID() {
        return savingsAccountTypeID;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    //setters
    /**
     * @param savingsAccountTypeID the savingsAccountTypeID to set
     */
    public void setSavingsAccountTypeID(int savingsAccountTypeID) {
        this.savingsAccountTypeID = savingsAccountTypeID;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    //outputString generator
    @Override
    public String toString(){
        String toReturn = "";

        toReturn += "savingsAccountTypeID: " + getSavingsAccountTypeID();
        toReturn += " type: " + getType();
        toReturn += " interestRate: " + getInterestRate();
        toReturn += " duration: " + getDuration();

        return toReturn;
    }
}
