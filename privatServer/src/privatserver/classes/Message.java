/*
 * Авторство Паршина Александра
 * По всем вопросам писать на e-mail parshin_sashek@mail.ru
 */
package privatserver.classes;

import java.util.List;

/**
 *
 * @author parsh
 */
public class Message
{
    public static List<Deposit> deposits = Deposit.getDeposits();
    public static StringBuilder findAccount(Long id)
    {
        StringBuilder buff = new StringBuilder();
        boolean isFind = false;

        if (id > 0)
        {
            for (int index = 0; index < deposits.size(); index++)
            {
                if (deposits.get(index).getAccountId() == id)
                {
                    isFind = true;
                    buff = deposits.get(index).getString();
                    break;
                }
            }
        }
        if (isFind)
        {
            return buff;
        }
        else
        {
            return new StringBuilder("Такого аккаунта не существует в базе");
        }
    }

    public static StringBuilder LIST()
    {
        StringBuilder buff = new StringBuilder();
        boolean isEmpty = false;
        if (deposits.isEmpty())
        {
            isEmpty = true;
        }

        else
        {
            for (int index = 0; index < deposits.size(); index++)
            {
                buff.append(deposits.get(index).getString());
            }
        }

        if (!isEmpty)
        {
            return buff;
        }
        else
        {
            return new StringBuilder("В базе нету депозитов");
        }
    }

    public static StringBuilder findDepositor(String Depositor)
    {
        StringBuilder buff = new StringBuilder();
        boolean isFind = false;
        for (int index = 0; index < deposits.size(); index++)
        {
            if (deposits.get(index).getDepositor().equals(Depositor))
            {
                isFind = true;
                buff.append(deposits.get(index).getString());
            }
        }

        if (isFind)
        {
            return buff;
        }
        else
        {
            return new StringBuilder("Такого вкладчика не существует в базе");
        }
    }

    public static StringBuilder showType(String Type)
    {
        StringBuilder buff = new StringBuilder();
        boolean isFind = false;

        for (int index = 0; index < deposits.size(); index++)
        {
            if (deposits.get(index).getType().equals(Type))
            {
                isFind = true;
                buff.append(deposits.get(index).getString());
            }
        }

        if (isFind)
        {
            return buff;
        }
        else
        {
            return new StringBuilder("Таких вкладов не существует в базе");
        }
    }

    public static StringBuilder showBank(String Name)
    {
        StringBuilder buff = new StringBuilder();
        boolean isFind = false;

        for (int index = 0; index < deposits.size(); index++)
        {
            if (deposits.get(index).getNameBank().equals(Name))
            {
                isFind = true;
                buff.append(deposits.get(index).getString());
            }
        }

        if (isFind)
        {
            return buff;
        }
        else
        {
            return new StringBuilder("Такого банка не существует в базе");
        }
    }

    public static boolean findId(long id)
    {
        boolean isFind = false;
        if (id > 0)
        {
            for (int index = 0; index < deposits.size(); index++)
            {
                if (deposits.get(index).getAccountId() == id)
                {
                    isFind = true;
                    break;
                }
            }
        }
        return isFind;
    }

    public static String addAccount(Deposit a)
    {
        try
        {
            deposits.add(a);
            XML.createXML(deposits);
            Deposit.setAllSumDeposits(Deposit.getAllSumDeposits().add(a.getAmountOnDeposit()));
        }
        catch (Exception e)
        {
            return "Ошибка";
        }
        return "Okey";
    }

    public static StringBuilder deleteAccount(Long id)
    {
        StringBuilder buff = new StringBuilder();
        boolean isFind = false;
        if (id > 0)
        {
            for (int index = 0; index < deposits.size(); index++)
            {
                if (deposits.get(index).getAccountId() == id)
                {
                    isFind = true;
                    buff = deposits.get(index).getString();
                    Deposit.setAllSumDeposits(Deposit.getAllSumDeposits().subtract(deposits.get(index).getAmountOnDeposit()));
                    deposits.remove(index);
                    break;
                }
            }
        }
        if (isFind)
        {
            //createXML(deposits);
            return buff;
        }
        else
        {
            return new StringBuilder("Такого айди не существует в базе");
        }
    }
    
}
