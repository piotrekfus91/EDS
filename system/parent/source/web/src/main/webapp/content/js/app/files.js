$(document).ready(function() {
    $('#files_tree').dynatree({
        fx: {
            height: 'toggle',
            duration: 200
        },
        initAjax: {
            url: '/rest/directories/root'
        },
        onLazyRead: function(node) {
            node.appendAjax({
                url: '/rest/directories/' + node.data.key
            });
        },
        onPostInit: function(isReloading, isError) {
            if(isError) {
                post_message_now('error', 'Błąd wczytywania katalogów głównych');
            } else {
                post_message_now('success', 'Wczytano katalogi główne');
            }
        }
    });
});