/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.MysqlDAO;

import Beans.CurrentAccount;
import Beans.SavingsAccount;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import DAO.SavingsAccountDAO;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author jhorgemiguel
 */
public class MysqlSavingsAccountDAO implements SavingsAccountDAO{
    
    private Connection conn;
    private PreparedStatement ps;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_SAVINGS_ACCOUNT =
            "That savings account doesn't exist in our database!";
    private static final String EMPTY_SAVINGS_ACCOUNT_TABLE =
            "There are no entries in the savings account table!";

    public MysqlSavingsAccountDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        ps = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public boolean insertSavingsAccount(SavingsAccount theSavingsAccountToInsert)
            throws SQLException{
        // Implement insert Savings Account here.
        // Return newly created Savings Account number
        // or a -1 on error
        
        //The number of the new current account to be returned.
        int newSavingsAccountNumber = -1;

        //The query to be executed.
        query = "INSERT INTO SavingsAccount(savingsAccountID, currentAccountID, savingsAccountTypeID, openDate, initialAmount)"
                + "VALUES(?,?,?,?,?);";
        
        
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theSavingsAccountToInsert.getSavingsAccountID());
        ps.setInt(2, theSavingsAccountToInsert.getCurrentAccountID());
        ps.setInt(3, theSavingsAccountToInsert.getSavingsAccountTypeID());
        ps.setDate(4, (Date) theSavingsAccountToInsert.getOpenDate());
        ps.setDouble(5, theSavingsAccountToInsert.getInitialAmount());

        //Executes the query.
        newSavingsAccountNumber = ps.executeUpdate();
        //closes the statement.
        ps.close();

        //returns the new current account number.
        if (newSavingsAccountNumber != -1)
            return true;
        else
            return false;
        
    }

    public boolean deleteSavingsAccount(SavingsAccount theSavingsAccountToDelete)
            throws SQLException{
        // Implement delete Savings Account here

        // Return true on success, false on failure
        boolean isDeleted = false;
        
        //The query to be executed.
        query = "DELETE FROM SavingsAccount WHERE savingsAccountID = ?;";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theSavingsAccountToDelete.getSavingsAccountID());

        //Executes the query.
        ps.executeUpdate();
        isDeleted = true;
        //closes the statement.
        ps.close();

        return isDeleted;
    }

    public SavingsAccount findSavingsAccountByID(int theSavingsAccountID)
            throws SQLException, UnknownRegistException{

        // Implement find a Savings Account here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        
        //The Current Account to be returned.
        SavingsAccount tempSavingsAccounttBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM SavingsAccount WHERE savingsAccountID = ?;";
            
        //Prepares the statement replacing ? with the values passed by argument.
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, theSavingsAccountID);
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_SAVINGS_ACCOUNT);
        }

        //creates a temporary beanObject to store the data.
        tempSavingsAccounttBean = new SavingsAccount();

        //inicializes the beanObject atributes with
        //the values extracted from the database.
        tempSavingsAccounttBean.setSavingsAccountID(rs.getInt("savingsAccountID"));
        tempSavingsAccounttBean.setCurrentAccountID(rs.getInt("currentAccountID"));
        tempSavingsAccounttBean.setSavingsAccountTypeID(rs.getInt("savingsAccountTypeID"));
        tempSavingsAccounttBean.setOpenDate(rs.getDate("openDate"));
        tempSavingsAccounttBean.setInitialAmount(rs.getDouble("initialAmount"));
        
        //closes the statement.
        ps.close();
        
        //returns the found Savings Account. 
        return tempSavingsAccounttBean;

    }

    public boolean updateSavingsAccount(SavingsAccount theSavingsAccountToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the Savings Account Data Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;
        
        //The Query to be Executed.
        query = "UPDATE SavingsAccount SET savingsAccountID = ?, currentAccountID = ?, savingsAccountTypeID = ?, openDate = ?, initialAmount = ? WHERE savingsAccountID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, theSavingsAccountToUpdate.getSavingsAccountID());
        ps.setInt(2, theSavingsAccountToUpdate.getCurrentAccountID());
        ps.setInt(3, theSavingsAccountToUpdate.getSavingsAccountTypeID());
        ps.setDate(4, (Date) theSavingsAccountToUpdate.getOpenDate());
        ps.setDouble(5, theSavingsAccountToUpdate.getInitialAmount());
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_SAVINGS_ACCOUNT);
        }
        isUpdated = true;

        //closes the statement.
        ps.close();

        return isUpdated;
    }

    public ArrayList<SavingsAccount> findAllSavingsAccount(int bankID)
            throws SQLException, EmptySetException {
        // implement search Savings Account here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<SavingsAccount> allSavingsAccountBeans = new ArrayList<SavingsAccount>();

        //The Query to be Executed.
        
        PreparedStatement ps;
        
        query = "SELECT s.SavingsAccountID, s.currentAccountID, s.savingsAccountTypeID, s.openDate, s.initialAmount"
                + " FROM SavingsAccount s INNER JOIN CurrentAccount c ON s.currentAccountID = c.currentAccountID"
                + " WHERE c.bankID = ?";
        
        ps = conn.prepareStatement(query);
                
        ps.setInt(1, bankID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_SAVINGS_ACCOUNT_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            SavingsAccount tempSavingsAccountBean = new SavingsAccount();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempSavingsAccountBean.setSavingsAccountID(rs.getInt(1));
            tempSavingsAccountBean.setCurrentAccountID(rs.getInt(2));
            tempSavingsAccountBean.setSavingsAccountTypeID(rs.getInt(3));
            tempSavingsAccountBean.setOpenDate((Date)rs.getDate(4));
            tempSavingsAccountBean.setInitialAmount(rs.getInt(5));

            //adds the temporary bean to the ArrayList to return.
            allSavingsAccountBeans.add(tempSavingsAccountBean);
        }

        //closes the statement.
        ps.close();

        //returns the ArrayList of savings accounts on the savings account table
        return allSavingsAccountBeans;
    }
    
    public ArrayList<SavingsAccount> findAllSavingsAccountByCurrent
            (CurrentAccount current) throws SQLException, EmptySetException {
        // implement search Savings Account here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<SavingsAccount> allSavingsAccountBeans = new ArrayList<SavingsAccount>();

        //The Query to be Executed.
        
        query = "SELECT s.SavingsAccountID, s.currentAccountID, s.savingsAccountTypeID, s.openDate, s.initialAmount"
                + " FROM SavingsAccount s INNER JOIN CurrentAccount c ON s.currentAccountID = c.currentAccountID"
                + " WHERE s.currentAccountID = ?";
        
        ps = conn.prepareStatement(query);
                
        ps.setInt(1, current.getCurrentAccountID());
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_SAVINGS_ACCOUNT_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            SavingsAccount tempSavingsAccountBean = new SavingsAccount();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempSavingsAccountBean.setSavingsAccountID(rs.getInt(1));
            tempSavingsAccountBean.setCurrentAccountID(rs.getInt(2));
            tempSavingsAccountBean.setSavingsAccountTypeID(rs.getInt(3));
            tempSavingsAccountBean.setOpenDate((Date)rs.getDate(4));
            tempSavingsAccountBean.setInitialAmount(rs.getInt(5));

            //adds the temporary bean to the ArrayList to return.
            allSavingsAccountBeans.add(tempSavingsAccountBean);
        }

        //closes the statement.
        ps.close();

        //returns the ArrayList of savings accounts on the savings account table
        return allSavingsAccountBeans;
    }
}
