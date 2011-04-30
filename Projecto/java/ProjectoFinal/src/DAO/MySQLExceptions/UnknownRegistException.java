/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.MySQLExceptions;

/**
 *
 * @author Flávio
 */
public class UnknownRegistException extends Exception{

    public UnknownRegistException(){

    }

    public UnknownRegistException(String msg){
        super(msg);
    }

    public UnknownRegistException(Throwable cause){
        super(cause);
    }

    public UnknownRegistException(String msg, Throwable cause){
        super(msg, cause);
    }
}