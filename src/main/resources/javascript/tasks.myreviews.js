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
        var stagingFrame = $(this).closest('.modal-content').find('iframe.stagingFrame');
        var liveFrame = $(this).closest('.modal-content').find('iframe.liveFrame');
        $.post($(this).attr("actionUrl"),
            {"stagingFrame":stagingFrame.contents().find('html').html(),
            "liveFrame":liveFrame.contents().find('html').html()} ,
            function (result) {
                debugger;
                alert(result["highlighted"]);
                stagingFrame.contents().find("html").html(result["highlighted"]);
            },
            'json'
        );
    });

}
