/*
 * PrincipalMenu.java
 */

package Principal;

import Beans.Bank;
import DAO.MySQLExceptions.EmptySetException;
import DAO.MysqlDAO.MysqlClientDAO;
import Beans.Client;
import Beans.CurrentAccount;
import Beans.CurrentMovementHistory;
import Beans.SavingsAccount;
import Beans.SavingsMovementHistory;
import DAO.BankDAO;
import DAO.MySQLExceptions.UnknownRegistException;
import DAO.MysqlDAO.MysqlBankDAO;
import DAO.MysqlDAO.MysqlCurrentAccountDAO;
import DAO.MysqlDAO.MysqlCurrentMovementHistoryDAO;
import DAO.MysqlDAO.MysqlSavingsAccountDAO;
import DAO.MysqlDAO.MysqlSavingsMovementHistoryDAO;
import clients.*;
import accounts.*;
import bankStartup.bankChooser;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

/**
 * The application's main frame.
 */
public class PrincipalMenu extends FrameView {

    MysqlBankDAO bankDAO;
    MysqlClientDAO clientDao;
    MysqlCurrentAccountDAO currentAccountDAO;
    MysqlSavingsAccountDAO savingsAccountDAO;
    MysqlCurrentMovementHistoryDAO currentHistoryDAO;
    MysqlSavingsMovementHistoryDAO savingsHistoryDAO;
    
    private Date refeDate;
    private int bankID;
    
    public PrincipalMenu(SingleFrameApplication app, Bank b) throws ClassNotFoundException, SQLException, EmptySetException {
        super(app);
        initComponents();
        setInformationPanel(b);
        
        refeDate = b.getRefDate();
        refenceDate.setText(b.getRefDate().toString());
        bankID = b.getBankID();
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        
//        // Initialize bank values.
//        resourceMap.getObject(null, null);
    }

    public void setInformationPanel(Bank b) throws ClassNotFoundException,
            SQLException, EmptySetException{
        clientDao = new MysqlClientDAO();
        clientsBank_lbl.setText(clientsBank_lbl.getText() + " " + 
                String.valueOf(clientDao.findAllClients(b.getBankID()).size()));
        currentAccountDAO = new MysqlCurrentAccountDAO();
        currentAccounts_lbl.setText(currentAccounts_lbl.getText() + " " +
                String.valueOf(currentAccountDAO.findAllCurrentAccounts(b.getBankID()).size()));
        simpleSavingsAccounts_lbl.setText(simpleSavingsAccounts_lbl.getText() + " " + 
                simpleSavingsAccountsList(b));
        goldSavingsAccount_lbl.setText(goldSavingsAccount_lbl.getText() + " " + 
                goldSavingsAccountsList(b));       
        totalCurrentDeposits_lbl.setText(totalCurrentDeposits_lbl.getText() + " " +
                allCurrentDeposits(b));
        totalSimpleDeposits_lbl.setText(totalSimpleDeposits_lbl.getText() + " " + 
                simpleSavingsDeposits(b));
        totalGoldDeposits_lbl.setText(totalGoldDeposits_lbl.getText() + " " + 
                goldSavingsDeposits(b));
    }
    
    public int simpleSavingsAccountsList(Bank b) throws ClassNotFoundException, SQLException, EmptySetException{
        savingsAccountDAO = new MysqlSavingsAccountDAO();
        ArrayList<SavingsAccount> allSavings = savingsAccountDAO.findAllSavingsAccount(b.getBankID());
        for (SavingsAccount s : allSavings){
            if (s.getSavingsAccountTypeID() == 2){
                allSavings.remove(s);
            }
        }
        return allSavings.size();
    }
    
    public int goldSavingsAccountsList(Bank b) throws ClassNotFoundException, SQLException, EmptySetException{
        savingsAccountDAO = new MysqlSavingsAccountDAO();
        ArrayList<SavingsAccount> allSavings = savingsAccountDAO.findAllSavingsAccount(b.getBankID());
        try{
            for (SavingsAccount s : allSavings){
                if (s.getSavingsAccountTypeID() == 1){
                    allSavings.remove(s);
                }
            }
            return allSavings.size();
        }catch (Exception e){
            Logger.getAnonymousLogger(e.toString());
        }finally{
            return 0;
        }
    }
    
    public int allCurrentDeposits(Bank b) throws ClassNotFoundException, SQLException, EmptySetException{
        currentHistoryDAO = new MysqlCurrentMovementHistoryDAO();
        ArrayList<CurrentMovementHistory> allCurrentDeposits = currentHistoryDAO.findAllCurrentMovementHistory(b.getBankID());
        for (CurrentMovementHistory ch : allCurrentDeposits){
            if (ch.getMovementType() == "Levantamento"){
                allCurrentDeposits.remove(ch);
            }
        }       
        return allCurrentDeposits.size();
    }
    
    public int simpleSavingsDeposits(Bank b) throws ClassNotFoundException, SQLException, EmptySetException{
        try{
            savingsHistoryDAO = new MysqlSavingsMovementHistoryDAO();
            ArrayList<SavingsMovementHistory> simpleDeposits = savingsHistoryDAO.findAllSimpleSavingsMovementHistory(b.getBankID());
            for (SavingsMovementHistory sm : simpleDeposits){
            if (sm.getMovementType() == "Levantamento")
                simpleDeposits.remove(sm);
            } 
            return simpleDeposits.size();
        }catch(Exception e){
            Logger.getAnonymousLogger(e.toString());
        }finally{
            return 0;
        }
    }
    
    public int goldSavingsDeposits(Bank b) throws ClassNotFoundException, SQLException, EmptySetException{
        savingsHistoryDAO = new MysqlSavingsMovementHistoryDAO();
        ArrayList<SavingsMovementHistory> goldDeposits = savingsHistoryDAO.findAllGoldSavingsMovementHistory(b.getBankID());
        try{
            for (SavingsMovementHistory sm : goldDeposits){
            if (sm.getMovementType() == "Levantamento")
                goldDeposits.remove(sm);
            } 
            return goldDeposits.size();
        }catch (Exception e){
            Logger.getAnonymousLogger(e.toString());
        }finally{
            return 0;
        }
    }
    
    public String changeRefDate(){
        String newDate = JOptionPane.showInputDialog(null, "Introduza a data nova:");
        if (newDate == ""){
            refeDate = dateConverter(newDate);
            try {
                updateBankDate();
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownRegistException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EmptySetException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newDate;
    }
    
    public void updateBankDate() throws SQLException, UnknownRegistException, ClassNotFoundException, EmptySetException{
        bankDAO = new MysqlBankDAO();
        ArrayList<Bank> banks = bankDAO.findAllBanks();
        bankDAO = new MysqlBankDAO();
        for (Bank b : banks){
            if (b.getBankID() == bankID)
                bankDAO.updateBank(b);
        }
    }
   
    
    //Convert String to Date Type
    public Date dateConverter(String data){
        SimpleDateFormat deta = new SimpleDateFormat("YYYY/MM/DD");
        String birthDate = data;
        Date newDate = new Date();
        try {
            newDate = deta.parse(birthDate);
        } catch (ParseException ex) {
            Logger.getLogger(CreateClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newDate;
    }
    
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PrincipalMenuApp.getApplication().getMainFrame();
            aboutBox = new PrincipalMenuAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PrincipalMenuApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        newClientBtn = new javax.swing.JButton();
        changeClientBtn = new javax.swing.JButton();
        removeClientBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        listClients_btn = new javax.swing.JButton();
        panel2 = new java.awt.Panel();
        openAccountBtn = new javax.swing.JButton();
        consultAccountBtn = new javax.swing.JButton();
        removeAccountBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        listAccounts_btn = new javax.swing.JButton();
        panel3 = new java.awt.Panel();
        jLabel4 = new javax.swing.JLabel();
        clientsBank_lbl = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        simpleSavingsAccounts_lbl = new javax.swing.JLabel();
        goldSavingsAccount_lbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        totalSimpleDeposits_lbl = new javax.swing.JLabel();
        totalGoldDeposits_lbl = new javax.swing.JLabel();
        currentAccounts_lbl = new javax.swing.JLabel();
        totalCurrentDeposits_lbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        makeDeposits_btn = new javax.swing.JButton();
        makeWithDrawalsBtn = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        changeBank_menu = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        listMenu = new javax.swing.JMenu();
        listCurrentAccountswithAmount_menu = new javax.swing.JMenuItem();
        listOfAccountsWithMovements_menu = new javax.swing.JMenuItem();
        listOfAccountsWithNoAmount_menu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        listOfClientsWithNumberOfAccounts = new javax.swing.JMenuItem();
        listOfClientsWithNoAccounts_menu = new javax.swing.JMenuItem();
        listOfClientsWithSumAmountAllAccounts_menu = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        refenceDate = new javax.swing.JLabel();
        changeRefDate_btn = new javax.swing.JButton();

        mainPanel.setMaximumSize(new java.awt.Dimension(367, 367));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(400, 400));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(Principal.PrincipalMenuApp.class).getContext().getResourceMap(PrincipalMenu.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        panel1.setName("clientActions"); // NOI18N

        newClientBtn.setText(resourceMap.getString("btn_newClient.text")); // NOI18N
        newClientBtn.setName("btn_newClient"); // NOI18N
        newClientBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newClientBtnMouseClicked(evt);
            }
        });

        changeClientBtn.setText(resourceMap.getString("btn_changeClient.text")); // NOI18N
        changeClientBtn.setName("btn_changeClient"); // NOI18N
        changeClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeClientBtnActionPerformed(evt);
            }
        });

        removeClientBtn.setText(resourceMap.getString("btn_removeClient.text")); // NOI18N
        removeClientBtn.setName("btn_removeClient"); // NOI18N
        removeClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeClientBtnActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        listClients_btn.setText(resourceMap.getString("listClients_btn.text")); // NOI18N
        listClients_btn.setName("listClients_btn"); // NOI18N
        listClients_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listClients_btnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panel1Layout = new org.jdesktop.layout.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel1Layout.createSequentialGroup()
                .add(panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panel1Layout.createSequentialGroup()
                        .add(33, 33, 33)
                        .add(jLabel2))
                    .add(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(newClientBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(changeClientBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .add(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(listClients_btn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .add(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(removeClientBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2)
                .add(18, 18, 18)
                .add(newClientBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(changeClientBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(listClients_btn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(removeClientBtn)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.setName("accountActions"); // NOI18N

        openAccountBtn.setText(resourceMap.getString("btn_openAccount.text")); // NOI18N
        openAccountBtn.setName("btn_openAccount"); // NOI18N
        openAccountBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openAccountBtnMouseClicked(evt);
            }
        });

        consultAccountBtn.setText(resourceMap.getString("btn_consultAccount.text")); // NOI18N
        consultAccountBtn.setName("btn_consultAccount"); // NOI18N
        consultAccountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultAccountBtnActionPerformed(evt);
            }
        });

        removeAccountBtn.setText(resourceMap.getString("btn_removeAccount.text")); // NOI18N
        removeAccountBtn.setName("btn_removeAccount"); // NOI18N
        removeAccountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAccountBtnActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        listAccounts_btn.setText(resourceMap.getString("listAccounts_btn.text")); // NOI18N
        listAccounts_btn.setName("listAccounts_btn"); // NOI18N
        listAccounts_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAccounts_btnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panel2Layout = new org.jdesktop.layout.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, openAccountBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, removeAccountBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .add(listAccounts_btn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, consultAccountBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .add(18, 18, 18)
                .add(openAccountBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(consultAccountBtn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(listAccounts_btn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(removeAccountBtn)
                .addContainerGap())
        );

        panel3.setName("bankDetails"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        clientsBank_lbl.setText(resourceMap.getString("lbl_NrClients.text")); // NOI18N
        clientsBank_lbl.setName("lbl_NrClients"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        simpleSavingsAccounts_lbl.setText(resourceMap.getString("lbl_nrSimples.text")); // NOI18N
        simpleSavingsAccounts_lbl.setName("lbl_nrSimples"); // NOI18N

        goldSavingsAccount_lbl.setText(resourceMap.getString("lbl_nrGold.text")); // NOI18N
        goldSavingsAccount_lbl.setName("lbl_nrGold"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        totalSimpleDeposits_lbl.setText(resourceMap.getString("lbl_depSimples.text")); // NOI18N
        totalSimpleDeposits_lbl.setName("lbl_depSimples"); // NOI18N

        totalGoldDeposits_lbl.setText(resourceMap.getString("lbl_depGold.text")); // NOI18N
        totalGoldDeposits_lbl.setName("lbl_depGold"); // NOI18N

        currentAccounts_lbl.setText(resourceMap.getString("currentAccounts_lbl.text")); // NOI18N
        currentAccounts_lbl.setName("currentAccounts_lbl"); // NOI18N

        totalCurrentDeposits_lbl.setText(resourceMap.getString("totalCurrentDeposits_lbl.text")); // NOI18N
        totalCurrentDeposits_lbl.setName("totalCurrentDeposits_lbl"); // NOI18N

        org.jdesktop.layout.GroupLayout panel3Layout = new org.jdesktop.layout.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel3Layout.createSequentialGroup()
                .add(panel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(panel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4)
                            .add(clientsBank_lbl)
                            .add(jLabel6)))
                    .add(panel3Layout.createSequentialGroup()
                        .add(56, 56, 56)
                        .add(panel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(totalGoldDeposits_lbl)
                            .add(totalSimpleDeposits_lbl)
                            .add(totalCurrentDeposits_lbl)))
                    .add(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel9))
                    .add(panel3Layout.createSequentialGroup()
                        .add(57, 57, 57)
                        .add(panel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(currentAccounts_lbl)
                            .add(simpleSavingsAccounts_lbl)
                            .add(goldSavingsAccount_lbl))))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel4)
                .add(18, 18, 18)
                .add(clientsBank_lbl)
                .add(18, 18, 18)
                .add(jLabel6)
                .add(10, 10, 10)
                .add(currentAccounts_lbl)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(simpleSavingsAccounts_lbl)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(goldSavingsAccount_lbl)
                .add(18, 18, 18)
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(totalCurrentDeposits_lbl)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(totalSimpleDeposits_lbl)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(totalGoldDeposits_lbl)
                .addContainerGap())
        );

        jPanel1.setForeground(resourceMap.getColor("jPanel1.foreground")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        makeDeposits_btn.setText(resourceMap.getString("makeDeposits_btn.text")); // NOI18N
        makeDeposits_btn.setName("makeDeposits_btn"); // NOI18N
        makeDeposits_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeDeposits_btnActionPerformed(evt);
            }
        });

        makeWithDrawalsBtn.setText(resourceMap.getString("makeWithDrawalsBtn.text")); // NOI18N
        makeWithDrawalsBtn.setName("makeWithDrawalsBtn"); // NOI18N
        makeWithDrawalsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeWithDrawalsBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(70, 70, 70)
                .add(makeDeposits_btn)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 118, Short.MAX_VALUE)
                .add(makeWithDrawalsBtn)
                .add(42, 42, 42))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(makeWithDrawalsBtn)
                    .add(makeDeposits_btn))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(mainPanelLayout.createSequentialGroup()
                                .add(panel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(panel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(panel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel1))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(20, 20, 20)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, panel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, panel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        panel2.getAccessibleContext().setAccessibleName(resourceMap.getString("accountActions.AccessibleContext.accessibleName")); // NOI18N
        panel2.getAccessibleContext().setAccessibleDescription(resourceMap.getString("accountActions.AccessibleContext.accessibleDescription")); // NOI18N

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        changeBank_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        changeBank_menu.setText(resourceMap.getString("changeBank_menu.text")); // NOI18N
        changeBank_menu.setName("changeBank_menu"); // NOI18N
        changeBank_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeBank_menuActionPerformed(evt);
            }
        });
        changeBank_menu.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                changeBank_menuMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        fileMenu.add(changeBank_menu);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(Principal.PrincipalMenuApp.class).getContext().getActionMap(PrincipalMenu.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        listMenu.setText(resourceMap.getString("listMenu.text")); // NOI18N
        listMenu.setName("listMenu"); // NOI18N

        listCurrentAccountswithAmount_menu.setText(resourceMap.getString("listCurrentAccountswithAmount_menu.text")); // NOI18N
        listCurrentAccountswithAmount_menu.setName("listCurrentAccountswithAmount_menu"); // NOI18N
        listCurrentAccountswithAmount_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listCurrentAccountswithAmount_menuActionPerformed(evt);
            }
        });
        listMenu.add(listCurrentAccountswithAmount_menu);

        listOfAccountsWithMovements_menu.setText(resourceMap.getString("listOfAccountsWithMovements_menu.text")); // NOI18N
        listOfAccountsWithMovements_menu.setName("listOfAccountsWithMovements_menu"); // NOI18N
        listOfAccountsWithMovements_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listOfAccountsWithMovements_menuActionPerformed(evt);
            }
        });
        listMenu.add(listOfAccountsWithMovements_menu);

        listOfAccountsWithNoAmount_menu.setText(resourceMap.getString("listOfAccountsWithNoAmount_menu.text")); // NOI18N
        listOfAccountsWithNoAmount_menu.setName("listOfAccountsWithNoAmount_menu"); // NOI18N
        listOfAccountsWithNoAmount_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listOfAccountsWithNoAmount_menuActionPerformed(evt);
            }
        });
        listMenu.add(listOfAccountsWithNoAmount_menu);

        jSeparator1.setName("jSeparator1"); // NOI18N
        listMenu.add(jSeparator1);

        jSeparator2.setName("jSeparator2"); // NOI18N
        listMenu.add(jSeparator2);

        listOfClientsWithNumberOfAccounts.setText(resourceMap.getString("listOfClientsWithNumberOfAccounts.text")); // NOI18N
        listOfClientsWithNumberOfAccounts.setName("listOfClientsWithNumberOfAccounts"); // NOI18N
        listOfClientsWithNumberOfAccounts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listOfClientsWithNumberOfAccountsActionPerformed(evt);
            }
        });
        listMenu.add(listOfClientsWithNumberOfAccounts);

        listOfClientsWithNoAccounts_menu.setText(resourceMap.getString("listOfClientsWithNoAccounts_menu.text")); // NOI18N
        listOfClientsWithNoAccounts_menu.setName("listOfClientsWithNoAccounts_menu"); // NOI18N
        listOfClientsWithNoAccounts_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listOfClientsWithNoAccounts_menuActionPerformed(evt);
            }
        });
        listMenu.add(listOfClientsWithNoAccounts_menu);

        listOfClientsWithSumAmountAllAccounts_menu.setText(resourceMap.getString("listOfClientsWithSumAmountAllAccounts_menu.text")); // NOI18N
        listOfClientsWithSumAmountAllAccounts_menu.setName("listOfClientsWithSumAmountAllAccounts_menu"); // NOI18N
        listOfClientsWithSumAmountAllAccounts_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listOfClientsWithSumAmountAllAccounts_menuActionPerformed(evt);
            }
        });
        listMenu.add(listOfClientsWithSumAmountAllAccounts_menu);

        menuBar.add(listMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(921, 45));

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        refenceDate.setText(resourceMap.getString("lbl_actualDate.text")); // NOI18N
        refenceDate.setName("lbl_actualDate"); // NOI18N

        changeRefDate_btn.setText(resourceMap.getString("changeRefDate_btn.text")); // NOI18N
        changeRefDate_btn.setName("changeRefDate_btn"); // NOI18N
        changeRefDate_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeRefDate_btnMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(statusMessageLabel)
                        .add(897, 897, 897)
                        .add(statusAnimationLabel)
                        .addContainerGap())
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(statusPanelLayout.createSequentialGroup()
                                .add(88, 88, 88)
                                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                .add(170, 170, 170))
                            .add(statusPanelLayout.createSequentialGroup()
                                .add(35, 35, 35)
                                .add(refenceDate)
                                .add(27, 27, 27)
                                .add(changeRefDate_btn)
                                .add(329, 329, 329)))
                        .add(59, 59, 59)
                        .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(161, 161, 161))))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(refenceDate)
                        .add(changeRefDate_btn)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(32, 32, 32)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel))
                .add(15, 15, 15))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void newClientBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newClientBtnMouseClicked
        // TODO add your handling code here:
        JFrame newClient = new CreateClient(bankID);
        newClient.setVisible(true);
    }//GEN-LAST:event_newClientBtnMouseClicked

    private void openAccountBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openAccountBtnMouseClicked
        // TODO add your handling code here:
        JFrame newaccount = new CreateAccount(bankID, refeDate);
        newaccount.setVisible(true);
    }//GEN-LAST:event_openAccountBtnMouseClicked

    private void changeRefDate_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeRefDate_btnMouseClicked
        String datenew = changeRefDate();
        if (datenew != null)
            refenceDate.setText(datenew);
    }//GEN-LAST:event_changeRefDate_btnMouseClicked

    private void changeBank_menuMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_changeBank_menuMenuKeyPressed
        // TODO add your handling code here:
        JFrame bankChooser = null;
        try {
            bankChooser = new bankChooser();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        bankChooser.setVisible(true);
    }//GEN-LAST:event_changeBank_menuMenuKeyPressed

    private void changeBank_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeBank_menuActionPerformed
        // TODO add your handling code here:
        JFrame bankChooser = null;
        try {
            bankChooser = new bankChooser();
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        bankChooser.setVisible(true);
    }//GEN-LAST:event_changeBank_menuActionPerformed

    private void listCurrentAccountswithAmount_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCurrentAccountswithAmount_menuActionPerformed
        // TODO add your handling code here:
        double value = Double.valueOf(JOptionPane.showInputDialog(null, "Introduza um valor, para obter as contas com saldo superior a esse valor:"));
        JFrame accountsWithAmountListFrame = new JFrame("Lista de contas com saldo superior ao valor");
        JComponent component = (JComponent) accountsWithAmountListFrame.getContentPane();
        String[] list = {"one ******************************************************** saldo",
            "two *********************************************** saldo",
            "three ********************************************* saldo"};
        JList accountList = new JList(list);
        accountList.setSize(600, 700);
        component.add(accountList);
        accountsWithAmountListFrame.pack();
        accountsWithAmountListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        accountsWithAmountListFrame.setVisible(true);
    }//GEN-LAST:event_listCurrentAccountswithAmount_menuActionPerformed

    private void listOfAccountsWithMovements_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listOfAccountsWithMovements_menuActionPerformed
        // TODO add your handling code here:
        String value = JOptionPane.showInputDialog(null, "Introduza a data, para obter as contas com movimentos realizados nessa mesma:");
        JFrame accountsWithMovementsListFrame = new JFrame("Lista de contas com movimentos realizados na data");
        JComponent component = (JComponent) accountsWithMovementsListFrame.getContentPane();
        String[] list = {"one ******************************************************** saldo",
            "two *********************************************** saldo",
            "three ********************************************* saldo"};
        JList accountList = new JList(list);
        accountList.setSize(600, 700);
        component.add(accountList);
        accountsWithMovementsListFrame.pack();
        accountsWithMovementsListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        accountsWithMovementsListFrame.setVisible(true);
    }//GEN-LAST:event_listOfAccountsWithMovements_menuActionPerformed

    private void listOfAccountsWithNoAmount_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listOfAccountsWithNoAmount_menuActionPerformed
        // TODO add your handling code here:
        JFrame accountsWithNoAmountListFrame = new JFrame("Lista de contas sem saldo disponível");
        JComponent component = (JComponent) accountsWithNoAmountListFrame.getContentPane();
        String[] list = {"one ******************************************************** saldo",
            "two *********************************************** saldo",
            "three ********************************************* saldo"};
        JList accountList = new JList(list);
        accountList.setSize(600, 700);
        component.add(accountList);
        accountsWithNoAmountListFrame.pack();
        accountsWithNoAmountListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        accountsWithNoAmountListFrame.setVisible(true);
    }//GEN-LAST:event_listOfAccountsWithNoAmount_menuActionPerformed

    private void listOfClientsWithNumberOfAccountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listOfClientsWithNumberOfAccountsActionPerformed
        // TODO add your handling code here:
        String value = JOptionPane.showInputDialog(null, "Introduza um número, para obter clientes com o número de contas igual ou superior à mesma:");
        JFrame clientsWithNrOfAccountsListFrame = new JFrame("Lista de clientes com número de contas igual ou superior ao valor");
        JComponent component = (JComponent) clientsWithNrOfAccountsListFrame.getContentPane();
        String[] list = {"one ******************************************************** saldo",
            "two *********************************************** saldo",
            "three ********************************************* saldo"};
        JList accountList = new JList(list);
        accountList.setSize(600, 700);
        component.add(accountList);
        clientsWithNrOfAccountsListFrame.pack();
        clientsWithNrOfAccountsListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        clientsWithNrOfAccountsListFrame.setVisible(true);
    }//GEN-LAST:event_listOfClientsWithNumberOfAccountsActionPerformed

    private void listOfClientsWithNoAccounts_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listOfClientsWithNoAccounts_menuActionPerformed
        // TODO add your handling code here:
        JFrame clientsWithNoAccountsListFrame = new JFrame("Lista de clientes sem contas associadas");
        JComponent component = (JComponent) clientsWithNoAccountsListFrame.getContentPane();
        String[] list = {"one ******************************************************** saldo",
            "two *********************************************** saldo",
            "three ********************************************* saldo"};
        JList accountList = new JList(list);
        accountList.setSize(600, 700);
        component.add(accountList);
        clientsWithNoAccountsListFrame.pack();
        clientsWithNoAccountsListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        clientsWithNoAccountsListFrame.setVisible(true);
    }//GEN-LAST:event_listOfClientsWithNoAccounts_menuActionPerformed

    private void listOfClientsWithSumAmountAllAccounts_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listOfClientsWithSumAmountAllAccounts_menuActionPerformed
        // TODO add your handling code here:
        JFrame sumAccountAmmountListFrame = new JFrame("Lista de clientes com a soma do saldo das contas que possui");
        JComponent component = (JComponent) sumAccountAmmountListFrame.getContentPane();
        String[] list = {"one ******************************************************** saldo",
            "two *********************************************** saldo",
            "three ********************************************* saldo"};
        JList accountList = new JList(list);
        accountList.setSize(600, 700);
        component.add(accountList);
        sumAccountAmmountListFrame.pack();
        sumAccountAmmountListFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        sumAccountAmmountListFrame.setVisible(true);
    }//GEN-LAST:event_listOfClientsWithSumAmountAllAccounts_menuActionPerformed

    private void listClients_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listClients_btnActionPerformed
        // TODO add your handling code here:
        JFrame clientsList = new ListClients(bankID);
        clientsList.setVisible(true);     
    }//GEN-LAST:event_listClients_btnActionPerformed

    private void listAccounts_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listAccounts_btnActionPerformed
        // TODO add your handling code here:
        JFrame accountsList = new ListAccounts(bankID);
        accountsList.setVisible(true);
    }//GEN-LAST:event_listAccounts_btnActionPerformed

    private void changeClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeClientBtnActionPerformed
        // TODO add your handling code here:
        int clientNIF = Integer.valueOf(JOptionPane.showInputDialog(null, "Introduza o NIF do cliente que pretende alterar:"));
        MysqlClientDAO client = null;
        Client c = null;
        try {
            client = new MysqlClientDAO();
            c = client.findClient(clientNIF);
        } catch (UnknownRegistException ex){
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFrame change = new ChangeClient(c, bankID);
        change.setVisible(true);
    }//GEN-LAST:event_changeClientBtnActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void consultAccountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultAccountBtnActionPerformed
        // TODO add your handling code here:
        int accountID = Integer.valueOf(JOptionPane.showInputDialog(null, "Introduza o ID da conta que pretende consultar:"));
        try {
            JFrame infoAccount = new JFrame("Info Account: " + accountID);
            JTextArea info = new JTextArea();
            currentAccountDAO = new MysqlCurrentAccountDAO();
            ArrayList<CurrentAccount> allCurrent = currentAccountDAO.findAllCurrentAccounts(bankID);            
            for (CurrentAccount c : allCurrent){
                if (c.getCurrentAccountID() == accountID)
                    info.setText(c.toString());
            }
            savingsAccountDAO = new MysqlSavingsAccountDAO();
            ArrayList<SavingsAccount> allSavings = savingsAccountDAO.findAllSavingsAccount(bankID);
            for (SavingsAccount s : allSavings){
                if (s.getSavingsAccountID() == accountID)
                    info.setText(s.toString());
            }
            infoAccount.add(info);
            infoAccount.setVisible(true);
        } catch (EmptySetException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_consultAccountBtnActionPerformed

    private void removeClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeClientBtnActionPerformed
        // TODO add your handling code here:
        int clientNIF = Integer.valueOf(JOptionPane.showInputDialog(null, "Introduza o NIF do cliente que pretende remover:"));
        try {
            clientDao = new MysqlClientDAO();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (clientDao.deleteClient(clientNIF))
                JOptionPane.showMessageDialog(null, "Client removed", "Removing Client", JOptionPane.OK_OPTION);
            else
                JOptionPane.showMessageDialog(null, "Error removing client", "Removing Client", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_removeClientBtnActionPerformed

    private void removeAccountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAccountBtnActionPerformed
        // TODO add your handling code here:
        int accountID = Integer.valueOf(JOptionPane.showInputDialog(null, "Introduza o ID da conta que pretende remover:"));
        try {
            currentAccountDAO = new MysqlCurrentAccountDAO();
            ArrayList<CurrentAccount> allCurrent = currentAccountDAO.findAllCurrentAccounts(bankID);            
            for (CurrentAccount c : allCurrent){
                if (c.getCurrentAccountID() == accountID)
                    currentAccountDAO.deleteCurrentAccount(c);
            }
            savingsAccountDAO = new MysqlSavingsAccountDAO();
            ArrayList<SavingsAccount> allSavings = savingsAccountDAO.findAllSavingsAccount(bankID);
            for (SavingsAccount s : allSavings){
                if (s.getSavingsAccountID() == accountID)
                    savingsAccountDAO.deleteSavingsAccount(s);
            }
        } catch (EmptySetException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Account Removed", "Removing Account", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_removeAccountBtnActionPerformed

    private void makeDeposits_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeDeposits_btnActionPerformed
        // TODO add your handling code here:
        int accountID = Integer.valueOf(JOptionPane.showInputDialog(null, 
                "Introduza o nº da conta que pretende fazer depósito:", 
                "Fazer Depósitos", JOptionPane.INFORMATION_MESSAGE));
        try {
                currentAccountDAO = new MysqlCurrentAccountDAO();
                CurrentAccount c = currentAccountDAO.findCurrentAccountByID(accountID);
                makeCurrentDeposits(c);
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownRegistException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_makeDeposits_btnActionPerformed

    private void makeWithDrawalsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeWithDrawalsBtnActionPerformed
        // TODO add your handling code here:
        int accountID = Integer.valueOf(JOptionPane.showInputDialog(null, 
                "Introduza o nº da conta que pretende fazer levantamento:", 
                "Fazer Levantamento", JOptionPane.INFORMATION_MESSAGE));
        try {
            currentAccountDAO = new MysqlCurrentAccountDAO();
            savingsAccountDAO = new MysqlSavingsAccountDAO();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        CurrentAccount c;
        SavingsAccount s;
        try {
            if ((c = currentAccountDAO.findCurrentAccountByID(accountID)) != null){
                makeCurrentWithdrawals(c);
            }
            if ((s = savingsAccountDAO.findSavingsAccountByID(accountID)) !=null){
                if (s.getSavingsAccountTypeID() == 2){
                    JOptionPane.showMessageDialog(null, "Não é premitido fazer levantamentos neste tipo de conta", "Erro:", JOptionPane.ERROR_MESSAGE);
                }else if (s.getSavingsAccountTypeID() == 1){
                    makeSavingsWithDrawals(s);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownRegistException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_makeWithDrawalsBtnActionPerformed

    public void makeCurrentDeposits(CurrentAccount c){
        Double amount = Double.valueOf(JOptionPane.showInputDialog(null, "Introduza a montante que pretende depositar:", "Fazendo Depósito:", JOptionPane.INFORMATION_MESSAGE));
        try{
            c.setCurrentAmount(c.getCurrentAmount() + amount);

            try{
                currentAccountDAO = new MysqlCurrentAccountDAO();
                currentAccountDAO.updateCurrentAccount(c);
                CurrentMovementHistory current = new CurrentMovementHistory(c.getCurrentAccountID(), "Deposito", amount, refeDate);
                currentHistoricDeposit(current);
            } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownRegistException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch (NullPointerException nu){
            Logger.getAnonymousLogger(nu.toString());
        }
    }
    
    public void currentHistoricDeposit(CurrentMovementHistory current) throws SQLException, ClassNotFoundException{
        MysqlCurrentMovementHistoryDAO currentHistoryDAO = new MysqlCurrentMovementHistoryDAO();
        currentHistoryDAO.insertCurrentMovementHistory(current);
    }
    
    public void makeCurrentWithdrawals(CurrentAccount c){
        Double amount = Double.valueOf(JOptionPane.showInputDialog(null, "Introduza a montante que pretende levantar:", "Fazendo Levantamento:", JOptionPane.INFORMATION_MESSAGE));
        c.setCurrentAmount(c.getCurrentAmount() - amount);
        try{
            currentAccountDAO = new MysqlCurrentAccountDAO();
            currentAccountDAO.updateCurrentAccount(c);
            CurrentMovementHistory current = new CurrentMovementHistory(c.getCurrentAccountID(), "Levantamento", amount, refeDate);
            currentHistoricWithDrawals(current);
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownRegistException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void currentHistoricWithDrawals(CurrentMovementHistory current) throws ClassNotFoundException, SQLException{
        MysqlCurrentMovementHistoryDAO currentHistoryDAO = new MysqlCurrentMovementHistoryDAO();
        currentHistoryDAO.insertCurrentMovementHistory(current);
    }
    
    public void makeSavingsWithDrawals(SavingsAccount s){
        Double amount = 0.0;
        do{
            amount = Double.valueOf(JOptionPane.showInputDialog(null, "Introduza a montante que pretende levantar:", "Fazendo Levantamento:", JOptionPane.INFORMATION_MESSAGE));
        }while (amount != s.getInitialAmount());
        try{
            savingsAccountDAO = new MysqlSavingsAccountDAO();
            SavingsMovementHistory savings = new SavingsMovementHistory(s.getSavingsAccountID(), "Levantamento", amount, refeDate);
            savingsHistoricWithDrawals(savings);
            accountClosedown(s.getSavingsAccountID());
        } catch (EmptySetException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownRegistException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void accountClosedown(int accountID) throws SQLException, 
            UnknownRegistException, ClassNotFoundException, EmptySetException{
        MysqlCurrentAccountDAO currentAccounts;
        MysqlSavingsAccountDAO savingsAccounts;
        currentAccounts = new MysqlCurrentAccountDAO();
        ArrayList<CurrentAccount> allCurrentAccounts = currentAccounts.findAllCurrentAccounts(bankID); 
        for (CurrentAccount c : allCurrentAccounts){
            if (c.getCurrentAccountID() == accountID){
                currentAccounts = new MysqlCurrentAccountDAO();
                currentAccounts.deleteCurrentAccount(c);
            }
        }
        savingsAccounts = new MysqlSavingsAccountDAO();
        ArrayList<SavingsAccount> allSavingsAccounts = savingsAccounts.findAllSavingsAccount(bankID);
        for (SavingsAccount s : allSavingsAccounts){
            if (s.getSavingsAccountID() == accountID){
                savingsAccounts = new MysqlSavingsAccountDAO();
                savingsAccounts.deleteSavingsAccount(s);
            }
        }        
    }
    
    public void savingsHistoricWithDrawals(SavingsMovementHistory s) throws SQLException, ClassNotFoundException{
        MysqlSavingsMovementHistoryDAO savingsHistoryDAO = new MysqlSavingsMovementHistoryDAO();
        savingsHistoryDAO.insertSavingsMovementHistory(s);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeBank_menu;
    private javax.swing.JButton changeClientBtn;
    private javax.swing.JButton changeRefDate_btn;
    private javax.swing.JLabel clientsBank_lbl;
    private javax.swing.JButton consultAccountBtn;
    private javax.swing.JLabel currentAccounts_lbl;
    private javax.swing.JLabel goldSavingsAccount_lbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JButton listAccounts_btn;
    private javax.swing.JButton listClients_btn;
    private javax.swing.JMenuItem listCurrentAccountswithAmount_menu;
    private javax.swing.JMenu listMenu;
    private javax.swing.JMenuItem listOfAccountsWithMovements_menu;
    private javax.swing.JMenuItem listOfAccountsWithNoAmount_menu;
    private javax.swing.JMenuItem listOfClientsWithNoAccounts_menu;
    private javax.swing.JMenuItem listOfClientsWithNumberOfAccounts;
    private javax.swing.JMenuItem listOfClientsWithSumAmountAllAccounts_menu;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton makeDeposits_btn;
    private javax.swing.JButton makeWithDrawalsBtn;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton newClientBtn;
    private javax.swing.JButton openAccountBtn;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private java.awt.Panel panel3;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel refenceDate;
    private javax.swing.JButton removeAccountBtn;
    private javax.swing.JButton removeClientBtn;
    private javax.swing.JLabel simpleSavingsAccounts_lbl;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel totalCurrentDeposits_lbl;
    private javax.swing.JLabel totalGoldDeposits_lbl;
    private javax.swing.JLabel totalSimpleDeposits_lbl;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
