/**
 * Created by Pirghie on 02/06/2015.
 */

window.onload = function () {
    var banButton = document.getElementById('ban-button');
    var unbanButton = document.getElementById('unban-button');

    banButton.onclick = onsubmitban();
    unbanButton.onclick = onsubmitunban();
}

function onsubmitban(){

    var usernametoban = document.getElementById('ban-username').value;
    var messageToBan = [];
    messageToBan.push(usernametoban);

    var xhr = new XMLHttpRequest();

    var messageBody = 'ban=' + encodeURIComponent(JSON.stringify(messageToBan));

    xhr.open("POST",'http://localhost/Adwise09/public/account/banunban');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var banresult = this.responseText;
    }
    xhr.send(messageBody);
}

function onsubmitunban(){
    var xhr = new XMLHttpRequest();

    var messageBody = 'loginparams=' + encodeURIComponent(JSON.stringify(messageToLogin));

    xhr.open("POST",'http://localhost/Adwise09/public/home/login');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;
        var loginResult = this.responseText;
    }
    xhr.send(messageBody);
}