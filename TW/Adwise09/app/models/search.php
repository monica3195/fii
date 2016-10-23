<?php

require_once("KeywordsTree.php");
require_once("../app/utils/Database.php");

class MyHeap extends SplPriorityQueue
{
    public function compare($priority1, $priority2)
    {
        if ($priority1 === $priority2) return 0;
        return $priority1 < $priority2 ? -1 : 1;
    }
}

function get_Keywords($content,$type)
{
    $wordsinit = explode(" ", strtolower($content));

    $db_handler=Database::getConnection();

    $searchTree=TreeBuilder::getTreeRoot($db_handler);

    $words=$wordsinit;

    if ($type==0) {
        $words = Array();
        $index = 0;

        for ($j = 0; $j < count($wordsinit); ++$j) {
            $currentWord = $wordsinit[$j];
            $splitword = "";
            $aux = $searchTree;

            $i = 0;

            while ($i < strlen($currentWord)) {
                $char = $currentWord[$i];

                $next = $aux->children[$char];

                if ($next == null) {//am ajuns la un sfirsit de cale , pastrez cuvintul curent si merg in radacina
                    if (strlen($splitword) == 0) {
                        $splitword = $char;
                        $words[$index++] = $splitword;
                        $aux = $searchTree;
                        ++$i;
                        $splitword = "";
                    } else {
                        $words[$index++] = $splitword;
                        $aux = $searchTree;
                        $splitword = "";
                    }
                } else {
                    $splitword .= $char;
                    $aux = $next;
                    ++$i;
                }
            }

            if (strlen($splitword) > 0) {
                $words[$index++] = $splitword;
            }

        }
    }

    sort($words, SORT_NATURAL | SORT_FLAG_CASE);

    $nr = 1;

    for ($i = 1; $i < count($words); ++$i)
    {
        if ($words[$i]==$words[$i-1]) ++$nr;
        else
        {
            $result[$words[$i-1]]=$nr;
            $nr=1;
        }
    }

    if (count($words)>0)
        $result[$words[count($words)-1]]=$nr;

    return $result;

}

function update_Keywords($content)
{//la postarea unei intrebari fac update la tree-ul meu si la tabela keywords
    $kewords_array = get_Keywords($content,1);

    $db_handler=Database::getConnection();

    $searchTree=TreeBuilder::getTreeRoot($db_handler);

    foreach ($kewords_array as $word => $app)
    {

        KeywordsTree::insert_word($searchTree,$word,1);

        $dbStmt = "SELECT COUNT(*) as NR FROM keywords WHERE keyword='".$word."'";
        $countString = oci_parse($db_handler,$dbStmt);
        var_dump($dbStmt);
        $rez = oci_execute($countString);
        $nr_of_words = oci_fetch_array($countString, OCI_ASSOC+OCI_RETURN_NULLS)['NR'];

        if ($nr_of_words==0) //daca cuvintul nu exista il inserez
        {
            $insertString = "INSERT INTO KEYWORDS(keyword,appearences) VALUES (:v1, 1)";
            var_dump($insertString);
            $compiled = oci_parse($db_handler, $insertString);
            oci_bind_by_name($compiled, ':v1', $word);
            oci_execute($compiled);
        }
        else //update the keywords table
        {
            $updateString = 'UPDATE KEYWORDS SET appearences=appearences+1 WHERE keyword=:v1';
            $compiled = oci_parse($db_handler, $updateString);
            oci_bind_by_name($compiled, ':v1', $word);
            oci_execute($compiled);
        }
    }
}

//acum am arborele si descriptorul bazei de date, pot sa iau stringul de cautare si sa construiesc
//diferentele de unghiuri dintre el si intrebarile din baza de date

function searchByContent($q_content)
{

    $db_handler=Database::getConnection();

    $searchTree=TreeBuilder::getTreeRoot($db_handler);

    $countString = oci_parse($db_handler, 'SELECT COUNT(*) as NR FROM questions');
    $rez = oci_execute($countString);
    $nr_of_doc = oci_fetch_array($countString, OCI_ASSOC + OCI_RETURN_NULLS)['NR'];

    $queryString = oci_parse($db_handler, 'SELECT id_question, question_content FROM questions');
    $req = oci_execute($queryString);
    $q_Keywords = get_Keywords($q_content,0);

    foreach ($q_Keywords as $word => $tf) {
        $generalapp = KeywordsTree::get_hits($searchTree, $word);
        if ($generalapp != 0) $wq[$word] = $tf * log($nr_of_doc / $generalapp);
        else $wq[$word] = 0;
    }

    $q_heap = new MyHeap();

    while ($row = oci_fetch_array($queryString, OCI_ASSOC + OCI_RETURN_NULLS)) {
        $doc_id = $row['ID_QUESTION'];
        $doc_content = $row['QUESTION_CONTENT'];

        $wd = Array();

        $doc_Keywords = get_Keywords($doc_content,0);

        foreach ($doc_Keywords as $word => $tf) {
            $generalapp = KeywordsTree::get_hits($searchTree, $word);
            if ($generalapp != 0) $wd[$word] = $tf * log($nr_of_doc / $generalapp);
            else $wd[$word] = 0;
        }

        $sus = 0;
        $jos1 = 0;

        foreach ($q_Keywords as $key => $word) {
            if ($wd[$key] != null) {
                $sus += $wq[$key] * $wd[$key];
                $jos1 += $wq[$key] * $wq[$key];
            }
        }

        $jos2 = 0;

        foreach ($doc_Keywords as $key => $word) {
            $jos2 += $wd[$key] * $wd[$key];
        }

        $jos = sqrt($jos1) * sqrt($jos2);

        if ($jos != 0) {
            $scor = $sus / $jos;
        } else {
            $scor = 0;
        }

        $q_heap->insert($doc_id, $scor);

    }

    $q_heap->setExtractFlags(MyHeap::EXTR_BOTH);

    $q_heap->top();

    $result = Array();
    $index = 0;

    while ($q_heap->valid() && $index < 100) {
        $result[$index] = $q_heap->current()['data'];
        ++$index;
        $q_heap->next();
    }

    // echo json_encode($result);
    $_SESSION['content'] = json_encode($result);
}

?>
