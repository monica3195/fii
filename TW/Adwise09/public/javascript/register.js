/**
 * Created by Pirghie on 02/06/2015.
 */
window.onload = function () {
    var registerButton = document.getElementById('register-button');
    registerButton.onclick = onsubmitRegister;
}

function onsubmitRegister(){

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var cpassword = document.getElementById('cpassword').value;
    var email = document.getElementById('email').value;

    var serverResponseDiv = document.getElementById('register-result');

    if(username.length == '' || password.length == ''){
        serverResponseDiv.innerHTML = "Field/s empty";
        serverResponseDiv.style.display = "inline";
        return;
    }

    if(password != cpassword){
        serverResponseDiv.innerHTML = "Passwords mismatch";
        serverResponseDiv.style.display = "inline";
        return;
    }

    var xhr = new XMLHttpRequest();
    var serverRequest = [];
    serverRequest.push(username);
    serverRequest.push(password);
    serverRequest.push(cpassword);
    serverRequest.push(email);

    var messageBody = 'registerinfo=' + encodeURIComponent(JSON.stringify(serverRequest));

    xhr.open("POST",'http://localhost/Adwise09/public/home/register');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var registerResult = this.responseText;

        var jsonRegisterResponse = JSON.parse(registerResult);

        serverResponseDiv.innerHTML = jsonLoginResponse[1];
        serverResponseDiv.style.display = "inline";
    }

    xhr.send(messageBody);
}
