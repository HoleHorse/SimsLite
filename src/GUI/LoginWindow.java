package GUI;

import DBcon.DBcon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginWindow extends javax.swing.JFrame implements ActionListener {
    Connection con = DBcon.getCon();
    public LoginWindow() {
        initComponents();
    }

    public static void createWindow() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    private void initComponents() {
        LblUser = new javax.swing.JLabel();
        LblPass = new javax.swing.JLabel();
        BtnLogin = new javax.swing.JButton();
        textUser = new javax.swing.JTextField();
        passF = new javax.swing.JPasswordField();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login page");
        LblUser.setText("Username:");
        LblPass.setText("Password:");
        BtnLogin.setText("Login");
        BtnLogin.addActionListener(this);
        passF.addActionListener(this);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(BtnLogin)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(LblPass, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(LblUser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(textUser)
                                                        .addComponent(passF, javax.swing.GroupLayout.DEFAULT_SIZE, 180,
                                                                Short.MAX_VALUE))))
                                .addContainerGap(55, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textUser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passF, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(BtnLogin)
                                .addContainerGap(31, Short.MAX_VALUE)));
        pack();
    }// </editor-fold>
    // Variables declaration - do not modify

    private javax.swing.JButton BtnLogin;
    private javax.swing.JLabel LblPass;
    private javax.swing.JLabel LblUser;
    private javax.swing.JPasswordField passF;
    private javax.swing.JTextField textUser;

    // End of variables declaration
    @Override
    public void actionPerformed(ActionEvent e) {
        Statement st = null;
        String username = textUser.getText();
        String password = passF.getText();
        String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "';";
        String query1 = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password
                + "' AND isadmin=true;";
        try {
            if (con != null) {
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    rs = st.executeQuery(query1);
                    if (rs.next()) {
                        // TODO
                    } else
                        // TODO
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username or password or both wrong");
                }
                st.close();
            }
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
    }
}
