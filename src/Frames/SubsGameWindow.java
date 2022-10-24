package Frames;

public class SubsGameWindow extends javax.swing.JFrame {

    public SubsGameWindow() {
        initComponents();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TextArea = new javax.swing.JTextArea();
        ReadNew = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        RaedOld = new javax.swing.JButton();
        Sub = new javax.swing.JButton();
        Switch = new javax.swing.JButton();
        Unsub = new javax.swing.JButton();
        Work = new javax.swing.JButton();
        Eat = new javax.swing.JButton();
        SeeSavings = new javax.swing.JButton();
        GetDrink = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TextArea.setColumns(20);
        TextArea.setRows(5);
        jScrollPane1.setViewportView(TextArea);

        ReadNew.setText("Read new");

        Exit.setText("Exit");

        RaedOld.setText("Read old");

        Sub.setText("Sub.");

        Switch.setText("Switch");

        Unsub.setText("Unsub.");

        Work.setText("Work");

        Eat.setText("Eat");

        SeeSavings.setText("See savings");

        GetDrink.setText("Get drink");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(ReadNew, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(RaedOld, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Switch, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Sub, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Unsub, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Work, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Eat, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addComponent(SeeSavings, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(GetDrink, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ReadNew)
                                        .addComponent(RaedOld)
                                        .addComponent(Switch)
                                        .addComponent(Sub)
                                        .addComponent(Unsub)
                                        .addComponent(Exit)
                                        .addComponent(Work)
                                        .addComponent(Eat)
                                        .addComponent(SeeSavings)
                                        .addComponent(GetDrink))
                                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    public static void createWindow() {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubsGameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton Eat;
    private javax.swing.JButton Exit;
    private javax.swing.JButton GetDrink;
    private javax.swing.JButton RaedOld;
    private javax.swing.JButton ReadNew;
    private javax.swing.JButton SeeSavings;
    private javax.swing.JButton Sub;
    private javax.swing.JButton Switch;
    private javax.swing.JTextArea TextArea;
    private javax.swing.JButton Unsub;
    private javax.swing.JButton Work;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration
}
