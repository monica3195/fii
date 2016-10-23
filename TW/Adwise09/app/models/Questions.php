<?php
/**
 * Created by PhpStorm.
 * User: Iustines - PC
 * Date: 5/24/2015
 * Time: 8:22 PM
 */
require_once("QuestionModel.php");

class Questions{

    public static function getNumberOfQuestions(){
        $sqlStmt = 'SELECT COUNT(*) FROM QUESTIONS';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    public static function getNumberOfQuestionsFromCategory($category){
        $sqlStmt = 'SELECT COUNT(*) FROM QUESTIONS WHERE ID_CATEGORY=(SELECT ID_CATEGORY FROM
        CATEGORIES WHERE NAME_CATEGORY=:CN ) ';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':CN',$category);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    public static function getNumberOfQuestionsWithTag($tag){
        $sqlStmt = 'select count(*) from questions_tags where id_tag = (select ID_TAG from tags where TAG_NAME=:TAG) ';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':TAG',$tag);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    public static function getLastPostedQuestions($pageNum){

        $rowNumStart = $pageNum*20;
        $rowNumEnd = $rowNumStart + 19;

        $sqlStmt = 'SELECT * FROM(SELECT ID_Question,Question_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER()
 over (order by TIME_POSTED DESC) rn FROM QuestionS)WHERE RN BETWEEN :S AND :F';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':S',$rowNumStart);
        oci_bind_by_name($stid,':F',$rowNumEnd);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;
            //$QuestionModelList[] = new QuestionModel($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
                //$questionList[] = new QuestionModel($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            }
            return $questionList;
        }

        return false;
    }

    public static function getQuestionsFromCategory($pageNum,$category){

        $rowNumStart = $pageNum*20;
        $rowNumEnd = $rowNumStart + 19;



        $sqlStmt = 'SELECT * FROM (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,
        ROW_NUMBER() over (order by TIME_POSTED DESC) rn FROM QUESTIONS WHERE ID_CATEGORY=(SELECT ID_CATEGORY FROM
        CATEGORIES WHERE NAME_CATEGORY=:CN ) ) WHERE RN BETWEEN :S AND :F';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':CN',$category);
        oci_bind_by_name($stid,':S',$rowNumStart);
        oci_bind_by_name($stid,':F',$rowNumEnd);
        oci_execute($stid);



        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;
            //$questionList[] = new QuestionModel($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
                //$questionList[] = new QuestionModel($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            }

            return $questionList;
        }

        return false;
    }

    public static function getQuestionsWithTag($pageNum,$tag){

        $rowNumStart = $pageNum*20;
        $rowNumEnd = $rowNumStart + 19;

        $sqlStmt = 'SELECT   * FROM (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,
ROW_NUMBER() over (order by TIME_POSTED DESC) rn FROM QUESTIONS WHERE ID_QUESTION IN (SELECT ID_QUESTION
FROM QUESTIONS_TAGS WHERE ID_TAG= (SELECT ID_TAG FROM TAGS WHERE TAG_NAME= :TAG ))) WHERE RN BETWEEN
:S AND :F';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':TAG',$tag);
        oci_bind_by_name($stid,':S',$rowNumStart);
        oci_bind_by_name($stid,':F',$rowNumEnd);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;
            //$questionList[] = new QuestionModel($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
                //$questionList[] = new QuestionModel($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            }

            return $questionList;
        }

        return false;
    }

    public static function getQuestionsById($arrayId){
        foreach($arrayId as $id){
            $sqlStmt = "SELECT * FROM QUESTIONS WHERE ID_QUESTION = :ID";
            $stid = oci_parse(Database::getConnection(), $sqlStmt);
            oci_bind_by_name($stid,':ID',$id);

            oci_execute($stid);

            if(!$stid){
                return false;
            }

            if(($row = oci_fetch_row($stid)) != false){
                $singleQuestion = new QuestionModel();
                $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;

                while (($row = oci_fetch_row($stid)) != false) {
                    $singleQuestion = new QuestionModel();
                    $singleQuestion->constructor($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                    $questionList[] = $singleQuestion;
                }


            }
        }


        return $questionList;

    }
}
?>