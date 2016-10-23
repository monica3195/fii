<?php
class Database{

    static public function getConnection(){

        if($_SESSION['dbconnection'] == null){
            Database::connect();
        }
        return $_SESSION['dbconnection'];
    }

    static public function connect(){

        //Database connection statement
        //$dbStmt = "(DESCRIPTION=(ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521)))(CONNECT_DATA=(SID=xe)))";
        $dbStmt = "(DESCRIPTION=(ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 46.101.135.217)(PORT = 1521)))(CONNECT_DATA=(SID=xe)))";
        $connection = OCILogon("adwise", "vascan", $dbStmt);

        if($connection){
            $_SESSION['dbconnection'] = $connection;
        }else{
           //header("Location:".PUBLIC_ROOT."/assets/img/db.jpg");
        }

        if($connection == false){
            header("Location:".PUBLIC_ROOT."/assets/img/db.jpg");
        }
    }

    static public function disconnect(){
        $connection = $_SESSION['dbconnection'];
        ocilogoff($connection);
        oci_close($connection);
        $_SESSION['dbconnection'] = null;
    }
}
?>