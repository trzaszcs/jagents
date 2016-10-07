(function ($) {
    var exampleSocket = new WebSocket("ws://localhost:8080/stats-ws");
    var statsBox = $('#statsBox');

    var buildItem = function (json) {
        var container = $('<div>')
        $('<p>').text("method: "+json.methodName).appendTo(container)
        $('<p>').text("args: "+json.args).appendTo(container)
        $('<p>').text("execution time: "+json.executionTime).appendTo(container)
        $('<p>').text("callStack: "+json.callStack).appendTo(container)
        return container
    };

    exampleSocket.onmessage = function (event) {
        console.log('received data: ', event.data);
        var eventEntry = JSON.parse(event.data);
        statsBox.append(buildItem(eventEntry));
        // statsBox.innerHTML += "<li>" + event.data+ "</li>";
    }

})($);
