$('#file_upload').plupload({
    runtimes: 'html5',
    url: rest('/upload'),
    unique_names: true
});