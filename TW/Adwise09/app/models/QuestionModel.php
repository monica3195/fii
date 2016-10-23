<?php
/**
 * Created by PhpStorm.
 * User: Iustines - PC
 * Date: 5/24/2015
 * Time: 9:01 PM
 */

require_once 'AnswerModel.php';
class QuestionModel {
    private $questionID;
    private $questionContent;
    private $timePosted;
    private $categoryID;
    private $profileID;
    private $viewNumber;
    private $tags;
    private $answers;
    private $nrOfAnswers;
    private $raiting;
    private $nrViews;

    /**
     * @return mixed
     */
    public function getNrViews()
    {
        return $this->nrViews;
    }

    /**
     * @param mixed $nrViews
     */
    public function setNrViews($nrViews)
    {
        $this->nrViews = $nrViews;
    }

    /**
     * @return mixed
     */
    public function getRaiting()
    {
        return $this->raiting;
    }

    /**
     * @param mixed $raiting
     */
    public function setRaiting($raiting)
    {
        $this->raiting = $raiting;
    }

    /**
     * @return mixed
     */
    public function getNrOfAnswers()
    {
        return $this->nrOfAnswers;
    }

    /**
     * @param mixed $nrOfAnswers
     */
    public function setNrOfAnswers($nrOfAnswers)
    {
        $this->nrOfAnswers = $nrOfAnswers;
    }

    public function __constructor(){

    }

    public function construct( $vQuestionID,$vQuestionContent,$vTimePosted,$vCategoryID,$vProfileID,$vViewNumber){
        $this->questionID = $vQuestionID;
        $this->questionContent = $vQuestionContent;
        $this->timePosted = $vTimePosted;
        $this->categoryID = $vCategoryID;
        $this->profileID = $vProfileID;
        $this->viewNumber  = $vViewNumber;

    }

    public function constructor( $vQuestionID,$vQuestionContent,$vTimePosted,$vCategoryID,$vProfileID,$vViewNumber){
        $this->questionID = $vQuestionID;
        $this->questionContent = $vQuestionContent;
        $this->timePosted = $vTimePosted;
        $this->categoryID = $vCategoryID;
        $this->profileID = $vProfileID;
        $this->viewNumber  = $vViewNumber;

        $this->loadNrOfViews();
        $this->loadRating();
    }

    public function getQuestionID(){
        return $this->questionID;
    }

    public function getQuestionContent(){
        return $this->questionContent;
    }

    public function getTimePosted(){
        return $this->timePosted;
    }

    public function getCategoryID(){
        return $this->categoryID;
    }

    public function getProfileID(){
        return $this->profileID;
    }

    public function getViewNumber(){
        return $this->viewNumber;
    }

    public function setQuestionID($value){
        $this->questionID = $value;
    }

    public function setQuestionContent($value){
        $this->questionContent = $value;
    }

    public function setTimePosted($value){
        $this->timePosted = $value;
    }

    public function setCategoryID($value){
        $this->categoryID = $value;
    }

    public function setProfileID($value){
        $this->profileID = $value;
    }

    public function setViewNumber($value){
        $this->viewNumber = $value;
    }

    public function getQuestionCategory(){
        $sqlStmt = 'SELECT NAME_CATEGORY FROM CATEGORIES WHERE ID_CATEGORY= :ID';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':ID',$this->categoryID);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    public function getUser(){
        $sqlStmt = 'SELECT USERNAME FROM PROFILES WHERE ID_PROFILE=:ID';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':ID',$this->profileID);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    public function getLikes(){
        $sqlStmt = 'SELECT COUNT(*) FROM QUESTIONS_LIKES WHERE ID_QUESTION=:ID';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':ID',$this->questionID);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    /**
     * @return mixed
     */
    public function getTags()
    {
        return $this->tags;
    }

    /**
     * @param mixed $tags
     */
    public function setTags($tags)
    {
        $this->tags = $tags;
    }

    public function getDislikes(){
        $sqlStmt = 'SELECT COUNT(*) FROM QUESTIONS_DISLIKES WHERE ID_QUESTION=:ID';
        $stid = oci_parse(Database::getConnection(), $sqlStmt);
        oci_bind_by_name($stid,':ID',$this->questionID);
        oci_execute($stid);
        $row = oci_fetch_row($stid);

        return $row[0];
    }

    public function getAnswers(){
            return $this->answers;
    }

    public function delete(){
        $stid = oci_parse(Database::getConnection(), 'begin QUESTIONS_ANSWERS.DELETE_QUESTION(:qid); end;');
        oci_bind_by_name($stid,':qid',$this->questionID);

        oci_execute($stid);

        oci_free_statement($stid);
    }

    public function like($pid){
        $stid = oci_parse(Database::getConnection(), 'begin QUESTIONS_ANSWERS.LIKE_QUESTION(:qid,:pid); end;');
        oci_bind_by_name($stid,':qid',$this->questionID);
        oci_bind_by_name($stid,':pid',$pid);

        oci_execute($stid);

        oci_free_statement($stid);
    }

    public function dislike($pid){
        $stid = oci_parse(Database::getConnection(), 'begin QUESTIONS_ANSWERS.DISLIKE_QUESTION(:qid,:pid); end;');
        oci_bind_by_name($stid,':qid',$this->questionID);
        oci_bind_by_name($stid,':pid',$pid);

        oci_execute($stid);

        oci_free_statement($stid);
    }

    public function postAnswer($vPid,$ansCont){
        $stid = oci_parse(Database::getConnection(), 'begin QUESTIONS_ANSWERS.POST_ANSWER(:pid,:qid,:content); end;');
        oci_bind_by_name($stid,':pid',$vPid);
        oci_bind_by_name($stid,':qid',$this->questionID);
        oci_bind_by_name($stid,':content',$ansCont);

        oci_execute($stid);

        oci_free_statement($stid);
    }

    public function editQuestion($val){
        $this->setQuestionContent($val);
    }

    public function loadTags(){

        $questionID = $this->getQuestionID();
        $stid = oci_parse(Database::getConnection(), 'SELECT t.TAG_NAME
                                                      FROM TAGS t, QUESTIONS_TAGS qt, QUESTIONS q
                                                      WHERE q.ID_QUESTION = :qid AND t.ID_TAG = qt.ID_TAG AND qt.ID_QUESTION = q.ID_QUESTION');
        oci_bind_by_name($stid,':qid',$questionID);
        oci_execute($stid);

        while($row = oci_fetch_assoc($stid)){
            $tagValue = $row['TAG_NAME'];
            $this->tags[$tagValue] = true;
        }
        oci_free_statement($stid);
    }

    public function loadQuestionByID($questionID){

        $stid = oci_parse(Database::getConnection(), 'SELECT * FROM QUESTIONS WHERE ID_QUESTION = :qid');
        oci_bind_by_name($stid,':qid',$questionID);
        oci_execute($stid);
        $row = oci_fetch_assoc($stid);

        //set fields
        $this->setCategoryID($row['ID_CATEGORY']);
        $this->setQuestionContent($row['QUESTION_CONTENT']);
        $this->setQuestionID($row['ID_QUESTION']);
        $this->setTimePosted($row['TIME_POSTED']);
        $this->setViewNumber($row['NMB_VIEWS']);
        $this->setProfileID($row['ID_PROFILE']);
        oci_free_statement($stid);

        //Load tags
        $this->loadTags();
        $this->loadNrOfAnswers();
        $this->loadAnswersByPage(0);
        $this->loadNrOfViews();
        $this->loadRating();
    }

    public function loadAnswersByPage($page_number){

        $db_stm='select a.*, p.username
                 from answers a, profiles p
                 where rownum < ( :p1 +1)*10 and a.id_question = :v1 and p.id_profile=a.id_profile
                 minus
                 select a.*, p.username
                 from answers a, profiles p
                 where rownum <= :p1 * 10 and a.id_question = :v1 and p.id_profile=a.id_profile';

        $db_handler=Database::getConnection();

        $st=oci_parse($db_handler,$db_stm);

        $currentQuestionId = $this->questionID;
        oci_bind_by_name($st,':v1', $currentQuestionId);
        oci_bind_by_name($st,':p1',$page_number);

        oci_execute($st);

        $current_Page=Array();
        $index=0;

        while ($row = oci_fetch_array($st, OCI_ASSOC + OCI_RETURN_NULLS))
        {

            $id_answer=$row['ID_ANSWER'];
            $db_stm1='select NVL(sum(like_dislike),0) as NR from answers_votes where id_answer=:v1';

            $st1=oci_parse($db_handler,$db_stm1);

            oci_bind_by_name($st1,':v1',$id_answer);

            oci_execute($st1);
            $rating=oci_fetch_array($st1, OCI_ASSOC + OCI_RETURN_NULLS)['NR'];

            $current_answer = new AnswerModel($row['ID_ANSWER'],$row['ID_PROFILE'],$row['ID_QUESTION'],$row['ANSW_CONT'],$row['TIME_POSTED'],$row['USERNAME'],$rating);
            $current_Page[$index++]=$current_answer;
        }

        $this->answers = $current_Page;

    }

    public function loadNrOfAnswers(){

        $db_handler=Database::getConnection();
        $st=oci_parse($db_handler,'SELECT COUNT(*) as NR from answers where id_question=:v1');

        oci_bind_by_name($st,':v1',$this->questionID);
        oci_execute($st);

        $this->nrOfAnswers=oci_fetch_array($st, OCI_ASSOC + OCI_RETURN_NULLS)['NR'];

    }


    public function loadNrOfViews(){
        $db_handler=Database::getConnection();
        $st=oci_parse($db_handler,'SELECT nmb_views as NR from questions where id_question=:v1');

        oci_bind_by_name($st,':v1',$this->questionID);
        oci_execute($st);

        $this->nrViews=oci_fetch_array($st, OCI_ASSOC + OCI_RETURN_NULLS)['NR'];
    }

    public function loadRating(){
        $db_handler=Database::getConnection();
        $st=oci_parse($db_handler,'SELECT NVL(sum(like_dislike),0) as NR from questions_votes where id_question=:v1');

        oci_bind_by_name($st,':v1',$this->questionID);
        oci_execute($st);

        $this->raiting=oci_fetch_array($st, OCI_ASSOC + OCI_RETURN_NULLS)['NR'];
    }

    public function addView(){

        $db_handler=Database::getConnection();
        $st=oci_parse($db_handler,'UPDATE questions SET nmb_views=nmb_views+1 WHERE id_question=:v1');

        oci_bind_by_name($st,':v1',$this->questionID);
        oci_execute($st);
        $this->nrViews+=1;

    }
}

?>
