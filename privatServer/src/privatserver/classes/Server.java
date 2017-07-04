/*
 * Авторство Паршина Александра
 * По всем вопросам писать на e-mail parshin_sashek@mail.ru
 */
package privatserver.classes;

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
import privatserver.MainForm;
import util.Patterns;


/**
 *
 * @author parsh
 */
public class Server
{
    static private ServerSocket server;
    static private int port = 5678;
    static private Socket connection;
    static private ObjectInputStream input;
    static private ObjectOutputStream output;
    
    static private int countConnections = 0;
    static private int countName = 0;
    
    public Server()
    {
        try
        {
            server = new ServerSocket(port);
            MainForm._addLinePLog("Сервер запущен на порте:" + Integer.toString(port));
            while (true)
            {
                Socket socket = server.accept();

                Server.Connection con = new Server.Connection(socket);
                con.start();
            }
        }
        catch (IOException e)
        {
            MainForm._addLinePLog("Ошибка: Порт " + port + " занят");
            MainForm._addLinePLog("Освободите порт и перезаупстите программу");
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
                MainForm._addLinePLog("Ошибка Connection");
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
                        MainForm._addLinePLog(name + " отключился.");
                        countConnections--;
                        MainForm._setLabelCount(countConnections);
                        return false;
                    }
                    else
                    {
                        MainForm._addLinePLog("Запрос от (" + name + "): " + str);

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
                name += Integer.toString(countName + 1);
                MainForm._addLinePLog("Новое подключение: " + name);
                countConnections++;
                countName++;
                MainForm._setLabelCount(countConnections);
                
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
                MainForm._addLinePLog("Ошибка закрытия сокета");
            }
        }
    }
}
