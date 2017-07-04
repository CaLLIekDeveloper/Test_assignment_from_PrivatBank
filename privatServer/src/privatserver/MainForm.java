package privatserver;

import javax.swing.text.DefaultCaret;
import privatserver.classes.Deposit;
import privatserver.classes.Server;
import privatserver.classes.XML;

/**
 *
 * @author parsh
 */
public class MainForm extends javax.swing.JFrame
{
    public static void main(String args[])
    {
        MainForm form = new MainForm();
        Server serv = new Server();
    }
    
    public static void _setLabelCount(int temp)
    {
        jLabel2.setText(Integer.toString(temp));
    }

    
    public static void _addLinePLog(String line)
    {
        pLog.append(line+"\n");
    }
    
    public static void _clearPLog()
    {
        pLog.setText(null);
    }
    



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сервер");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Количество подключений");

        jLabel2.setText("0");

        pLog.setColumns(20);
        pLog.setRows(5);
        pLog.setFocusable(false);
        jScrollPane1.setViewportView(pLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(356, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {
//GEN-HEADEREND:event_formWindowClosing
        XML.createXML(Deposit.getDeposits());
    }//GEN-LAST:event_formWindowClosing

////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public MainForm()
    {

        initComponents();
        this.setVisible(true);

        DefaultCaret caret = (DefaultCaret) pLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        XML.readXml();

        pLog.append("Количество депозитов: " + Deposit.getDeposits().size() + "\n");
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea pLog;
    // End of variables declaration//GEN-END:variables
}
