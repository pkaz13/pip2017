/**
    * Created by Rafal Lebioda on 26.03.2017.
    */

$(document).ready(function() {

	$('#product-form-modal').on('show.bs.modal', function (event) {
		var productId = $(event.relatedTarget).data('product-id');

		if (productId) {
			$(this).find('.modal-title').text("Edycja produktu");
			$(this).find('.button-submit').text("Aktualizuj");
			$("#id_form_div").show();

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
	        $("#imageUrl_form").val("");
	    }
	});

    $("#confirm-delete-modal").on('show.bs.modal', function(event){
        var productId = $(event.relatedTarget).data('product-id');

        var columns = $("#product-"+productId).find('td');
        $(this).find('.name-placeholder').text(columns.eq(1).text());
        $(this).find('.button-delete').attr('onclick', 'location.href="/admin/products/delete?productId='+productId+'"');
    });

    var keywords = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        // prefetch: '/admin/products/get_all_keywords_suggestions.json',
        remote: {
            url: '/admin/products/get_keywords_suggestions.json?searchTerm=%QUERY',
            wildcard: '%QUERY'
        },
        limit: 6
    });
    keywords.initialize();

    $('#keywords_form').tagsinput({
        allowDuplicates: false,
        itemValue: 'id',
        itemText: 'word',
        typeaheadjs: {
            name: "keywords",
            displayKey: "word",
            source: keywords.ttAdapter()
        },
        freeInput: true
    });


    // $('#keywords_form').tokenfield({
    //     autocomplete: {
    //         source: ['red', 'blue', 'green', 'yellow', 'violet', 'brown', 'purple', 'black', 'white'],
    //         delay: 100
    //     },
    //     showAutocompleteOnFocus: true
    // });

    // zapobieganie duplikacji - tokenfield
    // $('#keywords_form').on('tokenfield:createtoken', function (event) {
    //     var existingTokens = $(this).tokenfield('getTokens');
    //     $.each(existingTokens, function(index, token) {
    //         if (token.value === event.attrs.value)
    //             event.preventDefault();
    //     });
    // });

});