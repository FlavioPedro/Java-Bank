/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.CurrentMovementHistory;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Flávio
 */
// Interface that all BankDAOs must support
public interface CurrentMovementHistoryDAO {

    public int insertCurrentMovementHistory(CurrentMovementHistory theCurrentMovementHistoryToInsert) throws SQLException;
    public CurrentMovementHistory findCurrentMovementHistory(int theCurrentMovementHistoryID) throws SQLException, UnknownRegistException;
    public boolean updateCurrentMovementHistory(CurrentMovementHistory theCurrentMovementHistoryToUpdate) throws SQLException, UnknownRegistException;
    public ArrayList<CurrentMovementHistory> findAllCurrentMovementHistory(int bankID) throws SQLException, EmptySetException;

}
