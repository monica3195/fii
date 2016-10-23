<?php
class ShowReport extends Controller{

    public function index($args){
        $content = $_POST['content'];
        echo $content;
    }
}
?>

