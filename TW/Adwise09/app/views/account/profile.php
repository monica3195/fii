<?php
    require_once('../app/models/Helper.php');
    require_once('../app/models/User.php');
    require_once('../app/models/QuestionModel.php');
    require_once('../app/models/AnswerModel.php');
?>


<script src="../javascript/changePassword.js"></script>
<script src="../javascript/editProfile.js"></script>
<script src="../javascript/forms.js"></script>
<script src="../javascript/profileTabs.js"></script>
<script src="../javascript/adminban.js"></script>

<link rel="stylesheet" href="<?php echo(PUBLIC_ROOT . "/assets/css/profile-page.css"); ?>"/>
<div class="tabs">
    <ul>
        <li><a href="#profil" class="active">Profile info</a></li>
        <li><a href="#password">Change password</a></li>
        <li><a href="#activitate">Activity</a></li>
        <li><a href="#badge">Badges</a></li>
        <?php if($data){
            if($data->getIsAdmin() == 1){
        ?>
        <li><a href="#admin">Admin Panel</a></li>
        <?php }} ?>
    </ul>
    <div id="profil">
            <div class="update-succes" style="display: none">
            </div>
            <div class="name-words">
                <p class="name-word">
                    Email:
                </p>
                <p class="name-word">
                    First name:
                </p>
                <p class="name-word">
                    Last name:
                </p>
                <p class="name-word">
                    Age:
                </p>
                <p class="name-word">
                    Country:
                </p>
                <p class="name-word">
                    City:
                </p>
            </div>
        <div class="values">

            <div class="value-message">
                <input class="prof-data" type="email" id="email" name="email"
                       value="<?php echo($data ? $data->getEmail() : 'none'); ?>" readonly>

                <div class="error-message" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="text" id="first_name" name="first-name"
                       value="<?php echo($data ? $data->getFirstName() : 'none'); ?>" readonly>

                <div class="error-message" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="text" id="last_name" name="last-name"
                       value="<?php echo($data ? $data->getLastName() : 'none'); ?>" readonly>

                <div class="error-message" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="text" id="age" name="age"
                       value="<?php echo($data ? $data->getAge() : 'none'); ?>"
                       readonly>

                <div class="error-message" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="text" id="country" name="country"
                       value="<?php echo($data ? $data->getCountry() : 'none'); ?>" readonly>

                <div class="error-message" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="text" id="city" name="city"
                       value="<?php echo($data ? $data->getCity() : 'none'); ?>"
                       readonly>

                <div class="error-message" style="display: none"></div>
            </div>

        </div>
        <button class="profile-button" onclick="editable()" type="button">Edit</button>
        <button class="profile-button" id="saveProfileBt" type="submit">Save</button>
    </div>

    <div id="password">
        <div class="name-words">
            <p class="name-word">
                Old password:
            </p>

            <p class="name-word">
                New password:
            </p>

            <p class="name-word">
                New password, once again:
            </p>
        </div>
        <div class="values">
            <div class="value-message">
                <input class="prof-data" type="password" name="old-password" id="old-password">

                <div class="error-message" id="error-message-old-password" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="password" name="new-password" id="new-password">

                <div class="error-message" id="error-message-new-password" style="display: none"></div>
            </div>
            <div class="value-message">
                <input class="prof-data" type="password" name="new-password-again" id="new-password-again">

                <div class="error-message" id="error-message-new-password-again" style="display: none"></div>
            </div>
        </div>
        <button class="password-button" id="changePasswordBt" type="button">Save</button>
    </div>


    <?php
    $qpage = $_GET['qpage'];
    if(!$qpage)
        $qpage = 0;


    $apage = $_GET['apage'];
    if(!$apage)
        $apage = 0;

    $questions = $data->loadQuestionsByPage($qpage);
    $answers = $data->loadAnswersByPage($apage);
    ?>

    <div id="activitate">
        <div class="questions">
            <div class="questions-header">
                Questions:
            </div>
            <?php
                for($i=0; $i < count($questions);$i++){
            ?>
            <div class="single-element">
                <a href="<?php echo(PUBLIC_ROOT."/question/index/".$questions[$i]->getQuestionId());?>" class="link-to-activity">
                    <?php echo $questions[$i]->getTimePosted(); ?>
                </a>
                <a class="delete" href="<?php echo(PUBLIC_ROOT."/question/deleteQuestion/".$questions[$i]->getQuestionId());?>">
                </a>
            </div>
            <?php
                }
            ?>
            <div class="buttons-wrapper">
                <div class="buttons">
                        <?php
                            if($qpage > 0) {
                                $vmqpage = $qpage-1;
                                ?>
                                <div class="prev-butt">
                                    <a href="<?php echo(PUBLIC_ROOT . "/account/index?" . "apage=" . $apage . "&qpage=" . $vmqpage);?>">
                                        Previous
                                    </a>
                                </div>
                            <?php
                            }
                        ?>
                        <?php
                        if($qpage < 5) {
                            $vMqpage = $qpage+1;
                        ?>
                        <div class="next-butt">
                            <a href="<?php echo(PUBLIC_ROOT."/account/index?"."apage=".$apage."&qpage=".$vMqpage);?>">
                            Next
                            </a>
                        </div>
                        <?php
                        }
                        ?>
                </div>
            </div>
        </div>
        <hr>
        <div class="answers">
            <div class="answers-header">
                Answers:
            </div>
            <?php
            for($i=0; $i < count($answers);$i++){
            ?>
            <div class="single-element">
                <a href="<?php echo(PUBLIC_ROOT."/question/index/".$answers[$i]->getIdquestion());?>" class="link-to-activity">
                    <?php echo (substr($answers[$i]->getAnswcontent(),0,25)." ".$answers[$i]->getTimeposted()); ?>
                </a>
                <a class="delete" href="<?php echo(PUBLIC_ROOT."/question/deleteAnswer/".$answers[$i]->getIdquestion());?>">
                </a>
            </div>
            <?php
            }
            ?>
            <div class="buttons-wrapper">
                <div class="buttons">
                        <?php
                            if($apage > 0) {
                                $vmapage = $apage-1;
                        ?>
                        <div class="prev-butt">
                            <a href="<?php echo(PUBLIC_ROOT."/account/index?"."apage=".$vmapage."&qpage=".$qpage);?>">
                            Previous
                            </a>
                        </div>
                            <?php
                            }
                        ?>
                        <?php
                            if($apage < 5) {
                                    $vMapage = $apage+1;
                        ?>
                        <div class="next-butt">
                            <a href="<?php echo(PUBLIC_ROOT."/account/index?"."apage=".$vMapage."&qpage=".$qpage);?>">
                            Next
                            </a>
                        </div>
                            <?php
                            }
                        ?>
                </div>
            </div>
        </div>
    </div>
    <div id="badge">

        <?php
            $bagdes = $data->getBadges();
            foreach($bagdes as $badge){
               echo("<div class='badge'>".$badge."</div>");
            }
        ?>


    </div>
	
    <div id="admin">
        <div class="ban">
            <form method="post" class="ban-form" action="<?php echo(PUBLIC_ROOT."/account/banunban/ban");?>">
                <div class="header">
                    Type in username you want to ban:
                </div>
                <input type="text" id="username"  name="username">
                <input class="ban-unban-button" type="submit" id="ban-button" value="Ban">
            </form>
        </div>
        <div class="unban">
            <form method="POST" class="unban-form" action="<?php echo(PUBLIC_ROOT."/account/banunban/unban");?>">
                <div class="header">
                    Type in username you want to unban:
                </div>
                <input type="text" id="username" name="username">
                <input class="ban-unban-button" id="unban-button" type="submit" value="unban">
            </form>
        </div>
        <div class="report">
            <div class="header">
                Generate report:
            </div>
            <form class="report-form" action="<?php echo(PUBLIC_ROOT."/report/index");?>" method="get">
                <select name="period-of-time" class="period-of-time" style="float: left; margin-right: 20px;">
                    <option value="month">last month</option>
                    <option value="week">last week</option>
                    <option value="day">last day</option>
                </select>
                <input type="text" placeholder="geographical area" width="100px" style=" float: left; ;margin-right: 20px">
                <div class="report-format-header">
                    Specify report format:
                </div>
                <select name="report-format" class="report-format">
                    <option value="JSON">JSON</option>
                    <option value="HTML">HTML</option>
                    <option value="ATOM">ATOM</option>
                </select>
                <input type="submit" value="Generate" style="margin-top: 60px; margin-left: 430px;">
            </form>
        </div>
    </div>
</div>
</body>
</html>