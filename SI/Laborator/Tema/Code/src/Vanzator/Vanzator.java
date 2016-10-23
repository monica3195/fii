package Vanzator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

import Utilities.CommitU;
import Utilities.Info;
import Utilities.Payword;
import Utilities.PaywordLant;
import Utilities.Rascumparare;
import Utilities.Util;

public class Vanzator {

    public static RSAPrivateKey privateKeyVanzator;
    public static RSAPublicKey publicKeyVanzator;
    static public KeyPair keyPair;
    private static String V = "127.0.0.1:9002";
    private static HashMap<CommitU, List<Payword>> usersPayword = new HashMap<CommitU, List<Payword>>();
    private static HashMap<CommitU, List<Integer>> usersI = new HashMap<CommitU, List<Integer>>();

    public static void main(String[] args) throws Exception {
        //Thread rascumparare (caine de paza !)
        VanzatorRascumpareThread recuperatorulPazeste = new VanzatorRascumpareThread();
        recuperatorulPazeste.start();

        System.out.println("Start vanzator server");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(Info.vanzatorPort);
        try {
            keyPair = Utilities.Util.getKey();
            privateKeyVanzator = (RSAPrivateKey)keyPair.getPrivate();
            publicKeyVanzator = (RSAPublicKey)keyPair.getPublic();

            while (true) {
                new VanzatorThread(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    public static void rascumpararePaywords() throws IOException{

        try {

            Socket socketToBroker = new Socket(Utilities.Info.address, Utilities.Info.brokerPort);
            PrintWriter out = new PrintWriter(socketToBroker.getOutputStream(), true);

            //V->B:commit(U),ci,i
            out.println("vanzator");
            System.out.println(usersPayword.size());

            for(int i = 0;i<usersPayword.size();++i)
            {
                Rascumparare r = new Rascumparare();
                r.commitU = (CommitU) usersPayword.keySet().toArray()[i];
                r.ci = (List<Payword>) usersPayword.values().toArray()[i];
                r.i = (List<Integer>) usersI.values().toArray()[i];

                byte[] data = Util.serialize(r);
                String strRas = Util.base64Encode(data);
                out.println(strRas);
            }

            out.println(".");
        }
        catch (IOException e){
            System.out.println("Socket to broker exception");
        }
        usersPayword.clear();
    }

    private static class VanzatorThread extends Thread {

        boolean userAutorization = false;
        CommitU commitU = null;
        List<Payword> lastPayword = new ArrayList<>();
        List<Integer> l = new ArrayList<>();
        private Socket socket;
        private int clientNumber;

        public VanzatorThread(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.lastPayword.add(0, new Payword());
            this.lastPayword.add(1, new Payword());
            this.lastPayword.add(2, new Payword());

            this.l.add(0, 0);
            this.l.add(1, 0);
            this.l.add(2, 0);


            log("New connection with client# " + clientNumber + " at " + socket);
        }

        public void run() {
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Get messages from the client, line by line;
                while (true) {

                    String input = in.readLine();

                    if (input == null || input.equals(".")) {
                        break;
                    }
                    else {
                        if(userAutorization == false)
                        {
                            commitU = CommitU.validateCommitU(input);
                            if(commitU == null)
                            {
                                break;//unauthorized
                            }
                            else
                            {
                                log("New user authorized " + commitU.commitment.userCU.certificate.U);
                                userAutorization = true;
                            }
                        }
                        else//authorized
                        {
                            byte[] data = Util.base64Decode(input);
                            Payword p = (Payword) Util.deserialize(data);

                            int auxType = -1;
                            switch (p.type){
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
                            if(auxType > -1 ) {
                                if (p.i > lastPayword.get(auxType).i && PaywordLant.verifyPayword(commitU.commitment.c0List.get(auxType), p.ci, p.i)) {

                                    lastPayword.set(auxType, p);
                                    l.set(auxType, p.i);
                                    log("Verified payword" + p.toString());

                                    usersPayword.put(commitU, lastPayword);
                                    usersI.put(commitU, l);
                                }else{
                                    log("Invalid payword " + p.toString());
                                }
                            }
                        }
                    }

                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SignatureException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                   /* usersPayword.put(commitU, lastPayword);
                    usersI.put(commitU, l);*/
                    socket.close();


                } catch (Exception e) {
                    log("Couldn't close a socket, what's going on?");
                }
            }
        }

        private void log(String message) {
            System.out.println(message);
        }

        private  void rascumpararePaywords() throws IOException{

            try {

                Socket socketToBroker = new Socket(Utilities.Info.address, Utilities.Info.brokerPort);
                PrintWriter out = new PrintWriter(socketToBroker.getOutputStream(), true);

                //V->B:commit(U),ci,i
                out.println("vanzator");
                for(int i = 0;i<usersPayword.size();++i)
                {
                    Rascumparare r = new Rascumparare();
                    r.commitU = (CommitU) usersPayword.keySet().toArray()[i];
                    r.ci = (List<Payword>) usersPayword.values().toArray()[i];
                    r.i = (List<Integer>) usersI.values().toArray()[i];

                    byte[] data = Util.serialize(r);
                    String strRas = Util.base64Encode(data);
                    out.println(strRas);
                }

                out.println(".");
            }
            catch (IOException e){
                System.out.println("Socket to broker exception");
            }
        }
    }

    public static class VanzatorRascumpareThread extends Thread {
        public Calendar startThreadTimePlusOneDay;
        public Calendar currentThreadTime;
        final int DEELAY_TIME_MS = 5000;


        public VanzatorRascumpareThread(){
            startThreadTimePlusOneDay = Calendar.getInstance();
            startThreadTimePlusOneDay.setTime(new Date());
            startThreadTimePlusOneDay.add(Calendar.DATE, 1);

            currentThreadTime = Calendar.getInstance();
        }

        public void run(){
            while (true) {
                currentThreadTime.setTime(new Date());

                if (currentThreadTime.getTimeInMillis() > startThreadTimePlusOneDay.getTimeInMillis()){
                    System.out.println("One day !");
                    try{
                        rascumpararePaywords();
                    }catch (Exception e){
                        System.out.println("Errors in rascumparare !");
                    }
                    break; // end thread
                }else {
                    try{
                        Thread.sleep(DEELAY_TIME_MS);
                    }catch (Exception e){

                    }

                }
            }
        }
    }
}