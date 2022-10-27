package GUI;

import Mongo.Mongo;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;

public class AuthWindow extends javax.swing.JFrame {
    private static final AuthWindow instance = new AuthWindow();
    public AuthWindow() {
        initComponents();
    }
    private void initComponents() {

        JLabel signIn = new JLabel();
        JLabel usernameL = new JLabel();
        JLabel passwordL = new JLabel();
        JTextField usernameT = new JTextField();
        JTextField passwordT = new JTextField();
        JButton signInB = new JButton();
        JButton notRegisterB = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        signIn.setFont(new Font("sanserif", Font.PLAIN, 36));
        signIn.setText("Sign in");

        usernameL.setText("Username");

        passwordL.setText("Password");

        signInB.setText("Sign in");
        signInB.addActionListener(evt -> signIN(usernameT.getText(), passwordT.getText()));

        notRegisterB.setText("Not Registered");
        notRegisterB.addActionListener(this::openReg);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(usernameL)
                                        .addComponent(passwordL))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(signIn)
                                        .addComponent(usernameT, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordT, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(signInB)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(notRegisterB)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(signIn)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameL)
                                        .addComponent(usernameT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordL))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(signInB)
                                        .addComponent(notRegisterB))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void signIN(String username, String password) {
        Document user = Mongo.login(username, password);
        if(user != null) {
            instance.setLocation(2000, 2000);
            if(!user.getBoolean("isadmin")) {
                GameWindow.createWindow();
            } else {
                AdminWindow.createWindow();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No matching user found");
        }
    }
    private void openReg(java.awt.event.ActionEvent evt) {
        RegWindow.createWindow();
    }
    public static void createWindow() {
        java.awt.EventQueue.invokeLater(() -> instance.setVisible(true));
    }

}
