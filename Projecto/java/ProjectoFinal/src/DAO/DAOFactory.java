/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.MysqlDAO.MysqlDAOFactory;
import java.sql.*;

/**
 *
 * @author Flávio
 */
public abstract class DAOFactory {

    //List of teh DAO types curently suported by the factory:
    
    public static final int MYSQL = 1;
    
    // There will be a method for each DAO that can be 
    // created. The concrete factories will have to 
    // implement these methods.
    public abstract BankDAO getBankDAO()
            throws ClassNotFoundException, SQLException;
    public abstract ClientDAO getClientDAO()
            throws ClassNotFoundException, SQLException;
    public abstract CurrentAccountDAO getCurrentAccountDAO()
            throws ClassNotFoundException, SQLException;
    public abstract CurrentMovementHistoryDAO getCurrentMovementHistoryDAO()
            throws ClassNotFoundException, SQLException;
    public abstract HolderDAO getHolderDAO()
            throws ClassNotFoundException, SQLException;
    public abstract SavingsAccountDAO getSavingsAccountDAO()
            throws ClassNotFoundException, SQLException;
    public abstract SavingsAccountTypeDAO getSavingsAccountTypeDAO()
            throws ClassNotFoundException, SQLException;
    public abstract SavingsMovementHistoryDAO getSavingsMovementHistoryDAO()
            throws ClassNotFoundException, SQLException;

    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL:
                return new MysqlDAOFactory();
            default:
              return null;
        }
      }

}
