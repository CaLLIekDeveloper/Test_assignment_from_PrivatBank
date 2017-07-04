/*
 * Авторство Паршина Александра
 * По всем вопросам писать на e-mail parshin_sashek@mail.ru
 */
package util;

import java.util.regex.Pattern;

/**
 *
 * @author parsh
 */
public class Patterns
{
    //Паттерны
    static private Pattern infoAccount = Pattern.compile("^(info account <)(\\d+)\\>$");
    static private Pattern infoDepositor = Pattern.compile("^(info depositor <)(.+)\\>$");
    static private Pattern showType = Pattern.compile("^(show type <)(.+)\\>$");
    static private Pattern showBank = Pattern.compile("^(show bank <)(.+)\\>$");
    static private Pattern add = Pattern.compile("^(add <)(.+)\\>$");
    static private Pattern delete = Pattern.compile("^(delete <)(.+)\\>$");
    static private Pattern fillDeposits = Pattern.compile("^(fillDeposits <)(.+)\\>$");

    public static Pattern getInfoAccount()
    {
        return infoAccount;
    }

    public static Pattern getInfoDepositor()
    {
        return infoDepositor;
    }

    public static Pattern getShowType()
    {
        return showType;
    }

    public static Pattern getShowBank()
    {
        return showBank;
    }

    public static Pattern getAdd()
    {
        return add;
    }

    public static Pattern getDelete()
    {
        return delete;
    }

    public static Pattern getFillDeposits()
    {
        return fillDeposits;
    }
    
}
