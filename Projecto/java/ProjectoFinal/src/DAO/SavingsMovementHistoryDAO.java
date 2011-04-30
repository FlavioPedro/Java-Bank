/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.SavingsMovementHistory;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author jhorgemiguel
 */
// Interface that all BankDAOs must support
public interface SavingsMovementHistoryDAO {

    public int insertSavingsMovementHistory (SavingsMovementHistory theSavingsMovementHistoryToInsert)
            throws SQLException;
    public SavingsMovementHistory findSavingsMovementHistory(int theSavingsMovementHistoryID)
            throws SQLException, UnknownRegistException;
    public boolean updateSavingsMovementHistory(SavingsMovementHistory theSavingsMovementHistoryToUpdate)
            throws SQLException, UnknownRegistException;
    public ArrayList<SavingsMovementHistory> findAllSavingsMovementHistory(int bankID)
            throws SQLException, EmptySetException;
    public ArrayList<SavingsMovementHistory> findAllSimpleSavingsMovementHistory(int bankID)
            throws SQLException, EmptySetException;
    public ArrayList<SavingsMovementHistory> findAllGoldSavingsMovementHistory(int bankID)
            throws SQLException, EmptySetException;

}
