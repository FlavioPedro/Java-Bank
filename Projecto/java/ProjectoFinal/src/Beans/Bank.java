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
public class Bank {

    //properties
    private int bankID;
    private String bankName;
    private Date refDate;

    //obj Builders
    public Bank(){

    }

    public Bank(String name, Date date){
        setBankName(name);
        setRefDate(date);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">

    /**
     * @return the bankID
     */
    public int getBankID() {
        return bankID;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @return the refDate
     */
    public Date getRefDate() {
        return refDate;
    }
    //</editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @param refDate the refDate to set
     */
    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    /**
     * @param bankID the bankID to set
     */
    public void setBankID(int bankID) {
        this.bankID = bankID;
    }
    // </editor-fold>

    //outputString generator
    @Override
    public String toString(){
        String toReturn = "";

        toReturn += "bankID: " + getBankID();
        toReturn += " bankName: " + getBankName();
        toReturn += " refDate: " + getRefDate();

        return toReturn;
    }
}
