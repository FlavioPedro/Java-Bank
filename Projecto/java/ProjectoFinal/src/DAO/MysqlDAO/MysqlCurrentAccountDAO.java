/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.MysqlDAO;

import Beans.CurrentAccount;
import DAO.CurrentAccountDAO;
import java.sql.*;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.util.ArrayList;
import java.util.logging.Logger;

        
/**
 *
 * @author jhorgemiguel
 */
public class MysqlCurrentAccountDAO implements CurrentAccountDAO{

    private Connection conn;
    private PreparedStatement ps;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_CURRENT_ACCOUNT =
            "That current account doesn't exist in our database!";
    private static final String EMPTY_CURRENT_ACCOUNT_TABLE =
            "There are no entries in the current account table!";

    public MysqlCurrentAccountDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        ps = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public boolean insertCurrentAccount(CurrentAccount theCurrentAccountToInsert)
            throws SQLException{
        // Implement insert current account here.
        // Return newly created current account number
        // or a -1 on error
        
        //The number of the new current account to be returned.
        int newCurrentAccountNumber = -1;

        //The query to be executed.
        query = "INSERT INTO CurrentAccount(currentAccountID, bankID, openDate, currentAmount, initialAmount)"
                + "VALUES(?,?,?,?,?);";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theCurrentAccountToInsert.getCurrentAccountID());
        ps.setInt(2, theCurrentAccountToInsert.getBankID());
        ps.setDate(3, (Date) theCurrentAccountToInsert.getOpenDate());
        ps.setDouble(4, theCurrentAccountToInsert.getCurrentAmount());
        ps.setDouble(5, theCurrentAccountToInsert.getInitialAmount());

        //Executes the query.
        newCurrentAccountNumber = ps.executeUpdate();
        //closes the statement.
        ps.close();

        //returns the new current account number.
        if (newCurrentAccountNumber != -1)
            return true;
        else
            return false;
    }

    public boolean deleteCurrentAccount(CurrentAccount theCurrentAccountToDelete)
            throws SQLException{
        // Implement delete Current Account here

        // Return true on success, false on failure
        boolean isDeleted = false;
        
        //The query to be executed.
        query = "DELETE FROM CurrentAccount WHERE currentAccountID = ?;";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theCurrentAccountToDelete.getCurrentAccountID());

        //Executes the query.
        ps.executeUpdate();
        isDeleted = true;
        //closes the statement.
        ps.close();

        return isDeleted;
    }

    public CurrentAccount findCurrentAccountByID(int theCurrentAccountID)
            throws SQLException, UnknownRegistException{

        // Implement find a Current Account here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        
        //The Current Account to be returned.
        CurrentAccount tempCurrentAccountBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM CurrentAccount WHERE currentAccountID = ?;";
        
        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theCurrentAccountID);
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_CURRENT_ACCOUNT);
        }
        try{
        //creates a temporary beanObject to store the data.
        tempCurrentAccountBean = new CurrentAccount();

        //inicializes the beanObject atributes with
        //the values extracted from the database.
        tempCurrentAccountBean.setCurrentAccountID(rs.getInt("currentAccountID"));
        tempCurrentAccountBean.setBankID(rs.getInt("bankID"));
        tempCurrentAccountBean.setOpenDate(rs.getDate("openDate"));
        tempCurrentAccountBean.setCurrentAmount(rs.getInt("currentAmount"));
        tempCurrentAccountBean.setInitialAmount(rs.getInt("initialAmount"));
        
        //closes the statement.
        ps.close();
        
        //returns the found Current Account. 
        return tempCurrentAccountBean;
        } catch (SQLException sql){
            Logger.getAnonymousLogger(sql.toString());
        } finally {
            return null;
        }
        
        
    }

    public boolean updateCurrentAccount(CurrentAccount theCurrentAccountToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the CurrentAccountData Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;
        
        //The Query to be Executed.
        query = "UPDATE CurrentAccount SET currentAccountID = ?, bankID = ?, openDate = ?, currentAmount = ?, initialAmount = ? WHERE currentAccountID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.

        ps = conn.prepareStatement(query);
        
        ps.setInt(1, theCurrentAccountToUpdate.getCurrentAccountID());
        ps.setInt(2, theCurrentAccountToUpdate.getBankID());
        ps.setDate(3, (Date) theCurrentAccountToUpdate.getOpenDate());
        ps.setDouble(4, theCurrentAccountToUpdate.getCurrentAmount());
        ps.setDouble(5, theCurrentAccountToUpdate.getInitialAmount());
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_CURRENT_ACCOUNT);
        }
        isUpdated = true;

        //closes the statement.
        ps.close();

        return isUpdated;
    }

    public ArrayList<CurrentAccount> findAllCurrentAccounts(int ID)
            throws SQLException, EmptySetException {
        // implement search current accounts here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<CurrentAccount> allCurrentAccountBeans = new ArrayList<CurrentAccount>();
        
        PreparedStatement ps;
        
        //The Query to be Executed.
        query = "SELECT * FROM CurrentAccount WHERE bankID = ?";
        
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, ID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        
        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_CURRENT_ACCOUNT_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            CurrentAccount tempCurrentAccountBean = new CurrentAccount();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempCurrentAccountBean.setCurrentAccountID(rs.getInt(1));
            tempCurrentAccountBean.setBankID(rs.getInt(2));
            tempCurrentAccountBean.setOpenDate((Date)rs.getDate(3));
            tempCurrentAccountBean.setCurrentAmount(rs.getInt(4));
            tempCurrentAccountBean.setInitialAmount(rs.getInt(5));

            //adds the temporary bean to the ArrayList to return.
            allCurrentAccountBeans.add(tempCurrentAccountBean);
        }

        //closes the statement.
        ps.close();

        //returns the ArrayList of current accounts on the current account table
        return allCurrentAccountBeans;
    }
}
