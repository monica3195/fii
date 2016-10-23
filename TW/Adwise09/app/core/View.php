<?php

class View{

    private $file;

    private $data;

    public function __construct($file, $data = null){
        $this->file = $file;
        $this->data = $data;
    }

    public function __toString(){
        return " ";
    }
}