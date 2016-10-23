<?php
/**
 * Created by PhpStorm.
 * User: Iustines - PC
 * Date: 6/2/2015
 * Time: 1:05 AM
 */

require_once('Questions.php');

class ReportGenerator{
    private $questionArray;
    private $answerArray;


    public function getQuestionArray()
    {
        return $this->questionArray;
    }
    public function setQuestionArray($questionArray)
    {
        $this->questionArray = $questionArray;
    }
    public function getAnswerArray()
    {
        return $this->answerArray;
    }
    public function setAnswerArray($answerArray)
    {
        $this->answerArray = $answerArray;
    }

    public static function generateQuestionsByCategory($category,$dayNo){
        $sqlStmt = 'SELECT * FROM QUESTIONS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL) AND ID_CATEGORY IN (SELECT ID_CATEGORY FROM CATEGORIES WHERE NAME_CATEGORY = :CAT)';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_bind_by_name($stid,':CAT',$category);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
            }

            return $questionList;
        }

        return false;
    }
    public static function generateQuestionsByRegion($region,$dayNo){
        $sqlStmt = 'SELECT * FROM QUESTIONS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL) AND ID_PROFILE IN (SELECT ID_PROFILE FROM PROFILES WHERE COUNTRY = :CNTRY)';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_bind_by_name($stid,':CNTRY',$region);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
            }

            return $questionList;
        }

        return false;
    }
    public static function generateQuestionsByDate($dayNo){
        $sqlStmt = 'SELECT * FROM QUESTIONS WHERE  TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL)';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
            }

            return $questionList;
        }

        return false;
    }
    public static function generateQuestionsByCategoryAndRegion($category,$region,$dayNo){
        $sqlStmt = 'SELECT * FROM QUESTIONS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL) AND ID_PROFILE IN (SELECT ID_PROFILE FROM PROFILES WHERE COUNTRY = :CNTRY) AND ID_CATEGORY
          IN (SELECT ID_CATEGORY FROM CATEGORIES WHERE NAME_CATEGORY = :CAT)';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_bind_by_name($stid,':CAT',$category);
        oci_bind_by_name($stid,':CNTRY',$region);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        if(($row = oci_fetch_row($stid)) != false){
            $singleQuestion = new QuestionModel();
            $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
            $questionList[] = $singleQuestion;

            while (($row = oci_fetch_row($stid)) != false) {
                $singleQuestion = new QuestionModel();
                $singleQuestion->construct($row[0], $row[1], $row[2] , $row[3] , $row[4] , $row[5]);
                $questionList[] = $singleQuestion;
            }

            return $questionList;
        }

        return false;
    }

    public static function generateAnswersByCategory($category,$dayNo){
        $sqlStmt = 'SELECT * FROM ANSWERS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL) AND ID_QUESTION IN (SELECT ID_QUESTION FROM QUESTIONS WHERE ID_CATEGORY IN (SELECT
         ID_CATEGORY FROM CATEGORIES WHERE NAME_CATEGORY = :CAT))';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_bind_by_name($stid,':CAT',$category);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        $answersList=Array();
        $index=0;

        while ($row = oci_fetch_array($stid, OCI_ASSOC + OCI_RETURN_NULLS))
        {
            $current_answer = new AnswerModel($row['ID_ANSWER'],$row['ID_PROFILE'],$row['ID_QUESTION'],$row['ANSW_CONT'],$row['TIME_POSTED'],$row['USERNAME'],'');
            $answersList[$index++]=$current_answer;
        }

        return $answersList;
    }
    public static function generateAnswersByRegion($region,$dayNo){
        $sqlStmt = 'SELECT * FROM ANSWERS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL) AND ID_PROFILE IN (SELECT ID_PROFILE FROM PROFILES WHERE COUNTRY = :CNTRY)';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_bind_by_name($stid,':CNTRY',$region);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        $answersList=Array();
        $index=0;

        while ($row = oci_fetch_array($stid, OCI_ASSOC + OCI_RETURN_NULLS))
        {
            $current_answer = new AnswerModel($row['ID_ANSWER'],$row['ID_PROFILE'],$row['ID_QUESTION'],$row['ANSW_CONT'],$row['TIME_POSTED'],$row['USERNAME'],'');
            $answersList[$index++]=$current_answer;
        }

        return $answersList;
    }
    public static function generateAnswersByDate($dayNo){
        $sqlStmt = 'SELECT * FROM ANSWERS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL)';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        $answersList=Array();
        $index=0;

        while ($row = oci_fetch_array($stid, OCI_ASSOC + OCI_RETURN_NULLS))
        {
            $current_answer = new AnswerModel($row['ID_ANSWER'],$row['ID_PROFILE'],$row['ID_QUESTION'],$row['ANSW_CONT'],$row['TIME_POSTED'],$row['USERNAME'],'');
            $answersList[$index++]=$current_answer;
        }

        return $answersList;
    }
    public static function generateAnswersByCategoryAndRegion($category,$region,$dayNo){
        $sqlStmt = 'SELECT * FROM ANSWERS WHERE TIME_POSTED > (SELECT (TO_DATE(SYSDATE, \'DD-MON-RR\')
         - :DAY) FROM DUAL) AND ID_PROFILE IN (SELECT ID_PROFILE FROM PROFILES WHERE COUNTRY = :CNTRY) AND ID_QUESTION IN
(SELECT ID_QUESTION FROM QUESTIONS WHERE ID_CATEGORY IN (SELECT ID_CATEGORY FROM CATEGORIES WHERE NAME_CATEGORY = :CAT))';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':DAY',$dayNo);
        oci_bind_by_name($stid,':CAT',$category);
        oci_bind_by_name($stid,':CNTRY',$region);
        oci_execute($stid);

        if(!$stid){
            return false;
        }

        $answersList=Array();
        $index=0;

        while ($row = oci_fetch_array($stid, OCI_ASSOC + OCI_RETURN_NULLS))
        {
            $current_answer = new AnswerModel($row['ID_ANSWER'],$row['ID_PROFILE'],$row['ID_QUESTION'],$row['ANSW_CONT'],$row['TIME_POSTED'],$row['USERNAME'],'');
            $answersList[$index++]=$current_answer;
        }

        return $answersList;
    }


    public function generate($dayNo,$region,$category){
        var_dump($region);
        if($region == null && $category==null)
        {
            $this->answerArray = ReportGenerator::generateAnswersByDate($dayNo);
            $this->questionArray = ReportGenerator::generateQuestionsByDate($dayNo);
            return;
        }

        if($category == null)
        {
            $this->answerArray = ReportGenerator::generateAnswersByRegion($region,$dayNo);
            $this->questionArray = ReportGenerator::generateQuestionsByRegion($region,$dayNo);
            return;
        }

        if($region == null)
        {
            $this->answerArray = ReportGenerator::generateAnswersByCategory($category,$dayNo);
            $this->questionArray = ReportGenerator::generateQuestionsByCategory($category,$dayNo);
            return;
        }

        $this->answerArray = ReportGenerator::generateAnswersByCategoryAndRegion($category,$region,$dayNo);
        $this->questionArray = ReportGenerator::generateQuestionsByCategoryAndRegion($category,$region,$dayNo);


    }
}



?>