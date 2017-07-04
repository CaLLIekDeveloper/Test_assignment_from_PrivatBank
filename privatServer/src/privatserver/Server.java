package privatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import privatserver.classes.Deposit;
import privatserver.classes.Message;
import privatserver.classes.XML;
import util.Patterns;

/**
 *
 * @author parsh
 */
public class Server extends javax.swing.JFrame
{
    static public void _addLinepLog(String str)
    {
        pLog.append(str+"\n");
    }
    static private ServerSocket server;
    

    static private int port = 5678;
    static private Socket connection;
    static private ObjectInputStream input;
    static private ObjectOutputStream output;

    static private int countConnections = 0;



    public static void main(String args[])
    {
        Server serv = new Server();
    }

    public void setCount()
    {
        jLabel2.setText(Integer.toString(countConnections));
    }

    
    public static JTextArea _getPlog()
    {
        return pLog;
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
    public Server()
    {

        initComponents();
        this.setVisible(true);

        DefaultCaret caret = (DefaultCaret) pLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        XML.readXml();

        pLog.append("Количество депозитов: " + Deposit.getDeposits().size() + "\n");

        try
        {
            server = new ServerSocket(port);
            pLog.append("Сервер запущен на порте:" + Integer.toString(port) + "\n");
            while (true)
            {
                Socket socket = server.accept();

                Connection con = new Connection(socket);
                con.start();
            }
        }
        catch (IOException e)
        {
            pLog.append("Ошибка: Порт " + port + " занят\n");
            pLog.append("Освободите порт и перезаупстите программу\n");
            return;
        }
    }

    class Connection extends Thread
    {

        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private String name = "";

        public Connection(Socket socket)
        {
            this.socket = socket;

            try
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),Charset.defaultCharset()));
                out = new PrintWriter(socket.getOutputStream(), true);

            }
            catch (IOException e)
            {
                pLog.append("Ошибка Connection\n");
                close();
            }
        }

        private void outFail()
        {
            out.println("Неверный запрос");
            endMessage();
        }
        
        private void endMessage()
        {
            out.println("end");
            out.flush();
        }
        
        private boolean sheckInput(String str)
        {
            if (str.equals("exit"))
                    {
                        pLog.append(name + " отключился.\n");
                        countConnections--;
                        setCount();
                        return false;
                    }
                    else
                    {
                        pLog.append("Запрос от (" + name + "): " + str + "\n");

                        Matcher mInfoAccount = Patterns.getInfoAccount().matcher(str);
                        Matcher mInfoDepositor = Patterns.getInfoDepositor().matcher(str);
                        Matcher mShowType = Patterns.getShowType().matcher(str);
                        Matcher mShowBank = Patterns.getShowBank().matcher(str);
                        Matcher mAdd = Patterns.getAdd().matcher(str);
                        Matcher mDelete = Patterns.getDelete().matcher(str);
                        Matcher mFill = Patterns.getFillDeposits().matcher(str);

                        if (str.equals("list"))
                        {
                            out.println(Message.LIST());
                            endMessage();

                        }
                        else if (str.equals("sum"))
                        {
                            out.println("Сумма всех вкладов: " + new DecimalFormat("#").format(Deposit.getAllSumDeposits()));
                            endMessage();
                        }
                        else if (str.equals("count"))
                        {
                            out.println("Количество депозитов: " + Deposit.getDeposits().size());
                            endMessage();
                        }
                        else if (mInfoAccount.matches())
                        {
                            try
                            {
                                String id = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
                                out.println(Message.findAccount(Long.parseLong(id)));
                                endMessage();
                            }
                            catch (Exception e)
                            {
                                outFail();
                                
                            }
                        }
                        else if (mInfoDepositor.matches())
                        {
                            String Depositor = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
                            out.println(Message.findDepositor(Depositor));
                            endMessage();
                        }
                        else if (mShowType.matches())
                        {
                            String Type = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
                            out.println(Message.showType(Type));
                            endMessage();
                        }
                        else if (mShowBank.matches())
                        {
                            String Bank = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
                            out.println(Message.showBank(Bank));
                            endMessage();
                        }
                        else if (mAdd.matches())
                        {
                            String id = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
                            try
                            {
                                Deposit B = new Deposit();
                                B.setNameBank(id.substring(0, id.indexOf(',')));
                                
                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setCountry(id.substring(0, id.indexOf(',')));

                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setType(id.substring(0, id.indexOf(',')));

                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setDepositor(id.substring(0, id.indexOf(',')));

                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setAccountId(Long.parseLong(id.substring(0, id.indexOf(','))));

                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setAmountOnDeposit(new BigDecimal(id.substring(0, id.indexOf(','))));

                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setProfitability(Double.parseDouble(id.substring(0, id.indexOf(','))));
                                
                                id = id.substring(id.indexOf(',') + 1, id.length());
                                B.setTime(Integer.parseInt(id.substring(0, id.length())));
                                
                                
                                if (B.getAmountOnDeposit().compareTo(BigDecimal.ZERO) == -1
                                        || B.getAmountOnDeposit().compareTo(BigDecimal.ZERO) == 0
                                        || B.getProfitability() <= 0
                                        || B.getTime() <= 0
                                        || Message.findId(B.getAccountId()))
                                {
                                    outFail();
                                }
                                else
                                {
                                    out.println(Message.addAccount(B));
                                    endMessage();
                                }
                            }
                            catch (Exception e)
                            {
                                outFail();
                            }
                        }
                        else if (mDelete.matches())
                        {
                            try
                            {
                                String id = str.substring(str.indexOf('<') + 1, str.indexOf('>'));
                                out.println(Message.deleteAccount(Long.parseLong(id)));
                                endMessage();
                            }
                            catch (Exception e)
                            {
                                outFail();
                            }
                        }

                        else if (mFill.matches())
                        {
                            boolean ok = true;
                            String tempS = str.substring(str.indexOf('<') + 1, str.indexOf('>'));

                            try
                            {
                                int tempCount = Integer.parseInt(tempS);
                                for (int index1 = 0; index1 < tempCount; index1++)
                                {
                                    Deposit tempD = new Deposit();
                                    tempD.randomizerDeposit();
                                    Deposit.getDeposits().add(tempD);
                                    Deposit.setAllSumDeposits(Deposit.getAllSumDeposits().add(tempD.getAmountOnDeposit()));
                                }
                            }
                            catch (Exception e)
                            {
                                ok = false;
                                outFail();
                            }
                            if (ok)
                            {
                                out.println("Okey");
                                endMessage();
                            }
                        }

                        else
                        {
                            outFail();
                        }
                    }
            return true;
        }
        @Override
        public void run()
        {
            try
            {
                name = "Client ";
                name += Integer.toString(countConnections + 1);
                pLog.append("Новое подключение: " + name + "\n");
                countConnections++;
                setCount();
                
                //Work
                while (sheckInput(in.readLine())){}

            }
            catch (IOException e)
            {
            }
            finally
            {
                close();
            }

        }

        public void close()
        {
            try
            {
                in.close();
                out.close();
                socket.close();
            }
            catch (Exception e)
            {
                pLog.append("Ошибка закрытия сокета\n");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea pLog;
    // End of variables declaration//GEN-END:variables
}
