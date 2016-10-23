package Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;

import javax.xml.bind.DatatypeConverter;

public class CU implements Serializable
{
    public Certificate certificate;
    public String sig;

    public CU()
    {
    }
    
    public static CU validateCU(String strCU) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
    	
    	byte[] data = Util.base64Decode(strCU);
    	CU CU = (Utilities.CU) Util.deserialize(data);
    	
    	return validateCU(CU);
    }

    public static CU validateCU(CU CU) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{

    	byte[] bytes = Util.serialize(CU.certificate);
    	RSAPublicKey publicKey = CU.certificate.publicKeyBroker;
    	
    	Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initVerify(publicKey);
        sig.update(bytes);
        byte[] sigBytes = DatatypeConverter.parseBase64Binary(CU.sig);
        
        if(true == sig.verify(sigBytes))
        {
        	return CU;
        }
        else
        {
        	return null;
        }
    }

}
