/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author jhorgemiguel
 */
public class Client {
    private String name;
    private String address;
    private String zipCode;
    private String mail;
    private String contact;
    private String docType;
    private String docNumber;
    private GregorianCalendar birthDate;
    private String sex;
    private int nif;
    private List<BankAccount> bankAccounts;

    //Constructors    
    public Client(String name, String address, String zipCode,
            String contact, String docType, String docID, Date birthDate,
            String nif){
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.contact = contact;
        this.docType = docType;
        this.docNumber = docID;
        this.birthDate.setGregorianChange(birthDate);
        this.nif = Integer.getInteger(nif);
    }

    public Client(String name, String address, String zipCode, String mail,
            String contact, String docType, String docID, Date birthDate,
            String sex, String nif){
            this(name, address, zipCode, contact, docType,
                    docID, birthDate, nif);
            this.mail = mail;
            this.sex = sex;
    }

    //Getters
    public int getNIF() {
        return nif;
    }

    public String getAddress() {
        return address;
    }

    public GregorianCalendar getBirthDate() {
        return birthDate;
    }

    public String getContact() {
        return contact;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public String getDocType() {
        return docType;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getSex() {
        return sex;
    }

    //Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(GregorianCalendar birthDate) {
        this.birthDate = birthDate;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    // Related methods of bank accounts
    public int numberOfBankAccounts(){
        return bankAccounts.size();
    }

    public void addBankAccount(BankAccount account){
        bankAccounts.add(account);
    }
            
    
    @Override
    public String toString(){
        String c = "ID: " + getNIF() + "\n"
                + "Nome: " + getName() + "\n"
                + "Morada: " + getAddress() + "\n"
                + "Código Postal: " + getZipCode() + "\n";
        if (getMail() != null){
            c += "E-mail: " + getMail() + "\n";
        }
        c += "Contacto: " + getContact() + "\n"
                + "Tipo de Doc: " + getDocType() + "\n"
                + "Nº de Doc.: " + getDocNumber() + "\n"
                + "Data de Nascimento: " + getBirthDate() + "\n";
        if (getSex() != null)
                c += "Sexo: " + getSex() + "\n";
                c += "\n *********************** \n";
        return c;
    }

}
