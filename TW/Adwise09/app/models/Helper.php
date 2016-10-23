<?php
/**
 * Created by PhpStorm.
 * User: Iustines - PC
 * Date: 5/31/2015
 * Time: 2:21 PM
 */
require_once('Globals.php');
require_once('../app/utils/Database.php');

class Helper{

    public static function curPageURL() {
        $pageURL = 'http';
        if ($_SERVER["HTTPS"] == "on") {$pageURL .= "s";}
        $pageURL .= "://";
        if ($_SERVER["SERVER_PORT"] != "80") {
            $pageURL .= $_SERVER["SERVER_NAME"].":".$_SERVER["SERVER_PORT"].$_SERVER["REQUEST_URI"];
        } else {
            $pageURL .= $_SERVER["SERVER_NAME"].$_SERVER["REQUEST_URI"];
        }
        return $pageURL;

    }

    public static function validateEmail($email){
        /*if(count($email) < minEmailSize || count($email) > maxEmailSize )
            return false;
        if(!preg_match(emailPattern,$email))
            return false;
        */
        return true;
    }

    public static function validateUsername($username){
        if(count($username) < minEmailSize ||count($username) > maxEmailSize )
            return false;
        if(!preg_match(usernamePattern,$username))
            return false;

        return true;
    }
    public static function validatePassword($password){
        if(count($password) < minPasswordSize ||count($password) > maxPasswordSize )
            return false;
        if(!preg_match(passwordPattern,$password))
            return false;

        return true;
    }
    public static function validateCity($city){
        if(count($city) < minCitySize ||count($city) > maxCitySize )
            return false;
        if(!preg_match(cityPatttern,$city))
            return false;

        return true;
    }
    public static function validateCountry($country){
        if(count($country) < minCountrySize ||count($country) > maxCountrySize )
            return false;
        if(!preg_match(countryPatttern,$country))
            return false;

        return true;
    }

    public static function getCategories(){
        $sqlStmt = 'SELECT NAME_CATEGORY FROM CATEGORIES';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_execute($stid);


        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $categoryList[] = $row[0];

            while (($row = oci_fetch_row($stid)) != false) {
                $categoryList[] = $row[0];
            }
            return $categoryList;
        }

        return false;
    }

}


?>