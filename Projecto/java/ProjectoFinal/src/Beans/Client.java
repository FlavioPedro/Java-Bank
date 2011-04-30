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
public class Client {

    //properties
    private int clientNIF;
    private int bankID;
    private String name;
    private String address;
    private String zipCode;
    private String mail;
    private String contact;
    private String docType;
    private String docNumber;
    private Date birthDate;
    private String sex;


    //obj builders
    public Client(){

    }

    public Client(int theClientNIF, int theBankID, String aName,
            String anAddress, String aZipCode,
            String aContact, String aDocType, String aDocNumer, Date aBirthDate){

        setClientNIF(theClientNIF);
        setBankID(theBankID);
        setName(aName);
        setAddress(anAddress);
        setZipCode(aZipCode);
        setContact(aContact);
        setDocType(aDocType);
        setDocNumber(aDocNumer);
        setBirthDate(aBirthDate);
    }

    public Client(int theClientNIF, int theBankID, String aName,
            String anAddress, String aZipCode,
            String aContact, String aDocType, String aDocNumer,
            Date aBirthDate,String mail, String sex){

            this(theClientNIF, theBankID, aName, anAddress, aZipCode,
                    aContact, aDocType,
                    aDocNumer, aBirthDate);
            setMail(mail);
            setSex(sex);
    }


    //getters

    /**
     * @return the clientNIF
     */
    public int getClientNIF() {
        return clientNIF;
    }

        /**
     * @return the bankID
     */
    public int getBankID() {
        return bankID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @return the docType
     */
    public String getDocType() {
        return docType;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }


    //Setters

    /**
     * @param clientNIF the clientNIF to set
     */
    public void setClientNIF(int clientNIF) {
        this.clientNIF = clientNIF;
    }

        /**
     * @param bankID the bankID to set
     */
    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @param docNumber the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }



    //outputString generator
    @Override
    public String toString(){


        String toReturn = "";

        toReturn += "clientID: " + getClientNIF();
        toReturn += " bankID: " +getBankID();
        toReturn += " name: " + getName();
        toReturn += " address: " + getAddress();
        toReturn += " zipCode: " + getZipCode();
        toReturn += " mail: " + getMail();
        toReturn += " contact: " + getContact();
        toReturn += " docType: " + getDocType();
        toReturn += " docNumber: " + getDocNumber();
        toReturn += " birthDate: " + getBirthDate();
        toReturn += " sex: " + getSex();

        return toReturn;
    }
}
