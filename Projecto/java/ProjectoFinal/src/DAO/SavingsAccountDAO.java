/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.SavingsAccount;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author jhorgemiguel
 */
// Interface that all BankDAOs must support
public interface SavingsAccountDAO {

    public boolean insertSavingsAccount(SavingsAccount theSavingsAccountToInsert)
            throws SQLException;
    public boolean deleteSavingsAccount(SavingsAccount theSavingsAccountToDelete)
            throws SQLException;
    public SavingsAccount findSavingsAccountByID(int theSavingsAccountID)
            throws SQLException, UnknownRegistException;
    public boolean updateSavingsAccount(SavingsAccount theSavingsAccountToUpdate)
            throws SQLException, UnknownRegistException;
    public ArrayList<SavingsAccount> findAllSavingsAccount(int bankID)
            throws SQLException, EmptySetException;

}
