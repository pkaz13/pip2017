jQuery(function($) {
    // reset handler that clears the form
    $('form input:reset, form button:reset').click(function () {
        $(this).parents('form').each(function(){
        	$(this).find(':radio, :checkbox').removeAttr('checked');
        	$(this).find('textarea, :text, select').val('');
        });
        
        $(this).blur();
        
        return false;
    });
    
    $('form input.autofocus, form textarea.autofocus').each(function () {
	    $(this).focus(); 
	    if (this.setSelectionRange) {
		    var length= $(this).val().length;
		    this.setSelectionRange(length, length);
	    } else {
	    	 $(this).val($(this).val());
	    }
    });
    
    $('[data-toggle="tooltip"]').tooltip();
});