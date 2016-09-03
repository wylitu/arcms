/**
 * 获取表单重复提交token
 */
function createToken(formId){
    $.ajax({
        type: "get", // 请求方式
        url: baseUrl + "/createToken", //url地址
        contentType: "application/json",
        dataType: "json",
        success: function (response, ifo) {
            if(!$('#token') || !$('#token').val() || $('#token').val()=="undefined"){
                $('#'+formId).append("<input type=\"hidden\" id=\"token\" name=\"token\" value='"+response.token+"'>");
            }else{
                $('#token').val(response.token);
            }


        }, error: function () {
            alert("error");
        }
    })
 }

