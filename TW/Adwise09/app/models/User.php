<?php

require_once('QuestionModel.php');
require_once('AnswerModel.php');

class User{

    private $profileID  = null;
    private $password   = null;
    private $userName   = null;
    private $firstName  = null;
    private $lastName   = null;
    private $email      = null;
    private $age        = null;
    private $city       = null;
    private $country    = null;
    private $is_admin   = null;
    private $is_banned  = null;

    /**
     * @return null
     */
    public function getAge()
    {
        return $this->age;
    }

    /**
     * @param null $age
     */
    public function setAge($age)
    {
        $this->age = $age;
    }

    /**
     * @return null
     */
    public function getCity()
    {
        return $this->city;
    }

    /**
     * @param null $city
     */
    public function setCity($city)
    {
        $this->city = $city;
    }

    /**
     * @return null
     */
    public function getCountry()
    {
        return $this->country;
    }

    /**
     * @param null $country
     */
    public function setCountry($country)
    {
        $this->country = $country;
    }

    /**
     * @return null
     */
    public function getIsAdmin()
    {
        return $this->is_admin;
    }

    /**
     * @param null $is_admin
     */
    public function setIsAdmin($is_admin)
    {
        $this->is_admin = $is_admin;
    }

    /**
     * @return null
     */
    public function getIsBanned()
    {
        return $this->is_banned;
    }

    /**
     * @param null $is_banned
     */
    public function setIsBanned($is_banned)
    {
        $this->is_banned = $is_banned;
    }




    public function __constructor(){

    }

    /**
     * @return int
     */
    public function getProfileID()
    {
        return $this->profileID;
    }

    /**
     * @param int $profileID
     */
    public function setProfileID($profileID)
    {
        $this->profileID = $profileID;
    }


    /**
     * @return null
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * @param null $password
     */
    public function setPassword($password)
    {
        $this->password = $password;
    }

    /**
     * @return boolean
     */
    public function isLogged()
    {
        return $this->logged;
    }

    /**
     * @param boolean $logged
     */
    public function setLogged($logged)
    {
        $this->logged = $logged;
    }

    /**
     * @return null
     */
    public function getUserName()
    {
        return $this->userName;
    }

    /**
     * @param null $userName
     */
    public function setUserName($userName)
    {
        $this->userName = $userName;
    }

    /**
     * @return null
     */
    public function getFirstName()
    {
        return $this->firstName;
    }

    /**
     * @param null $firstName
     */
    public function setFirstName($firstName)
    {
        $this->firstName = $firstName;
    }

    /**
     * @return null
     */
    public function getLastName()
    {
        return $this->lastName;
    }

    /**
     * @param null $lastName
     */
    public function setLastName($lastName)
    {
        $this->lastName = $lastName;
    }

    /**
     * @return null
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @param null $email
     */
    public function setEmail($email)
    {
        $this->email = $email;
    }

    public function loadUserDetails(){

        if($_SESSION['dbconnection'] && $this->profileID){
            
            $userParserHandlerOCI = oci_parse($_SESSION['dbconnection'], "SELECT * FROM PROFILES WHERE ID_PROFILE = ".$this->getProfileID());
            
            if($userParserHandlerOCI){
                $queryResult = oci_execute($userParserHandlerOCI);
                if($queryResult){
                    $rowUser = oci_fetch_array($userParserHandlerOCI, OCI_ASSOC+OCI_RETURN_NULLS);
                    if($rowUser){
                        $this->setEmail($rowUser['EMAIL']);
                        $this->setFirstName($rowUser['FIRST_NAME']);
                        $this->setLastName($rowUser['LAST_NAME']);
                        $this->setPassword($rowUser['PASSWD']);
                        $this->setUserName($rowUser['USERNAME']);
                        $this->setAge($rowUser['AGE']);
                        $this->setCity($rowUser['CITY']);
                        $this->setCountry($rowUser['COUNTRY']);
                        $this->setIsAdmin($rowUser['IS_ADMIN']);
                        $this->setIsBanned($rowUser['IS_BANNED']);

                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    public function saveUserDetails(){
        if($_SESSION['dbconnection'] && $this->profileID){


            $userParserHandlerOCI = oci_parse($_SESSION['dbconnection'], "BEGIN PROFILE_UTILS.EDIT_ALL(:nfname, :nlname, :nemail, :nage, :ncountry, :ncity, :profileid) ;END;");
            $fn = $this->getFirstName();
            $ln = $this->getLastName();
            $em = $this->getEmail();
            $age = $this->getAge();
            $ct = $this->getCountry();
            $city = $this->getCity();
            $profileId = $this->getProfileID();

            oci_bind_by_name($userParserHandlerOCI, ':nfname', $fn);
            oci_bind_by_name($userParserHandlerOCI, ':nlname', $ln);
            oci_bind_by_name($userParserHandlerOCI, ':nemail', $em);
            oci_bind_by_name($userParserHandlerOCI, ':nage', $age);
            oci_bind_by_name($userParserHandlerOCI, ':ncountry', $ct);
            oci_bind_by_name($userParserHandlerOCI, ':ncity', $city);
            oci_bind_by_name($userParserHandlerOCI, ':profileid', $profileId);

            if($userParserHandlerOCI){
                $queryResult = oci_execute($userParserHandlerOCI);
                if($queryResult){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    public function changePassword($encryptedPassword){

        if($_SESSION['dbconnection'] && $this->profileID){

            $userParserHandlerOCI = oci_parse($_SESSION['dbconnection'], "BEGIN PROFILE_UTILS.EDIT_PASSWD(:newpass, :username); END;");

            if($userParserHandlerOCI){

                $profileId =  $this->getProfileID();
                oci_bind_by_name($userParserHandlerOCI,':newpass',$encryptedPassword);
                oci_bind_by_name($userParserHandlerOCI,':username', $profileId);

                $queryResult = oci_execute($userParserHandlerOCI);

                if($queryResult){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    public function loadAnswersByPage($page){

        $db_stm="SELECT id_answer, id_question, time_posted
                 FROM answers
                 WHERE rownum < (:p1+1)*10 and id_profile=:v1
                 minus
                 SELECT id_answer, id_question, time_posted
                 FROM answers
                 WHERE rownum <= :p1*10 and id_profile=:v1";

        $db_handler=Database::getConnection();

        $st=oci_parse($db_handler,$db_stm);

        oci_bind_by_name($st,':v1',$this->profileID);
        oci_bind_by_name($st,':p1',$page);

        oci_execute($st);

        $current_Page=Array();
        $index=0;

        while ($row = oci_fetch_array($st, OCI_ASSOC + OCI_RETURN_NULLS))
        {
            $current_answer=new AnswerModel($row['ID_ANSWER'],$this->profileID,$row['ID_QUESTION'],'',$row['TIME_POSTED'],'','');
            $current_Page[$index++]=$current_answer;
        }

        return $current_Page;

    }

    public function loadQuestionsByPage($page){


        $db_stm="SELECT id_question, time_posted
                 FROM questions
                 WHERE rownum < (:p1+1)*10 and id_profile=:v1
                 minus
                 SELECT id_question, time_posted
                 FROM questions
                 WHERE rownum <= :p1*10 and id_profile=:v1";

        $db_handler=Database::getConnection();

        $st=oci_parse($db_handler,$db_stm);

        oci_bind_by_name($st,':v1',$this->profileID);
        oci_bind_by_name($st,':p1',$page);

        oci_execute($st);

        $current_Page=Array();
        $index=0;


        while ($row = oci_fetch_array($st, OCI_ASSOC + OCI_RETURN_NULLS))
        {
            $current_question=new QuestionModel();
            $current_question->constructor($row['ID_QUESTION'],'',$row['TIME_POSTED'],'',$this->profileID,'');
            $current_Page[$index++]=$current_question;
        }

        return $current_Page;
    }

    public function getBadges()
    {
        $db_handler=Database::getConnection();

        $countLikes=null;
        $countAnsQuestions=null;

        $profileId = $this->getProfileID();
        $st=oci_parse($db_handler, 'BEGIN :rez := PROFILE_UTILS.ALL_VOTES(:id_profile); END;');
        oci_bind_by_name($st,':rez',$countLikes,40);
        oci_bind_by_name($st,':id_profile',$profileId);
        oci_execute($st);

        $st=oci_parse($db_handler, 'BEGIN :rez := PROFILE_UTILS.ALL_ACTIVITY(:id_profile); END;');
        oci_bind_by_name($st,':rez',$countAnsQuestions,40);
        oci_bind_by_name($st,':id_profile',$profileId);
        oci_execute($st);

        if ($countAnsQuestions<=10) $activityBadge='First Step';
        else if ($countAnsQuestions<=20) $activityBadge='Beginer';
        else if ($countAnsQuestions<=30) $activityBadge='Normal User';
        else if ($countAnsQuestions<=50) $activityBadge='Active User';
        else $activityBadge='Hyper-Active User';


        if ($countLikes<0) $likeBadge='Haters Gonna Hate';
        else if ($countLikes<=10) $likeBadge='Nice Guy';
        else if ($countLikes<=20) $likeBadge='One More Step For Popularity';
        else if ($countLikes<=50) $likeBadge='Popular';
        else if ($countLikes<=100) $likeBadge='Hero';
        else $likeBadge='King';

        $result[0]=$activityBadge;
        $result[1]=$likeBadge;

        return $result;
    }
}