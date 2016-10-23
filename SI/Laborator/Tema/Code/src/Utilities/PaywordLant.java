package Utilities;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

public class PaywordLant implements Serializable {

	public String[] ci;
	public int i;
	
	public PaywordLant(int n) throws NoSuchAlgorithmException
	{
		ci = new String[n + 1];

		byte[] b = new byte[20];
		new Random().nextBytes(b);
		ci[n] = DatatypeConverter.printHexBinary(b);

		for (int i = n - 1; i >= 0; --i)
		{
	       	b = MessageDigest.getInstance("SHA1").digest(b);
	    	ci[i] = DatatypeConverter.printHexBinary(b);
	 	}

	 	i = 0;       
	}

    public static boolean verifyPayword(String c0, String ci, int n) throws NoSuchAlgorithmException
    {
        byte[] b = DatatypeConverter.parseHexBinary(ci);

        for (int i = n-1; i >=0; --i)
        {
            b = MessageDigest.getInstance("SHA1").digest(b);
        }
        String cn = DatatypeConverter.printHexBinary(b);

        return c0.equals(cn);
    }
}
