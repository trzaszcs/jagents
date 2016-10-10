(function ($) {
    var exampleSocket = new WebSocket("ws://localhost:8080/stats-ws");
    var statsBox = $('#statsBox');

    var buildFunctionCallItem = function (name, args, durationTime, callStack, statsTime) {
        var container = $('<li>')

        var ul = $('<ul>')
        ul.appendTo(container)

        $('<li>').text("method: "+name).appendTo(ul)
        $('<li>').text("duration time: "+durationTime).appendTo(ul)
        $('<li>').text("execution time: "+statsTime).appendTo(ul)
        $('<li>').text("args: "+args).appendTo(ul)
        $('<li>').text("callStack: "+callStack).appendTo(ul)
        return container
    };

    exampleSocket.onmessage = function (event) {
        console.log('received data: ', event.data);
        var entry = JSON.parse(event.data);
        statsBox.append(buildFunctionCallItem(entry.methodName, entry.args, entry.durationTime, entry.callStack, entry.statsTime));
    }

})($);
