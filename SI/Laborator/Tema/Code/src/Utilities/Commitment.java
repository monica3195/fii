package Utilities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Commitment implements Serializable
{
    public String V;
    public CU userCU;
    public List<String> c0List;
    public Date d;
    public String info;

    public Commitment()
    {
    }

}
