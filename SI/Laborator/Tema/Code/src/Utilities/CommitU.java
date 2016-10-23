package Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public class CommitU implements Serializable{

	public Commitment commitment;
	public String sig;
	
	public CommitU()
	{
	}
	
	public static CommitU validateCommitU(String strCommitU) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

            //verific semnatura lui U pe commit(U)
            byte[] data = Util.base64Decode(strCommitU);
            CommitU commitU = (CommitU) Util.deserialize(data);

            return  validateCommitU(commitU);

    }

        public static CommitU validateCommitU(CommitU commitU) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

                byte[] bytes = Util.serialize(commitU.commitment);
                RSAPublicKey publicKeyUser = commitU.commitment.userCU.certificate.publicKeyUser;

                Signature sig = Signature.getInstance("SHA1WithRSA");
                sig.initVerify(publicKeyUser);
                sig.update(bytes);
                byte[] sigBytes = DatatypeConverter.parseBase64Binary(commitU.sig);

                if (false == sig.verify(sigBytes)) {
                        return null;
                }
                //verific semnatura lui B pe C(U)
                if (null == CU.validateCU(commitU.commitment.userCU)) {
                        return null;
                }

                //verific data
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                currentDate = calendar.getTime();
                if (true == currentDate.before(commitU.commitment.d)) {
                        return null;
                }

                return commitU;
        }

}
