<?php
    class Account extends Controller{

        private $user = null;

        private function loadModelFromSession()
        {
            if (isset($_SESSION['usermodel'])) {
                $this->user = $this->model('user');
                if (isset($_SESSION['usermodel'])) {
                    $this->user = unserialize($_SESSION['usermodel']);
                        return true;
                }
            }
            return false;
        }

        public function index($args = null){

            if(isset($_SESSION['logged'])){
                if($this->loadModelFromSession()){
                    $this->view('home/header', $this->user);
                    $this->view('account/profile', $this->user);
                }else{
                    //Session problem
                }
            }else{
                header('Location: ../../');
            }
        }

        public function editprofile(){
            if(isset($_SESSION['logged']) && $this->loadModelFromSession()) {

                if(!empty($_POST)){
                    $editProfileParams = json_decode($_POST['editprofileparams']);

                    $this->user->setFirstName($editProfileParams[0]);
                    $this->user->setLastName($editProfileParams[1]);
                    $this->user->setEmail($editProfileParams[2]);
                    $this->user->setAge($editProfileParams[3]);
                    $this->user->setCountry($editProfileParams[4]);
                    $this->user->setCity($editProfileParams[5]);


                    if($this->user->saveUserDetails()){
                        echo "Profile updated !";
                    }else{
                        echo "Internal error !";
                    }


                }else{
                    $this->view('home/header', $this->user);
                    $this->view('account/editprofile', $this->user);
                }
            }else{
                header('Location: ../../');
            }
        }

        public function changepassword($args = null){

            if(isset($_SESSION['logged']) && $this->loadModelFromSession()){

                if(!empty($_POST)) {
                    $emptyFields = count(array_filter($_POST, create_function('$a', 'return $a == null;')));
                    if ($emptyFields < 1) {
                        $changePasswordParams = json_decode($_POST['changepasswordparams']);

                        $oldpassw = $changePasswordParams[0];
                        $newpassw = $changePasswordParams[1];

                            if (md5($oldpassw) == $this->user->getPassword()){
                                if($this->user->changePassword(md5($newpassw))) {
                                    echo "Password changed";
                                }else{
                                    echo "Internal error update";
                                }
                            }else{
                                echo "Old password mismatch";
                            }

                    }
                }
                else {
                    $this->view('home/header', $this->user);
                    $this->view('account/changepassword');
                }
            }else{
                header('Location: ../../');
            }
        }

        public function checkPassword($arg){

            if(isset($_SESSION['logged'])){
                if(isset($_SESSION['usermodel'])){

                    $this->user = $this->model('user');
                    if(isset($_SESSION['usermodel'])) {
                        $this->user = unserialize($_SESSION['usermodel']);
                    }
                }
            }else{
                header('Location: ../../');
            }
        }

        public function banunban($args){

            if(isset($_SESSION['logged'])){
                if(isset($_SESSION['usermodel'])){

                    $this->user = $this->model('user');
                    if(isset($_SESSION['usermodel'])) {
                        $this->user = unserialize($_SESSION['usermodel']);
                    }
                }
                if($this->user->getIsAdmin()){

                    if(isset($_POST['username'])){

                        $userName = $_POST['username'];
                        $std = oci_parse($_SESSION['dbconnection'], "SELECT COUNT(*) AS NR FROM PROFILES WHERE USERNAME = :u");
                        oci_bind_by_name($std, ':u', $userName);
                        $resultExec = oci_execute($std);

                        $number =  oci_fetch_array($std, OCI_ASSOC + OCI_RETURN_NULLS)['NR'];

                        if($number == 0){
                            echo "User doesn't exit !";
                            return;
                        }

                        if($args[0] == 'ban') {
                            $std = oci_parse($_SESSION['dbconnection'], "UPDATE PROFILES SET IS_BANNED = 1 WHERE USERNAME = :u");
                        }else{
                            $std = oci_parse($_SESSION['dbconnection'], "UPDATE PROFILES SET IS_BANNED = 0 WHERE USERNAME = :u");
                        }
                        oci_bind_by_name($std, ':u', $userName);

                        $result = oci_execute($std);

                        if($result){
                            if($args[0] == 'ban'){
                                echo "Banned !";
                            }else{
                                echo "Unbanned";
                            }

                        }else{
                            echo "User not found";
                        }
                    }else{
                        echo "Invalid post message";
                    }
                }else{
                    header('Location: ../../');
                }
            }else{
                header('Location: ../../');
            }
        }
    }
