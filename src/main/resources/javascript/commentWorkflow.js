function commentWorkflow(url,node,comment) {
    $.ajax({
        url: url,
        context: document.body,
        dataType: "json",
        data: {
            node: node,
            comment: comment
        },
        success: function () {
            document.getElementById(node+'Comments').value = "";
            alert("Your comment has been add");
        },
        error: function (request, status, error) {
            document.getElementById(node+'Comments').value = "";
            alert("An error occured, comment has not been add");
        }
    })

};