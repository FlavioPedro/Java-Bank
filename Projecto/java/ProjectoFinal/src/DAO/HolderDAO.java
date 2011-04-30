/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.Holder;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author jhorgemiguel
 */
// Interface that all BankDAOs must support
public interface HolderDAO {

    public boolean insertHolder(Holder theHolderToInsert) throws SQLException;
    public boolean deleteHolder(Holder theHolderToDelete) throws SQLException;
    public Holder findHolder(int theHolderID) throws SQLException, UnknownRegistException;
    public boolean updateHolder(Holder theHolderToUpdate) throws SQLException, UnknownRegistException;
    public ArrayList<Holder> findAllHolder(int bankID) throws SQLException, EmptySetException;

}
