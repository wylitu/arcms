var manualTarget = "";
var poolId = "";
var goodsId = "";
var dtNow=new Date();
var dtNew=new Date(dtNow.getTime()+7*24*60*60*1000);
var endtimeStr = "" + dtNew.getFullYear() + "-";
endtimeStr += (dtNew.getMonth()+1) + "-";
endtimeStr += (dtNew.getDate());

$("#endtime").prop("value",endtimeStr);
$("#birthHb").bind("click", function(event) {
    $("#hiddenDiv1").show();
    $("#hiddenDiv2").hide();
    $("#hiddenDiv3").hide();
    $("#hiddenDiv4").hide();
});
$("#birthGifts").bind("click", function(event) {
    $("#hiddenDiv1").hide();
    $("#hiddenDiv2").show();
    $("#hiddenDiv3").hide();
    $("#hiddenDiv4").hide();
});
$("#birthPoints").bind("click", function(event) {
    $("#hiddenDiv1").hide();
    $("#hiddenDiv2").hide();
    $("#hiddenDiv3").show();
    $("#hiddenDiv4").hide();
});
$("#birthDiscount").bind("click", function(event) {
    $("#hiddenDiv1").hide();
    $("#hiddenDiv2").hide();
    $("#hiddenDiv3").hide();
    $("#hiddenDiv4").show();
});

$("#manual").bind("click",function(event){
    $("#target").prop("class","col-sm-4");
    $("#findCus").show();
});
$("#week").bind("click",function(event){
    $("#endtime").prop("value",endtimeStr);
    $("#target").prop("class","col-sm-3");
    $("#findCus").hide();
});
$("#month").bind("click",function(event){
    var dtNew2=new Date(dtNow.getTime()+30*24*60*60*1000);
    var endtimeStr2 = "" + dtNew2.getFullYear() + "-";
    endtimeStr2 += (dtNew2.getMonth()+1) + "-";
    endtimeStr2 += (dtNew2.getDate());
    $("#endtime").prop("value",endtimeStr2);
    $("#target").prop("class","col-sm-3");
    $("#findCus").hide();
});
/*选择红包池*/
function selectHBPool() {
    //弹出悬浮层，展现红包池列表
    //红包列表
    var TableModal = $('#HBTable').DataTable({
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
        "bDestroy":true,
        "processing": true,
        "searching": false,
        "serverSide": true,
        "ajax": {
            "url":  baseUrl+'/hbPool/datas',
            "data": function (d) {
                var name = $("#searchForm :input[name='name']").val();
                var type = $("#searchForm :input[name='type']").val();
                var startDate = $("#searchForm :input[name='startDate']").val();
                var endDate = $("#searchForm :input[name='endDate']").val();
                if (!isNullOrEmpty(name)) {
                    d.name = name;
                }
                if (!isNullOrEmpty(type)) {
                    d.type = type;
                }
                if (!isNullOrEmpty(startDate)) {
                    d.startDate = startDate;
                }
                if (!isNullOrEmpty(endDate)) {
                    d.endDate = endDate;
                }
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="cbox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            }, {
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return getLocalTime(data);
                },
                "targets":2
            }, {
                "render": function (data, type, row) {

                    if(data == "unBounded"){
                        return "未关联";
                    } else if (data == "unReceived"){
                        return "待领取";

                    }else if (data == "receiving"){
                        return "领取中";
                    } else if (data == "done"){
                        return "结束";
                    }
                    return data;
                },
                "targets":3
            },{
                "render": function (data, type, row) {
                    return (row.number-row.numberObtain);
                },
                "targets":6
            },{
                "render": function (data, type, row) {
                    if(data == "gjc"){
                        return "关键词领取";
                    } else if (data == "jfdh"){
                        return "积分兑换领取";
                    }else if (data == "yyy"){
                        return "摇一摇领取";
                    }else if(data="hpf"){
                        return  "好评返领取";
                    }else if(data="gw"){
                        return   "购物领取";
                    }else  if(data="fxs"){
                        return   "分享领取";
                    }else {
                        return  "";
                    }
                    return data;
                },
                "targets":7
            },{
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return getLocalTime(data);

                },
                "targets":8
            }
        ],
        "sort":false,
        "columns": [
            {"data": "poolId"},
            {"data": "name"},
            {"data": "gmtCreated"},
            {"data": "states"},
            {"data": "number"},
            {"data": "numberObtain"},
            {"data": "null"},
            {"data": "type"},
            {"data": "endDate"}
        ]
    });
    $('#hbpoolModal').modal('show');
    $('#searchBut').click(function(){
        TableModal.draw();
    });
    $('#submitBut').click(function(){

        //var val = '';
        var i = 0;
        $(":checkbox[name='cbox']").each(function(){
            if ($(this).prop('checked')){
                poolId = $(this).val();
                i++;
            }
        });
        if (i > 1){
            alert('红包池只能选择一个');
            return false;
        }
        if (poolId == ''|| poolId == null){
            alert("未选择红包池");
            return false;
        }
        $("#addForm input[name='bizId']").val(val);
    });

}
//赠送对象， 手动选择
function selectSendTarget(period) {
    //弹出悬浮层，展现红包池列表
    //红包列表
    var TableModal = $('#birthdayTable').DataTable({
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
        "bDestroy":true,
        "processing": true,
        "searching": false,
        "serverSide": true,
        "ajax": {
            "url":  baseUrl+'/birthday/manual/operation',
            "data": function (d) {
                var name = $("#searchForm :input[name='name']").val();
                var type = $("#searchForm :input[name='type']").val();
                var startDate = $("#searchForm :input[name='startDate']").val();
                var endDate = $("#searchForm :input[name='endDate']").val();
                d.timebucket=period;
                if (!isNullOrEmpty(name)) {
                    d.name = name;
                }
                if (!isNullOrEmpty(type)) {
                    d.type = type;
                }
                if (!isNullOrEmpty(startDate)) {
                    d.startDate = startDate;
                }
                if (!isNullOrEmpty(endDate)) {
                    d.endDate = endDate;
                }
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="manualcbox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            }
        ],
        "sort":false,
        "columns": [
            {"data": "userId"},
            {"data": "wxNickName"},
            {"data": "firstSource"},
            {"data": "mobile"},
            {"data": "birthday"}
        ]
    });
    $('#birthdayModal').modal('show');
    $('#searchBut').click(function(){
        TableModal.draw();
    });
    $('#manualsubmit').click(function(){
        //var val = '';
        var i = 0;
        $(":checkbox[name='manualcbox']").each(function(){
            if($(this).prop("checked")){
                manualTarget += $(this).val()+",";
                //val = $(this).val();
                i++;
            }
        });
        alert(manualTarget);
        if (manualTarget == ''|| manualTarget == null){
            alert("赠送对象至少选择一个");
            return false;
        }
        //$("#addForm input[name='bizId']").val(manualTarget);
    });
}

//选择生日礼物
function selectBirthdayGifts() {
    //弹出悬浮层，展现红包池列表
    //红包列表
    var TableModal = $('#giftsTable').DataTable({
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
        "bDestroy":true,
        "processing": true,
        "searching": false,
        "serverSide": true,
        "ajax": {
            "url":  baseUrl+'/birthday/select/birthday/gifts',
            "data": function (d) {
                var name = $("#searchForm :input[name='name']").val();
                var type = $("#searchForm :input[name='type']").val();
                var startDate = $("#searchForm :input[name='startDate']").val();
                var endDate = $("#searchForm :input[name='endDate']").val();
                if (!isNullOrEmpty(name)) {
                    d.name = name;
                }
                if (!isNullOrEmpty(type)) {
                    d.type = type;
                }
                if (!isNullOrEmpty(startDate)) {
                    d.startDate = startDate;
                }
                if (!isNullOrEmpty(endDate)) {
                    d.endDate = endDate;
                }
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="giftscbox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            }
        ],
        "sort":false,
        "columns": [
            {"data": "goodsId"},
            {"data": "title"},
            {"data": "quantity"},
            {"data": "price"},
            {"data": "point"},
        ]
    });
    $('#giftsModal').modal('show');
    $('#searchBut').click(function(){
        TableModal.draw();
    });
    $('#giftssubmit').click(function(){
        //var val = '';
        var i = 0;
        $(":checkbox[name='giftscbox']").each(function(){
            if ($(this).prop('checked')){
                goodsId = $(this).val();
                i++;
            }
        });
        alert(goodsId);
        if (i > 1){
            alert('红包池只能选择一个');
            return false;
        }
        if (goodsId == ''|| goodsId == null){
            alert("未选择红包池");
            return false;
        }
        $("#addForm input[name='bizId']").val(val);
    });
}
//保存回复设置
$('#save').click(function(){
    var title = $("#birthdayForm input[name='title']").val();
    var careType = $("#birthdayForm input[name='careType']:checked").val();
    //有两种类型7天内过生日，30天内过生日，还有一种是手动选择的
    var sendTarget = $("#birthdayForm input[name='sendTarget']:checked").val();
    var getLimit = $("#birthdayForm input[name='getLimit']:checked").val();
    //var poolId = $("#birthdayForm input[name='poolId']").val(); //红包池ID
    //var goodsId = $("#birthdayForm input[name='goodsId']").val(); //积分商场商品id
    var points = $("#birthdayForm input[name='points']").val();
    var discount = $("#birthdayForm input[name='discount']").val();
    var secretPassword = $("#birthdayForm input[name='secretPassword']").val();
    var remindShortMessage = $("#birthdayForm input[name='remindShortMessage']:checked").val();
    var shortMessageContent = $("#birthdayForm textarea[name='shortMessageContent']").val();
    var endTime = $("#birthdayForm input[name='endTime']").val();
    //对页面的数据进行校验
    if(title=="" || title==null) {
        alert("生日活动标题不能为空");
        return false;
    }
    if(remindShortMessage==1) {
        if(shortMessageContent=="" || shortMessageContent==null) {
            alert("短信提醒内容不能为空");
            return false;
        }
    }

    formData = {title:title,careType:careType,manualTarget:manualTarget,getLimit:getLimit,poolId:poolId,goodsId:goodsId,
        points:points,discount:discount,secretPassword:secretPassword,remindShortMessage:remindShortMessage,shortMessageContent:shortMessageContent,endTime:endTime,sendTarget:sendTarget};
    $.ajax({
        type : "post",
        url : baseUrl+"/birthday/save/info",
        data : formData,
        dataType : "json",
        success : function(data){
            if (data.errorCode == "-1"){
                alert('保存失败');
            }else{
                alert('保存成功');
            }
        }
    });
});