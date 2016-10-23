<?php
    require_once('../app/utils/Database.php');
    require_once('../app/models/search.php');

    class Question extends Controller{

        private $user = null;
        private $answers = null;

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

        public function index($args)
        {
            $page = $_GET['page'];
            if(!$page)
                $page = 0;

            //Load user model
            if (isset($_SESSION['logged'])) {
                if ($this->loadModelFromSession()) {
                    //User loaded
                } else {
                    //Session problem
                }
            }
            //Render header view
            $this->view('home/header', $this->user);

            //Render question page
            //TODO Load question info
            $questionID = null;
            $questionInstanceM = $this->model('questionmodel');
            //$args[0] = 13;
            if (isset($args[0]) && is_numeric($args[0])) {
                $questionID = $args[0];
                $questionInstanceM->loadQuestionByID($questionID);
                $questionInstanceM->loadAnswersByPage($page);
                $nrAns = $questionInstanceM->getNrOfAnswers();
                //Add new view
                $questionInstanceM->addView();
            }
            //$this->view('question/question', $questionID ? $questionInstanceM : null);
            $this->view('question/question', $questionID ? [$questionInstanceM, $this->user] : null);
            $this->view('home/pagination',["pageno" => $page, "totalpages" => $nrAns/10-1 , "type" => '' , "param" => '']);
        }

        private function processcontent($text)
        {
            $result=Array();
            $index=0;
            for ($i=0; $i<strlen($text); ++$i){
                $result[$index++]=$text[$i];
                if ($text[$i]=="<") $result[++$index]=" ";
            }

            return implode($result);
        }

        private function processtag($text)
        {
            $result=Array();
            $index=0;
            for ($i=0; $i<strlen($text); ++$i)
                if ($text[$i]!=',')
                {
                    $result[$index++]=$text[$i];
                    if ($text[$i]=="<") $result[++$index]=" ";
                }

            return implode($result);
        }

        public function postQuestion(){
            //preiau datele
            $category=strtoupper($_POST['category']);
            $content=$this->processcontent($_POST['content']);
            if (strlen($content)==0)
            {
                header("Location: public/home");
                die();
            }
            $userUN = null;
            if(isset($_SESSION['usermodel'])) {
                $userUN = $this->model('user');
                $userUN = unserialize($_SESSION['usermodel']);
            }
            if (!$userUN) $user_id=0;
            else $user_id=$userUN->getProfileID();
            $tags="";

            foreach ( $_POST['tag'] as $key => $value )
            {
                $value=$this->processtag($value);
                if (strlen($value)>0) $tags.=$value.',';
            }
            $tags=rtrim($tags, ",");
            //fac update la structura arborescenta

            update_Keywords($content);

            //fac update in baza de date
            $db_handler=Database::getConnection();
            $st=oci_parse($db_handler, 'BEGIN :rez := QUESTIONS_ANSWERS.POST_QUESTION(:id_profile, :content, :category, :tags); END;');
            $tagsUp = strtoupper($tags);
            $q_id=null;
            oci_bind_by_name($st,':id_profile',$user_id);
            oci_bind_by_name($st,':content',$content);
            oci_bind_by_name($st,':category',$category);
            oci_bind_by_name($st,':tags',$tagsUp);
            oci_bind_by_name($st,':rez',$q_id,40);
            $ok=oci_execute($st);

            if(!$ok || !$st){
                header("Location:".PUBLIC_ROOT);
                die();
            }
            else {
                header("Location:".PUBLIC_ROOT."/question/index/".$q_id);
                die();
            }
        }

        /**
         * @param $args - arg[0] describe the question id
         * @return boolean
         */
        public function postAnswer($args){

            //Load user model
            $serverResponse = [];
            $serverResponse[0] = true;
            if (isset($_SESSION['logged'])) {
                if ($this->loadModelFromSession()) {
                    //User loaded
                } else {
                    //Session problem
                }
            }

            // 0 anonim user
            //Post question
            $questionID = null;
            $profileID = ($this->user) ? $this->user->getProfileID(): 0;

            if($_SESSION['dbconnection']){

                $JSONAnswerToPost = $_POST['postanswerparams'];
                $AnswerToPostArray = json_decode($JSONAnswerToPost);

                $questionID = $AnswerToPostArray[1];

                if(!is_numeric($questionID)){
                    $serverResponse[0] = false;
                    $serverResponse[1] = "Invalid question id !";
                }

                //get answer content
                $answerContent =$AnswerToPostArray[0];
               // $answerContent = htmlentities($answerContent, UTF-8	);

                if(strlen($answerContent) < 30){
                    $serverResponse[0] = false;
                    $serverResponse[1] = "Answer minimum length is 20";
                }

                $resultOC = false;
                if($serverResponse[0] == true) {
                    $dbHandler = oci_parse(Database::getConnection(), "BEGIN QUESTIONS_ANSWERS.POST_ANSWER(:profile_id, :q_id, :a_content) ;END;");
                    oci_bind_by_name($dbHandler, ':profile_id', $profileID);
                    oci_bind_by_name($dbHandler, ':q_id', $questionID);
                    oci_bind_by_name($dbHandler, ':a_content', $answerContent);

                    $resultOC = oci_execute($dbHandler);
                }
                if($resultOC){
                    $serverResponse[0] = true;
                    $serverResponse[1] = "Answered !";
                }else if ($serverResponse[0] == true){
                    $serverResponse[0] = false;
                    $serverResponse[1] = "Insert error !";
                }
            }else{
                $serverResponse[0] = false;
                $serverResponse[1] = "Database problem !";
            }
            echo json_encode($serverResponse);
        }

        public function deleteAnswer($args){

            echo "Delete answer !";

            if (isset($_SESSION['logged'])) {
                if ($this->loadModelFromSession()) {
                    //User loaded
                } else {
                    //Session problem
                }
            }

            if($this->user->getIsAdmin() || ($this->hasAnswers($this->user->getProfileID(), $args[0]))){

                if(is_numeric($args[0])) {

                    $std = oci_parse(Database::getConnection(), "BEGIN QUESTIONS_ANSWERS.DELETE_ANSWER(:ANS_ID) ;END;");
                    oci_bind_by_name($std, ':ANS_ID', $args[0]);
                    $res = oci_execute($std);

                    if($res){
                        echo "<script>alert('Answer deleted !')</script>";
                       // header("Location:".PUBLIC_ROOT);
                    }else{
                        echo "<script>alert('Invalid answer id !')</script>";
                       // header("Location:".PUBLIC_ROOT);
                    }
                }else{
                    echo "<script>alert('Invalid answer id !')</script>";
                    //header("Location:".PUBLIC_ROOT);
                }
            }else{
                //No rights

                //header("Location:".PUBLIC_ROOT);
            }
        }

        public function deleteQuestion($args){
            if (isset($_SESSION['logged'])) {
                if ($this->loadModelFromSession()) {
                    //User loaded
                } else {
                    //Session problem
                }
            }

            if($this->user->getIsAdmin() || ($this->hasQuestion($this->user->getProfileID(), $args[0]))){

                if(is_numeric($args[0])) {

                    $std = oci_parse(Database::getConnection(), "BEGIN QUESTIONS_ANSWERS.DELETE_QUESTION(:QUE_ID) ;END;");
                    oci_bind_by_name($std, ':QUE_ID', $args[0]);
                    $res = oci_execute($std);

                    if($res){
                        echo "<script>alert('Question deleted !')</script>";
                        header("Location:".PUBLIC_ROOT);
                    }else{
                        echo "<script>alert('Invalid question id !')</script>";
                        header("Location:".PUBLIC_ROOT);
                    }
                }else{
                    echo "<script>alert('Invalid question id !')</script>";
                    header("Location:".PUBLIC_ROOT);
                }
            }else{
                //No rights
                header("Location:".PUBLIC_ROOT);
            }
        }

        public function vote(){

            if ($_POST['question'])
            {

                $responseToClient = [];
                $JSONFromClient = json_decode($_POST['question']);
                $q_id = $JSONFromClient[0];
                $vote = $JSONFromClient[1];

                $userUN = null;
                if (isset($_SESSION['usermodel'])) {
                    $userUN = $this->model('user');
                    $userUN = unserialize($_SESSION['usermodel']);
                }

                if (!$userUN) $user_id = 0;
                else $user_id = $userUN->getProfileID();

                //fac update in baza de date
                $newRating = 0;
                if ($vote == 1)
                    $db_stm = "BEGIN :r := QUESTIONS_ANSWERS.LIKE_QUESTION(:v1,:v2); END;";
                else $db_stm = "BEGIN :r := QUESTIONS_ANSWERS.DISLIKE_QUESTION(:v1,:v2); END;";

                $db_handler = Database::getConnection();

                $st = oci_parse($db_handler, $db_stm);

                oci_bind_by_name($st, ':v1', $q_id);
                oci_bind_by_name($st, ':r', $newRating);
                oci_bind_by_name($st, ':v2', $user_id);

                $ok = oci_execute($st);

                if (!$ok) {
                    $responseToClient[0] = false;
                }
                else {
                    $responseToClient[0] = true;
                    $responseToClient[1] = $newRating;
                }
                echo json_encode($responseToClient);
            }
            else
            {
                $responseToClient = [];
                $JSONFromClient = json_decode($_POST['answer']);

                $a_id = $JSONFromClient[0];
                $vote =  $JSONFromClient[1];

                $newRating = 0;

                $userUN = null;
                if (isset($_SESSION['usermodel'])) {
                    $userUN = $this->model('user');
                    $userUN = unserialize($_SESSION['usermodel']);
                }

                if (!$userUN) $user_id = 0;
                else $user_id = $userUN->getProfileID();

                //fac update in baza de date
                if ($vote == 1) $db_stm = "BEGIN :r := QUESTIONS_ANSWERS.LIKE_ANSWER(:v1,:v2); END;";
                else $db_stm = "BEGIN :r := QUESTIONS_ANSWERS.DISLIKE_ANSWER(:v1,:v2); END;";

                $db_handler = Database::getConnection();

                $st = oci_parse($db_handler, $db_stm);

                oci_bind_by_name($st, ':v1', $a_id);
                oci_bind_by_name($st, ':v2', $user_id);
                oci_bind_by_name($st, ':r', $newRating);

                $ok = oci_execute($st);

                if (!$ok) {
                    $responseToClient[0] = false;
                }
                else {
                    $responseToClient[0] = true;
                    $responseToClient[1] = $newRating;
                }
                echo json_encode($responseToClient);
            }
        }

        private function hasAnswers($uid, $ansId){
            $result = false;
            $dbHandler = oci_parse(Database::getConnection(), "BEGIN :r := WHOSANSWER(:a, :u); END;");
            oci_bind_by_name($dbHandler, ':r', $result);
            oci_bind_by_name($dbHandler, ':a', $ansId);
            oci_bind_by_name($dbHandler, ':u', $uid);

            $resultExec = oci_execute($dbHandler);

            if($result and $resultExec)
            {
                return true;
            }else{
                return false;
            }
        }

        private function hasQuestion($uid, $questionId){

            $result = false;
            $dbHandler = oci_parse(Database::getConnection(), "BEGIN :r := WHOSQUESTION(:q, :u); END;");
            oci_bind_by_name($dbHandler, ':r', $result);
            oci_bind_by_name($dbHandler, ':q', $questionId);
            oci_bind_by_name($dbHandler, ':u', $uid);

            $resultExec = oci_execute($dbHandler);

            if($result and $resultExec)
            {
                return true;
            }else{
                return false;
            }
        }
    }