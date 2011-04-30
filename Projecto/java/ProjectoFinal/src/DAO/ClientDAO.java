/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;
import Beans.Client;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Flávio
 */
// Interface that all ClientDAOs must support
public interface ClientDAO {

    public boolean insertClient(Client theClientToInsert)
            throws SQLException;
    public boolean deleteClient(int theNIFOfClientToDelete)
            throws SQLException;
    public Client findClient(int theClientNif)
            throws SQLException, UnknownRegistException;
    public boolean updateClient(Client theClientToUpdate)
             throws SQLException, UnknownRegistException;
    public ArrayList<Client> findAllClients(int ID)
            throws SQLException, EmptySetException;

}