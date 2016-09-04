
function feedbackMessage(){
    var title = $('#msgTitle').val();
    var content = $('#msgContent').val();
    if(isNullOrEmpty(title) || isNullOrEmpty(content)){
        alert("请填写标题和内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: baseUrl + "/mobile/message/addFeedback?title="+title+"&content="+content,
        dataType: "json",
        success : function(event, XMLHttpRequest, ajaxOptions) {
            var result = jQuery.parseJSON(ajaxOptions.responseText);
            if (result.errorCode =="0") {
                // 设置信息
                alert("保存成功");
            } else {
                alert(result.errorMessage);
            }
        },
        error : function(event, jqXHR, ajaxSettings, thrownError) {
            alert("操作失败")
        }
    })
}