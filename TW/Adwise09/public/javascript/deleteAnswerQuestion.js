/**
 * Created by Pirghie on 30/05/2015.
 */

window.onload = function (){
    var deleteQuestion = document.getElementById("deleteAnswer");
    var deleteAnswer = document.getElementById("deleteQuestion");

    deleteAnswer.onclick = onsubmit;
    deleteQuestion.onclick = onsubmit;
}



function onsubmit(element){


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