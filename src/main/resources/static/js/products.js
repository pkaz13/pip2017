/**
    * Created by Rafal Lebioda on 26.03.2017.
    */

function openModalWindow(rowId) {
    if(rowId=="product_0"){
        $("#modal_headear").text("Adding new product");
        $("#button_submit").text("Add product");
        $("#id_form_div").hide();
        $("#id_form").val("0");
        $("#name_form").val("");
        $("#description_form").val("");
        $("#imageUrl_form").val("");
    }
    else {
        var row=$("#"+rowId);
        var columns = row.find('td');
        $("#id_form_div").show();
        $("#id_form").val(columns.eq(0).text());
        $("#name_form").val(columns.eq(1).text());
        $("#description_form").val(columns.eq(2).text());
        $("#imageUrl_form").val(columns.eq(3).find("img").attr('src'));
        $("#modal_headear").text("Editing product");
        $("#button_submit").text("Update");
    }
    $('#edit').modal('show');
}

function openDeleteModalWindow(productId){
    var url='location.href="/admin/products/delete?productId='+productId+'"';
    $('#button_delete_product').attr('onclick', url);
    $('#delete').modal('show');
}

$(document).ready(function() {
    $("#newProductButton").click(function(){
        openModalWindow("product_0");
    });

    $(".button_edit").click(function(){
        var buttonId=this.id;
        var id = buttonId.split("_")[1];
        openModalWindow("product_"+id);
    });

    $(".button_delete").click(function(){
        var buttonId=this.id;
        var id = buttonId.split("_")[1];
        openDeleteModalWindow(id);
    });

    $('.keywords-input').tokenfield();
    $('.keywords-input').tokenfield('disable');
});