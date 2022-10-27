package GUI;

import Mongo.Mongo;

import javax.swing.*;
import java.awt.*;

public class RegWindow extends javax.swing.JFrame {
    public RegWindow() {
        initComponents();
    }

    private void initComponents() {

        javax.swing.JTextField passwordT = new javax.swing.JTextField();
        javax.swing.JButton regBtn = new javax.swing.JButton();
        javax.swing.JLabel register = new javax.swing.JLabel();
        javax.swing.JLabel usernameL = new javax.swing.JLabel();
        javax.swing.JLabel passwordL = new javax.swing.JLabel();
        javax.swing.JTextField usernameT = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        regBtn.setText("Register");

        register.setFont(new java.awt.Font("sansserif", Font.PLAIN, 36)); // NOI18N
        register.setText("Register");

        usernameL.setText("Username");

        passwordL.setText("Password");

        regBtn.addActionListener(evt -> Register(usernameT.getText(), passwordT.getText()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(register)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(passwordL)
                                                .addGap(21, 21, 21)
                                                .addComponent(passwordT))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(usernameL)
                                                .addGap(18, 18, 18)
                                                .addComponent(usernameT, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(regBtn)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(register)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameL)
                                        .addComponent(usernameT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordL))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(regBtn)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void Register(String username, String password) {
        if(!Mongo.isValid(username, password)) {
            JOptionPane.showMessageDialog(this,
                    Mongo.specify(username, password));
        } else {
            Mongo.register(username, password);
            dispose();
        }
    }

    public static void createWindow() {
        java.awt.EventQueue.invokeLater(() -> new RegWindow().setVisible(true));
    }

}
