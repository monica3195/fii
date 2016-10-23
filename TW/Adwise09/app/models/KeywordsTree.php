<?php

class KeywordsTree {

    static function insert_word(Node $root, $text, $number_of_aparitions)
    {
        $v = $root;
        foreach(str_split($text) as $char) {
            $next = $v->children[$char];
            if ($next == null)
            {
                $v->children[$char] = $next = new Node();
            }
            $v = $next;
            $v->nr_of_documents+=$number_of_aparitions;
        }

    }

    static function get_hits(Node $root, $text)
    {
        $v = $root;
        foreach(str_split($text) as $char) {
            $next = $v->children[$char];
            if ($next === null)
            {
                return 0;
            }
            $v = $next;
        }

        return $v->nr_of_documents;

    }

}

class Node {

    public $children;
    public $nr_of_documents;

    function __construct()
    {
        $children = Array();
        $nr_of_documents = 0;
    }
}

class TreeBuilder {

    static function getTreeRoot($db_handler)
    {

        if($_SESSION['root']==null) {//build the tree from database

            if ($db_handler==null) return null;

            $queryString = oci_parse($db_handler, 'SELECT keyword, appearences FROM keywords');

            if (!$queryString) {
                $e = oci_error($db_handler);
                trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
                return null;
            }

            $req = oci_execute($queryString);
            if (!$req) {
                $e = oci_error($queryString);
                trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
                return null;
            }

            $root=new Node();

            while ($row = oci_fetch_array($queryString, OCI_ASSOC+OCI_RETURN_NULLS))
            {
                KeywordsTree::insert_word($root,strtolower($row['KEYWORD']),$row['APPEARENCES']);
            }

            $_SESSION['root']=$root;

        }

        return $_SESSION['root'];
    }

    static function dispose()
    {

        if (isset($_SESSION['root'])) unset($_SESSION['root']);

    }

}

?>