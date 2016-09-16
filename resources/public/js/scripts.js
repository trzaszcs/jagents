(function ($) {
    var exampleSocket = new WebSocket("ws://localhost:8080/stats-ws");


    var statsBox = document.getElementById('statsBox');

    exampleSocket.onmessage = function (event) {
        statsBox.innerHTML += "<li>" + event.data+ "</li>";
    }

})($);
