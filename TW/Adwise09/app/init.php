<?php

define('INC_ROOT', dirname(__DIR__));

// Root path for assets
define('PUBLIC_ROOT',
    'http://'.$_SERVER['HTTP_HOST'].
    str_replace(
        $_SERVER['DOCUMENT_ROOT'],
        '',
        str_replace('\\', '/', INC_ROOT).'/public'
    )
);

define('APP_ROOT',
    'http://'.$_SERVER['HTTP_HOST'].
    str_replace(
        $_SERVER['DOCUMENT_ROOT'],
        '',
        str_replace('\\', '/', INC_ROOT).'/app'
    )
);

require 'core/app.php';
require 'core/Controller.php';
require 'utils/Database.php';
require 'utils/Cookie.php';
require_once 'models/KeywordsTree.php';

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

//Connect to database and set connection handler in session array
if(isset($_SESSION['dbconnection']) and ($_SESSION['dbconnection'] == null)){
    Database::connect();
}

if(!isset($_SESSION['dbconnection'])) {
    Database::connect();
}