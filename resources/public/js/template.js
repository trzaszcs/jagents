var templateBuilder = (function ($) {

    var statsBox = $('#statsBox');

    var buildList = function (args) {
        var ul = $('<ul>');
        for (var c=0;c<args.length;c++) {
            $('<li>').text(args[c]).appendTo(ul);
        }
        return ul;
    }

    var buildName = function (name) {
        return $('<span>').text(name);
    }

    var buildFunctionCallItem = function (id, name, args, durationTime, callStack, statsTime) {
        var container = $('<li>');

        var ul = $('<ul>');
        ul.appendTo(container);

        $('<li>').append(buildName("id:"), id).appendTo(ul);
        $('<li>').append(buildName("method:"), name).appendTo(ul);
        var argsLi = $('<li>');
        argsLi.append(buildName('args:'), buildList(args));
        argsLi.appendTo(ul);
        $('<li>').append(buildName("duration time:"), durationTime).appendTo(ul);
        $('<li>').append(buildName("execution time:"),statsTime).appendTo(ul);
        var stackLi = $('<li>')
        stackLi.append(buildName("callstack:"), buildList(callStack));
        stackLi.appendTo(ul);
        return container;
    };

    var addItem = function (entry) {
        statsBox.prepend(buildFunctionCallItem(entry.id, entry.methodName, entry.args, entry.durationTime, entry.callStack, entry.statsTime));
    }
    return {
        addStatsItem: addItem
    }
});
