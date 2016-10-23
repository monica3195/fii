package Utilities;

import java.io.Serializable;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

public class Certificate implements Serializable
{
    public String B;
    public String U;
    public RSAPublicKey publicKeyUser;
    public RSAPublicKey publicKeyBroker;
    public Date exp;
    public int info;

    public Certificate()
    {
    }
}
