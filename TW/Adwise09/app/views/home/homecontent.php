<div id="page">
    <link rel="stylesheet" href="<?php echo(PUBLIC_ROOT . "/assets/css/questions.css"); ?>"/>
    <?php

    require_once('../app/utils/Database.php');
    require_once('../app/models/QuestionModel.php');

        if ($data) {
            foreach ($data as $question) {
                $time = $question->getTimePosted();
                $uname = $question->getUser();
                $catName = $question->getQuestionCategory();
                $content = $question->getQuestionContent();
                $qid = $question->getQuestionID();
                $views = $question->getNrViews();
                $rating = $question->getRaiting();


                ?>
                <div class="question" id="questions_id_from_database">
                    <a href="<?php echo(PUBLIC_ROOT."/question/index/".$qid);?>">
                        <div class="info">
                            <div class="stats">
                                <div class="rating">
                                    <div class="value">
                                        <?php echo($rating); ?>
                                    </div>
                                    <div class="word">
                                        rating
                                    </div>
                                </div>
                                <div class="views">
                                    <div class="value">
                                        <?php echo($views); ?>
                                    </div>
                                    <div class="word">
                                        views
                                    </div>
                                </div>
                            </div>
                            <div class="general-info">
                                <div class="posted-when">
                                    <p>
                                        Posted on: <?php echo($time); ?>
                                    </p>
                                </div>
                                <div class="posted-by">
                                    Posted by: <?php echo($uname); ?>
                                </div>
                                <div class="category-name">
                                    Category: <?php echo($catName); ?>
                                </div>
                            </div>
                        </div>
                        <div class="content">
                            <p>
                                <?php echo($content); ?>
                            </p>
                        </div>
                    </a>
                </div>
                <hr class="delimiter">
            <?php
            }
        }
        ?>
    </div>



