/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectofinal;
import DAO.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jhorgemiguel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
            throws ClassNotFoundException, SQLException {
            
            SQLConnection dao = new SQLConnection();
    
            dao.Connection();
            
            DataAcessObject data = new DataAcessObject();
            
            ArrayList<Bank> banks = new ArrayList<Bank>(data.GetBanks(dao));
            
            System.out.print(banks.toString());
            
            
        //SQLConnection liga = new SQLConnection("root", "1234");
//        DataAcessObject o = new DataAcessObject();
//
//        liga.Connection();   
//        
//        //o.DAOGetClients(liga);
//
//        System.out.println(o.GetBanks(liga));

//        ArrayList<Client> clients = new ArrayList<Client>();
//        clients.add(new Client(123, "Henrique Rocha"));
//        clients.add(new Client(321, "Jorge Miguel"));
//        clients.add(new Client(456, "Monica Seidl"));
//        storeClients(clients);
//        List<Client> clients = loadOnDB();
//        for (Client c : clients) {
//            System.out.println(c);
//        }

//        loadOnDB();

    }
    
//    public static List<Client> loadClients() {
//        List<Client> result = new ArrayList<Client>();
//        Scanner sc;
//        try {
//            sc = new Scanner(new File("batatasfritas"));
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            if (line.equals(""))
//                continue;
//            String[] fields = line.split(",");
//            Client c = new Client(new Integer(fields[1]), fields[0]);
//            result.add(c);
//        }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
//    
//    public static void storeClients(List<Client> clients) {
//        try {
//            PrintWriter pw = new PrintWriter(new 
//                    BufferedWriter(new FileWriter("batatasfritas")));
//            for (Client c : clients) {
//                pw.println(c);
//            }
//            pw.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
//
//
//    public static List<Client> loadOnDB() throws ClassNotFoundException, SQLException{
//        String url, user, pass;
//        Class.forName("com.mysql.jdbc.Driver");
//        url = "jdbc:mysql://localhost/Bank";
//        user = "root";
//        pass = "1234";
//
//        Connection conn = DriverManager.getConnection(url, user, pass);
//        List<Client> clients = new ArrayList<Client>();
//        try{
//            Statement stmt = conn.createStatement();
//            ResultSet rset = stmt.executeQuery("SELECT * From Client");
//            if (rset.wasNull()){
//                System.out.println("Não há valores na tabela");
//            }else{
//                while(rset.next()){
//                    Client c = new Client(rset.getInt("docNr"), rset.getString("name"));
//                    clients.add(c);
//                }
//            }
//        } catch (SQLException e){
//            System.out.print("Problemas de ligação com a BD: " + e);
//        }
//        conn.close();
//        return clients;
//    }

        
    
    
}
