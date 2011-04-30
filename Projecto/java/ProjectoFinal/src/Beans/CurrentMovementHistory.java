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
public class CurrentMovementHistory {

    //properties
    private int currentMovementHistoryID;
    private int currentAccountID;
    private String movementType;
    private double amountMoved;
    private Date movementDate;

    //obj builders
    public CurrentMovementHistory(){

    }

    public CurrentMovementHistory (int theAccountID,
            String theMovementType, Double theAmountMoved, Date theDate){

        setCurrentAccountID(theAccountID);
        setMovementType(theMovementType);
        setAmountMoved(theAmountMoved);
        setMovementDate(theDate);
    }

    //getters
    /**
     * @return the currentMovementHistoryID
     */
    public int getCurrentMovementHistoryID() {
        return currentMovementHistoryID;
    }

    /**
     * @return the currentAccountID
     */
    public int getCurrentAccountID() {
        return currentAccountID;
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
    public void setCurrentMovementHistoryID(int currentMovementHistoryID) {
        this.currentMovementHistoryID = currentMovementHistoryID;
    }

    /**
     * @param currentAccountID the currentAccountID to set
     */
    public void setCurrentAccountID(int currentAccountID) {
        this.currentAccountID = currentAccountID;
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

        toReturn += "currentMovementHistoryID: " + getCurrentMovementHistoryID();
        toReturn += " currentAcountID: " + getCurrentAccountID();
        toReturn += " movementType: " + getMovementType();
        toReturn += " amountMoved: " + getAmountMoved();
        toReturn += " movementDate: " + getMovementDate();


        return toReturn;
    }
}
