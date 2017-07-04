package privatclient.classes;

import java.math.BigDecimal;

enum TypeDeposit{noNeed, Need, Calculated, Cumulative, Saving, Metal}
/**
 *
 * @author parsh
 */
public class Deposit {
    
    private String Name;
    private String Country;
    private String Type;
    private String Depositor;
    private long AccountId;
    private BigDecimal AmountOnDeposit;
    private double Profitability;
    private long time;
    
    Deposit()
    {
        Name="Приват";
        Country="Украина";
        Type="до востребования";
        Depositor = "Паршин Александр";
        AccountId = 1;
        AmountOnDeposit = new BigDecimal("1600");
        Profitability = 50;
        time=6;
    }
    Deposit(String name, String country, String type, String depositor,
            BigDecimal amountOnDeposit, long profitability, long Time)
    {
        Name=name; Country=country; Type=type; Depositor=depositor;
        AmountOnDeposit = amountOnDeposit; Profitability = profitability;
        time=Time;
    };

    public String getName()
    {
        return Name;
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
    
    public void setName(String Name)
    {
        this.Name = Name;
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
    
    
    @Override
    public String toString()
    {
        return "Values{" + "Name='" + Name + '\'' +
                ", Country='" + Country + '\'' +
                ", Type='" + Type + '\'' +
                ", Depositor='" + Depositor +'\'' +
                ", AccountId='" + AccountId +'\'' +
                ", AmountOnDeposit='" + AmountOnDeposit + '\'' +
                ", Profitability='" + Profitability +'\'' +
                ", time='" + time +'}';
    }
    
    public String toXML()
    {
        return  
                "<deposit>" +"\n" +
                "<Name>" + Name +"</Name>" +"\n"+
                "<Country>" + Country +"</Country>" + "\n" +
                "<Type>" + Type +"</Type>" + "\n" +
                "<Depositor>" + Depositor +"</Depositor>" + "\n" +
                "<AccountId>" + AccountId +"</AccountId>" + "\n" +
                "<AmountOnDeposit>" + AmountOnDeposit +"</AmountOnDeposit>" + "\n" +
                "<Profitability>" + Profitability +"</Profitability>" + "\n" +
                "<Time>" + time +"</Time>" + "\n" +
                "</deposit>\n";
    }
}
