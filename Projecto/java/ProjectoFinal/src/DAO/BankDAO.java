/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.Bank;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Flávio
 */
// Interface that all BankDAOs must support
public interface BankDAO {

    public int insertBank(Bank theBankToInsert)
            throws SQLException;
    public boolean deleteBank(Bank theBankToDelete)
            throws SQLException;
    public Bank findBankByName(String theBankName)
            throws SQLException, UnknownRegistException;
    public boolean updateBank(Bank theBankToUpdate)
            throws SQLException, UnknownRegistException;
    public ArrayList<Bank> findAllBanks()
            throws SQLException, EmptySetException;

}
