/*
 * Авторство Паршина Александра
 * По всем вопросам писать на e-mail parshin_sashek@mail.ru
 */
package privatclient.util;

import java.util.regex.Pattern;

/**
 *
 * @author parsh
 */
public class Patterns
{
    static private final Pattern PPORT = Pattern.compile("^(set port <)(\\d\\d\\d\\d)\\>$");
    static private final Pattern FILLDEPOSITS = Pattern.compile("^(fillDeposits <)(.+)\\>$");

    public static Pattern getPPORT()
    {
        return PPORT;
    }

    public static Pattern getFILLDEPOSITS()
    {
        return FILLDEPOSITS;
    }
    
    
}
