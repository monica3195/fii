<?php


Class App{

    protected $controller = 'home';

    protected $method = 'index';

    protected $params = [];

    public function __construct(){

        $url = $this->parseURL();

        if(file_exists('../app/controllers/' .$url[0].'.php')){
            $this->controller = $url[0];
            unset($url[0]);
        }

        //Load that controller
        require_once('../app/controllers/'.$this->controller.'.php');

        //Create an instance of controller
        $this->controller = new $this->controller;

        //If has a second param passes
        //This shoudl be a method
        if(isset($url[1])){
            if(method_exists($this->controller, $url[1])){
                $this->method = $url[1];
                unset($url[1]);
            }
        }

        //Set parameters to either array or empty array
        $this->params = $url ? array_values($url) : [];

        call_user_func([$this->controller, $this->method], $this->params);
    }

    public function parseURL(){

        if(isset($_GET['url'])){
            return $url = explode('/', filter_var(rtrim($_GET['url'],'/'),FILTER_SANITIZE_URL));
        }
    }
}