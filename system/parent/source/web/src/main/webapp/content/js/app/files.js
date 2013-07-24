var currentNode;

$(document).ready(function() {
    $('#files_tree').dynatree({
        fx: {
            height: 'toggle',
            duration: 200
        },
        initAjax: {
            url: '/rest/directories/root'
        },
        onCreate: function(node, span) {
            bindContextMenu(span);
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
        },
        onActivate: function(node) {
            currentNode = node;
        }
    });
});

function bindContextMenu() {
    $('#files_tree').contextMenu({
        selector: 'li',
        callback: function(key, options) {
            if(key == "delete") {
                delete_file_system_entry(currentNode.data.key);
            }
        },
        items: {
            "rename": {
                name: "Zmień nazwę",
                icon: "edit"
            },
            "delete": {
                name: "Usuń",
                icon: "delete"
            }
        }
    })
}


function delete_file_system_entry(id) {
    $.ajax({
        type: "DELETE",
        url: "/rest/directories/delete/" + id
    });
}