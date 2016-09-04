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
            if(!$('#submit_token') || !$('#submit_token').val() || $('#submit_token').val()=="undefined"){
                $('#'+formId).append("<input type=\"hidden\" id=\"submit_token\" name=\"submit_token\" value='"+response.submit_token+"'>");
            }else{
                $('#submit_token').val(response.submit_token);
            }


        }, error: function () {
            alert("error");
        }
    })
 }

