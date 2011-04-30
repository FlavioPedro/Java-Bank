 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.MysqlDAO;

// MysqlClientDAO implementation of the
// ClientDAO interface. This class can contain all
// Mysql specific code and SQL statements.
// The client is thus shielded from knowing
// these implementation details.

import DAO.ClientDAO;
import java.sql.*;
import Beans.Client;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MySQLExceptions.UnknownRegistException;
import java.util.ArrayList;
/**
 *
 * @author Flávio
 */
public class MysqlClientDAO implements ClientDAO {

    private Connection conn;
    private PreparedStatement ps;
    private String query = "";
    private ResultSet rs = null;

    //Error Messages
    private static final String NO_SUCH_CLIENT =
            "That client doesn't exist in our database!";
    private static final String EMPTY_CLIENT_TABLE =
            "There are no entries in the client table!";

    public MysqlClientDAO() throws ClassNotFoundException, SQLException{
    // initialization
        conn = MysqlDAOFactory.createConnection();
        ps = conn.prepareStatement(query);

    }


    // The following methods can use
    // MysqlDAOFactory.createConnection()
    // to get a connection as required

    public boolean insertClient(Client theClientToInsert)
            throws SQLException{
        // Implement insert client here.
        // Return newly created client number
        // or a -1 on error

        PreparedStatement ps = null;
        
        //The number of the new client to be returned.
        int newClientNumber = -1;
        
        //The query to be executed.
        query = "INSERT INTO Client"
                + "(NIF, bankID, name, address, zipCode, mail, contact, docType,"
                + " docNr, birthDate, sex)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?);";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theClientToInsert.getClientNIF());
        ps.setInt(2, theClientToInsert.getBankID());
        ps.setString(3, theClientToInsert.getName());
        ps.setString(4, theClientToInsert.getAddress());
        ps.setString(5, theClientToInsert.getZipCode());
        ps.setString(6, theClientToInsert.getMail());
        ps.setString(7, theClientToInsert.getContact());
        ps.setString(8, theClientToInsert.getDocType());
        ps.setString(9, theClientToInsert.getDocNumber());
        ps.setDate(10, new java.sql.Date(theClientToInsert.getBirthDate().getTime()));
        ps.setString(11, theClientToInsert.getSex());

         //Executes the query.
        newClientNumber = ps.executeUpdate();
        //closes the statement.
        ps.close();
        
        if (newClientNumber != -1)
            return true;
        else
            return false;
    }

    public boolean deleteClient(int theNIFOfClientToDelete)
            throws SQLException{
        // Implement delete client here

        // Return true on success, false on failure
        boolean isDeleted = false;

        //The query to be executed.
        query = "DELETE FROM Client WHERE NIF = ?;";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theNIFOfClientToDelete);

        //Executes the query.
        ps.executeUpdate();
        isDeleted = true;
        //closes the statement.
        ps.close();

        return isDeleted;
    }

    public Client findClient(int theClientNif)
            throws SQLException, UnknownRegistException{

        // Implement find a client here using supplied
        // argument values as search criteria
        // Return a Transfer Object if found,
        // return null on error or if not found
        PreparedStatement ps = null;
        //The client to be returned.
        Client tempClientBean = null;
        //The Query to be Executed.
        query = "SELECT * FROM Client WHERE NIF = ?;";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theClientNif);
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();
        //Checks if the RedultSet is Empty, if so throws an exception.
        if (rs.first() == false) {
            throw new UnknownRegistException(NO_SUCH_CLIENT);
        }

        //creates a temporary beanObject to store the data.
        tempClientBean = new Client();

        //inicializes the beanObject atributes with
        //the values extracted from the batabase.
        tempClientBean.setClientNIF(rs.getInt("NIF"));
        tempClientBean.setBankID(rs.getInt("bankID"));
        tempClientBean.setName(rs.getString("name"));
        tempClientBean.setAddress(rs.getString("address"));
        tempClientBean.setZipCode(rs.getString("zipCode"));
        tempClientBean.setMail(rs.getString("mail"));
        tempClientBean.setContact(rs.getString("contact"));
        tempClientBean.setDocType(rs.getString("docType"));
        tempClientBean.setDocNumber(rs.getString("docNr"));
        tempClientBean.setBirthDate((Date)rs.getDate("birthDate"));
        tempClientBean.setSex(rs.getString("sex"));
        

        //closes the statement.
        ps.close();

        //returns the found bank.
        return tempClientBean;

    }

    public boolean updateClient(Client theClientToUpdate)
            throws SQLException, UnknownRegistException{
        // implement update record here using data
        // from the ClientData Transfer Object

        // Return true on success, false on failure or
        // error
        boolean isUpdated = false;

        //The Query to be Executed.
        query = "UPDATE Client SET NIF = ?, bankID = ?, name = ?,"
               + " address = ?, zipCode = ?, mail = ?, contact = ?, docType = ?,"
               + " docNr = ?, birthDate = ? WHERE NIF = ?;";

        ps = conn.prepareStatement(query);
        
        //Prepares the statement replacing ? with the values passed by argument.
        ps.setInt(1, theClientToUpdate.getClientNIF());
        ps.setInt(2, theClientToUpdate.getBankID());
        ps.setString(3, theClientToUpdate.getName());
        ps.setString(4, theClientToUpdate.getAddress());
        ps.setString(5, theClientToUpdate.getZipCode());
        ps.setString(6, theClientToUpdate.getMail());
        ps.setString(7, theClientToUpdate.getContact());
        ps.setString(8, theClientToUpdate.getDocType());
        ps.setString(9, theClientToUpdate.getDocNumber());
        ps.setDate(10, new java.sql.Date(theClientToUpdate.getBirthDate().getTime()));
        ps.setInt(11, theClientToUpdate.getClientNIF());

        //Executes the Query and gets the result into a ResultSet.
        isUpdated = ps.execute();

        //Checks if the ResultSet is Empty, if so throws an exception.
        isUpdated = true;

        //closes the statement.
        ps.close();

        return isUpdated;
    }

    public ArrayList<Client> findAllClients(int ID)
            throws SQLException, EmptySetException {
        // implement search Banks here using the
        // supplied criteria.
        // Return an ArrayList.

        //The ArrayList to be filled with the ResultSet and returned.
        ArrayList<Client> allClientBeans = new ArrayList<Client>();
        PreparedStatement ps = null;
        
        //The Query to be Executed.
        query = "SELECT * FROM Client WHERE bankID = ?";
        
        ps = conn.prepareStatement(query);
        
        ps.setInt(1, ID);
        
        //Executes the Query and gets the result into a ResultSet.
        rs = ps.executeQuery();

        //Checks if the ResultSet is Empty, if so throws an exception.
        if(rs.wasNull()){
            throw new EmptySetException(NO_SUCH_CLIENT);
        }else{
            while (rs.next()) {

                //creates a temporary beanObject to store the data.
                Client tempClientBean = new Client();

                //inicializes the beanObject atributes with
                //the values extracted from the database.
                tempClientBean.setClientNIF(rs.getInt(1));
                tempClientBean.setBankID(rs.getInt(2));
                tempClientBean.setName(rs.getString(3));
                tempClientBean.setAddress(rs.getString(4));
                tempClientBean.setZipCode(rs.getString(5));
                tempClientBean.setMail(rs.getString(6));
                tempClientBean.setContact(rs.getString(7));
                tempClientBean.setDocType(rs.getString(8));
                tempClientBean.setDocNumber(rs.getString(9));
                tempClientBean.setBirthDate((Date)rs.getDate(10));
                tempClientBean.setSex(rs.getString(11));

                //adds the temporary bean to the ArrayList to return.
                allClientBeans.add(tempClientBean);
            }
        }
        //closes the statement.
        ps.close();

        //returns the ArrayList of banks on the bank table
        return allClientBeans;
    }
}
