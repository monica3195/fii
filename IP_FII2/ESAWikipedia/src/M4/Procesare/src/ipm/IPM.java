/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipm;

/**
 *
 * @author Bejan
 * @author Dămoc
 * @author Perju
 * @author Policiuc
 */
public class IPM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	new NewIndex().start();
    	new ReadXml("rodump.xml", "rodumpindex.txt").ReadData();    
    }
    
}
