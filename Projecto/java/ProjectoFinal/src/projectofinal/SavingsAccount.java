/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import Beans.BankAccount;
import java.util.Date;

/**
 *
 * @author jhorgemiguel
 */
public abstract class SavingsAccount extends BankAccount{
    private double interestRate;
    private CurrentAccount associatedTo;
    
    public SavingsAccount(Date openDate, double initial, int id, 
            double interestRate){
        super(openDate, initial, id);
        this.interestRate = interestRate;
    }


    // Getters & Setters
    public void setAssociatedTo(CurrentAccount o) {
        this.associatedTo = o;
    }

    public CurrentAccount associatedTo() {
        return associatedTo;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof SavingsAccount) {
            SavingsAccount other = (SavingsAccount) o;
            return this.getAccountID() == other.getAccountID();
        }
        return false;
    }
    
    @Override
    public void deposit(double amount) {
        throw new UnsupportedOperationException(
                "Não é permitido depositar numa conta a prazo");
    }

}
