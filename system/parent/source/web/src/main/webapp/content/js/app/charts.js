$(document).ready(function() {
    load_upload_statistics_chart();
});

function load_upload_statistics_chart() {
    var chart = new cfx.Chart();
    chart.setGallery(cfx.Gallery.Lines);
    chart.getLegendBox().setVisible(false);
    chart.setDataSource(get_upload_statistics());
    set_title(chart, 'Upload w ostatnim tygodniu');
    chart.create('ChartDiv');
}

function get_upload_statistics() {
    var data = [];
    $.ajax({
        type: "GET",
        url: rest('/statistics/upload/lastWeek'),
        async: false,
        success: function(result) {
            data = result;
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu statystyk');
        }
    })
    return data;
}

function set_title(chart, title) {
    var titles = chart.getTitles();
    titles.clear();
    var titleToSet = new cfx.TitleDockable();
    titleToSet.setText(title);
    titles.add(titleToSet);
}