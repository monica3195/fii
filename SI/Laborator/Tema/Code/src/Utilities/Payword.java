package Utilities;

import java.io.Serializable;

public class Payword implements Serializable{

	public String ci;
	public int i;
    public String type;

	public Payword(){
		i = 0;
		ci = "Undefined ci";
	}

    public void setType(String _type){
        type = _type;
    }

    public String toString(){
        String output = new String("[" + type + "]" + i);
        return output;
    }
}
