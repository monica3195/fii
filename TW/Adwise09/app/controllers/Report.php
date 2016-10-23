<?php

require_once('../app/models/ReportGenerator.php');

class Report extends Controller{

    public function index($args){


        $region = $_GET['region'];
        if(!$region)
            $region = null;

        $category = $_GET['category'];
        if(!$category)
            $category = null;

        $dayNo = $_GET['date'];
        if(!$dayNo)
            $dayNo = 0;

        $temp = new ReportGenerator();
        $temp->generate($dayNo,$region,$category);
        $vect[0] = $temp->getQuestionArray();
        $vect[1] = $temp->getAnswerArray();

        var_dump($vect);

        $json = json_encode($vect);

        $file = 'report.json';
        file_put_contents($file, $json);

    }
}
?>