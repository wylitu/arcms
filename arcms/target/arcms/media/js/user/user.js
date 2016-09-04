/**
 * 绑定会员卡
 */
function bindMemberCard(){
    var card = $('#memberCard').val();
    if(isNullOrEmpty(card)){
        alert("请输入会员卡号");
        return;
    }
    $.ajax({
        type: "POST",
        url: baseUrl + "/mobile/user/bindCard?memberCard="+card,
        dataType: "json",
        success : function(event, XMLHttpRequest, ajaxOptions) {
            var error = jQuery.parseJSON(ajaxOptions.responseText).error;
            if (error) {
                // 设置信息
                alert("操作失败");
            } else {
                alert("操作成功");
            }
        },
        error : function(event, jqXHR, ajaxSettings, thrownError) {
            alert("操作失败")
        }
    })
}


function memberPay(){
    //var card = $('#memberCard').val();
    //
    //
    //$.ajax({
    //    type: "POST",
    //    url: baseUrl + "/mobile/user/doPay?lastTime="+card,
    //    dataType: "json",
    //    success : function(event, XMLHttpRequest, ajaxOptions) {
    //        var error = jQuery.parseJSON(ajaxOptions.responseText).error;
    //        if (error) {
    //            // 设置信息
    //            alert("操作失败");
    //        } else {
    //            alert("操作成功");
    //        }
    //    },
    //    error : function(event, jqXHR, ajaxSettings, thrownError) {
    //        alert("操作失败")
    //    }
    //})
}


