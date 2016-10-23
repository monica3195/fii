<?php

/**
 * Class Controller - The base controller for all other controllers. Extend this for each
 * created controller and get access to it's wonderful functionality
 */
class Controller
{

    /**
     * @brief - Render a view
     * @param $viewName - The name of the view to include/render
     * @param $data     - Any data that need to be available within the view
     */
    public function view($viewName, $data = []){

        require_once 'View.php';

        if(file_exists('../app/views/'.$viewName.'.php')) {
            require_once '../app/views/' . $viewName . '.php';
        }else{
            $viewName = '404';
            require_once '../app/views/404.php';
        }

        $view = new View($viewName, $data);
        echo $view;
    }

    public function model($modelName){
        require_once '../app/models/'.ucfirst($modelName).'.php';

        return new $modelName();
    }
}
