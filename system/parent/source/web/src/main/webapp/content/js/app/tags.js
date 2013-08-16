function autocomplete_set(elem) {
    elem .bind( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).data( "ui-autocomplete" ).menu.active ) {
            event.preventDefault();
        }
    })
    .autocomplete({
        source: function( request, response ) {
            $.getJSON( rest('/tags/autocomplete'), {
                term: autocomplete_extract_last( request.term )
            }, response );
        },
        search: function() {
            var term = autocomplete_extract_last( this.value );
            if ( term.length < 2 ) {
                return false;
            }
        },
        focus: function() {
            return false;
        },
        select: function( event, ui ) {
            var terms = autocomplete_split( this.value );
            terms.pop();
            terms.push( ui.item.value );
            terms.push( "" );
            this.value = terms.join( ", " );
            return false;
        }
    });
}

function autocomplete_split( val ) {
    return val.split( /,\s*/ );
}
function autocomplete_extract_last( term ) {
    return autocomplete_split( term ).pop();
}