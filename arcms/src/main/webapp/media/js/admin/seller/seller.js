/**
 * 弹出新建商家的框
 */
function   showAddForm(){
  //  createToken('addForm');
    $('#sellerModal').modal('show');
}

/**
 * 编辑
 */
function edit(id){
    $.post(baseUrl + "/getSellerDetail", {id: id}, function (data) {

        new Dsy({
            // 绑定三个select的id
            s:['provinceEdit','cityEdit','districtEdit'],
            // 是否是大陆
            isMainLand:true
        });
        $('#footer').show();
        loadData('#sellerModalDetail', data.seller);
        $('#sellerModalDetail').modal('show');
    }, "json")
}


/**
 * 查看
 */
function view(id){
    $.post(baseUrl + "/getSellerDetail", {id: id}, function (data) {

        new Dsy({
            // 绑定三个select的id
            s:['provinceEdit','cityEdit','districtEdit'],
            // 是否是大陆
            isMainLand:true
        });
        $('#footer').hide();

        loadData('#sellerModalDetail', data.seller);
        $('#sellerModalDetail').modal('show');
    }, "json")
}


/**
 * 设置佣金
 */
function  setCommissionForm(id){
    $('#CommissionForm input[name="commissionId"]').val(id);
    $('#setCommissionModal').modal('show');
}

/**
 * 设置状态
 */
function  setStateForm(){
    event.preventDefault();
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
 * 保存设置商家佣金比例
 * @param id
 */
$("#saveOrUpdateCommission").click(function (event) {
    event.preventDefault()

    var commission = $('#CommissionForm input[name="commission"]').val();

    var checks = $(".table td :input[type='checkbox']:checked");
    if (checks.length == 0) {
        alert("没有选中的商家,请选择商家！");
        return;
    }
    var saveDataAry=[]

    $.each(checks, function (index, check) {
        var data={'id':$(check).val(),'commission':commission};
        saveDataAry.push(data);
    })

    var  saveStr=JSON.stringify(saveDataAry).replace(/\"/g,"'");

    if(isNullOrEmpty(commission)){
        alert('佣金比例必填!');
        return ;
    }

    var imgId = [];

    $.ajaxFileUpload({
        url : baseUrl + '/seller/setCommission',
        secureuri:false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {
            CommissionData:saveStr
        },
        success: function (data){
            $('#imgTable').DataTable().draw();
            alert("设置佣金成功!");
        },
        error: function(data){
            alert("保存失败!");
        }
    });
});

/**
 * 保存商家信息
 * @param id
 */
$("#saveOrUpdate").click(function (event) {
    event.preventDefault()

    var imgId = [];
    var sellerName = $('#addForm input[name="sellerName"]').val();
    var businesstouserRecommendCode=$('input[name="businesstouserRecommendCode"]').val();
    var contacts  =$('input[name="contacts"]').val();
    var mobile  =$('input[name="mobile"]').val();
    var discount  =$('input[name="discount"]').val();
    var province  =$('#province').val();
    var city  =$('#city').val();
    var district  =$('#district').val();
    var address  =$('input[name="address"]').val();
    var email  =$('input[name="email"]').val();
    var phone  =$('input[name="phone"]').val();
    var sellerBrief  =$('input[name="sellerBrief"]').val();

    var alipayUserId  =$('input[name="alipayUserId"]').val();
    var alipayUserName  =$('input[name="alipayUserName"]').val();

    //商家详情
//    var sellerDetail = editor.getContent();
//    if(isNullOrEmpty(sellerDetail)){
//        alert('商家详情必填');
//        return ;
//    }

    $.each($(':input[type="file"]'),function(i,value){
       imgId.push($(':input[type="file"]').eq(i).attr('id'));
    });

    var data = {"sellerName":sellerName,"businesstouserRecommendCode":businesstouserRecommendCode,"contacts":contacts,"mobile":mobile,"discount":discount, "province":province,"city":city, "district":district,"address":address,"email":email,"phone":phone,"sellerBrief":sellerBrief,"alipayUserId":alipayUserId,"alipayUserName":alipayUserName};
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
                $('#imgTable').DataTable().draw();
                $('#addForm')[0].reset();
                $('#sellerModal').modal('hide');
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
 * 更新商家信息
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
    var province  =$('#provinceEdit').val();
    var city  =$('#cityEdit').val();
    var district  =$('#districtEdit').val();
    var address  =$('#detailForm input[name="address"]').val();
    var email  =$('#detailForm input[name="email"]').val();
    var phone  =$('#detailForm input[name="phone"]').val();
    var sellerBrief  =$('#detailForm input[name="sellerBrief"]').val();

    var alipayUserId  =$('#detailForm input[name="alipayUserId"]').val();
    var alipayUserName  =$('#detailForm input[name="alipayUserName"]').val();

    $.each($('#detailForm :input[type="file"]'),function(i,value){
        imgId.push($('#detailForm :input[type="file"]').eq(i).attr('id'));
    });
console.dir(imgId);
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
                $('#imgTable').DataTable().draw();
                $('#detailForm')[0].reset();
                $('#sellerModalDetail').modal('hide');
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
 * 分页
 * @type {*|jQuery}
 */
var oTable = $('#imgTable').dataTable({
    "language": {
        "paginate": {
            "first": "首页",
            "last": "末页",
            "next": "下一页",
            "previous": "上一页"
        },
        "lengthMenu": "每页 _MENU_ 条记录",
        "zeroRecords": "没有数据",
        "info": "共 _TOTAL_ 记录， 当前第 _PAGE_ 页",
        "infoFiltered": ""
    },
    "pagingType": "full_numbers",
    "processing": true,
    "searching": false,
    "serverSide": true,
    "ajax": {
        "url": baseUrl + '/seller/datas',
        "data": function (d) {
            var sellerName = $("#searchForm :input[name='sellerName']").val();
            var isChecked = $("#searchForm :input[name='isChecked']").val();
            var startDate = $("#searchForm :input[name='startDate']").val();
            var endDate = $("#searchForm :input[name='endDate']").val();
            if (!isNullOrEmpty(sellerName)) {
                d.sellerName = sellerName;
            }
            if (!isNullOrEmpty(isChecked)) {
                d.isChecked = isChecked;
            }
            if (!isNullOrEmpty(startDate))
                d.startDate = startDate;
            {
            }
            if (!isNullOrEmpty(endDate)) {
                d.endDate = endDate;
            }
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return '<th ><label><input type="checkbox" class="ace" value="' + data + '"/><span class="lbl"></span></label></th>';
            },
            "targets": 0
        },
        {
            "render": function (data, type, row) {
                if (data == "0") {
                    return "待审核"
                } else if (data == "1") {
                    return "已审核"
                }
            },
            "targets": 3
        },
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return  '';
                } else
                    return getLocalTime(data);
            },
            "targets": 8
        },
        {
            "render": function (data, type, row) {

                return '<td > <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"> <a class="blue" href="javascript:view(' + row.id + ')"> 查看 |</a>  <a class="blue" href="javascript:edit(' + row.id + ')">编辑 |</a> </div></td>';
            },
            "targets": 9
        }
    ],
    "columns": [
        {"data": "id", "sortable": false},
        {"data": "sellerName", "sortable": false},
        {"data": "commission", "sortable": false},
        {"data": "isChecked", "sortable": false},
        {"data": "contacts","sortable": false},
        {"data": "phone", "sortable": false},
        {"data": "address" ,"sortable": false},
        {"data": "businesstouserRecommendCode", "sortable": false},
        {"data": "gmtCreated", "sortable": false},
        {"data": null, "sortable": false}
    ]
});


/**
 * 删除商家信息
 */
$("#delSeller").click(function (event) {
    event.preventDefault()
    if (confirm("确认删除吗")) {
        var checks = $(".table td :input[type='checkbox']:checked");
        if (checks.length == 0) {
            alert("没有选中的商家,请选择商家！");
            return;
        }
        var ids = [];
        $.each(checks, function (index, check) {
            ids.push($(check).val());
        })
        $.ajax({
            type: "post", // 请求方式
            url: baseUrl + "/seller/delete", //url地址
            data: JSON.stringify(ids), //数据
            contentType: "application/json",
            dataType: "json",
            success: function (response, ifo) {
                $('#imgTable').DataTable().draw();
                alert("删除成功");
            }, error: function () {
                alert("error");
            }
        })
    }
});

/**
 * 全选和反选
 */
function selectAll() {
    if ($('#allCheckbox:checked').val() == 'true') {
        $(".table td :input[type='checkbox']").prop("checked", "checked");
    } else {
        $(".table td :input[type='checkbox']").prop("checked", false);
    }
}



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
            $('#'+key+"Edit").append("<option value='"+ value+"' selected='selected'>"+ value +"</option>");
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

$('#sellerSearch').click(function(event){
    $('#imgTable').DataTable().draw();
    event.preventDefault()
})

