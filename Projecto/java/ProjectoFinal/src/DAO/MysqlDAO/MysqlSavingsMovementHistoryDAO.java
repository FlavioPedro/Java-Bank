/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.MysqlDAO;

import Beans.SavingsMovementHistory;
import DAO.SavingsMovementHistoryDAO;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jhorgemiguel
 */
public class MysqlSavingsMovementHistoryDAO implements SavingsMovementHistoryDAO{

    private Connection conn;
    private PreparedStatement statm;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_SAVINGS_MOVEMENT_HISTORY =
            "That savings account doesn't exist in our database!";
    private static final String EMPTY_SAVINGS_MOVEMENT_HISTORY_TABLE =
            "There are no entries in the savings account table!";

    public MysqlSavingsMovementHistoryDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        statm = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public int insertSavingsMovementHistory(SavingsMovementHistory theSavingsMovementHistoryToInsert)
            throws SQLException{
        // Implement insert Savings Movement History here.
        // Return newly created Savings Movement History number
        // or a -1 on error
        
        PreparedStatement statm = null;
        //The number of the new Savings Movement History to be returned.
        int newSavingsMovementHistoryNumber = -1;

        //The query to be executed.
        query = "INSERT INTO SavingsMovementHistory(savingsAccountID, movementType, amountMoved, movementDate)"
                + "VALUES(?,?,?,?);";
        
        statm = conn.prepareStatement(query); 
        
        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theSavingsMovementHistoryToInsert.getSavingsAccountID());
        statm.setDate(4, (Date) theSavingsMovementHistoryToInsert.getMovementDate());
        statm.setString(2, theSavingsMovementHistoryToInsert.getMovementType());
        statm.setDouble(3, theSavingsMovementHistoryToInsert.getAmountMoved());

        //Executes the query.
        newSavingsMovementHistoryNumber = statm.executeUpdate();
        //closes the statement.
        statm.close();

        //returns the new Savings Movement History number.
        return newSavingsMovementHistoryNumber;
    }

    public SavingsMovementHistory findSavingsMovementHistory(int theSavingsMovementHistoryID)
            throws SQLException, UnknownRegistException{

        // Implement find a Savings Movement History here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        
        //The Savings Movement History to be returned.
        SavingsMovementHistory tempSavingsMovementHistoryBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM SavingsMovementHistory WHERE savingsMovementHistoryID = ?;";
        
        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theSavingsMovementHistoryID);
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_SAVINGS_MOVEMENT_HISTORY);
        }

        //creates a temporary beanObject to store the data.
        tempSavingsMovementHistoryBean = new SavingsMovementHistory();

        //inicializes the beanObject atributes with
        //the values extracted from the database.
        tempSavingsMovementHistoryBean.setSavingsAccountID(rs.getInt("savingsAccountID"));
        tempSavingsMovementHistoryBean.setSavingsMovementHistoryID(rs.getInt("currentMovementHistoryID"));
        tempSavingsMovementHistoryBean.setMovementType(rs.getString("movementType"));
        tempSavingsMovementHistoryBean.setAmountMoved(rs.getDouble("amountMoved"));
        tempSavingsMovementHistoryBean.setMovementDate(rs.getDate("movementDate"));
        
        //closes the statement.
        statm.close();
        
        //returns the found Savings Movement History. 
        return tempSavingsMovementHistoryBean;

    }

    public boolean updateSavingsMovementHistory(SavingsMovementHistory theSavingsMovementHistoryToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the Savings Movement History Data Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;
        
        //The Query to be Executed.
        query = "UPDATE SavingsMovementHistory SET savingsAccountID = ?, movementType = ?, amountMoved = ?, movementDate = ? WHERE savingsMovementHistoryID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.

        statm.setInt(1, theSavingsMovementHistoryToUpdate.getSavingsAccountID());
        statm.setInt(5, theSavingsMovementHistoryToUpdate.getSavingsMovementHistoryID());
        statm.setDate(4, (Date) theSavingsMovementHistoryToUpdate.getMovementDate());
        statm.setDouble(3, theSavingsMovementHistoryToUpdate.getAmountMoved());
        statm.setString(2, theSavingsMovementHistoryToUpdate.getMovementType());
        
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_SAVINGS_MOVEMENT_HISTORY);
        }
        isUpdated = true;

        //closes the statement.
        statm.close();

        return isUpdated;
    }

    public ArrayList<SavingsMovementHistory> findAllSavingsMovementHistory(int bankID)
            throws SQLException, EmptySetException {
        // implement search all Savings Movement History here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<SavingsMovementHistory> allSavingsMovementHistoryBeans = new ArrayList<SavingsMovementHistory>();

        //The Query to be Executed.
        query = "SELECT sh.savingsMovementHistoryID, sh.savingsAccountID, sh.movementType, sh.amountMoved, sh.movementDate"
                + " FROM SavingsMovementHistory sh INNER JOIN savingsAccount s ON sh.savingsAccountID = s.savingsAccountID"
                + " INNER JOIN CurrentAccount c ON s.currentAccountID = c.currentAccountID"
                + " WHERE c.bankID = ?";
        statm.setInt(1, bankID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_SAVINGS_MOVEMENT_HISTORY_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            SavingsMovementHistory tempSavingsMovementHistoryBean = new SavingsMovementHistory();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempSavingsMovementHistoryBean.setSavingsMovementHistoryID(rs.getInt(1));
            tempSavingsMovementHistoryBean.setSavingsAccountID(rs.getInt(2));
            tempSavingsMovementHistoryBean.setMovementType(rs.getString(3));
            tempSavingsMovementHistoryBean.setMovementDate((Date)rs.getDate(5));
            tempSavingsMovementHistoryBean.setAmountMoved(rs.getDouble(4));

            //adds the temporary bean to the ArrayList to return.
            allSavingsMovementHistoryBeans.add(tempSavingsMovementHistoryBean);
        }

        //closes the statement.
        statm.close();

        //returns the ArrayList of Savings Movement History on the current account table
        return allSavingsMovementHistoryBeans;
    }
    
    public ArrayList<SavingsMovementHistory> findAllSimpleSavingsMovementHistory(int bankID)
            throws SQLException, EmptySetException {
        // implement search all Savings Movement History here using the
        // supplied criteria.
        // Return an ArrayList.

        PreparedStatement ps = null;
        
        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<SavingsMovementHistory> allSavingsMovementHistoryBeans = new ArrayList<SavingsMovementHistory>();
        
        //The Query to be Executed.
        query = "SELECT sh.savingsMovementHistoryID, sh.savingsAccountID, sh.movementType, sh.amountMoved, sh.movementDate"
                + " FROM SavingsMovementHistory sh INNER JOIN savingsAccount s ON sh.savingsAccountID = s.savingsAccountID"
                + " INNER JOIN CurrentAccount c ON s.currentAccountID = c.currentAccountID"
                + " WHERE s.savingsAccountTypeID = 1 AND c.bankID = ?";
        
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, bankID);
        try{
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_SAVINGS_MOVEMENT_HISTORY_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            SavingsMovementHistory tempSavingsMovementHistoryBean = new SavingsMovementHistory();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempSavingsMovementHistoryBean.setSavingsMovementHistoryID(rs.getInt(1));
            tempSavingsMovementHistoryBean.setSavingsAccountID(rs.getInt(2));
            tempSavingsMovementHistoryBean.setMovementType(rs.getString(3));
            tempSavingsMovementHistoryBean.setMovementDate((Date)rs.getDate(5));
            tempSavingsMovementHistoryBean.setAmountMoved(rs.getDouble(4));

            //adds the temporary bean to the ArrayList to return.
            allSavingsMovementHistoryBeans.add(tempSavingsMovementHistoryBean);
        }

        //closes the statement.
        statm.close();

        //returns the ArrayList of Savings Movement History on the current account table
        return allSavingsMovementHistoryBeans;
        }catch(SQLException sql){
            Logger.getLogger(query, EMPTY_SAVINGS_MOVEMENT_HISTORY_TABLE);
        }finally{
            return null;
        }
    }
    
    public ArrayList<SavingsMovementHistory> findAllGoldSavingsMovementHistory(int bankID)
            throws SQLException, EmptySetException {
        // implement search all Savings Movement History here using the
        // supplied criteria.
        // Return an ArrayList.

        PreparedStatement ps = null;
        
        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<SavingsMovementHistory> allSavingsMovementHistoryBeans = new ArrayList<SavingsMovementHistory>();

        //The Query to be Executed.
        query = "SELECT sh.savingsMovementHistoryID, sh.savingsAccountID, sh.movementType, sh.amountMoved, sh.movementDate"
                + " FROM SavingsMovementHistory sh INNER JOIN SavingsAccount s ON sh.savingsAccountID = s.savingsAccountID"
                + " INNER JOIN CurrentAccount c ON s.currentAccountID = c.currentAccountID"
                + " WHERE s.savingsAccountTypeID = 2 AND c.bankID = ?";
        
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, bankID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        
        try{
            //Checks if the ResultSet is Empty, if so throws an exception.
            if(rs.wasNull()){
                throw new EmptySetException(EMPTY_SAVINGS_MOVEMENT_HISTORY_TABLE);
            }
            while (rs.next()) {

                //creates a temporary beanObject to store the data.
                SavingsMovementHistory tempSavingsMovementHistoryBean = new SavingsMovementHistory();

                //inicializes the beanObject atributes with
                //the values extracted from the batabase.
                tempSavingsMovementHistoryBean.setSavingsMovementHistoryID(rs.getInt(1));
                tempSavingsMovementHistoryBean.setSavingsAccountID(rs.getInt(2));
                tempSavingsMovementHistoryBean.setMovementType(rs.getString(3));
                tempSavingsMovementHistoryBean.setMovementDate((Date)rs.getDate(5));
                tempSavingsMovementHistoryBean.setAmountMoved(rs.getDouble(4));

                //adds the temporary bean to the ArrayList to return.
                allSavingsMovementHistoryBeans.add(tempSavingsMovementHistoryBean);
            }

            //closes the statement.
            ps.close();

            //returns the ArrayList of Savings Movement History on the current account table
            return allSavingsMovementHistoryBeans;
        }catch(SQLException e){
            Logger.getAnonymousLogger(e.toString());
        }finally{
            return null;
        }
    }
}
