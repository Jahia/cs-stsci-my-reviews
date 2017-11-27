$(document).ready(function() {

    initCompareModales();

})
function initCompareModales() {
    $(".compareTaskItem").find( "a" ).click(function() {
        $("#CompareWindowTasks").find('.modal-body').load($(this).parent().attr("url"),function(){
            $('#CompareWindowTasks').modal({show:true});
        });
    });
    $(".highlightButton").click(function() {

    });

}