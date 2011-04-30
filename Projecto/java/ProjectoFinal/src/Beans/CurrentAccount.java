/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MysqlDAO.MysqlCurrentAccountDAO;
import DAO.MysqlDAO.MysqlCurrentMovementHistoryDAO;
import DAO.MysqlDAO.MysqlSavingsAccountDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Flávio
 */
public class CurrentAccount extends BankAccount{

    private ArrayList<SavingsAccount> allSavingsAccount;
    MysqlCurrentAccountDAO current;
    MysqlSavingsAccountDAO savings;
    MysqlCurrentMovementHistoryDAO currentHistory;
    //properties
    private int currentAccountID;
    private int bankID;
    private Date openDate;
    private double currentAmount;
    private double initialAmount;

    //obj builders
    public CurrentAccount(){}
    
    public CurrentAccount(int theCurrentAccountID, int theBankID, 
            Date theOpenDate, double theCurrentAmount, double theInitialAmount){
        setCurrentAccountID(theCurrentAccountID);
        setBankID(theBankID);
        setOpenDate(theOpenDate);
        setCurrentAmount(theCurrentAmount);
        setInitialAmount(theInitialAmount);
        try {
            savings = new MysqlSavingsAccountDAO();
            this.allSavingsAccount = savings.findAllSavingsAccountByCurrent(this);
        } catch (EmptySetException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return the initialAmount
     */
    public double getInitialAmount() {
        return initialAmount;
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
    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    
    public ArrayList<SavingsAccount> getSavingsAccounts(){
        return allSavingsAccount;
    }
    
    public void setSavingsAccount(ArrayList<SavingsAccount> theSavings){
        allSavingsAccount = theSavings;
    }
    
    public void addSavingsAccount(SavingsAccount savings){
        allSavingsAccount.add(savings);
    }
    
    public void removeSavingsAccount(SavingsAccount savings){
        allSavingsAccount.remove(savings);
    }
    
    public void close(){
        if (!this.allSavingsAccount.isEmpty())
            JOptionPane.showMessageDialog(null, "There are savings accounts associated to this current account.", "Removing Current Account:", JOptionPane.ERROR_MESSAGE);
        else if (this.getCurrentAmount() != 0)
                JOptionPane.showMessageDialog(null, "There's amount on this current account.", "Removing Current Account:", JOptionPane.ERROR_MESSAGE);
        else{
            try {
                    current = new MysqlCurrentAccountDAO();
                    current.deleteCurrentAccount(this);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);    
                } catch (SQLException ex) {
                    Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    //Output String generator
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

    @Override
    public double getAmount() {
        double currentAmount = this.getCurrentAmount();
        double savingsInitialAmount = 0;
        for (SavingsAccount s : this.allSavingsAccount){
            savingsInitialAmount += s.getInitialAmount();
        }
        return currentAmount + savingsInitialAmount;
    }

    @Override
    public void deposit(double amount) {
        this.setCurrentAmount(this.getCurrentAmount() + amount);
        try {
            currentHistory = new MysqlCurrentMovementHistoryDAO();
            CurrentMovementHistory history = new CurrentMovementHistory
                (currentAccountID, "Deposito", amount, openDate);
            currentHistory.insertCurrentMovementHistory(history);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void withdrawal(double amount){
        this.setCurrentAmount(this.getCurrentAmount() - amount);
        try {
            currentHistory = new MysqlCurrentMovementHistoryDAO();
            CurrentMovementHistory history = new CurrentMovementHistory
                (currentAccountID, "Levantamento", amount, openDate);
            currentHistory.insertCurrentMovementHistory(history);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
