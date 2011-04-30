/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.MysqlDAO;
import DAO.BankDAO;
import DAO.ClientDAO;
import DAO.CurrentAccountDAO;
import DAO.CurrentMovementHistoryDAO;
import DAO.DAOFactory;
import DAO.HolderDAO;
import DAO.SavingsAccountDAO;
import DAO.SavingsAccountTypeDAO;
import DAO.SavingsMovementHistoryDAO;
import java.sql.*;
/**
 *
 * @author Flávio
 */

//Mysql concrete DAO Factory implementation
public class MysqlDAOFactory extends DAOFactory {
    public static final String DRIVER =
        "com.mysql.jdbc.Driver";
    public static final String DBURL =
        "jdbc:mysql://localhost/Bank";
    public static final String PASSWORD =
        "1234";
    public static final String USERNAME =
        "root";
    // method to create Mysql connections
    public static Connection createConnection()
            throws ClassNotFoundException, SQLException{

        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        return conn;

    }

    public BankDAO getBankDAO() 
            throws ClassNotFoundException, SQLException{

        // MysqlBankDAO implements BankDAO
        return new MysqlBankDAO();
    }
    public ClientDAO getClientDAO()
            throws ClassNotFoundException, SQLException{

        // MysqlClientDAO implements ClientDAO
        return new MysqlClientDAO();
    }
    public CurrentAccountDAO getCurrentAccountDAO()
            throws ClassNotFoundException, SQLException{

        // MysqlCurrentAccountDAO implements CurrentAccountDAO
        return new MysqlCurrentAccountDAO();
    }
    public CurrentMovementHistoryDAO getCurrentMovementHistoryDAO()
            throws ClassNotFoundException, SQLException{

        // MyqlCurrentMovementHistoryDAO implements CurrentMovementHistoryDAO
        return new MysqlCurrentMovementHistoryDAO();
    }
    public HolderDAO getHolderDAO() 
            throws ClassNotFoundException, SQLException{

        // MysqlHolderDAO implements HolderDAO
        return new MysqlHolderDAO();
    }
    public SavingsAccountDAO getSavingsAccountDAO()
            throws ClassNotFoundException, SQLException{

        // MysqlSavingsAccountDAO implements SavingsAccountDAO
        return new MysqlSavingsAccountDAO();

    }
    public SavingsAccountTypeDAO getSavingsAccountTypeDAO()
            throws ClassNotFoundException, SQLException{

        // MysqlSavingsAccounTypeDAO implements SavingsAccountTypeDAO
        return new MysqlSavingsAccountTypeDAO();

    }
    public SavingsMovementHistoryDAO getSavingsMovementHistoryDAO()
            throws ClassNotFoundException, SQLException{

        // MysqlSavingsMovementHistoryDAO implements SavingsMovementHistoryDAO
        return new MysqlSavingsMovementHistoryDAO();
    }
}