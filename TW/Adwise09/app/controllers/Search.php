<?php
class Search extends Controller{

    public function index($args){

        if(isset($_GET['searchtype']) and isset($_GET['searchcontent'])){

            if(isset($_GET['searchtype']) and ($_GET['searchtype']== 'tag')){

            }else if(isset($_GET['searchtype']) and ($_GET['searchtype']== 'content')){

            }else{

            }

        }else{

        }
    }
}