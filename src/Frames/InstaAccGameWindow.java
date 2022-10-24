package Frames;

public class InstaAccGameWindow extends javax.swing.JFrame {

    public InstaAccGameWindow() {
        initComponents();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        Details = new javax.swing.JButton();
        Eat = new javax.swing.JButton();
        Switch = new javax.swing.JButton();
        Unsub = new javax.swing.JButton();
        Work = new javax.swing.JButton();
        SeeSavings = new javax.swing.JButton();
        GetDrink = new javax.swing.JButton();
        Post = new javax.swing.JButton();
        Exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        Details.setText("Details");

        Eat.setText("Eat");

        Switch.setText("Switch");

        Unsub.setText("Unsub.");

        Work.setText("Work");

        SeeSavings.setText("See savings");

        GetDrink.setText("Get drink");

        Post.setText("Post");

        Exit.setText("Exit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(Post, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Details, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Switch, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Eat, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Unsub, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Work, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(SeeSavings, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(GetDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(Post)
                                        .addComponent(Details)
                                        .addComponent(Switch)
                                        .addComponent(Eat)
                                        .addComponent(Unsub)
                                        .addComponent(Exit)
                                        .addComponent(Work)
                                        .addComponent(SeeSavings)
                                        .addComponent(GetDrink))
                                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>

    public static void createWindow() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InstaAccGameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton Details;
    private javax.swing.JButton Eat;
    private javax.swing.JButton Exit;
    private javax.swing.JButton GetDrink;
    private javax.swing.JButton Post;
    private javax.swing.JButton SeeSavings;
    private javax.swing.JButton Switch;
    private javax.swing.JButton Unsub;
    private javax.swing.JButton Work;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration
}
