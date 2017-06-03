/*$("#passwordfield").on("keyup",function(){
    if($(this).val())
        $(".fa-eye").show();
    else
        $(".fa-eye").hide();
});*/

$("#passwordfield").mousedown(function(){
    $("#passwordfield").attr('type','text');
}).mouseup(function(){
    $("#passwordfield").attr('type','password');
}).mouseout(function(){
    $("#passwordfield").attr('type','password');
});
