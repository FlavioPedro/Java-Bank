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
public class SavingsMovementHistory {

    //properties
    private int savingsMovementHistoryID;
    private int savingsAccountID;
    private String movementType;
    private double amountMoved;
    private Date movementDate;

    //obj builders
    public SavingsMovementHistory(){

    }
    
    public SavingsMovementHistory(int theAccountID,
            String theMovementType, Double theAmountMoved, Date theDate){

        setSavingsAccountID(theAccountID);
        setMovementType(theMovementType);
        setAmountMoved(theAmountMoved);
        setMovementDate(theDate);
    }

    //getters
    /**
     * @return the currentMovementHistoryID
     */
    public int getSavingsMovementHistoryID() {
        return savingsMovementHistoryID;
    }

    /**
     * @return the currentAccountID
     */
    public int getSavingsAccountID() {
        return savingsAccountID;
    }

    /**
     * @return the movementType
     */
    public String getMovementType() {
        return movementType;
    }

    /**
     * @return the amountMoved
     */
    public double getAmountMoved() {
        return amountMoved;
    }

    /**
     * @return the movementDate
     */
    public Date getMovementDate() {
        return movementDate;
    }

    //setters
    /**
     * @param currentMovementHistoryID the currentMovementHistoryID to set
     */
    public void setSavingsMovementHistoryID(int savingsMovementHistoryID) {
        this.savingsMovementHistoryID = savingsMovementHistoryID;
    }

    /**
     * @param currentAccountID the currentAccountID to set
     */
    public void setSavingsAccountID(int savingsAccountID) {
        this.savingsAccountID = savingsAccountID;
    }

    /**
     * @param movementType the movementType to set
     */
    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    /**
     * @param amountMoved the amountMoved to set
     */
    public void setAmountMoved(double amountMoved) {
        this.amountMoved = amountMoved;
    }

    /**
     * @param movementDate the movementDate to set
     */
    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    //outputString generator
    @Override
    public String toString(){


        String toReturn = "";

        toReturn += "savingsMovementHistoryID: " + getSavingsMovementHistoryID();
        toReturn += " savingsAcountID: " + getSavingsAccountID();
        toReturn += " movementType: " + getMovementType();
        toReturn += " amountMoved: " + getAmountMoved();
        toReturn += " movementDate: " + getMovementDate();


        return toReturn;
    }
}
