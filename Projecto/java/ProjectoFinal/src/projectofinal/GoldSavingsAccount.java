/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import java.io.*;
import java.util.*;
import java.util.logging.*;



/**
 *
 * @author jhorgemiguel
 */
public class GoldSavingsAccount extends SavingsAccount{
    public static final double MIN_INITIAL_AMOUNT = 25000.0;
    public static final double MIN_CURRENT_ACCOUNT_AMOUNT = 100000.0;
    
    
        
    
    /**
     * Create a new Gold Savings Account
     * @param day, month, year -> date
     * @param initialAmount
     * @param id
     * @throws Exception throws an exception if the minimum initial amount is
     * is not enough to open an account of this type.
     */
    public GoldSavingsAccount(Date openDate, double initialAmount, 
            int id, double interestRate) throws Exception {
            if (initialAmount < MIN_INITIAL_AMOUNT)
            throw new Exception("O montante mínimo para a abertura da conta a "
                    + "prazo Gold é de " + MIN_INITIAL_AMOUNT +
                    "€");
            else if (associatedTo().getAmount() < MIN_CURRENT_ACCOUNT_AMOUNT)
            throw new Exception("Tem de ter na conta a ordem, um mínimo de "
                    + MIN_CURRENT_ACCOUNT_AMOUNT + "€ para abrir a conta "
                    + "pretendida.");
            else{
                setOpenDate(openDate);
                setInitialAmount(initialAmount);
                setAccountID(id);
                setInterestRate(0.09);
                /*GoldSavingsAccount a = new GoldSavingsAccount(openDate, 
                        initialAmount, id, 0.09);*/
            }
        }
        /*
            else{
                setOpenDate(openAccountDate);
                setInitialAmount(initialAmount);
                setAccountID(id);
                setInterestRate(0.09);
        }*/

   
    @Override
    public double getAmount() {
        return getInitialAmount();
    }

    @Override
    public void close() throws Throwable {
        associatedTo().deposit(getInitialAmount() + (getInitialAmount() * getInterestRate()));
        this.finalize();
    }

    public String histMovements(ArrayList<String> hist) throws IOException {
        String movements = "";
        try {
            PrintWriter pw = new PrintWriter(new 
                    BufferedWriter(new FileWriter("Historico Mov " + Calendar.getInstance())));
            for (String movement: hist){
                if (hist.contains(""))
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
