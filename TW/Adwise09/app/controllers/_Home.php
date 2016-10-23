
<?php

class Home extends Controller{

    //User object
    public $user = null;

    public function index($args){
        //Initialize userModel
        $this->user = $this->model('user');

        if(!isset($_SESSION['logged'])){
            require_once '../app/utils/Cookie.php';
            $userCredentials = Cookie::Get();

            //If cookie is set
            if($userCredentials != null){
                //Check if cookie data is valid
                $this->login($userCredentials['username'], $userCredentials['password']);
            }
        }

        $userUN = null;
        if(isset($_SESSION['usermodel'])) {
            $userUN = unserialize($_SESSION['usermodel']);
            $this->user = $userUN;
        }


        //Check if previous login requested to set cookie and set it
        if(isset($_SESSION['setcookie']) and $_SESSION['setcookie']){
            require_once '../app/utils/Cookie.php';
            Cookie::Set($this->user->getUserName(),$this->user->getPassword());
            unset($_SESSION['setcookie']);
        }
	
	//Iustin ....
	 if(isset($args[1]) and is_numeric($args[1])){
            $pageNo = $args[1];
        }else{
            $pageNo = 0;
        }


	require_once('../app/models/QuestionModel.php');
        require_once('../app/controllers/QuestionsSelector.php');

        $questionArray = Questions::getLastPostedQuestions($pageNo);
        $type = "default";
        $param = 0;
        $maxQuestNum = Questions::getNumberOfQuestions() / 20;
        $this->view('home/header', $userUN ? $userUN : null);
        $this->view('home/postquestion');
        $this->view('home/homecontent',$questionArray ? $questionArray : null);
        $this->view('home/pagination',  ["pageno" => $pageNo, "totalpages" => $maxQuestNum , "type" => $type , "param" => $param]);
	
    }

    public function login($userName = null, $password = null){

        if($userName == null){
            if(isset($_POST['loginparams'])){

                $loginparams = json_decode($_POST['loginparams']);

                $userName = $loginparams[0];
                $password = md5($loginparams[1]);

                if(isset($loginparams[2]) and ($loginparams[2] == true)){
                    $_SESSION['setcookie'] = true;
                }

                if(isset($loginparams[2]) and ($loginparams[2] == true)){
                    $this->login($userName, $password, true);
                }else{
                    $this->login($userName, $password);
                }

            }else{
                if(!isset($_SESSION['logged'])){
                    $this->view('home/header');
                    $this->view('home/login');
                }else{
                    header('Location:'.PUBLIC_ROOT);
                }
            }
        }else{
            error_reporting(0);
            if($_SESSION['dbconnection']) {
                $profileID = null;
                $responseToClient = [];

                $stid = oci_parse($_SESSION['dbconnection'], 'BEGIN :profileID := ACCESS_UTILS.LOG_IN(:pass, :user); END;');
                oci_bind_by_name($stid, ':user', $userName);
                oci_bind_by_name($stid, ':pass', $password);
                oci_bind_by_name($stid, ':profileID', $profileID, 40);

                $exe = oci_execute($stid);

                $errorArray  = oci_error($stid);

                if($errorArray != null){
                    if($errorArray['code'] > 0){
                        $responseToClient[0] = false;
                        $responseToClient[1] = 'Invalid credentials';
                    }
                }

                if (!$stid || !$exe) {
                    $responseToClient[0] = false;
                    $responseToClient[1] = 'Internal error';
                }

                oci_free_statement($stid);

                //Set model values
                if ($profileID != null) {

                    //Initialize userModel
                    $this->user = $this->model('user');
                    $this->user->setProfileID($profileID);

                    if ($this->user->loadUserDetails()) {


                        if(!$this->user->getIsBanned()) {

                            $responseToClient[0] = true;
                            $responseToClient[1] = 'Logged successfully';

                            $_SESSION['logged'] = true;
                            $_SESSION['usermodel'] = serialize($this->user);
                            //header("Location: public/");
                        }else{
                            $responseToClient[0] = false;
                            $responseToClient[1] = 'This account is banned';
                        }
                    }else{
                        $responseToClient[0] = false;
                        $responseToClient[1] = 'Load user error';
                    }
                }else{
                    $responseToClient[0] = false;
                    $responseToClient[1] = 'Account not found';
                }
            }else{
                $responseToClient[0] = false;
                $responseToClient[1] = 'Database connection problem';
            }
            $JSONReponseToClient = json_encode( $responseToClient);
            ignore_user_abort(true);
            header("Connection: close");
            header("Content-Length: " . mb_strlen($responseToClient));
            echo  $JSONReponseToClient;
            return ($responseToClient == true)?true:false;
        }
    }

    public function logout(){

        setcookie('PHPSESSID', '', time() - 3600, '/');
        setcookie('rememberme', '', time() - 3600, '/');
        $_SESSION = null;
        $_COOKIE = null;
        if(session_destroy()){
            echo '/home/logout';
            header('Location: ../../');
        }else{
            echo 'logout failure';
        }

    }

    public function register(){

        $serverReponse = [];
        if(!empty($_POST)) {
            $emptyFields = count(array_filter($_POST,create_function('$a','return $a == null;')));
            if($emptyFields < 1) {
                echo json_encode($_POST);
                return;
                $jsonResult = $this->processRegister($_POST);
                echo $jsonResult;
            }else{
                $serverReponse[0] = false;
                $serverReponse[1] = "Field/s empty";
                echo json_encode($serverReponse);
                //$this->view("home/register");
            }
        }else{
            if (!isset($_SESSION['logged'])) {
                $this->view('home/header');
                $this->view("home/register");
            } else { //used is logged redirect to home
                echo "Redirect";
                header('Location :'.PUBLIC_ROOT);
            }
        }
    }

    private function processRegister($registerUserArray){

        require_once "../app/models/Helper.php";
        $serverResponse = [true];

        if(isset($registerUserArray['username'])
            and isset($registerUserArray['password'])
            and isset($registerUserArray['cpassword'])
            and isset($registerUserArray['email'])
        ){
            //Some other checks before
            if($registerUserArray['password'] != $registerUserArray['cpassword']){
                $serverResponse[0] = false;
                $serverResponse[1] = "Passwords mismatch";
            }

            if(!Helper::validatePassword($registerUserArray['password'])){
                $serverResponse[0] = false;
                $serverResponse[1] = "Password must be between 8 and 30";
            }

            if(!Helper::validateEmail($registerUserArray['email'])){
                $serverResponse[0] = false;
                $serverResponse[1] = "Invalid email length, between 5 and 30, or invalid email format";
            }

            if($serverResponse[0] == true) {
                $profileID = null;
                $stid = oci_parse($_SESSION['dbconnection'], 'begin :ok := ACCESS_UTILS.INREGISTRARE(:pass, :user, :mail); end;');
                $encryptedPassWord = md5($registerUserArray['password']);
                oci_bind_by_name($stid, ':user', $registerUserArray['username']);
                oci_bind_by_name($stid, ':pass', $encryptedPassWord);
                oci_bind_by_name($stid, ':mail', $registerUserArray['email']);
                oci_bind_by_name($stid, ':ok', $profileID, 4);

                $executeResult = oci_execute($stid);

                if (!$executeResult || !$stid) {
                    $serverResponse[0] = false;
                    $serverResponse[1] = "User name exists !";
                }
            }
            return json_encode($serverResponse);
        }
    }
}