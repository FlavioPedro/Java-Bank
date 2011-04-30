/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.MysqlDAO;

import Beans.SavingsAccountType;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import DAO.SavingsAccountTypeDAO;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author jhorgemiguel
 */
public class MysqlSavingsAccountTypeDAO implements SavingsAccountTypeDAO{

    private Connection conn;
    private PreparedStatement statm;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_SAVINGS_ACCOUNT_TYPE =
            "That savings account type doesn't exist in our database!";
    private static final String EMPTY_SAVINGS_ACCOUNT_TYPE_TABLE =
            "There are no entries in the savings account type table!";

    public MysqlSavingsAccountTypeDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        statm = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public int insertSavingsAccountType(SavingsAccountType theSavingsAccountTypeToInsert)
            throws SQLException{
        // Implement insert Savings Account Type here.
        // Return newly created Savings Account Type number
        // or a -1 on error
        
        //The number of the new Savings Account Type to be returned.
        int newSavingsAccountTypeNumber = -1;

        //The query to be executed.
        query = "INSERT INTO SavingsAccountType(savingsAccountTypeID, type, interestRate, duration)"
                + "VALUES(?,?,?,?);";
        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theSavingsAccountTypeToInsert.getSavingsAccountTypeID());
        statm.setInt(4, theSavingsAccountTypeToInsert.getDuration());
        statm.setString(2, theSavingsAccountTypeToInsert.getType());
        statm.setDouble(3, theSavingsAccountTypeToInsert.getInterestRate());

        //Executes the query.
        newSavingsAccountTypeNumber = statm.executeUpdate();
        //closes the statement.
        statm.close();

        //returns the new Savings Account Type number.
        return newSavingsAccountTypeNumber;
    }

    public SavingsAccountType findSavingsAccountType(int theSavingsAccountTypeID)
            throws SQLException, UnknownRegistException{

        // Implement find a Savings Account Type here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        
        //The Savings Account Type to be returned.
        SavingsAccountType tempSavingsAccountTypeBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM SavingsAccountType WHERE savingsAccountTypeID = ?;";
        
        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theSavingsAccountTypeID);
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_SAVINGS_ACCOUNT_TYPE);
        }

        //creates a temporary beanObject to store the data.
        tempSavingsAccountTypeBean = new SavingsAccountType();

        //inicializes the beanObject atributes with
        //the values extracted from the database.
        tempSavingsAccountTypeBean.setSavingsAccountTypeID(rs.getInt("savingsAccountTypeID"));
        tempSavingsAccountTypeBean.setType(rs.getString("type"));
        tempSavingsAccountTypeBean.setInterestRate(rs.getDouble("interestRate"));
        tempSavingsAccountTypeBean.setDuration(rs.getInt("duration"));
                
        //closes the statement.
        statm.close();
        
        //returns the found Savings Account Type. 
        return tempSavingsAccountTypeBean;

    }

    public boolean updateSavingsAccountType(SavingsAccountType theSavingsAccountTypeToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the Savings Account Type Data Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;
        
        //The Query to be Executed.
        query = "UPDATE SavingsAccountType SET savingsAccountTypeID = ?, type = ?, interestRate = ?, duration = ? WHERE savingsAccountTypeID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.

        statm.setInt(1, theSavingsAccountTypeToUpdate.getSavingsAccountTypeID());
        statm.setString(2, theSavingsAccountTypeToUpdate.getType());
        statm.setDouble(3, theSavingsAccountTypeToUpdate.getInterestRate());
        statm.setInt(4, theSavingsAccountTypeToUpdate.getDuration());
        
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_SAVINGS_ACCOUNT_TYPE);
        }
        isUpdated = true;

        //closes the statement.
        statm.close();

        return isUpdated;
    }

    public ArrayList<SavingsAccountType> findAllSavingsAccountType()
            throws SQLException, EmptySetException {
        // implement search Savings Account Type here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<SavingsAccountType> allSavingsAccountTypeBeans = new ArrayList<SavingsAccountType>();

        //The Query to be Executed.
        query = "SELECT * FROM SavingsAccountType;";
                
        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_SAVINGS_ACCOUNT_TYPE_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            SavingsAccountType tempSavingsAccountTypeBean = new SavingsAccountType();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempSavingsAccountTypeBean.setSavingsAccountTypeID(rs.getInt(1));
            tempSavingsAccountTypeBean.setType(rs.getString(2));
            tempSavingsAccountTypeBean.setInterestRate(rs.getDouble(3));
            tempSavingsAccountTypeBean.setDuration(rs.getInt(5));
            
            //adds the temporary bean to the ArrayList to return.
            allSavingsAccountTypeBeans.add(tempSavingsAccountTypeBean);
        }

        //closes the statement.
        statm.close();

        //returns the ArrayList of Savings Account Type on the current account table
        return allSavingsAccountTypeBeans;
    }    
}
