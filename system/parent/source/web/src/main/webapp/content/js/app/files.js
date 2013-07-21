$(document).ready(function() {
    $('#files_tree').dynatree({
        fx: {
            height: 'toggle',
            duration: 200
        },
        initAjax: {
            url: '/rest/directories/root'
        }
    });
});