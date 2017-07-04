/*
 * Авторство Паршина Александра
 * По всем вопросам писать на e-mail parshin_sashek@mail.ru
 */
package privatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.swing.JTextArea;
import static privatclient.MainForm.inputField;
import privatclient.util.Patterns;

/**
 *
 * @author parsh
 */
public class Client
{

    static private BufferedReader in;
    static private PrintWriter out;
    static private Socket socket;

    //private static final String IP = "127.0.0.1";
    static private int port = 5678;

    static private Matcher mPort;
    static private Matcher mFill;

    static private boolean connect = false;
    static private boolean admin = false;
    static private final String PASSWORD = "112233445566";

    static private JTextArea pLog;

    static public boolean getConnect()
    {
        return connect;
    }

    static private void printInfo()
    {
        pLog.append("Краткая инструкция\n");
        pLog.append("  info  - краткая информация\n");
        pLog.append("  clear - очистить лог\n");
        pLog.append("  exit  - отключиться от сервера\n");
        pLog.append("  list  - список всех вкладов\n");
        pLog.append("  sum   - общая сумма вкладов\n");
        pLog.append("  info account <account id>  - информация по счету\n");
        pLog.append("  info depositor <depositor> - информация по имени вкладчика\n");
        pLog.append("  show type <type>           - Все вклады указанного типа\n");
        pLog.append("  show bank <name>           - Все вклады в указанном банке\n");
        pLog.append("  add <deposit info>         - добавить информацию о вкладе\n");
        pLog.append("   add <Name,Country,Type,Depositor,Id,Amount,Procent,Time>\n");
        pLog.append("  delete <account id>        - удалить счет\n");
        if (admin)
        {
            pLog.append("  fillDeposits <countAdd>    - заполнить БД депозитами\n");
        }
    }

    static public void tryConnect()
    {
        try
        {
            socket = new Socket(InetAddress.getLocalHost(), port);
            connect = true;
        }
        catch (IOException ex)
        {
            pLog.setText(null);
            pLog.append("Ошибка: Сервер не доступен\n");
            pLog.append("Запустите сервер и введите комманду \"reconect\"\n");
            connect = false;
        }

        if (connect)
        {
            pLog.setText(null);
            pLog.append("Успех: Вы подключились к серверу");
            inputField.setText("info");
            _setStreams();
        }
    }

    static private void _setStreams()
    {
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.defaultCharset()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static private void _tryMatcherPatterns()
    {
        mFill = Patterns.getFILLDEPOSITS().matcher(inputField.getText());
        mPort = Patterns.getPPORT().matcher(inputField.getText());
    }

    static public void execution() throws IOException
    {
        try
        {
        if (inputField.getText().length() > 0)
        {
            _tryMatcherPatterns();
            if (inputField.getText().equals("info"))
                {
                    printInfo();
                }
                else if (inputField.getText().equals("clear"))
                {
                    pLog.setText("");
                }
                else if (inputField.getText().equals("exit"))
                {
                    pLog.append("Вы отключились от сервера");
                    close();
                }
            else
            if (connect)
            {
                if (MainForm._getAutoScroll().isSelected())
                {
                    pLog.setText("");
                }

                if (inputField.getText().equals("exit"))
                {
                    out.println("exit");
                    connect = false;
                    pLog.append("Вы отключились от сервера");
                    return;
                }

                else if (inputField.getText().equals(PASSWORD))
                {
                    admin = true;
                }
                else if (mFill.matches() && admin)
                {
                    out.println(inputField.getText());
                    pLog.append("Запрос: " + inputField.getText() + "\n");
                }
                else
                {
                    out.println(inputField.getText());
                    pLog.append("Запрос: " + inputField.getText() + "\n");
                    out.flush();
                    _getAnswerFromServer();
                }
            }
            else
            {
                if (inputField.getText().equals("reconect"))tryConnect();
            }
        }
        }catch(Exception ex)
        {
            close();
        }
    }

    static private String returnUTFstring(String str)
    {
        Charset cset = Charset.forName("UTF-8");
        ByteBuffer buff = ByteBuffer.wrap(str.getBytes());
        CharBuffer chbuf = cset.decode(buff);
        String start = chbuf.toString();
        return start;
    }

    static private void _getAnswerFromServer() throws IOException
    {
        while (true)
        {
            try
            {

                String inputline = in.readLine();
                if (inputline.equals("end"))
                {
                    break;
                }
                if (inputline != null)
                {
                    pLog.append(inputline + "\n");
                }
            }
            catch (Exception e)
            {
                pLog.append("Ошибка: Подключения к серверу.");
                close();
                break;
            }
        }
    }

    static public void start()
    {
        pLog = MainForm._getPLog();
        tryConnect();
    }

    static public void close()
    {
        try
        {
            connect = false;
            out.println("exit");
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e)
        {
            pLog.append("Ошибка\n");
        }
    }

    private Client()
    {
    }
}
