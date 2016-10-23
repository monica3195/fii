package ro.infoiasi.sgbd;

import java.sql.*;

/**
 * Created by dimitrie on 23.03.2015.
 */
public class Pirghie_Dimitrie_B1_5_3 {

    private Connection oracleCon;

    private boolean isDriver(){

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return true;
        }catch (ClassNotFoundException e){
            System.out.println("OracleDriver issue : " + e.getMessage());
            return false;
        }
    }


    //Trebuie instantiat un obiect de Tipul Pirghie_Dimitrie_B1_5_3 cu parametrii (Host, Port, User, Password)
    public Pirghie_Dimitrie_B1_5_3(String host, String port, String user, String password){

        if(isDriver()){
            String getConStmt = "jdbc:oracle:thin:@" + host + ":" + port;

            try {
                this.oracleCon = DriverManager.getConnection(getConStmt, user, password);
            }catch (SQLException e){
                System.out.println("Oracle connection issue " + e.getMessage());
            }
        }
    }

    //Functie apelata pentru a returna salariul unui Employee din tabela emp, daca nu exista (empno), functia stocata arunca o excetie
    // user-defined cu un cod, si mesaj, in cazul acesta se prinde exceptia de tip SQLException, se afiseaza mesajul si se returneaza -1
    public int getEmpSal(int empno){

        if(this.oracleCon != null){

            try {

                CallableStatement clstmt = this.oracleCon.prepareCall("{? = call GETSAL (?)}");
                clstmt.registerOutParameter(1, Types.INTEGER);
                clstmt.setInt(2, empno);
                clstmt.execute(); // Throws Oracle Exception
                int empSalary = clstmt.getInt(1);

               return empSalary;

            }catch (SQLException e){
                System.out.println("getEmpSal exception : " +e.getMessage());
            }
        }else{
            System.out.println("No connection established");

        }
        return -1;
    }
}
