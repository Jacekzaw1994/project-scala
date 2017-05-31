$(document).ready(function () {
    /**
     * Returns a random integer between min (inclusive) and max (inclusive)
     * Using Math.round() will give you a non-uniform distribution!
     */
    function getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
    var i = 1;

    function myLoop () {
        setTimeout(function () {
            $('span.amount').height(getRandomInt(0, 100) + '%');
            myLoop();
        }, 2000);
    }

    myLoop();
});