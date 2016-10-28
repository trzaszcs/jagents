$(document).ready(function () {
    var template = templateBuilder($);
    var stats = statsBuilder(template);
    stats.listen();
});
