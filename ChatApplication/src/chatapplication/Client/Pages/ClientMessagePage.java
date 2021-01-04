/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client.Pages;

import chatapplication.Client.Client;
import chatapplication.Client.Pages.Components.MessageComponent;
import chatapplication.Server.Message;
import chatapplication.Server.Server;
import chatapplication.Server.ServerClientModel;
import chatapplication.Server.ServerClientSerializable;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author rtanyildizi
 */
public class ClientMessagePage extends JFrame {
    Server server;
    Client client;
    List<ServerClientModel> clientList;

    public ClientMessagePage() {
        initComponents();
    }

    /**
     * Yeni bir ClientMessagePage sayfası oluşturur.
     * @param client
     */
    public ClientMessagePage(Client client) {
        this.client = client;
        this.client.getEventHandler().addWorkerClientListener(new ClientMessagePage_ClientWorkerListener(this));
        this.client.getAllUsers();
        this.clientList = Collections.synchronizedList(new ArrayList<>());
        initComponents();
    }

    /**
     * tfMessage içerisindeki mesajı okur ve Client tarafından bağlanılan Server'a gönderir.
     */
    public void sendMessage() {
        final String message = tfMessage.getText();
        this.client.sendMessage(message);
        tfMessage.setText("");
    }
    
    /**
     * Client ile Server arasındaki bağlantıyı koparır.
     */
    void disconnect() {   
        try {
            this.client.disconnect();
            this.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error occured while disconnecting from server. Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 
     */
    void changeUsername(){
        String newUsername = tfChangeUsername.getText().trim();
        if(newUsername != null && !"".equals(newUsername)){
            this.client.changeUsername(newUsername);
        }else{
            JOptionPane.showMessageDialog(this, "Username can not be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void onClientList(ServerClientSerializable[] clientList){
        String[] usernames = new String[clientList.length];
        for (int i = 0; i < usernames.length; i++) {
            usernames[i] = clientList[i].getUsername();
        }
        lsUsers.setListData(usernames);
    }
    
    public void onClientMessageSent(Message messageSentByUsername){
        MessageComponent messageComp = new MessageComponent(messageSentByUsername);
        pnlMessageBoard.add(messageComp);
        pnlMessageBoard.repaint();
        this.revalidate();
        System.out.println(messageSentByUsername);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfMessage = new javax.swing.JTextField();
        btnSendMessage = new javax.swing.JButton();
        btnDisconnect = new javax.swing.JButton();
        btnChangeUsername = new javax.swing.JButton();
        tfChangeUsername = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsUsers = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        pnlMessageBoard = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tfMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMessageActionPerformed(evt);
            }
        });

        btnSendMessage.setText("Send");
        btnSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMessageActionPerformed(evt);
            }
        });

        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        btnChangeUsername.setText("Change Username");
        btnChangeUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeUsernameActionPerformed(evt);
            }
        });

        lsUsers.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lsUsers);

        jLabel1.setText("Users");

        pnlMessageBoard.setLayout(new javax.swing.BoxLayout(pnlMessageBoard, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane.setViewportView(pnlMessageBoard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addComponent(jScrollPane))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDisconnect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(tfChangeUsername)
                            .addComponent(btnChangeUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSendMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(58, 58, 58))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDisconnect)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(tfChangeUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChangeUsername))
                    .addComponent(jScrollPane))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSendMessage))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfMessageActionPerformed
        this.sendMessage();
    }//GEN-LAST:event_tfMessageActionPerformed

    private void btnSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMessageActionPerformed
        this.sendMessage();
    }//GEN-LAST:event_btnSendMessageActionPerformed

    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        this.disconnect();
    }//GEN-LAST:event_btnDisconnectActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.disconnect();
    }//GEN-LAST:event_formWindowClosing

    private void btnChangeUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeUsernameActionPerformed
        this.changeUsername();
    }//GEN-LAST:event_btnChangeUsernameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeUsername;
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnSendMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lsUsers;
    private javax.swing.JPanel pnlMessageBoard;
    private javax.swing.JTextField tfChangeUsername;
    private javax.swing.JTextField tfMessage;
    // End of variables declaration//GEN-END:variables
}
