/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jhorgemiguel
 */
public class SimpleSavingsAccount extends SavingsAccount{
    private double amount;

    public SimpleSavingsAccount(Date openDate, double initial, int id){
        super(openDate, initial, id, 0.04);
        amount = initial;
    }

    // Make account survey
    public void withdrawal(int amount) throws Throwable{
        if (this.amount - amount == 0){
            this.amount -= amount;
            close();
        } else {
            throw new UnsupportedOperationException(
                    "Não foi possivel fazer o levantamento. Tem de levantar o "
                    + "dinheiro todo.");
        }
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void close() throws Throwable {
        if (amount != 0){
            associatedTo().deposit(amount + (amount * getInterestRate()));
        }
        this.finalize();
    }

    @Override
    public String histMovements(ArrayList<String> hist) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}