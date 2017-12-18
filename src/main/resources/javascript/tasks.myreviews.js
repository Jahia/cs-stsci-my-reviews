$(document).ready(function() {

    initCompareModales();

})
function initCompareModales() {
    $(".compareTaskItem").click(function() {
        $("#CompareWindowTasks").find('.modal-body').load($(this).parent().attr("url"),function(){
            $('#CompareWindowTasks').modal({show:true});
        });
    });
    $(".highlightButton").click(function() {
        var stagingFrame = $(this).closest('.modal-content').find('iframe.stagingFrame');
        var liveFrame = $(this).closest('.modal-content').find('iframe.liveFrame');
        $.post($(this).attr("actionUrl"),
            {"stagingFrame":stagingFrame.contents().find('html').find('body').html(),
            "liveFrame":liveFrame.contents().find('html').find('body').html()} ,
            function (result) {
                stagingFrame.contents().find("html").find('body').html(result["highlighted"]);
            },
            'json'
        );
    });

}
