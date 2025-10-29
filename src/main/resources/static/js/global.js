$(document).ready(function() {
    var path = window.location.pathname;

    $('.nav-link').each(function() {
        if ($(this).attr('href') === path) {
            $(this).addClass('active');
        } else {
            $(this).removeClass('active');
        }
    });
});
