/**
 * Created by Pirghie on 30/05/2015.
 */

window.onload = function () {
    var loginButton = document.getElementById('loginBt');
    loginButton.onclick = onsubmit;
}

function onsubmit(){
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var rememberCheckd = document.getElementById('rememberme').checked;

    var messageToLogin = [];//[username, password];
    messageToLogin.push(username);
    messageToLogin.push(password);
    messageToLogin.push(rememberCheckd);

    var serverResponseDiv = document.getElementById('loginresult');

    if(username.length == '' || password.length == ''){
        serverResponseDiv.innerHTML = "Field/s empty";
        serverResponseDiv.style.display = "inline";
        return;
    }

    var xhr = new XMLHttpRequest();

    var messageBody = 'loginparams=' + encodeURIComponent(JSON.stringify(messageToLogin));

    xhr.open("POST",'http://localhost/Adwise09/public/home/login');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var loginResult = this.responseText;
        serverResponseDiv.innerHTML = loginResult;
        serverResponseDiv.style.display = "inline";

        var jsonLoginResponse = JSON.parse(loginResult);

        if(jsonLoginResponse[0] == true){
            redirectHome();
        }

        serverResponseDiv.innerHTML = jsonLoginResponse[1];
        serverResponseDiv.style.display = "inline";
    }

    xhr.send(messageBody);
}

function redirectHome(){
    window.history.back(-1);
    //window.location('../public');
}