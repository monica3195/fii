package Broker;
import Utilities.*;
import Utilities.Certificate;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class Broker {


	public static RSAPrivateKey privateKeyBroker;
    public static RSAPublicKey publicKeyBroker;
	static public KeyPair keyPair;
	private static String B = "127.0.0.1:9000";
	private static String separator = ",";
    private static Map<String, Integer> bank = new HashMap<>();
	
    public static void main(String[] args) throws Exception {
        System.out.println("Start broker server");

        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(Info.brokerPort);
        try {
            keyPair = Utilities.Util.getKey();
            privateKeyBroker = (RSAPrivateKey)keyPair.getPrivate();
            publicKeyBroker = (RSAPublicKey)keyPair.getPublic();
            
            while (true) {
                new BrokerThread(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }


    private static class BrokerThread extends Thread {
        private Socket socket;
        private int clientNumber;
        private static RSAPublicKey publicKeyUser;
        
        public BrokerThread(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client " + clientNumber + " at " + socket);
        }

        public void run() {
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                	
                    String input = in.readLine();

                    //Stop connection
                    if (input == null || input.equals(".")) {
                        break;
                    }

                    else if(input.equals("user"))
                    {
                        log("User");
                    	//primeste informatii personale de la U
                    	String U = in.readLine();
                    	String strKu = in.readLine();
                    	
                    	byte[] bytesKu = Util.base64Decode(strKu);
                    	publicKeyUser = Util.publicKeyFromBytes(bytesKu);

                    	//B->U:C(U)
                    	out.println(getUserCertificate(U, publicKeyUser));
                        log("Send user Certificate (U) " + U);
                    	initBank(U);
                    	break;
                    }
                    else if(input.equals("vanzator"))
                    {
                        log("Vanzator");
                    	while (true) {
                        	
                            String p = in.readLine();
                            if (p == null || p.equals(".")) {
                                break;
                            }
                            log("Payment");

                            byte[] data = Util.base64Decode(p);
                            Rascumparare r = (Rascumparare)Util.deserialize(data);

                            //B verifica commit(U)
                            log(r.toString());
                            log("B verifica commit(U)");
                            CommitU commitU = CommitU.validateCommitU(r.commitU);

                            if(commitU == null)
                            {
                                continue;//se duce la urmatarul request
                            }
                            else// verifica ultima plata (prin l aplicari ale functiei h)
                            {
                                for(int i = 0; i < r.ci.size(); i++) {
                                    Payword currentPayWord = r.ci.get(i);
                                    int l = r.i.get(i);

                                    if(l < 1){
                                        continue;
                                    }

                                    int auxType = -1;
                                    switch (currentPayWord.type){
                                        case "min":
                                            auxType = 0;
                                            break;
                                        case "med":
                                            auxType = 1;
                                            break;
                                        case "max":
                                            auxType = 2;
                                            break;
                                    }

                                    if (true == PaywordLant.verifyPayword(r.commitU.commitment.c0List.get(auxType), currentPayWord.ci, l)) {
                                        updateBank(r.commitU.commitment.userCU.certificate.U, l, currentPayWord.type);
                                    }
                                }
                            }
                    	}
                        showBank();
                    }
                }
            } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                /*try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");*/
            }
        }
       
        private String getUserCertificate(String U, RSAPublicKey Ku) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException, IOException
        {
        	//return "sigb(B,U,Kb,Ku,exp,info)";
        	//creez certificatul
        	Certificate c = new Certificate();
            c.B = B;
            c.U = U;
            c.publicKeyUser = Ku;
            c.publicKeyBroker = publicKeyBroker;
            c.exp = new Date();

            //Adaug data expirare + 1 zi
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, 1);
            c.exp = calendar.getTime();

            //atasez semnatura
            CU cu = new CU();
            cu.certificate = c;
            
            //generez semnatura
            byte[] bytes = Util.serialize(c);
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initSign(privateKeyBroker);
            sig.update(bytes);
            byte[] sigBytes = sig.sign();
            
            cu.sig = DatatypeConverter.printBase64Binary(sigBytes);
           
            byte[] data = Util.serialize(cu);
            return Util.base64Encode(data);
        }
        
        private void log(String message) {
            System.out.println(message);
        }

        private void initBank(String U){
            /*
            if(bank.containsKey(U)){
                bank.put(U, bank.get(U) + 100);
            }else {
                bank.put(U,100);
            }
            */
            bank.put(U,1000);
            showBank();
        }

        private void updateBank(String U, int chainLength, String payWordType){
            if(bank.containsKey(U)){

                int auxType = -1;
                switch (payWordType){
                    case "min":
                        auxType = 1;
                        break;
                    case "med":
                        auxType = 3;
                        break;
                    case "max":
                        auxType = 7;
                        break;
                }

                bank.put(U, bank.get(U) - (auxType * chainLength));
            }
        }

        private void showBank(){
            System.out.println("Show bank");
            for(String key : bank.keySet()){
                System.out.println(key + " " + bank.get(key));
            }
        }

    }
}