<?php
/**
 * Created by PhpStorm.
 * User: Iustines - PC
 * Date: 5/30/2015
 * Time: 11:54 PM
 */
require_once('../app/models/Questions.php');
require_once('../app/models/QuestionModel.php');
require_once('../app/utils/Database.php');
require_once('../app/models/search.php');
require_once('../app/models/Globals.php');


class QuestionsSelector extends Controller{

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

    public function index($args){


        if(($_SESSION['logged'] == true))
            $this->loadModelFromSession();


        $page = $_GET['page'];
        if(!$page)
            $page = 0;

        $content = $_GET['search-content'];
        $type = $_GET['type-of-search'];

        //var_dump($_GET);

        if($type == "by-content"){
            if($page == 0){
                searchByContent($content);
            }

//            if(!isset($_SESSION["content"]))
               // return;

  //          if($page > 9 || $page < 0)
             //

            $contentSearchId = json_decode($_SESSION["content"]);
          
            $k = 0;
            for($i = $page*20; $i < ($page + 1)*20; $i++){
                $currentSearchPage[$k++] = $contentSearchId[$i];
            }
            $maxQuestNum = count($contentSearchId);
            $questionArray = Questions::getQuestionsById($currentSearchPage);
        }

        if($type == "by-tag"){           
            $questionArray = Questions::getQuestionsWithTag($page,$content);
            $maxQuestNum = Questions::getNumberOfQuestionsWithTag($content);
        }

        if($type == "by-category"){

            $questionArray = Questions::getQuestionsFromCategory($page,$content);

            $maxQuestNum = Questions::getNumberOfQuestionsFromCategory($content);
        }


        $this->view('home/header', $this->user ? $this->user : null);
        $this->view('home/postquestion');
        $this->view('home/homecontent',$questionArray ? $questionArray : null);
        $this->view('home/pagination',  ["pageno" => $page, "totalpages" => $maxQuestNum/20-1 , "type" => $type , "param" => '']);



    }

    public static function checkPageCorrect($page){
        if(!is_numeric($page))
            return false;

        return true;
    }

    public static function checkTagsCorrect($params){
        $OK = true;

        return true;
    }

    public static function checkCategoriesCorrect($params){
        $OK = true;

        return true;
    }

    public static function select($type,$page,$param){

        if(!QuestionsSelector::checkPageCorrect($page)) {
            return false;
        }

        if($type == "default"){
            $questionArray = Questions::getLastPostedQuestions($page);
            return $questionArray;
        }

        if($type == "categories"){
            if(!QuestionsSelector::checkCategoriesCorrect($param))
                return false;
            $questionArray = Questions::getQuestionsFromCategory($page,$param);
            return $questionArray;
        }

        if($type = "tag"){
            if(!QuestionsSelector::checkTagsCorrect($param))
                return false;
            $questionArray = Questions::getQuestionsWithTag($page,$param);
            return $questionArray;
        }


        if($type = "content"){
            if(!isset($_SESSION["content"]))
                return false;
            if($page > 9 || $page < 0)
                return false;

            if($page == 0){
                search($param);
            }

            $contentSearchId = $_SESSION["content"];
            $k = 0;
            for($i = $page*20; $i < ($page + 1)*20; $i++){
                $currentSearchPage[$k++] = $contentSearchId[$i];
            }

            $questionArray = Questions::getQuestionsById($currentSearchPage);
            return $questionArray;
        }

        return false;
    }
}
?>