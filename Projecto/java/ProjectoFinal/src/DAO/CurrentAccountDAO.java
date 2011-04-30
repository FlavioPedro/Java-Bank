/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.CurrentAccount;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Flávio
 */
// Interface that all BankDAOs must support
public interface CurrentAccountDAO {

    public boolean insertCurrentAccount(CurrentAccount theCurrentAccountToInsert) throws SQLException;
    public boolean deleteCurrentAccount(CurrentAccount theCurrentAccountToDelete) throws SQLException;
    public CurrentAccount findCurrentAccountByID(int theCurrentAccountID) throws SQLException, UnknownRegistException;
    public boolean updateCurrentAccount(CurrentAccount theCurrentAccountToUpdate) throws SQLException, UnknownRegistException;
    public ArrayList<CurrentAccount> findAllCurrentAccounts(int ID) throws SQLException, EmptySetException;

}
