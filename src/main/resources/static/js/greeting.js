/**
    * Created by Rafal Lebioda on 14.03.2017.
    */
var lastSelected = $(':focus');

jQuery(function ($) {
    $(".message-item").on('blur', function () {
        lastSelected = $(this);
    })
});

function addText(el, text) {
    lastSelected.focus().val(lastSelected.val() + text);
    return false;
}
