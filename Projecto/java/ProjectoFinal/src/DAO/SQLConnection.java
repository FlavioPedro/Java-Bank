/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.*;

/**
 *
 * @author jhorgemiguel
 */
public class SQLConnection {
    
    //properties
    private String server;
    private String database;
    private String url;
    private String user;
    private String pass;
    private Connection conn;
    
    //obj builder
    public SQLConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        server = "localhost";
        database = "Bank";
        url = "jdbc:mysql://" + server + "/" + database;
        user = "root";
        pass = "1234";
    }

    public SQLConnection(String user, String pass) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        server = "localhost";
        database = "Bank";
        url = "jdbc:mysql://" + server + "/" + database;
        this.user = user;
        this.pass = pass;
    }



    
    public void Connection() throws SQLException{
        setConn(DriverManager.getConnection(getUrl(), getUser(), getPass()));
    }

    public void CloseConnection() throws SQLException{
        getConn().close();
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
