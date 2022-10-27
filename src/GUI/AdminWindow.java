package GUI;

import Mongo.Mongo;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.swing.*;

public class AdminWindow extends javax.swing.JFrame {

    private static final javax.swing.JTextArea TextArea = new javax.swing.JTextArea();
    private static final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

    public AdminWindow() {
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        TextArea.setColumns(20);
        TextArea.setRows(5);
        jScrollPane1.setViewportView(TextArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    public static void createWindow() {
        java.awt.EventQueue.invokeLater(() -> new AdminWindow().setVisible(true));
        displayLogs();
    }

    private static void displayLogs() {
        FindIterable<Document> logs = Mongo.getLog();
        for (Document d : logs) {
            TextArea.append("User: " + d.getObjectId("User_id").toString() + " Game: " + d.getObjectId("Game_id").toString() + "\n");
            for (String log : d.getList("Log", String.class)) {
                TextArea.append(log);
            }
        }
    }
}
