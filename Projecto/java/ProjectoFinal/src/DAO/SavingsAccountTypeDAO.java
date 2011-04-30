/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.SavingsAccountType;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author jhorgemiguel
 */
// Interface that all BankDAOs must support
public interface SavingsAccountTypeDAO {

    public int insertSavingsAccountType(SavingsAccountType theSavingsAccountTypeToInsert)
            throws SQLException;
    public SavingsAccountType findSavingsAccountType(int theSavingsAccountTypeID)
            throws SQLException, UnknownRegistException;
    public boolean updateSavingsAccountType(SavingsAccountType theSavingsAccountTypeToUpdate)
            throws SQLException, UnknownRegistException;
    public ArrayList<SavingsAccountType> findAllSavingsAccountType() 
            throws SQLException, EmptySetException;

}
