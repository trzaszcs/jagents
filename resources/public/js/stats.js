var statsBuilder = (function (template) {

    var socketListen = function () {
        var exampleSocket = new WebSocket("ws://localhost:8080/stats-ws");
        exampleSocket.onmessage = function (event) {
            console.log('received data: ', event.data);
            var entry = JSON.parse(event.data);
            template.addStatsItem(entry);
        }
    }
    return {
        listen: socketListen
    }
});
