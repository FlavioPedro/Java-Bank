/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.MySQLExceptions;

/**
 *
 * @author Flávio
 */
public class EmptySetException extends Exception{

    public EmptySetException(){

    }

    public EmptySetException(String msg){
        super(msg);
    }

    public EmptySetException(Throwable cause){
        super(cause);
    }

    public EmptySetException(String msg, Throwable cause){
        super(msg, cause);
    }
}