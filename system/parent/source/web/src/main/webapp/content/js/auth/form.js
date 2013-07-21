$(document).ready(function() {
    $('input[type=submit]').button();

    roundLoginFormCorners();

    $('.log_input_text').focus(function() {
        $(this).animate({
            height: '+=10',
            fontSize: '+=8',
            marginBottom: '-=5',
            marginTop: '-=5',
            marginLeft: '-=10',
            width: '+=10'
        }, 400);
    });

    $('.log_input_text').blur(function() {
        $(this).animate({
            height: '-=10',
            fontSize: '-=8',
            marginBottom: '+=5',
            marginTop: '+=5',
            marginLeft: '+=10',
            width: '-=10'
        }, 400);
    });
});

function roundLoginFormCorners() {
    roundCorners('login_div', 30);
}

function roundCorners(clazz, px) {
    $('.' + clazz).corner(px + 'px');
}