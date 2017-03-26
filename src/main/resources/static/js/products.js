/**
    * Created by Rafal Lebioda on 26.03.2017.
    */

function openModalWindow(rowId) {
    $('#edit').modal('show')
    if(rowId=="product_0"){
        $("#modal_headear").text("Adding new product");
        $("#button_submit").text("Add product");
        $("#id_form").val("0");
        $("#name_form").val("");
        $("#description_form").val("");
        $("#imageUrl_form").val("");
    }
    else {
        var row=$("#"+rowId);
        var columns = row.find('td');
        $("#id_form").val(columns.eq(0).text());
        $("#name_form").val(columns.eq(1).text());
        $("#description_form").val(columns.eq(2).text());
        $("#imageUrl_form").val(columns.eq(3).find("img").attr('src'));
        $("#modal_headear").text("Editing product");
        $("#button_submit").text("Update");


    }
}