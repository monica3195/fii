/**
 * Created by Pirghie on 30/05/2015.
 */

window.onload = function (){

    var postAnswerBt = document.getElementById("post-answer-button");
    postAnswerBt.onclick = onsubmitPost;

    var upvoteQuestion = document.getElementById("upvote-question");
    var downvoteQuestion = document.getElementById("downvote-question");

    var upvoteAnswer = document.getElementsByClassName("upvote-answer");
    for (var i = 0; i < upvoteAnswer.length; i++){
        upvoteAnswer[i].onclick = onsubmitVoteAnswer;
    }

    var downvoteAnswer = document.getElementsByClassName("downvote-answer");
    for (var i = 0; i < downvoteAnswer.length; i++){
        downvoteAnswer[i].onclick = onsubmitVoteAnswer;
    }

    upvoteQuestion.onclick = onsubmitVoteQuestion;
    downvoteQuestion.onclick = onsubmitVoteQuestion;
}



function onsubmitPost(){

    var questionContent = document.getElementById('answer-content').value;
    var questionId = document.getElementById('question-id').value;

    var messageToPostAnswer = [];
    messageToPostAnswer.push(questionContent);
    messageToPostAnswer.push(questionId);

    var xhr = new XMLHttpRequest();

    var body = 'postanswerparams=' + encodeURIComponent(JSON.stringify(messageToPostAnswer));

    xhr.open("POST", 'http://localhost/Adwise09/public/question/postAnswer', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var postResult = this.responseText;
        console.log(postResult);
        var serverResponse = JSON.parse(postResult);

        if(serverResponse[0] == true){
            location.reload();
        }else{
            alert(serverResponse[1]);
        }
    }

    xhr.send(body);
};