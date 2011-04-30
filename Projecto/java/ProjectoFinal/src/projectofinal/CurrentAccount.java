    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jhorgemiguel
 */

public class CurrentAccount extends BankAccount{
    private int clientNIF;
    private int currentAmount;
    private List<Client> holders = new ArrayList<Client>();
    private Set<SavingsAccount> accountsAssociated = new LinkedHashSet<SavingsAccount>();

    public CurrentAccount(Date openDate, int initial, int id) {
        super(openDate, initial, id);
        currentAmount = initial;
    }
    
    
    //Getters & Setters

    // Making account operations

    /**
     * Withdraw money from the account
     * @param amount amount to withdraw
     */
    public void withdrawal(int amount){
        this.currentAmount -= amount;
    }

    //-------------------------------------
    // Account associations

    public void AccountAssociation(SavingsAccount term){
        if (term != null)
        {
            if (term.associatedTo() == null){
                if (term.equals(SavingsAccount.class))
                {
                    accountsAssociated.add(term);
                    term.setAssociatedTo(this);
                }
            }
        }
    }

    @Override
    public int getAccountID() {
        return super.getAccountID();
    }

    @Override
    public double getAmount() {
        double savingsAmounts = 0.0;
        for (SavingsAccount account : this.accountsAssociated)
            savingsAmounts += account.getInitialAmount();
        return currentAmount + savingsAmounts;
    }

    @Override
    public void deposit(double amount) {
        this.currentAmount += amount;
    }

    @Override
    public void close() throws Throwable {
        if (!this.accountsAssociated.isEmpty())
            throw new UnsupportedOperationException(
                    "Esta conta tem contas a prazo associadas.");
        else if (this.currentAmount != 0.0){
            throw new UnsupportedOperationException(
                    "Esta conta tem saldo disponível");
        } else{
            this.finalize();
        }
    }

    @Override
    public String histMovements(ArrayList<String> hist) throws IOException {
        String movements = "";
        try {
            PrintWriter pw = new PrintWriter(new 
                    BufferedWriter(new FileWriter("Historico Mov " + Calendar.getInstance())));
            for (String movement: hist){
                movements += movement;
                pw.println(movement);
            }
            pw.close();
            return movements;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}


