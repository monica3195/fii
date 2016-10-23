<!--<script src="<?php// echo (PUBLIC_ROOT);?>/javascript/deleteAnswerQuestion.js"></script> !-->
<script src="<?php echo (PUBLIC_ROOT);?>/javascript/votes.js"></script>
<script src="<?php echo(PUBLIC_ROOT);?>/javascript/postanswer.js"></script>
<?php
    if(is_a($data[0], 'QuestionModel') and ($data[0]->getQuestionId() != null)) {
            ?>
        <link rel="stylesheet" href="<?php echo(PUBLIC_ROOT);?>/assets/css/single-question-page.css"/>
        <div id="page">
            <div class="question" id="questions_id_from_database">
                <div class="info">
                    <input type="hidden" id="question-id" value="<?php echo($data[0]->getQuestionID());?>" />
                    <div class="views">
                        <div class="value">
                            <?php echo($data[0]->getNrViews());?>
                        </div>
                        <div class="word">
                            views
                        </div>
                    </div>
                    <div class="general-info">
                        <div class="posted-when">
                            <p>
                                Posted on: <?php echo($data[0]->getTimePosted());?>
                            </p>
                        </div>
                        <div class="posted-by">
                            Posted by: <?php echo($data[0]->getUser());?>
                        </div>
                        <div class="category-name">
                            Category: <?php echo($data[0]->getQuestionCategory());?>
                        </div>
                    </div>
                    <div class="rating">
                        <div class="value" id="value-raiting-question">
                            <?php echo($data[0]->getRaiting());?>
                        </div>
                        <div class="word">
                            rating
                        </div>
                    </div>
                </div>
                <div class="content">
				 	<?php
                        if(isset($data[1])){
                        if($data[1]->getIsAdmin()){ ?>
                    <div class="delete-wrapper">
                        <a class="delete" href="<?php echo(PUBLIC_ROOT."/question/deleteQuestion/".$data[0]->getQuestionID());?>" id="deleteQuestion" value="<?php echo($data[0]->getQuestionID());?>">
                        </a>
                    </div>
                    <?php }} ?>
                    <p>
                        <?php echo($data[0]->getQuestionContent());?>
                    </p>
                </div>
                <div class="vote-question">
                    <div class="upvote" id="upvote-question">
                        <a> Upvote </a>
                    </div>
                    <div class="downvote" id="downvote-question">
                        <a >Downvote </a>
                    </div>
                </div>
                <div class="tags">
                    <div class="tag-word">
                        Tags:
                    </div>

                    <?php

            $tags = $data[0]->getTags();
            if (count($tags) > 1) {
                foreach ($tags as $key => $value) {
                    ?>
                    <div class="single-tag">
                        <?php
                        echo $key;
                        ?>
                    </div>
                <?php
                }
            } else {
                echo "<div class='single-tag'>no tags</div>";
            }
            ?>
                </div>
            </div>
            <div class="nmb-answ">
                <div class="nmb-answ-word">
                    Answers:
                </div>
                <div class="nmb-answ-number">
                     <?php echo($data[0]->getNrOfAnswers());?>
                </div>
            </div>

            <hr class="after-question">

            <?php if (count($data[0]->getAnswers()) > 0){
                $answers = $data[0]->getAnswers();
                foreach($answers as $answer){

            ?>
            <div class="answer">
                <div class="info">
                    <div class="general-info">
                        <div class="posted-when">
                            <p>
                                Posted on: <?php echo($answer->getTimeposted());?>
                            </p>
                        </div>
                        <div class="posted-by">
                            Posted by: <?php echo($answer->getUsername());?>
                        </div>
                    </div>
                    <div class="rating-answer">
                        <div class="value">
                            <?php echo($answer->getRating());?>
                        </div>
                        <div class="word">
                            rating
                        </div>
                    </div>
                    <div class="vote-answer">
                        <input type="hidden" value="<?php echo($answer->getIdanswer());?>">
                        <div class="upvote-answer" id="upvote-answer">
                            <a > Upvote </a>
                        </div>
                        <div class="downvote-answer" id="downvote-answer">
                            <a > Downvote </a>
                        </div>
                    </div>
                </div>
                <div class="content">
                    <?php   if(isset($data[1])){
                            if($data[1]->getIsAdmin()){ ?>
					<div class="delete-wrapper">
                		    <a class="delete" href="<?php echo(PUBLIC_ROOT."/question/deleteAnswer/".$answer->getIdanswer());?>" id="deleteAnswer" value="<?php echo($answer->getIdanswer());?>"></a>
            		</div>
                        <?php }} ?>
                    <p>
                        <?php echo($answer->getAnswcontent());?>
                    </p>
                </div>
            </div>

            <?php
                } // end foreach
            } //end if
            ?>
            <div id="post-answer">
                    <div class="textarea-button">
                        <textarea id="answer-content" name="content" maxlength="500" rows="9" cols="75"></textarea>

                        <div class="post-button">
                            <input id="post-answer-button" type="submit" value="Post answer" style="font: 20px Arial">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    <?php
        }else{
        echo 'Invalid question model';
    }
?>