(function ($) {
    var getStats = function (callback) {
        $.ajax({
            url: '/stats'
        }).done(callback)
    };

    var statsBox = document.getElementById('statsBox');

    document.getElementById('refresh').onclick = function () {
        getStats(function(result) {
            statsBox.innerHTML += result
        });
    }

    var exampleSocket = new WebSocket("ws://localhost:5000/stats-ws");
   // exampleSocket.send("Ding !");

})($);
