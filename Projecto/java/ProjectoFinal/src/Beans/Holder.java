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

public class Holder {

    //properties
    private int holderID;
    private int clientID;
    private int currentAccountID;
    private Date associationDate;

    //obj Builders
    public Holder(){

    }

    public Holder(int aHolderID, int aClientID, int aCurrentAccountID){

        setHolderID(aHolderID);
        setClientID(aClientID);
        setCurrentAccountID(aCurrentAccountID);

    }

    public Holder(int aHolderID, int aClientID, int aCurrentAccountID, Date anAssociantionDate){

        this(aHolderID, aClientID, aCurrentAccountID);

        setAssociationDate(anAssociantionDate);
    }

    //getters
    /**
     * @return the holderID
     */
    public int getHolderID() {
        return holderID;
    }

    /**
     * @return the clientID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @return the currentAccountID
     */
    public int getCurrentAccountID() {
        return currentAccountID;
    }

    /**
     * @return the associationDate
     */
    public Date getAssociationDate() {
        return associationDate;
    }

    //setters
    /**
     * @param holderID the holderID to set
     */
    public void setHolderID(int holderID) {
        this.holderID = holderID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * @param currentAccountID the currentAccountID to set
     */
    public void setCurrentAccountID(int currentAccountID) {
        this.currentAccountID = currentAccountID;
    }

    /**
     * @param associationDate the associationDate to set
     */
    public void setAssociationDate(Date associationDate) {
        this.associationDate = associationDate;
    }

    //outputString generator
    @Override
    public String toString(){
        String toReturn = "";

        toReturn += "holderID: " + getHolderID();
        toReturn += " clientID: " + getClientID();
        toReturn += " currentAccountID: " + getCurrentAccountID();
        toReturn += " associationDate: " + getAssociationDate();

        return toReturn;
    }

}
