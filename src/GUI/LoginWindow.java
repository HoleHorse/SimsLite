package GUI;

import DBCon.DBCon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginWindow extends javax.swing.JFrame implements ActionListener {
    Connection con = DBCon.getCon();
    public LoginWindow() {
        initComponents();
    }

    public static void createWindow() {
        java.awt.EventQueue.invokeLater(() -> new LoginWindow().setVisible(true));
    }

    private void initComponents() {
        JLabel lblUser = new JLabel();
        JLabel lblPass = new JLabel();
        JButton btnLogin = new JButton();
        textUser = new javax.swing.JTextField();
        passF = new javax.swing.JPasswordField();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login page");
        lblUser.setText("Username:");
        lblPass.setText("Password:");
        btnLogin.setText("Login");
        btnLogin.addActionListener(this);
        passF.addActionListener(this);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnLogin)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE,
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
                                        .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textUser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passF, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnLogin)
                                .addContainerGap(31, Short.MAX_VALUE)));
        pack();
    }// </editor-fold>
    // Variables declaration - do not modify

    private javax.swing.JPasswordField passF;
    private javax.swing.JTextField textUser;

    // End of variables declaration
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = textUser.getText();
        String password = passF.getText();
        String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "';";
        String query1 = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password
                + "' AND isadmin=true;";
        try {
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    rs = st.executeQuery(query1);
                    if (rs.next()) {
                        // TODO
                    } else {
                        // TODO
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username or password or both are wrong");
                }
                st.close();
            }
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
    }
}
