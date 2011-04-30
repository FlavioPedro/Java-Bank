/*
 * PrincipalMenuApp.java
 */

package Principal;

import Beans.Bank;
import DAO.MySQLExceptions.EmptySetException;
import bankStartup.bankChooser;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class PrincipalMenuApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try {
            show(new bankChooser());
    //        show(new PrincipalMenu(this));
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenuApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenuApp.class.getName()).log(Level.SEVERE, null, ex);
        }
          //show(new PrincipalMenu(this));
    }
    
    public void menuStartup(Bank m) throws ClassNotFoundException, SQLException, EmptySetException{
          show(new PrincipalMenu(this, m));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of PrincipalMenuApp
     */
    public static PrincipalMenuApp getApplication() {
        return Application.getInstance(PrincipalMenuApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(PrincipalMenuApp.class, args);
    }
}
