/**
 * 查看详细信息方法
 * @type {*|jQuery}
 */
detail();

/**
 * 获取表单详细信息
 */
function  detail(){
    $.post( baseUrl+"/seller/detailBySellerId", {sellerId:0}, function(data){

        new Dsy({
            // 绑定三个select的id
            s:['province','city','district'],
            // 是否是大陆
            isMainLand:true
            // 默认浙江省

        });

        loadData('#detailForm', data.seller);
    }, "json")
}


/**
 * 编辑
 */
function edit(id){
    $.post(baseUrl + "/getSellerDetail", {id: id}, function (data) {
        loadData('#sellerModalDetail', data.seller);
        $('#sellerModalDetail').modal('show');
    }, "json")
}



/**
 * 设置状态
 */
function  setStateForm(){

    var checks = $(".table td :input[type='checkbox']:checked");
    if (checks.length == 0) {
        alert("没有选中的商家,请选择商家！");
        return;
    }
    var saveDataAry=[]

    $.each(checks, function (index, check) {
        var data={'id':$(check).val(),'state':'1'};
        saveDataAry.push(data);
    })

    var  saveStr=JSON.stringify(saveDataAry).replace(/\"/g,"'");
    var imgId = [];

    $.ajaxFileUpload({
        url : baseUrl + '/seller/setState',
        secureuri:false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {
            StateData:saveStr
        },
        success: function (data){
            $('#imgTable').DataTable().draw();
            alert("设置状态成功!");
        },
        error: function(data){
            alert("保存失败!");
        }
    });
}


/**
 * 保存商家信息
 * @param id
 */
//$("#saveOrUpdate").click(function (event) {
//    event.preventDefault()
//
//    var imgId = [];
//    var sellerName = $('#addForm input[name="sellerName"]').val();
//    var businesstouserRecommendCode=$('input[name="businesstouserRecommendCode"]').val();
//    var contacts  =$('input[name="contacts"]').val();
//    var mobile  =$('input[name="mobile"]').val();
//    var discount  =$('input[name="discount"]').val();
//    var province  =$('#province').val();
//    var city  =$('#city').val();
//    var district  =$('#district').val();
//    var address  =$('input[name="address"]').val();
//    var email  =$('input[name="email"]').val();
//    var phone  =$('input[name="phone"]').val();
//    var sellerBrief  =$('input[name="sellerBrief"]').val();
//
//    var alipayUserId  =$('input[name="alipayUserId"]').val();
//    var alipayUserName  =$('input[name="alipayUserName"]').val();
//
//    $.each($(':input[type="file"]'),function(i,value){
//       imgId.push($(':input[type="file"]').eq(i).attr('id'));
//    });
//
//    var data = {"sellerName":sellerName,"businesstouserRecommendCode":businesstouserRecommendCode,"contacts":contacts,"mobile":mobile,"discount":discount, "province":province,"city":city, "district":district,"address":address,"email":email,"phone":phone,"sellerBrief":sellerBrief,"sellerDetail":"","alipayUserId":alipayUserId,"alipayUserName":alipayUserName};
//    var  postData = JSON.stringify(data).replace(/\"/g,"'");
//    $.ajaxFileUpload({
//        url : baseUrl + '/seller/ajaxCreateSeller',
//        secureuri:false,
//        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
//        dataType: 'json',
//        data: {sellerData:postData},
//        success: function(reponseData){
//            if(reponseData.errorCode == 0){
//                alert(reponseData.info);
//                $('#imgTable').DataTable().draw();
//                $('#addForm')[0].reset();
//                $('#sellerModal').modal('hide');
//              //  freshUrl(baseUrl + "/seller/list");
//            }else{
//                alert(reponseData.errorMessage);
//            }
//        },
//        error: function(reponseData){
//            alert("保存失败!");
//        }
//    });
//
//});

/**
 * 保存更新商家信息
 * @param id
 */
$("#updateForm").click(function (event) {
    event.preventDefault()

    var imgId = [];
    var id = $('#detailForm input[name="id"]').val();
    var sellerId = $('#detailForm input[name="sellerId"]').val();
    var sellerName = $('#detailForm input[name="sellerName"]').val();
    var businesstouserRecommendCode=$('#detailForm input[name="businesstouserRecommendCode"]').val();
    var contacts  =$('#detailForm input[name="contacts"]').val();
    var mobile  =$('#detailForm input[name="mobile"]').val();
    var discount  =$('#detailForm input[name="discount"]').val();
    var province  =$('#province').val();
    var city  =$('#city').val();
    var district  =$('#district').val();
    var address  =$('#detailForm input[name="address"]').val();
    var email  =$('#detailForm input[name="email"]').val();
    var phone  =$('#detailForm input[name="phone"]').val();
    var sellerBrief  =$('#detailForm input[name="sellerBrief"]').val();

    var alipayUserId  =$('#detailForm input[name="alipayUserId"]').val();
    var alipayUserName  =$('#detailForm input[name="alipayUserName"]').val();

    $.each($('#detailForm :input[type="file"]'),function(i,value){
        imgId.push($('#detailForm :input[type="file"]').eq(i).attr('id'));
    });
    var data = {"id":id,"sellerId":sellerId,"sellerName":sellerName,"businesstouserRecommendCode":businesstouserRecommendCode,"contacts":contacts,"mobile":mobile,"discount":discount, "province":province,"city":city, "district":district,"address":address,"email":email,"phone":phone,"sellerBrief":sellerBrief,"alipayUserId":alipayUserId,"alipayUserName":alipayUserName};
    var  postData = JSON.stringify(data).replace(/\"/g,"'");
    $.ajaxFileUpload({
        url : baseUrl + '/seller/ajaxCreateSeller',
        secureuri:false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {sellerData:postData},
        success: function(reponseData){
            if(reponseData.errorCode == 0){
                alert(reponseData.info);
                detail();
              //  $('#detailForm')[0].reset();
                //  freshUrl(baseUrl + "/seller/list");
            }else{
                alert(reponseData.errorMessage);
            }
        },
        error: function(reponseData){
            alert("保存失败!");
        }
    });

});


/**
 * 加载数据
 * @param topSelector
 * @param obj
 */
function loadData(topSelector, obj) {
    var key, value, tagName, type, arr;
    for (x in obj) {
        key = x;
        value = obj[x];

        if("businessLicense"==key||"sellerPicture"==key){
            $("#detailForm  img[name='"+ key+"']").css("display","block");
            $("#detailForm  img[name='"+ key+"']").attr("src",baseUrl+value);
            continue;
        }

        if("province"==key||"city"==key||"district"==key){
           $('#'+key).append("<option value='"+ value+"' selected='selected'>"+ value +"</option>");
//            console.dir($('#'+key));
//            console.dir(value);
           // $('#'+key +" option[value='"+ value+"']").attr("selected", "selected");
//            $('#'+key).val(value);
         //   $('#'+key).find("option[value='"+value+"']").attr("selected",true);
            continue;
        }


        $(topSelector + " [name='" + key + "']," + topSelector + " [name='" + key + "[]']").each(function () {

            if ("startDate" == key || "endDate" == key) {
                return;
            }
            tagName = $(this)[0].tagName;
            type = $(this).attr('type');

            if (tagName == 'INPUT') {
                if (type == 'radio') {
                    if ($(this).val() == value) {
                        //  $('#hbpoolDetailModal  input[name="config"][value='+value+']').prop('checked', "checked");
                        //  $(this).prop('checked', "checked");
                        $(this).prop('checked', "checked");
                    } else {
                        $(this).removeAttr('checked');
                    }
                } else if (type == 'checkbox') {
                    arr = value.split(',');
                    for (var i = 0; i < arr.length; i++) {
                        if ($(this).val() == arr[i]) {
                            $(this).prop('checked', "checked");
                            break;
                        }
                    }
                } else {
                    $(this).val(value);
                }
            } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                $(this).val(value);
            }
        });
    }
}