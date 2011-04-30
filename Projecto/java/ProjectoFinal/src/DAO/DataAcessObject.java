/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import projectofinal.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author jhorgemiguel
 */
public class DataAcessObject extends SQLConnection{

    PreparedStatement statm;
    
    public DataAcessObject() throws SQLException, ClassNotFoundException{}


    // Obtaining a list of historic movements from bank accounts by Desc order
    public ArrayList<String> CurrentMovHistList(Date date, SQLConnection conn)
            throws Exception{
        try{
            Statement stmt = conn.getConn().createStatement();
            String query = "SELECT * "
                    + "FROM CurrentMovementHistory WHERE movementDate < ?"
                    + "ORDER BY movementDate DESC";
            statm.setDate(1, date);
            ResultSet res = stmt.executeQuery(query);
            ArrayList<String> currentHistoric = new ArrayList<String>();
            while (res.next()){
                int currentMovementID = res.getInt(1);
                String currentAccountID = res.getString(2);
                String MovementType = res.getString(3);
                String amountMoved = res.getString(4);
                String movementDate = res.getString(5);

                String result = "ID: " + currentMovementID + "\n";
                result += "ID Ordem: " + currentAccountID + "\n";
                result += "Tipo de Movimento: " + MovementType + "\n";
                result += "Montante: " + amountMoved + "\n";
                result += "Data do Movimento: " + movementDate;
                result += "\n********************************\n";

                currentHistoric.add(result);
            }
            stmt.close();
            conn.getConn().close();
            return currentHistoric;
        }
        catch(SQLException e){
            System.err.print(e.getMessage() + "Não foi possivel aceder à base de dados.");
            return null;
        }
    }

    public ArrayList<String> SavingsMovHistList(Date date, SQLConnection conn)
            throws Exception{
        try{
            Statement stmt = conn.getConn().createStatement();
            String query = "SELECT * "
                    + "FROM SavingsMovementHistory WHERE movementDate < ?"
                    + "ORDER BY movementDate DESC";
            statm.setDate(1, date);
            ResultSet res = stmt.executeQuery(query);
            ArrayList<String> savingsHistoric = new ArrayList<String>();
            while (res.next()){
                int savingsMovementID = res.getInt(1);
                String savingsAccountID = res.getString(2);
                String MovementType = res.getString(3);
                String amountMoved = res.getString(4);
                String movementDate = res.getString(5);

                String result = "ID: " + savingsMovementID + "\n";
                result += "ID Prazo: " + savingsAccountID + "\n";
                result += "Tipo de Movimento: " + MovementType + "\n";
                result += "Montante: " + amountMoved + "\n";
                result += "Data do Movimento: " + movementDate;
                result += "\n********************************\n";

                savingsHistoric.add(result);
            }
            stmt.close();
            conn.getConn().close();
            return savingsHistoric;
        }
        catch(SQLException e){
            System.err.print(e.getMessage() + "Não foi possivel aceder à base de dados.");
            return null;
        }
    }

    // Adding a Client to Database
    public void addClientToDB(SQLConnection conn, Client c, Bank b){
        try{
            Statement stmt = conn.getConn().createStatement();
            String query = "INSERT INTO Client(NIF, bankID, name, address, "
                    + "zipCode, mail, contact, docType, docNr, birthDate, sex)"
                    + "VALUES(?,?,'?','?','?','?','?','?','?', '?', ?)";
            statm.setInt(1, c.getNIF()); 
            statm.setInt(2, b.getBankID());
            statm.setString(3, c.getName());
            statm.setString(4, c.getAddress());
            statm.setString(5, c.getZipCode());
            statm.setString(6, c.getMail());
            statm.setString(7, c.getContact());
            statm.setString(8, c.getDocType());
            statm.setString(9, c.getDocNumber());
            statm.setString(10, c.getBirthDate().toString());
            if (c.getSex() == "M"){
                statm.setInt(11, 1);
            } else if (c.getSex() == "F"){
                statm.setInt(11, 2);
            }
            int res = stmt.executeUpdate(query);

            System.out.println(res);
        }
        catch(SQLException e){
            System.err.print(e.getMessage() + "Não foi possivel aceder à base de dados.");
        }
    }

    
    // Getting a list of banks from Database
    public List<Bank> GetBanks(SQLConnection conn)
            throws SQLException, ClassNotFoundException{
        try{
            List<Bank> banks = new ArrayList<Bank>();
            Statement stmt = conn.getConn().createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM Bank;");
            if (rset.wasNull() == true) {
                System.out.print("There's no bank created");
            }
            else{
                while (rset.next() == true) {
                    Bank banco = new Bank(rset.getString("bankName"),rset.getInt("bankID"));
                    banco.setRefDate(rset.getDate("refDate"));
                    banks.add(banco);
                }
            }
            rset.close();
            stmt.close();
            return banks;
        }
        catch (SQLException e){
            System.out.print("Erro de transmissão da base de dados: " + e);
            return null;
        }
    }
    
    // Getting a list of Clients from Databass
    public List<Client> GetClients(SQLConnection conn)
            throws SQLException, ClassNotFoundException{
        try{
            List<Client> allClients = new ArrayList<Client>();
            Statement stmt = conn.getConn().createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM Client;");
            if (rset.wasNull() == true) {
                System.out.print("Tabela encontra-se vazia");
            }
            else{
                while (rset.next() == true) {
                    Client cliente = new Client(rset.getString("name"),
                            rset.getString("address"), 
                            rset.getString("postalCode"),
                            rset.getString("mail"),
                            rset.getString("contact"),
                            rset.getString("doctype"),
                            rset.getString("docNr"),
                            rset.getDate("birthDate"),
                            rset.getString("sex"),
                            rset.getString("NIF"));
                    allClients.add(cliente);
                }
            }
            rset.close();
            stmt.close();
            return allClients;
        }
        catch (SQLException e){
            System.out.print("Erro de transmissão da base de dados: " + e);
            return null;
        }
    }

    // Getting a list of savings accounts from Database
    public List<SavingsAccount> GetSavingsAccounts(SQLConnection conn) 
            throws SQLException, ClassNotFoundException, Exception{
        try{
            List<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();
            Statement stmt = conn.getConn().createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM SavingsAccount;");
            if (rset.wasNull() == true) {
                System.out.print("There's no savings account created");
            }
            else{
                while (rset.next() == true) {
                    if(rset.getInt("savingsAccountID") == 1){
                        SimpleSavingsAccount simple = new SimpleSavingsAccount(rset.getDate("openDate"), rset.getInt("initialAmount"), rset.getInt("savingsAccountID"));
                        //simple.setAssociatedTo(rset.getString("orderAccountID"));
                        savingsAccounts.add(simple);
                    } else if (rset.getInt("savingsAccountID") == 2){
                        GoldSavingsAccount gold = new GoldSavingsAccount(rset.getDate("openDate"), rset.getInt("initialAmount"), rset.getInt("savingsAccountID"), 0.09);
                        //gold.setAccountAssociatedTo(rset.getString("orderAccountID"));
                        savingsAccounts.add(gold);
                    }
                }
            }
            rset.close();
            stmt.close();
            return savingsAccounts;
        }
        catch (SQLException e){
            System.out.print("Erro de transmissão da base de dados: " + e);
            return null;
        }
    }

    // Getting a list of order accounts from Database
    public List<CurrentAccount> GetOrderAccounts(SQLConnection conn)
            throws SQLException, ClassNotFoundException{
        try{
            List<CurrentAccount> orderAccounts = new ArrayList<CurrentAccount>();
            Statement stmt = conn.getConn().createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM OrderAccount;");
            if (rset.wasNull() == true) {
                System.out.print("There's no term accounts created");
            }
            else{
                while (rset.next() == true) {
                    CurrentAccount order = new CurrentAccount(rset.getDate("openDate"), rset.getInt("initialAmount"), rset.getInt("orderAccountID"));
                    order.setInitialAmount(rset.getInt("currentAmount"));
                    orderAccounts.add(order);
                }
            }
            rset.close();
            stmt.close();
            return orderAccounts;
        }
        catch (SQLException e){
            System.out.print("Erro de transmissão da base de dados: " + e);
            return null;
        }
    }
}
