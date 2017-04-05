/**
 * Created by Rafal Lebioda on 14.03.2017.
 */
var lastSelected = $(':focus');

jQuery(function ($) {
    $(".message-item").on('blur', function () {
        lastSelected = $(this);
    })

    $(".delete-btn").on("click", function () {
        var url = $(event.target).attr("href");
        console.log(url);
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        $.ajax({
            type: "DELETE",
            url: url,
            success: function (response) {
                window.location.href = response;
            },
            error: function (e) {
                alert('Failed!');
            }
        });
    });
});

function addText(el, text) {
    lastSelected.focus().val(lastSelected.val() + text);
    return false;
}


