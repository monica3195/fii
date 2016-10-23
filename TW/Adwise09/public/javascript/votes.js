/**
 * Created by Pirghie on 01/06/2015.
 */

function onsubmitVoteQuestion(Element){

    console.log(Element.currentTarget.id);

    var questionId = document.getElementById('question-id').value;
    var valVote = 0;

    if(Element.currentTarget.id == 'upvote-question'){
        valVote = 1;
    }else{
        valVote = -1;
    }

    var messageToVoteQuestion = [];
    messageToVoteQuestion.push(questionId);
    messageToVoteQuestion.push(valVote);

    var ajson = JSON.stringify(messageToVoteQuestion);

    var xhr = new XMLHttpRequest();

    var body = 'question=' + encodeURIComponent(ajson);

    xhr.open("POST", 'http://localhost/Adwise09/public/question/vote', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var voteQuestionResult = this.responseText;
        console.log(voteQuestionResult);
        var serverResponseUnJson = JSON.parse(voteQuestionResult);

        if(serverResponseUnJson[0] == true) {

            var valueraitingquestion = document.getElementById('value-raiting-question');
            valueraitingquestion.innerHTML = serverResponseUnJson[1];
        }
    }

    xhr.send(body);
}

function onsubmitVoteAnswer(Element) {

    console.log(Element.currentTarget.id);
    var a = Element.currentTarget.parentElement.getElementsByTagName('input');
    var answerID = parseInt(a[0].getAttribute('value'));
    var valVote = 0;
    var father = Element.currentTarget.parentNode;
    var grandpa = father.parentNode;
    var ratingaaux = grandpa.getElementsByClassName('rating-answer');
    var ratinga = ratingaaux[0].getElementsByClassName('value');

    if(Element.currentTarget.id == 'upvote-answer'){
        valVote = 1;
    }else{
        valVote = -1;
    }

    var messageToVoteAnswer = [];
    messageToVoteAnswer.push(answerID);
    messageToVoteAnswer.push(valVote);

    var ajson = JSON.stringify(messageToVoteAnswer);

    var xhr = new XMLHttpRequest();

    var body = 'answer=' + encodeURIComponent(ajson);

    xhr.open("POST", 'http://localhost/Adwise09/public/question/vote', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var voteAnswerResult = this.responseText;
        console.log(voteAnswerResult);
        var serverResponseUnJson = JSON.parse(voteAnswerResult);

        if(serverResponseUnJson[0] == true) {

            ratinga[0].innerHTML = serverResponseUnJson[1];
        }
    }

    xhr.send(body);

}