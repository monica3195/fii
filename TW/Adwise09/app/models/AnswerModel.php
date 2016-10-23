<?php


 class AnswerModel {

    public $idanswer;
    public $idprofile;
    public $idquestion;
    public $answcontent;
    public $timeposted;
    public $username;
    public $rating;

     public function __construct($idanswer, $idprofile, $idquestion, $answcontent, $timeposted, $username, $rating){
         $this->idanswer = $idanswer;
         $this->idprofile = $idprofile;
         $this->idquestion = $idquestion;
         $this->answcontent = $answcontent;
         $this->timeposted = $timeposted;
         $this->username = $username;
         $this->rating = $rating;
     }

     /**
      * @return mixed
      */
     public function getRating()
     {
         return $this->rating;
     }

     /**
      * @param mixed $rating
      */
     public function setRating($rating)
     {
         $this->rating = $rating;
     }

     /**
      * @return mixed
      */
     public function getUsername()
     {
         return $this->username;
     }

     /**
      * @param mixed $username
      */
     public function setUsername($username)
     {
         $this->username = $username;
     }

     /**
      * @return mixed
      */
     public function getIdanswer()
     {
         return $this->idanswer;
     }

     /**
      * @param mixed $idanswer
      */
     public function setIdanswer($idanswer)
     {
         $this->idanswer = $idanswer;
     }

     /**
      * @return mixed
      */
     public function getIdprofile()
     {
         return $this->idprofile;
     }

     /**
      * @param mixed $idprofile
      */
     public function setIdprofile($idprofile)
     {
         $this->idprofile = $idprofile;
     }

     /**
      * @return mixed
      */
     public function getIdquestion()
     {
         return $this->idquestion;
     }

     /**
      * @param mixed $idquestion
      */
     public function setIdquestion($idquestion)
     {
         $this->idquestion = $idquestion;
     }

     /**
      * @return mixed
      */
     public function getAnswcontent()
     {
         return $this->answcontent;
     }

     /**
      * @param mixed $answcontent
      */
     public function setAnswcontent($answcontent)
     {
         $this->answcontent = $answcontent;
     }

     /**
      * @return mixed
      */
     public function getTimeposted()
     {
         return $this->timeposted;
     }

     /**
      * @param mixed $timeposted
      */
     public function setTimeposted($timeposted)
     {
         $this->timeposted = $timeposted;
     }

 }
?>