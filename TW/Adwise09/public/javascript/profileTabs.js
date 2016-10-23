/**
 * Created by Pirghie on 31/05/2015.
 */
window.addEventListener("load", function () {
    makeTabs(".tabs")
});

function makeTabs(selector) {
    
    tab_lists_anchors = document.querySelectorAll(selector + " li a");
    divs = document.querySelector(selector).getElementsByTagName("div");

    for (var i = 0; i < tab_lists_anchors.length; i++) {
        if (tab_lists_anchors[i].classList.contains('active')) {
            divs[i].style.display = "block";
        }
    }
    for (i = 0; i < tab_lists_anchors.length; i++) {
            document.querySelectorAll(".tabs li a")[i].addEventListener('click', reloadForm);
    }

    //Adding events handlers creator
    addCheckInputEvents();
}
function editable() {
    var values = document.getElementsByTagName("input");
    for (var i = 0; i < values.length; i++) {
        if ((values[i].getAttribute("type").toLowerCase() == "text") || (values[i].getAttribute("type").toLowerCase() == "email")) {
            values[i].readOnly = false;
        }
    }
}

function checkInputTypeEvent(element){

    var JSONResponse = validatePassword(element.currentTarget.value);
    var elementTarget = element.currentTarget;
    //var father = elementTarget.

    var arrayResponse = JSON.parse(JSONResponse);


    console.log(element.currentTarget);
    console.log(element.currentTarget.toString());
    if((arrayResponse[0] != true)){
        console.log("Password not ok");
    }else{
        console.log("Password ok");
    }
}

function addCheckInputEvents(){
    var values = document.getElementsByTagName("input");
    for(var i = 0; i < values.length; i++){
        if(values[i].getAttribute("type").toLowerCase() == "password"){
            values[i].onblur = checkInputTypeEvent;
        }
    }
}

function reloadForm (e) {
    for (i = 0; i < divs.length; i++) {
        divs[i].style.display = "none";
    }
    for (i = 0; i < tab_lists_anchors.length; i++) {
        tab_lists_anchors[i].classList.remove("active");
    }
    clicked_tab = e.target || e.srcElement;
    clicked_tab.classList.add('active');
    div_to_show = clicked_tab.getAttribute('href');
    document.querySelector(div_to_show).style.display = "block";
    var nodes = document.getElementById(div_to_show.slice(1,div_to_show.length)).getElementsByTagName("*");
    for(var i=0; i<nodes.length; i++) {
        if (nodes[i].className != "error-message" && nodes[i].className != "update-succes") {
            nodes[i].style.display = "block";
        }
    }

    var values = document.getElementsByClassName("value-message")
    for (var i = 0; i < values.length; i++) {
        values[i].style.display = "block";
    }
    var inputs = document.getElementsByTagName("input");
    for (var i = 0; i < values.length; i++) {
        if (inputs[i].getAttribute("type").toLowerCase() == "text" || inputs[i].getAttribute("type").toLowerCase() == "password") {
            inputs[i].style.display = "block";
        }
    }
    if(div_to_show == "#password"){
        linkSavePassword();
    }else if(div_to_show == "#profil"){
        linkSaveProfile();
    }else if(div_to_show == "#activitate") {

    }else if(div_to_show == "#badge"){

    }else if(div_to_show == "#admin"){

    }

}