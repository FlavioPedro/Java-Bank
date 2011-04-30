/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.MysqlDAO;

// MysqlBankDAO implementation of the
// BankDAO interface. This class can contain all
// Mysql specific code and SQL statements.
// The client is thus shielded from knowing
// these implementation details.

import DAO.BankDAO;
import java.sql.*;
import Beans.Bank;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Flávio
 */
public class MysqlBankDAO implements BankDAO {

    private Connection conn;
    private PreparedStatement statm;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_BANK =
            "That bank doesn't exist in our database!";
    private static final String EMPTY_BANK_TABLE =
            "There are no entries in the bank table!";

    public MysqlBankDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        statm = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public int insertBank(Bank theBankToInsert)
            throws SQLException{
        // Implement insert bank here.
        // Return newly created bank number
        // or a -1 on error
                
        //The number of the new bank to be returned.
        int newBankNumber = -1;
        PreparedStatement ps = null;
        rs = null;  
        
        //The query to be executed.
        query = "INSERT INTO Bank(bankName, refDate)"
                + "VALUES(?,?)";
        
        ps = conn.prepareStatement(query); 
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setString(1, theBankToInsert.getBankName());
        ps.setDate(2, new java.sql.Date(theBankToInsert.getRefDate().getTime()));

        //Executes the query.
        newBankNumber = ps.executeUpdate();
        //closes the statement.
        ps.close();

        //returns the new bank number.
        return newBankNumber;
    }

    public boolean deleteBank(Bank theBankToDelete)
            throws SQLException{
        // Implement delete bank here

        // Return true on success, false on failure
        boolean isDeleted = false;
        
        //The query to be executed.
        query = "DELETE FROM Bank WHERE bankID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.
        statm.setInt(1, theBankToDelete.getBankID());

        //Executes the query.
        statm.executeUpdate(query);
        isDeleted = true;
        //closes the statement.
        statm.close();

        return isDeleted;
    }

    public Bank findBankByName(String theBankName)
            throws SQLException, UnknownRegistException{

        // Implement find a bank here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        
        //The bank to be returned.
        Bank tempBankBean = null;
        PreparedStatement ps = null;
        rs = null;
        
        //The Query to be Executed.
        query = "SELECT * FROM Bank WHERE bankName = ?";
        
        //Prepares the statement replacing ? with the values passed by argument.
        //try{
        
        ps = conn.prepareStatement(query);
        
        ps.setString(1, theBankName);
        
       //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();

            //Checks if the RedultSet is Empty, if so throws an exception.
            if (rs.wasNull()) {
                throw new UnknownRegistException(NO_SUCH_BANK);
            }
            else{
                while (rs.next()){
            
            //creates a temporary beanObject to store the data.
                    tempBankBean = new Bank();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
                    tempBankBean.setBankID(rs.getInt("bankID"));
                    tempBankBean.setBankName(rs.getString("bankName"));
                    tempBankBean.setRefDate((Date)rs.getDate("refDate"));
            }
            //closes the statement.
            statm.close();

            //returns the found bank. 
            return tempBankBean;    
        /*}catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro na ligação com a base de dados.", "Erro:", JOptionPane.ERROR_MESSAGE, null);
            return null;
        }*/
        }
    }

    public boolean updateBank(Bank theBankToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the BankData Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;
        
        //The Query to be Executed.
        query = "UPDATE Bank SET bankName = ?, refDate = ? WHERE bankID = ?;";

        //Prepares the statement replacing ? with the values passed by argument.

        statm.setString(1, theBankToUpdate.getBankName());
        statm.setDate(2, (Date) theBankToUpdate.getRefDate());
        statm.setInt(3, theBankToUpdate.getBankID());

        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if (rs.wasNull()) {
            throw new UnknownRegistException(NO_SUCH_BANK);
        }
        isUpdated = true;

        //closes the statement.
        statm.close();

        return isUpdated;
    }

    public ArrayList<Bank> findAllBanks()
            throws SQLException, EmptySetException {
        // implement search Banks here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<Bank> allBankBeans = new ArrayList<Bank>();

        //The Query to be Executed.
        query = "SELECT * FROM Bank;";

        //Executes the Query and gets the result into a ResultSet.
        rs = statm.executeQuery(query);

        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(EMPTY_BANK_TABLE);
        }
        while (rs.next()) {

            //creates a temporary beanObject to store the data.
            Bank tempBankBean = new Bank();

            //inicializes the beanObject atributes with
            //the values extracted from the batabase.
            tempBankBean.setBankID(rs.getInt(1));
            tempBankBean.setBankName(rs.getString(2));
            tempBankBean.setRefDate((Date)rs.getDate(3));

            //adds the temporary bean to the ArrayList to return.
            allBankBeans.add(tempBankBean);
        }

        //closes the statement.
        statm.close();
        rs.close();

        //returns the ArrayList of banks on the bank table
        return allBankBeans;
    }
}