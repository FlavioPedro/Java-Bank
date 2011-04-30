/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import DAO.DataAcessObject;
import DAO.SQLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author jhorgemiguel
 */
public class Bank {
    private int bankID;
    private String bankName;
    private GregorianCalendar refDate = new GregorianCalendar();
    private List<Client> clients = new ArrayList<Client>();
    private List<CurrentAccount> orderAccounts = new ArrayList<CurrentAccount>();
    private List<SimpleSavingsAccount> simpleAccounts = new ArrayList<SimpleSavingsAccount>();
    private List<GoldSavingsAccount> goldAccounts = new ArrayList<GoldSavingsAccount>();

    public Bank(String name, int bankid){
        bankName = name;
        bankID = bankID;
    }

    // Client related methods
    public void addClient(Client client){
        clients.add(client);
    }

    public void removeClient(Client client){
        clients.remove(client);
    }

    public String listOfClients(){
        String list = "";
        for (Client c: clients){
            list += c.toString();
        }
        return list;
    }

    // Bank account related methods without DAO
    //      Adding bank accounts
    public void addOrderAccount(CurrentAccount orderAccount){
        orderAccounts.add(orderAccount);
    }

    public void addSimpleTermAccount(SimpleSavingsAccount simpleAccount){
        simpleAccounts.add(simpleAccount);
    }

    public void addGoldTermAccount(GoldSavingsAccount goldAccount){
        goldAccounts.add(goldAccount);
    }

    //      Removing bank accounts
    public void removeOrderAccount(CurrentAccount orderAccount){
        orderAccounts.remove(orderAccount);
    }

    public void removeSimpleTermAccount(SimpleSavingsAccount simpleAccount){
        simpleAccounts.remove(simpleAccount);
    }

    public void removeGoldTermAccount(GoldSavingsAccount goldAccount){
        goldAccounts.remove(goldAccount);
    }
    
    //      List of bank accounts
    public String listOfOrderAccounts(){
        String list = "";
        for (CurrentAccount o : orderAccounts){
            list += o.toString();
        }
        return list;
    }

    public String listOfSimpleTermAccounts(){
        String list = "";
        for (SimpleSavingsAccount s : simpleAccounts){
            list += s.toString();
        }
        return list;
    }

    public String listOfGoldTermAccounts(){
        String list = "";
        for (GoldSavingsAccount g : goldAccounts){
            list += g.toString();
        }
        return list;
    }

    //Getters And Setters
    public GregorianCalendar getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate.setGregorianChange(refDate);
    }
    
    public int getNumberOfClients(){
        return clients.size();
    }

    public int getNumberOfOrderAccounts(){
        return orderAccounts.size();
    }

    public int getNumberOfSimpleAccounts() {
        return simpleAccounts.size();
    }

    public int getNumberOfGoldAccounts() {
	return goldAccounts.size();
    }

    public int getTotalOrderDeposits(){
        return 0;
    }

    public int getTotalSimpleDeposits() {
	return 0;
    }

    public int getTotalGoldDeposits() {
	return 0;
    }

    public String getBankName() {
        return bankName;
    }

    public int getBankID() {
        return bankID;
    }
    
    public void applyTaxes(SavingsAccount saving){
        if (saving instanceof SavingsAccount){
            saving.setInitialAmount(saving.getAmount() + (saving.getAmount()
                        * saving.getInterestRate()));
        }
    }

    public String getClients(SQLConnection con) throws SQLException, ClassNotFoundException{
        DataAcessObject dao = new DataAcessObject();
        List<Client> allClients = dao.GetClients(con);
        String s = "";
        for (Client c : allClients){
            s += "Nome: " + c.getName() + "\n"
                    + "Morada: " + c.getAddress() + "\n"
                    + "***********************\n";
        }
        return s;
    }

    @Override
    public String toString(){
        String banco = "Nome do Banco: " + getBankName() + "\n\n"
                + "Clientes: " + getNumberOfClients() + "\n\n"
                + "Contas Existentes:\n"
                + "     Contas a Ordem: " + getNumberOfOrderAccounts() + "\n"
                + "     Contas a Prazo:\n"
                + "         Simples: " + getNumberOfGoldAccounts() + "\n"
                + "         Gold: " + getNumberOfGoldAccounts() + "\n\n"
                + "Total de Depósitos:\n"
                + "     Contas a Ordem: " + getTotalOrderDeposits() + "\n"
                + "     Contas a Prazo:\n"
                + "         Simples: " + getTotalSimpleDeposits() + "\n"
                + "         Gold: " + getTotalGoldDeposits() ;
        return banco;
    }
}
