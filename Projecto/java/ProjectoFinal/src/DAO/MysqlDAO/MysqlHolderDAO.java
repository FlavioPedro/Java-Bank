/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.MysqlDAO;

import Beans.Holder;
import DAO.HolderDAO;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author jhorgemiguel
 */
public class MysqlHolderDAO implements HolderDAO{

    private Connection conn;
    private PreparedStatement statm;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_HOLDER =
            "That holder doesn't exist in our database!";
    private static final String EMPTY_HOLDER_TABLE =
            "There are no entries in the holder table!";

    public MysqlHolderDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        statm = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public boolean insertHolder(Holder theHolderToInsert)
            throws SQLException{
        // Implement insert holder here.
        // Return newly created holder number
        // or a -1 on error

        //The number of the new holder to be returned.
        int newHolderNumber = -1;

        //The query to be executed.
        query = "INSERT INTO Holder"
                + "(holderID, clientID, currentAccountID, associationDate)"
                + "VALUES(?,?,?,?);";

        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theHolderToInsert.getHolderID());
        statm.setInt(2, theHolderToInsert.getClientID());
        statm.setInt(3, theHolderToInsert.getCurrentAccountID());
        statm.setDate(4, (Date) theHolderToInsert.getAssociationDate());
        
         //Executes the query.
        newHolderNumber = statm.executeUpdate();
        //closes the statement.
        statm.close();

        //returns the new bank number.
        if (newHolderNumber != -1)
            return true;
        else
            return false;
    }

    public boolean deleteHolder(Holder theHolderToDelete)
            throws SQLException{
        // Implement delete holder here

        // Return true on success, false on failure
        boolean isDeleted = false;

        //The query to be executed.
        query = "DELETE FROM Holder WHERE holderID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theHolderToDelete.getHolderID());

        //Executes the query.
        statm.executeUpdate();
        isDeleted = true;
        //closes the statement.
        statm.close();

        return isDeleted;
    }

    public Holder findHolder(int theHolderID)
            throws SQLException, UnknownRegistException{

        // Implement find a holder here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found

        //The holder to be returned.
        Holder tempHolderBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM Holder WHERE holderID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theHolderID);
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_HOLDER);
        }

        //creates a temporary beanObject to store the data.
        tempHolderBean = new Holder();

        //inicializes the beanObject atributes with
        //the values extracted from the batabase.
        tempHolderBean.setHolderID(rs.getInt("holderID"));
        tempHolderBean.setClientID(rs.getInt("clientID"));
        tempHolderBean.setCurrentAccountID(rs.getInt("currentAccountID"));
        tempHolderBean.setAssociationDate((Date)rs.getDate("associationDate"));
        

        //closes the statement.
        statm.close();

        //returns the found holder.
        return tempHolderBean;

    }

    public boolean updateHolder(Holder theHolderToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the Holder Data Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;

        //The Query to be Executed.
        query = "UPDATE Holder SET clientID = ?, currentAccountID = ?,"
               + " associationDate = ? WHERE holderID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(4, theHolderToUpdate.getHolderID());
        statm.setInt(1, theHolderToUpdate.getClientID());
        statm.setInt(2, theHolderToUpdate.getCurrentAccountID());
        statm.setDate(3, (Date) theHolderToUpdate.getAssociationDate());

        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_HOLDER);
        }
        isUpdated = true;

        //closes the statement.
        statm.close();

        return isUpdated;
    }

    public ArrayList<Holder> findAllHolder(int bankID)
            throws SQLException, EmptySetException {
        // implement search haolder here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<Holder> allHolderBeans = new ArrayList<Holder>();

        //The Query to be Executed.
        query = "SELECT h.holderID, h.clientID, h.currentAccountID, h.associationDate"
                + " FROM Holder h INNER JOIN Client c ON h.clientID = c.clientID"
                + " WHERE c.bankID = ?;";
        statm.setInt(1, bankID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.first() == false){
            throw new EmptySetException(NO_SUCH_HOLDER);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            Holder tempHolderBean = new Holder();

            //inicializes the beanObject atributes with
            //the values extracted from the database.
            tempHolderBean.setHolderID(rs.getInt(1));
            tempHolderBean.setClientID(rs.getInt(2));
            tempHolderBean.setCurrentAccountID(rs.getInt(3));
            tempHolderBean.setAssociationDate((Date)rs.getDate(4));
            
            //adds the temporary bean to the ArrayList to return.
            allHolderBeans.add(tempHolderBean);
        }

        //closes the statement.
        statm.close();

        //returns the ArrayList of holders on the holder table
        return allHolderBeans;
    }
}
