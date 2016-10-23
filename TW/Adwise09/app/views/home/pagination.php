<link rel="stylesheet" href="<?php echo(PUBLIC_ROOT . "/assets/css/next-previous-buttons.css"); ?>" />
    <div class="both-buttons-wrapper">
        <div class="both-buttons">
<?php

require_once('../app/models/Helper.php');

/**
 * Created by PhpStorm.
 * User: Iustines - PC
 * Date: 5/30/2015
 * Time: 6:10 PM
 */
    if($data){
        // ["pageno" => $pageNo, "totalpages" => $maxQuestNum]
        $page = $data['pageno'];
        $totalpages = $data['totalpages'];
        $type = $data['type'];
        $param = $data['param'];


        if($type == "default"){
            if($page>0){
                $previous = $page - 1;
                $prev = PUBLIC_ROOT."/home/index/default/".$previous;
                ?>
                <a href="<?php echo $prev; ?>" class="button-previous"></a>

    <?php
            }
            if($page<$totalpages){
                $next = $page + 1;
                $nxt = PUBLIC_ROOT."/home/index/default/".$next;
                ?>
                <a href="<?php echo $nxt; ?> " class="button-next"></a>
    <?php
            }
        }
        else{

            if (strpos(Helper::curPageURL(),'&page=') !== false) {
                $url = substr(Helper::curPageURL(),0,strpos(Helper::curPageURL(),'&page='));

            }
            else
            {
                $url = Helper::curPageURL();
            }
            if($page>0){
                $previous = $page - 1;
                $prev = $url."&page=".$previous;
                ?>
                <a href="<?php echo $prev; ?> " class="button-previus"></a>
            <?php

            }
            if($page<$totalpages){
                $next = $page + 1;
                $nxt = $url."&page=".$next;
                ?>
                <a href="<?php echo $nxt; ?> " class="button-next"></a>
            <?php
            }
        }
    }else{
        echo "Data for pagination null </br> ";
    }
?>

    </div>
    </div>


