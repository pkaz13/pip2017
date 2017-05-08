/**
 * Created by Piotr on 08.04.2017.
 */

$(document).ready(function () {

    $('#keyword-form-modal').on('show.bs.modal', function (event) {
        var keywordId = $(event.relatedTarget).data('keyword-id');

        if (keywordId) {
            $(this).find('.modal-title').text("Edycja słowa kluczowego");
            $(this).find('.button-submit').text("Aktualizuj");
            $("#id_form_div").show();

            var columns = $("#keyword-" + keywordId).find('td');
            $("#id_form").val(columns.eq(0).text());
            $("#id_form_static").text(columns.eq(0).text());
            $("#word_form").val(columns.eq(1).text());
        }
        else {
            $(this).find('.modal-title').text("Dodaj nowe słowo kluczowe");
            $(this).find('.button-submit').text("Dodaj");
            $("#id_form_div").hide();

            $("#id_form").val("0");
            $("#id_form_static").text("");
            $("#word_form").val("");
        }
    });

    $("#confirm-delete-modal").on('show.bs.modal', function (event) {
        var keywordId = $(event.relatedTarget).data('keyword-id');

        var columns = $("#keyword-" + keywordId).find('td');
        $(this).find('.word-placeholder').text(columns.eq(1).text());
        $(this).find('.button-delete').attr('onclick', 'location.href="/admin/keywords/' + keywordId + '/delete"');
    });


});