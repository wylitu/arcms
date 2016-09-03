//cvs文件导入
function  addForm(){
    var data = $('#addForm').serialise();
    $.ajax({
        type: "post", // 请求方式
        url:  baseUrl+"/taobao/trade/import", //url地址
        data: data, //数据
        success: function (data, info ) {
            if(data.errorCode == 0){
                alert("导入成功");
            }else{
                alert(data.errorMessage);
            }
        }, error: function () {
            alert("error");
        }
    });
}
$('#addForm').submit(function(){
    addForm()
});
