/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.MysqlDAO;

import Beans.CurrentMovementHistory;
import DAO.CurrentMovementHistoryDAO;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author jhorgemiguel
 */
public class MysqlCurrentMovementHistoryDAO implements CurrentMovementHistoryDAO{

    private Connection conn;
    private PreparedStatement statm;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_CURRENT_ACCOUNT =
            "That current account doesn't exist in our database!";
    private static final String EMPTY_CURRENT_ACCOUNT_TABLE =
            "There are no entries in the current account table!";

    public MysqlCurrentMovementHistoryDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        statm = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public int insertCurrentMovementHistory(CurrentMovementHistory theCurrentMovementHistoryToInsert)
            throws SQLException{
        // Implement insert Current Movement History here.
        // Return newly created Current Movement History number
        // or a -1 on error
        
        //The number of the new Current Movement History to be returned.
        int newCurrentMovementHistoryNumber = -1;

        //The query to be executed.
        query = "INSERT INTO CurrentMovementHistory(currentAccountID, movementType, amountMoved, movementDate)"
                + "VALUES(?,?,?,?);";
        
        statm = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theCurrentMovementHistoryToInsert.getCurrentAccountID());
        statm.setDate(4, (Date) theCurrentMovementHistoryToInsert.getMovementDate());
        statm.setString(2, theCurrentMovementHistoryToInsert.getMovementType());
        statm.setDouble(3, theCurrentMovementHistoryToInsert.getAmountMoved());

        //Executes the query.
        newCurrentMovementHistoryNumber = statm.executeUpdate();
        //closes the statement.
        statm.close();

        //returns the new Current Movement History number.
        return newCurrentMovementHistoryNumber;
    }

    public CurrentMovementHistory findCurrentMovementHistory(int theCurrentMovementHistoryID)
            throws SQLException, UnknownRegistException{

        // Implement find a Current Movement History here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        
        //The Current Movement History to be returned.
        CurrentMovementHistory tempCurrentMovementHistoryBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM CurrentMovementHistory WHERE currentMovementHistoryID = ?;";
        
        statm = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theCurrentMovementHistoryID);
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_CURRENT_ACCOUNT);
        }

        //creates a temporary beanObject to store the data.
        tempCurrentMovementHistoryBean = new CurrentMovementHistory();

        //inicializes the beanObject atributes with
        //the values extracted from the database.
        tempCurrentMovementHistoryBean.setCurrentAccountID(rs.getInt("currentAccountID"));
        tempCurrentMovementHistoryBean.setCurrentMovementHistoryID(rs.getInt("currentMovementHistoryID"));
        tempCurrentMovementHistoryBean.setMovementType(rs.getString("movementType"));
        tempCurrentMovementHistoryBean.setAmountMoved(rs.getDouble("amountMoved"));
        tempCurrentMovementHistoryBean.setMovementDate(rs.getDate("movementDate"));
        
        //closes the statement.
        statm.close();
        
        //returns the found Current Movement History. 
        return tempCurrentMovementHistoryBean;

    }

    public boolean updateCurrentMovementHistory(CurrentMovementHistory theCurrentMovementHistoryToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the Current Movement History Data Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;
        
        //The Query to be Executed.
        query = "UPDATE CurrentMovementHistory SET currentAccountID = ?, movementType = ?, amountMoved = ?, movementDate = ? WHERE currentMovementHistoryID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.
        statm = conn.prepareStatement(query);
        
        statm.setInt(1, theCurrentMovementHistoryToUpdate.getCurrentAccountID());
        statm.setInt(5, theCurrentMovementHistoryToUpdate.getCurrentMovementHistoryID());
        statm.setDate(4, (Date) theCurrentMovementHistoryToUpdate.getMovementDate());
        statm.setDouble(3, theCurrentMovementHistoryToUpdate.getAmountMoved());
        statm.setString(2, theCurrentMovementHistoryToUpdate.getMovementType());
        
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_CURRENT_ACCOUNT);
        }
        isUpdated = true;

        //closes the statement.
        statm.close();

        return isUpdated;
    }

    public ArrayList<CurrentMovementHistory> findAllCurrentMovementHistory(int bankID)
            throws SQLException, EmptySetException {
        // implement search Banks here using the
        // supplied criteria.
        // Return an ArrayList.

        PreparedStatement ps = null;
        
        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<CurrentMovementHistory> allCurrentMovementHistoryBeans = new ArrayList<CurrentMovementHistory>();

        //The Query to be Executed.
        query = "SELECT ch.currentMovementHistoryID, ch.currentAccountID, ch.movementType, ch.amountMoved, ch.movementDate"
                + " FROM CurrentMovementHistory ch INNER JOIN CurrentAccount c ON ch.currentAccountID = c.currentAccountID"
                + " WHERE c.bankID = ?";
        
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, bankID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_CURRENT_ACCOUNT_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            CurrentMovementHistory tempCurrentMovementHistoryBean = new CurrentMovementHistory();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempCurrentMovementHistoryBean.setCurrentMovementHistoryID(rs.getInt(1));
            tempCurrentMovementHistoryBean.setCurrentAccountID(rs.getInt(2));
            tempCurrentMovementHistoryBean.setMovementType(rs.getString(3));
            tempCurrentMovementHistoryBean.setMovementDate((Date)rs.getDate(5));
            tempCurrentMovementHistoryBean.setAmountMoved(rs.getDouble(4));

            //adds the temporary bean to the ArrayList to return.
            allCurrentMovementHistoryBeans.add(tempCurrentMovementHistoryBean);
        }

        //closes the statement.
        statm.close();

        //returns the ArrayList of Current Movement History on the current account table
        return allCurrentMovementHistoryBeans;
    }
    
}
