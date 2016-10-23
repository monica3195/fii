/**
 * Created by Pirghie on 30/05/2015.
 */

window.onload = function (){
    var editProfileBt = document.getElementById("saveProfileBt");
    editProfileBt.onclick = onsubmitProfile;
}

function linkSaveProfile() {
    var editProfileBt = document.getElementById("saveProfileBt");
    editProfileBt.onclick = onsubmitProfile;
}


function onsubmitProfile(){

    var first_name = document.getElementById('first_name').value;
    var last_name = document.getElementById('last_name').value;
    var email = document.getElementById('email').value;
    var age = document.getElementById('age').value;
    var country = document.getElementById('country').value;
    var city = document.getElementById('city').value;

    var messageToEditProfile = [];
    messageToEditProfile.push(first_name);
    messageToEditProfile.push(last_name);
    messageToEditProfile.push(email);
    messageToEditProfile.push(age);
    messageToEditProfile.push(country);
    messageToEditProfile.push(city);

    var xhr = new XMLHttpRequest();

    var body = 'editprofileparams=' + encodeURIComponent(JSON.stringify(messageToEditProfile));

    xhr.open("POST", 'http://localhost/Adwise09/public/account/editprofile', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {
        if (this.readyState != 4)
            return;

        var changeResult = this.responseText;
        alert(changeResult);
//        serverResponseDiv.innerHTML = changeResult;
  //      serverResponseDiv.style.display = "inline";
    }

    xhr.send(body);
};