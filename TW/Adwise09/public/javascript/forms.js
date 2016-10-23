/**
 * Created by Pirghie on 30/05/2015.
 */

function add_fieldTag(){

    var nmb = document.querySelectorAll('.tag').length;
    if (nmb<7) {
        var form = document.getElementById("tags"),
            input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.setAttribute('class', 'tag');
        input.setAttribute('name', 'tag['+nmb+']');
        input.setAttribute('maxlength','20');
        form.appendChild(input);
    }
};

function validatePassword(passwordValue){
    if(passwordValue.length < 8 || passwordValue > 20){
        return JSON.stringify([false, "Password should be between 8 and 20 !"]);
    }else{
        return JSON.stringify([true, "Password ok !"]);
    }
}
function validateTextField(fieldValue){
    var Exp = /((^[0-9]+[a-z]+)|(^[a-z]+[0-9]+))+[0-9a-z]+$/i;
    if(fieldValue.match(Exp)){
        return true;
    }else{
        return false;
    }
}

function hideErrorDivs(){
    var errorDiv = document.getElementsByClassName('error-message');
}