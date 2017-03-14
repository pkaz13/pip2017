/**
    * Created by Rafal Lebioda on 14.03.2017.
    */

function addText(text) {
    if(document.getElementById("messageInput")!=null)
    {
        document.getElementById("messageInput").value = document.getElementById("messageInput").value + text;
    }
}
