/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import chatapplication.Exceptions.InvalidPortException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author rtanyildizi
 */
public class ServerHomePage extends javax.swing.JFrame {
    MyServer myServer;

    /**
     * Creates new form ServerHomePage
     */
    public ServerHomePage() {
        initComponents();
        
    }
    
    public void startServer(){
        try {
            String portStr = tfPort.getText().trim();
            if(MyServer.checkPort(portStr)){
                myServer = new MyServer(Integer.parseInt(portStr));
                myServer.start();
                pnlServerSettings.setVisible(false);   
            }
        } catch (IOException | InvalidPortException e) {
            JOptionPane.showMessageDialog(this, "Error occured while starting server.Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlServerSettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfHost = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        pnlServerSettings.setPreferredSize(new java.awt.Dimension(400, 150));
        pnlServerSettings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("Start ChatApplication's server");
        pnlServerSettings.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        tfHost.setText("localhost");
        tfHost.setToolTipText("");
        pnlServerSettings.add(tfHost, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 40, 160, -1));
        this.tfHost.setEditable(false);

        jLabel2.setText("Host:");
        pnlServerSettings.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, -1, -1));

        jLabel3.setText("Port:");
        pnlServerSettings.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 69, -1, -1));
        pnlServerSettings.add(tfPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 66, 160, -1));

        jLabel5.setText("Server settings:");
        pnlServerSettings.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        btnStart.setText("Start Server");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        pnlServerSettings.add(btnStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 92, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlServerSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlServerSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        startServer();
    }//GEN-LAST:event_btnStartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        //</editor-fold>
        try {
            // Use the system look and feel for the swing application
            String className = UIManager.getSystemLookAndFeelClassName();
            System.out.println("className = " + className);
            UIManager.setLookAndFeel(className);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ServerHomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        ServerHomePage serverHomePage = new ServerHomePage();
        SwingUtilities.updateComponentTreeUI(serverHomePage);  
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            serverHomePage.pack();
            serverHomePage.setLocationRelativeTo(null);
            serverHomePage.setVisible(true);
         });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel pnlServerSettings;
    private javax.swing.JTextField tfHost;
    private javax.swing.JTextField tfPort;
    // End of variables declaration//GEN-END:variables
}
