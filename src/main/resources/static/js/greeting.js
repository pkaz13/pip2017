/**
    * Created by Rafal Lebioda on 14.03.2017.
    */

function addText(el, text) {
	$('#message').val($('#message').val() + text);
	$(el).blur();
	return false;
}
