$(document).ready(function() {

    initCompareModales();

    $(".commentTaskButton").click(function () {
        var url = $(this).parent().attr("urlComment");
        var urlShow = $(this).parent().attr("urlShowComment");
        var taskID = $(this).parent().attr("taskId");
        var comment = $(this).parent().find('textarea').val();
        commentWorkflow(url,urlShow,taskID,comment);

        }
    )

    $(".displayCommentButton").click(function () {
        var urlShow = $(this).parent().attr("urlShowComment");
        var taskID = $(this).parent().attr("taskId");
        displayComments(urlShow,taskID);
    })

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

        if ($(this).attr("highlight")== "false"){
            $.post($(this).attr("actionUrl"),
                {"stagingFrame":stagingFrame.contents().find('html').find('body').html(),
                "liveFrame":liveFrame.contents().find('html').find('body').html()} ,
                function (result) {
                    stagingFrame.contents().find("html").find('body').html(result["highlighted"]);
                },
                'json'
            );
            $(this).attr("highlight","true");
        }else{
            // TODO: The is where we reload the iframe without the highlighting
            $('.stagingFrame').attr('src', $('.stagingFrame').attr('src'));
            $(this).attr("highlight","false");
        }
    });

}

function commentWorkflow(url,urlShow,node,comment) {
    $.ajax({
        url: url,
        context: document.body,
        dataType: "json",
        data: {
            node: node,
            comment: comment
        },
        success: function () {
            $("#"+node+"Comments").empty()
            displayComments(urlShow,node);

        },
        error: function (request, status, error) {
            $("#"+node+"Comments").empty()
            alert("An error occured, comment has not been add");
        }
    })

};

function displayComments(url,node) {
    $.ajax({
        url: url,
        context: document.body,
        dataType: "json",
        data: {
            node: node,
        },
        success: function (comments) {
            // TODO: This where we need to build the HTML that diplays the comments
            var response = comments['wfcomments'];
            var HTMLreponse = "<table border='1'>";

            for (var j = 0; j < response.length; j++){
                HTMLreponse+="<tr><td>" + response[j].comment+"</td><td>"+ response[j].user+"</td><td>"+ response[j].time+"</td></tr>";
            }

            HTMLreponse+="</table>";
            $(".displayComments"+node).html(HTMLreponse);
        },
        error: function (request, status, error) {
            alert("An error occured when retrieving comments");
        }
    })

};
