package User;


import Utilities.*;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class User {

    public static class progressBarThread extends Thread{
        final int THREAD_SLEEP_MS = 1000;
        int currentSec = 0;

        public progressBarThread(){}

        public void run(){

            while(currentSec < 100) {

                try {
                    Thread.sleep(THREAD_SLEEP_MS);
                } catch (Exception eee) {}

                if(playStatus){
                    currentSec++;
                    progressBar.setValue(currentSec);

                    if(0 == currentSec%10){
                        try {

                            buy(buyValueFromInput);
                            System.out.println("Buy" + buyValueFromInput);
                        }catch (Exception x){}
                    }
                }
            }
            closeConnectionWithVanzator();
        }
    }

    private BufferedReader in;
    private static PrintWriter out;


    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JSplitPane splitPaneVertical = new JSplitPane();

    private JFrame frame = new JFrame("PayWord");
    private JTextField dataField = new JTextField(40);
    private JButton playButton = new JButton("Play");
    public static JProgressBar progressBar = new JProgressBar();
    public static boolean playStatus = false;
    public static progressBarThread progressBarThreadObj = null;
    public static int buyValueFromInput = 0;


    public String U = "user identitate";
    static public KeyPair keyPair;
    public static RSAPrivateKey privateKeyUser;
    public static RSAPublicKey publicKeyUser;
    public static CU CU = null;

    static PaywordLant paywordLantMin;
    static PaywordLant paywordLantMed;
    static PaywordLant paywordLantMax;

    static int lastIndexOfPayWordLanMin = 0;
    static int lastIndexOfPayWordLanMed = 0;
    static int lastIndexOfPayWordLanMax = 0;

    public PaywordLant ci;

    public User() throws NoSuchAlgorithmException, UnknownHostException
    {
        System.out.println("Enter port :");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        Info.userPort = num;

        U = Utilities.Info.address + ":" + Utilities.Info.userPort;
        keyPair = Utilities.Util.getKey();
        privateKeyUser = (RSAPrivateKey)keyPair.getPrivate();
        publicKeyUser = (RSAPublicKey)keyPair.getPublic();

        // Layout GUI
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(playButton, BorderLayout.SOUTH);
        leftPanel.add(dataField, BorderLayout.CENTER);

        progressBar.setStringPainted(true);


        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(progressBar);

        splitPaneVertical.setDividerLocation(100);
        splitPaneVertical.setLeftComponent(leftPanel);
        splitPaneVertical.setRightComponent(rightPanel);


        frame.getContentPane().add(splitPaneVertical);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String dateFieldText = dataField.getText();
                System.out.println("Pressed " + dateFieldText);

                int number;

                try{
                    number = Integer.parseInt(dateFieldText);
                    buyValueFromInput = number;
                }catch (Exception eee){return;}


                if(progressBarThreadObj == null) {
                    progressBarThreadObj = new progressBarThread();
                    progressBarThreadObj.start();
                }

                playStatus = !playStatus;
            }
        });
    }

    private CU getCUfromBroker() throws IOException, InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, SignatureException {

        String strCU;
        CU cu;

        do
        {
            // Make connection and initialize streams
            Socket socket = new Socket(Utilities.Info.address, Utilities.Info.brokerPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //U furnizeaza informatii personale
            out.println("user");
            out.println(U);
            out.println(Util.base64Encode(publicKeyUser.getEncoded()));

            //B->U:C(U)
            strCU = in.readLine();

            cu = CU.validateCU(strCU);

        }
        while(null == cu);

        return cu;
    }

    private boolean sendCommitToVanzator()
    {
        try
        {
            // Creare conexiune vanzator
            Socket socket = new Socket(Utilities.Info.address, Utilities.Info.vanzatorPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //generez commitment
            Commitment comm = generateCommitment();

            //commitment + semnatura
            CommitU commitU = new CommitU();
            commitU.commitment = comm;

            //generez semnatura
            byte[] bytes = Util.serialize(comm);
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initSign(privateKeyUser);
            sig.update(bytes);
            byte[] sigBytes = sig.sign();

            commitU.sig = DatatypeConverter.printBase64Binary(sigBytes);;

            byte[] data = Util.serialize(commitU);
            String strCommitU = Util.base64Encode(data);

            //U->V:commit(U)
            out.println(strCommitU);
        }
        catch(Exception ex)
        {
            log("Error Commitment");
            return false;
        }
        return true;
    }

    private Commitment generateCommitment() throws NoSuchAlgorithmException
    {
        Commitment comm = new Commitment();
        comm.V = Info.addressVanzator+":"+Info.vanzatorPort;
        comm.userCU = CU;
        comm.d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        comm.d = calendar.getTime();
        comm.c0List = new ArrayList();
        //{paywordLantMin.ci[0], paywordLantMed.ci[0], paywordLantMax.ci[0]};
        comm.c0List.add(paywordLantMin.ci[0]);
        comm.c0List.add(paywordLantMed.ci[0]);
        comm.c0List.add(paywordLantMax.ci[0]);
        return comm;
    }

    private static void buy(int value) throws IOException, NoSuchAlgorithmException
    {
        //U->V:ci,i
        int noMax = 0;
        int noMed = 0;
        int noMin = 0;

        noMax = value/7;
        value = value - (noMax*7);
        noMed = (value)/3;
        value = value - (noMed * 3);
        noMin = (value)/1;
        System.out.println("Buy : MAX : MED : MIN = " + noMax + " : "+ noMed + " : " + noMin);

        if(noMax > 0){

            Payword p = new Payword();
            p.setType("max");
            lastIndexOfPayWordLanMax = lastIndexOfPayWordLanMax + noMax;
            p.ci = paywordLantMax.ci[lastIndexOfPayWordLanMax];
            p.i = lastIndexOfPayWordLanMax;

            byte[] data = Util.serialize(p);
            String strP = Util.base64Encode(data);
            out.println(strP);
        }

        if(noMed > 0){

            Payword p = new Payword();
            p.setType("med");
            lastIndexOfPayWordLanMed = lastIndexOfPayWordLanMed + noMed;
            p.ci = paywordLantMed.ci[lastIndexOfPayWordLanMed];
            p.i = lastIndexOfPayWordLanMed;

            byte[] data = Util.serialize(p);
            String strP = Util.base64Encode(data);
            out.println(strP);
        }

        if(noMin > 0){

            Payword p = new Payword();
            p.setType("min");
            lastIndexOfPayWordLanMin = lastIndexOfPayWordLanMin + noMin;
            p.ci = paywordLantMin.ci[lastIndexOfPayWordLanMin];
            p.i = lastIndexOfPayWordLanMin;

            byte[] data = Util.serialize(p);
            String strP = Util.base64Encode(data);
            out.println(strP);
        }

        /*
        p.setType("min");
        p.ci = paywordLantMin.ci[1];
        p.i = 1;
        byte[] data = Util.serialize(p);
        String strP = Util.base64Encode(data);
        out.println(strP);

        p.setType("med");
        p.ci = paywordLantMed.ci[4];
        p.i = 4;

        data = Util.serialize(p);
        strP = Util.base64Encode(data);
        out.println(strP);

        p.setType("max");
        p.ci = paywordLantMax.ci[7];
        p.i = 7;

        data = Util.serialize(p);
        strP = Util.base64Encode(data);
        out.println(strP);
        */

    }

    public static void main(String[] args) throws Exception {

        User client = new User();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);

        //inregistrarea U la B
        client.CU = client.getCUfromBroker();

        //generare lant payword
        client.paywordLantMax = new PaywordLant(100);
        client.paywordLantMin = new PaywordLant(100);
        client.paywordLantMed = new PaywordLant(100);

        client.sendCommitToVanzator();

      //  client.buy();
//        client.closeConnectionWithVanzator();
    }

    private void log(String message) {
        System.out.println(message);
    }

    private static void closeConnectionWithVanzator(){
        out.println(".");
    }
}
