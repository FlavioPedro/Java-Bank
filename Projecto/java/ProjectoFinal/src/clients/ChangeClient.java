/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CreateClient.java
 *
 * Created on 30/Mar/2011, 23:41:59
 */

package clients;

import Beans.Client;
import DAO.MySQLExceptions.UnknownRegistException;
import DAO.MysqlDAO.MysqlClientDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author jhorgemiguel
 */
public class ChangeClient extends javax.swing.JFrame {

    int BankID;
    
    /** Creates new form CreateClient */
    public ChangeClient() {
        initComponents();
    }
    
    public ChangeClient(Client c){
        this();
        clientName_txt.setText(c.getName());
        clientAddress_txt.setText(c.getAddress());
        clientZipCode_txt.setText(c.getZipCode());
        clientMail_txt.setText(c.getMail());
        clientContact_txt.setText(c.getContact());
        clientDocNumber_txt.setText(c.getDocNumber());
        clientBirthDate_txt.setText(c.getBirthDate().toString());
        clientNIF_txt.setText(String.valueOf(c.getClientNIF()));
        if (c.getSex() == "M")
            clientSexMale_rbn.setEnabled(true);
        else
            clientSexFemale_btn.setEnabled(true);
        if (c.getDocType() == "B. Identidade")
            clientDocType_cmb.setSelectedIndex(0);
        else if (c.getDocType() == "C. Cidadao")
            clientDocType_cmb.setSelectedIndex(1);
        else if (c.getDocType() == "Passaporte")
            clientDocType_cmb.setSelectedIndex(2);
        else
            clientDocType_cmb.setSelectedIndex(3);
    }

    public ChangeClient(Client c, int bankID){
        this(c);
        this.BankID = bankID;
    }
    
    public void missingField(){
        String message = "Por favor introduza todos os campos obrigatórios.";
        JOptionPane.showMessageDialog(new JFrame(), message, "Erro",
        JOptionPane.ERROR_MESSAGE); 
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        clientName_txt = new javax.swing.JTextField();
        clientAddress_txt = new javax.swing.JTextField();
        clientZipCode_txt = new javax.swing.JTextField();
        clientMail_txt = new javax.swing.JTextField();
        clientContact_txt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        clientDocType_cmb = new javax.swing.JComboBox();
        clientDocNumber_txt = new javax.swing.JTextField();
        clientBirthDate_txt = new javax.swing.JTextField();
        clientSexMale_rbn = new javax.swing.JRadioButton();
        clientSexFemale_btn = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        clientNIF_txt = new javax.swing.JTextField();
        changeClient_btn = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(Principal.PrincipalMenuApp.class).getContext().getResourceMap(ChangeClient.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        clientName_txt.setText(resourceMap.getString("txt_clientName.text")); // NOI18N
        clientName_txt.setName("txt_clientName"); // NOI18N

        clientAddress_txt.setText(resourceMap.getString("txt_clientAddress.text")); // NOI18N
        clientAddress_txt.setName("txt_clientAddress"); // NOI18N

        clientZipCode_txt.setText(resourceMap.getString("txt_clientPostal.text")); // NOI18N
        clientZipCode_txt.setName("txt_clientPostal"); // NOI18N

        clientMail_txt.setText(resourceMap.getString("txt_clientMail.text")); // NOI18N
        clientMail_txt.setName("txt_clientMail"); // NOI18N

        clientContact_txt.setText(resourceMap.getString("txt_clientContact.text")); // NOI18N
        clientContact_txt.setName("txt_clientContact"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientZipCode_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientMail_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientContact_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(clientAddress_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .add(clientName_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel1)
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(clientName_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(clientAddress_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(clientZipCode_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(clientMail_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(clientContact_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(10, 10, 10))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        clientDocType_cmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "B. Identidade", "C. Cidadao", "Passporte", "Outro" }));
        clientDocType_cmb.setName("cmb_clienteDocType"); // NOI18N
        clientDocType_cmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientDocType_cmbActionPerformed(evt);
            }
        });

        clientDocNumber_txt.setText(resourceMap.getString("txt_clientDocNum.text")); // NOI18N
        clientDocNumber_txt.setName("txt_clientDocNum"); // NOI18N

        clientBirthDate_txt.setText(resourceMap.getString("txt_clientBorn.text")); // NOI18N
        clientBirthDate_txt.setName("txt_clientBorn"); // NOI18N

        clientSexMale_rbn.setSelected(true);
        clientSexMale_rbn.setText(resourceMap.getString("rdb_clientSexM.text")); // NOI18N
        clientSexMale_rbn.setName("rdb_clientSexM"); // NOI18N

        clientSexFemale_btn.setText(resourceMap.getString("rdb_clientSexF.text")); // NOI18N
        clientSexFemale_btn.setName("rdb_clientSexF"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        clientNIF_txt.setText(resourceMap.getString("txt_clientNIF.text")); // NOI18N
        clientNIF_txt.setName("txt_clientNIF"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel7)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientDocType_cmb, 0, 274, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientDocNumber_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel14)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientNIF_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel11)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(clientBirthDate_txt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel13)
                        .add(51, 51, 51)
                        .add(clientSexMale_rbn)
                        .add(67, 67, 67)
                        .add(clientSexFemale_btn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 148, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel7)
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8)
                    .add(clientDocType_cmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(clientDocNumber_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(clientBirthDate_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(clientSexFemale_btn)
                    .add(jLabel13)
                    .add(clientSexMale_rbn))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel14)
                    .add(clientNIF_txt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        changeClient_btn.setText(resourceMap.getString("changeClient_btn.text")); // NOI18N
        changeClient_btn.setName("changeClient_btn"); // NOI18N
        changeClient_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeClient_btnMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(683, Short.MAX_VALUE)
                .add(changeClient_btn)
                .add(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(changeClient_btn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clientDocType_cmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientDocType_cmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientDocType_cmbActionPerformed

    
    
    private void changeClient_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeClient_btnMouseClicked
        // TODO add your handling code here:
        if (clientNIF_txt.getText().isEmpty() || clientName_txt.getText().isEmpty()
                | clientAddress_txt.getText().isEmpty() | clientZipCode_txt.getText().isEmpty()
                | clientContact_txt.getText().isEmpty() | clientDocNumber_txt.getText().isEmpty()
                | clientBirthDate_txt.getText().isEmpty()){
            missingField();
        } else{
            Client actualizando = new Client(Integer.parseInt(clientNIF_txt.getText()), 
                BankID, clientName_txt.getText(), clientAddress_txt.getText(), 
                clientZipCode_txt.getText(), clientContact_txt.getText(),
                clientDocType_cmb.getSelectedItem().toString(), clientDocNumber_txt.getText(),
                dateConverter(clientBirthDate_txt.getText()));
            if (clientMail_txt.getText().isEmpty()){
                actualizando.setMail(clientMail_txt.getText());
            }
            if (clientSexMale_rbn.isEnabled()){
                actualizando.setSex("M");
            } else if (clientSexFemale_btn.isEnabled()){
                actualizando.setSex("F");
            }
            MysqlClientDAO client;
            try {
                client = new MysqlClientDAO();
                try {
                    if (client.updateClient(actualizando))
                        JOptionPane.showMessageDialog(null, "Client Updated", "Client Updated", JOptionPane.OK_OPTION);
                    else
                        JOptionPane.showMessageDialog(null, "Client not Updated", "Client not Updated", JOptionPane.ERROR_MESSAGE);
                } catch (UnknownRegistException ex) {
                    Logger.getLogger(ChangeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChangeClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChangeClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }          
    }//GEN-LAST:event_changeClient_btnMouseClicked

    
        //Convert String to Date Type
    public Date dateConverter(String data){
        SimpleDateFormat deta = new SimpleDateFormat("yyyy/MM/DD");
        String birthDate = data;
        Date newDate = new Date();
        try {
            newDate = deta.parse(birthDate);
        } catch (ParseException ex) {
            Logger.getLogger(CreateClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newDate;
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChangeClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeClient_btn;
    private javax.swing.JTextField clientAddress_txt;
    private javax.swing.JTextField clientBirthDate_txt;
    private javax.swing.JTextField clientContact_txt;
    private javax.swing.JTextField clientDocNumber_txt;
    private javax.swing.JComboBox clientDocType_cmb;
    private javax.swing.JTextField clientMail_txt;
    private javax.swing.JTextField clientNIF_txt;
    private javax.swing.JTextField clientName_txt;
    private javax.swing.JRadioButton clientSexFemale_btn;
    private javax.swing.JRadioButton clientSexMale_rbn;
    private javax.swing.JTextField clientZipCode_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}