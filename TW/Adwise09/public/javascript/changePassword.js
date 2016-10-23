/**
 * Created by Pirghie on 29/05/2015.
 */

function linkSavePassword() {
    var changePasswordBt = document.getElementById("changePasswordBt");
    changePasswordBt.onclick = onsubmitPassword;
}


function onsubmitPassword(){    

    var oldpassword = document.getElementById('old-password').value;
    var newpassword = document.getElementById('new-password').value;
    var cnewpassword = document.getElementById('new-password-again').value;

    var oldPasswordErrorDiv = document.getElementById('error-message-old-password');
    var newPasswordErrorDiv = document.getElementById('error-message-new-password');
    var cnewPasswordErrorDiv = document.getElementById('error-message-new-password-again');

   /* if(!checkPasswordLength(oldpassword)){
        oldPasswordErrorDiv.style.display = 'inline';
        oldPasswordErrorDiv.innerHTML = "Password must be between 8 and 16 !";
        return;
    }else if(!checkPasswordLength(newpassword)){
        newPasswordErrorDiv.style.display = 'inline';
        newPasswordErrorDiv.innerHTML = "Password must be between 8 and 16 !";
        return;
    }else if(!checkPasswordLength(cnewpassword)){
        cnewPasswordErrorDiv.style.display = 'inline';
        cnewPasswordErrorDiv.innerHTML = "Password must be between 8 and 16 !";
        return;
    }*/

    if(newpassword != cnewpassword){
        cnewPasswordErrorDiv.style.display = 'inline';
        cnewPasswordErrorDiv.innerHTML = "Password mismatch !";
        return;
    }

    var messageToChangePassword = [];
    messageToChangePassword.push(oldpassword);
    messageToChangePassword.push(newpassword);

    var xhr = new XMLHttpRequest();

    var body = 'changepasswordparams=' + encodeURIComponent(JSON.stringify(messageToChangePassword));

    xhr.open("POST", 'http://localhost/Adwise09/public/account/changepassword', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var changeResult = this.responseText;
        alert(changeResult);
    }

    xhr.send(body);
};

function checkPasswordLength(pass){
        if(!(pass.length > 8 && pass.length < 16)){
            return false;
        }
        return true;
}