package privatserver.classes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 *
 * @author parsh
 */
public class Deposit {
    static private List<Deposit> deposits = Collections.synchronizedList(new ArrayList<Deposit>());
    
    //Ответы к запросам
    
    static private BigDecimal allSumDeposits;
    
    
    static private long maxId=1;
    private String NameBank;
    private String Country;
    private String Type;
    private String Depositor;
    private long AccountId;
    private BigDecimal AmountOnDeposit;
    private double Profitability;
    private long time;
    
    public Deposit()
    {
        NameBank="Приват";
        Country="Украина";
        Type="до востребования";
        Depositor = "Паршин Александр";
        AccountId = 1;
        AmountOnDeposit = new BigDecimal("1600");
        Profitability = 50;
        time=6;
    }
    public Deposit(String name, String country, String type, String depositor,
            BigDecimal amountOnDeposit, long profitability, long Time)
    {
        NameBank=name; Country=country; setType(type); Depositor=depositor;
        AmountOnDeposit = amountOnDeposit; Profitability = profitability;
        time=Time;
        if(this.AccountId>maxId)maxId=this.AccountId;
    };



    public static BigDecimal getAllSumDeposits()
    {
        return allSumDeposits;
    }

    public static void setAllSumDeposits(BigDecimal allSumDeposits)
    {
        Deposit.allSumDeposits = allSumDeposits;
    }
    
    
    static public List<Deposit> getDeposits()
    {
        return deposits;
    }
    public String getNameBank()
    {
        return NameBank;
    }

    public String getCountry()
    {
        return Country;
    }

    public String getType()
    {
        return Type;
    }

    public String getDepositor()
    {
        return Depositor;
    }

    public long getAccountId()
    {
        return AccountId;
    }

    public BigDecimal getAmountOnDeposit()
    {
        return AmountOnDeposit;
    }

    public double getProfitability()
    {
        return Profitability;
    }

    public long getTime()
    {
        return time;
    }
    
    public void setNameBank(String NameBank)
    {
        this.NameBank = NameBank;
    }

    public void setCountry(String Country)
    {
        this.Country = Country;
    }

    public void setType(String Type)
    {
        String[] TypeDeposit= {"до востребования", "срочный", "расчетный", "накопительный", "сберегательный", "металлический"};
        boolean f=false;
        for(int i=0; i<TypeDeposit.length; i++)
        {
            if(TypeDeposit[i].equals(Type))
            {
                this.Type = Type;
                f=true;
                break;
            }
        }
        if(!f)this.Type=TypeDeposit[0];
    }

    public void setDepositor(String Depositor)
    {
        this.Depositor = Depositor;
    }

    public void setAccountId(long AccountId)
    {
        this.AccountId = AccountId;
        if(this.AccountId>maxId)maxId=this.AccountId;
    }

    public void setAmountOnDeposit(BigDecimal AmountOnDeposit)
    {
        this.AmountOnDeposit = AmountOnDeposit;
    }

    public void setProfitability(double Profitability)
    {
        this.Profitability = Profitability;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
    
    public StringBuilder getString()
    { 
        StringBuilder buff = new StringBuilder();
                buff.append("-------------------------------------------------\n");
                buff.append("NameBank:").append(this.NameBank).append("\n");
                buff.append("Country:").append(this.Country).append("\n");
                buff.append("Type:"+this.Type+"\n");
                buff.append("Depositor:").append(this.Depositor).append("\n");
                buff.append("AccountId:").append(this.AccountId).append("\n");
                buff.append("AmountOnDeposit:").append(new DecimalFormat("#.00").format(this.AmountOnDeposit)).append("\n");
                buff.append("Profitability:").append(new DecimalFormat("#").format(this.Profitability)).append("\n");
                buff.append("Time:").append(this.time).append("\n");
                buff.append("=================================================\n");
        return buff;
    }
    public void randomizerDeposit()
    {
        Random rand = new Random();
        String[] typeName      = {"Приват","НБУ","ПУМБ","Ощадбанк"};
        String[] typeCountry   = {"Украина","Россия"};
        String[] typeDeposit   = {"до востребования", "срочный", "расчетный", "накопительный", "сберегательный", "металлический"};
        String[] typeDepositor = {"Паршин Олександр","Новиков Василий","Курченко Игорь","Решетник Юрий","Топчий Дмитрий"};
        
        System.out.println(Math.abs(rand.nextInt()) %typeName.length);
        
        this.NameBank = typeName[Math.abs(rand.nextInt()) %typeName.length];
        this.Country = typeCountry[Math.abs(rand.nextInt()) %typeCountry.length];
        this.Type = typeDeposit[Math.abs(rand.nextInt())% typeDeposit.length];
        this.Depositor = typeDepositor[Math.abs(rand.nextInt())% typeDepositor.length];
        this.AccountId = (maxId+1);  maxId++;
       
        Double temp = new Random().nextGaussian();
        if(temp<0)temp=-temp; if(temp==0) temp = 10000.0;
        temp*=10000;
        
        this.AmountOnDeposit = new BigDecimal(Double.toString(temp));
        this.Profitability = Math.abs(Math.random()*30); 
        this.time = Math.abs(rand.nextLong())%50;
    }
}