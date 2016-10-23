package ro.infoiasi.sgbd;
import java.sql.*;

/**
 * Created by dimitrie on 22.03.2015.
 */
public class SGBD_5 {

    public static void main(String args[]) throws SQLException {

        //public OracleSQL(String host, String port, String user, String password){
        Pirghie_Dimitrie_B1_5_3 oracleCon = new Pirghie_Dimitrie_B1_5_3("localhost", "1521", "dimitrie", "nokia1680");
        System.out.println("Sal : " + oracleCon.getEmpSal(7654));
        System.out.println("Sal : " + oracleCon.getEmpSal(112));
    }
}
