/**
    * Created by Rafal Lebioda on 26.03.2017.
    */

$(document).ready(function() {

	$('#product-form-modal').on('show.bs.modal', function (event) {
		var productId = $(event.relatedTarget).data('product-id');
        $("#keywords_form").tagsinput('removeAll');

		if (productId) {
			$(this).find('.modal-title').text("Edycja produktu");
			$(this).find('.button-submit').text("Aktualizuj");
			$("#id_form_div").show();

            $.ajax({
                url: "/admin/products/"+productId+"/keywords",
                success: function(data) {
                    $.each(data, function(index, item) {
                        $("#keywords_form").tagsinput('add', item);
                    })
                }
            });

			var columns = $("#product-"+productId).find('td');
			$("#id_form").val(columns.eq(0).text());
			$("#id_form_static").text(columns.eq(0).text());
			$("#name_form").val(columns.eq(1).text());
			$("#description_form").val(columns.eq(2).text());
			$("#imageUrl_form").val(columns.eq(4).find("img").attr('src'));
		} else {
			$(this).find('.modal-title').text("Dodaj nowy produkt");
	        $(this).find('.button-submit').text("Dodaj");
	        $("#id_form_div").hide();

	        $("#id_form").val("0");
	        $("#id_form_static").text("");
	        $("#name_form").val("");
	        $("#description_form").val("");
	        $("#keywords_form").val("");
	        $("#imageUrl_form").val("");
	    }
	});

    $("#confirm-delete-modal").on('show.bs.modal', function(event){
        var productId = $(event.relatedTarget).data('product-id');

        var columns = $("#product-"+productId).find('td');
        $(this).find('.name-placeholder').text(columns.eq(1).text());
        $(this).find('.button-delete').attr('onclick', 'location.href="/admin/products/'+productId+'/delete"');
    });

    var keywords = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        // prefetch: '/admin/products/get_all_keywords_suggestions',
        remote: {
            url: '/admin/product/keyword/suggestions?searchTerm=%QUERY',
            wildcard: '%QUERY'
        },
        limit: 6
    });
    keywords.initialize();

    $('.keywords').tagsinput({
        allowDuplicates: false,
        trimValue: true,
        typeaheadjs: {
            source: keywords.ttAdapter()
        }
    });
    
});