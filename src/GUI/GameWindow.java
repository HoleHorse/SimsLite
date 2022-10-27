package GUI;

import GamePlay.GamePlay;
import Mongo.Mongo;

import javax.swing.*;
import java.util.ArrayList;

public class GameWindow extends javax.swing.JFrame {
    static ArrayList<String> lines = new ArrayList<>();
    private static final javax.swing.JTextArea TextArea = new javax.swing.JTextArea();
    private static final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

    public GameWindow() {
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TextArea.setColumns(20);
        TextArea.setRows(5);
        jScrollPane1.setViewportView(TextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    public static void createWindow() {
        GameWindow g = new GameWindow();
        g.setVisible(true);
        GamePlay.play();
    }

    public static void appendText(String text) {
        int len = lines.size();
        if (len % 33 == 0 && len > 32) {
            TextArea.setText("");
            TextArea.append(lines.get(len - 3));
            TextArea.append(lines.get(len - 2));
            TextArea.append(lines.get(len - 1));
        }
        TextArea.append(text);
        lines.add(text);
    }

    public static String getText() {
        javax.swing.JFrame f = new JFrame();
        String ans = JOptionPane.showInputDialog(f, "Enter your choice (cancel or exit to end the game)");
        if (ans == null) {
            GamePlay.saveGame();
            Mongo.saveLog();
            System.exit(1);
        }
        return ans;
    }

    public static ArrayList<String> getLines() {
        return lines;
    }
}
