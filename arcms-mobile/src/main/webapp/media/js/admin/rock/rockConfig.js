$("#code").qrcode({
    render: "table", //table方式
    width: 200, //宽度
    height:200, //高度
    text: "www.codesky.net" //任意内容
});


function saveOrUpdateFrom(){

    var data = $('#rockConfigForm').serialize();
    alert(data);
    //var buyIntegralArgs = "";
    //$.each($(".integralBuy"),function(i,value){
    //    buyIntegralArgs = buyIntegralArgs +',' +value.value;
    //});
//{'conertMoney':'"+conertMoney+"','groupIntegral':'"+groupIntegral+"','attentionIntegral':'"+groupIntegral+"','spreadIntegral':'"+spreadIntegral+"','buyIntegralArgs':'"+buyIntegralArgs+"'}
    $.ajax({
        type: "post", // 请求方式
        url:  baseUrl+"rock/saveRockConfig", //url地址
        data: data, //数据
        dataType: "json",
        success: function (data) {
            if(data.errorCode == 0){
                alert("添加成功");

            }else{
                alert(data.errorMessage);
            }
        }, error: function () {
            alert("error");
        }
    });

}


/**
 * 选择积分商城商品
 */
function selPointGoods() {
    $('#pointGoodsSelectedModal').modal('show');

    $.each($(':checkbox[name="goodsId"]'),function(i,value){
        $(this).click(function(){
            if($(this).prop('checked')){
                $(':checkbox[name="goodsId"]').removeAttr('checked');
                $(this).prop('checked','checked');
            }
        });
    });
}


/**
 * 选择带字红包池
 */
function selPool() {
    $('#hbPoolSelectedModal').modal('show');

    $.each($(':checkbox[name="cb"]'),function(i,value){
        $(this).click(function(){
            if($(this).prop('checked')){
                $(':checkbox[name="cb"]').removeAttr('checked');
                $(this).prop('checked','checked');
            }
        });
    });

}


/**
 * 确定操作
 */
$("#poolSure").click(function (event) {
    var checks = $(".table td :input[type='checkbox']:checked");
    if (checks.length == 0) {
        alert("没有选中的红包池,请选择红包池！");
        return;
    }
    var  value= $(':checkbox[name="cb"]:checked').val();
    var array=  value.split(",");

        $('#rockConfigForm input[name="hbPoolName"]').val(array[1]);
        $('#rockConfigForm input[name="hbPoolId"]').val(array[0]);


    $('#hbPoolSelectedModal').modal('hide');
});

function rockConfig(){
    //$("#giftConfig").hide();
    //$("#hbPoll").hide();
    //$("#pointConfig").hide();
    $("#rockDetail").hide();
    $("#show").hide();
    $("#save").hide();
}



$("#next").click(function(){
    $("#rockGroup").hide();
    //$("#giftConfig").hide();
    //$("#pointConfig").hide();
    $("#rockDetail").show();
    //$("#hbPoll").show();
    //
    $("#next").hide();
    $("#show").show();
    $("#save").show();


});

$(".configType").click(function(){
    //var type = $("input[name='type']:checked").val();
    //if (type=='point'){
    //    $("#hbPoll").hide();
    //    $("#giftConfig").hide();
    //    $("#pointConfig").show();
    //}
    //if (type=='hb'){
    //    $("#giftConfig").hide();
    //    $("#pointConfig").hide();
    //    $("#hbPoll").show();
    //}
    //if (type=='gift'){
    //    $("#giftConfig").show();
    //    $("#pointConfig").hide();
    //    $("#hbPoll").hide();
    //}


});
rockConfig();




//function goRockDetailConfig(val){
//    $.ajax({
//        type : "post",
//        url : baseUrl+"knowCustomer/withPortrait",
//        data : {userId:val},
//        dataType : "json",
//        success : function(data){
//            if (data.count == 1){
//                window.open(baseUrl+"knowCustomer/userPortrait?userId="+val);
//            }else{
//                alert("该用户画像不存在");
//                return false;
//            }
//        }
//    });
//}


/**
 * 红包池分页
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
        "url":  baseUrl+'/hbPool/datas',
        "data": function (d) {
            var name = $("#searchForm :input[name='name']").val();
            var startDate = $("#searchForm :input[name='startDate']").val();
            var endDate = $("#searchForm :input[name='endDate']").val();
            d.type = 'yyy';//默认设置类型为好评返红包
            d.status='unBounded'//默认设置红包为未关联
            if (!isNullOrEmpty(name)) {
                d.name = name;
            }
            if (!isNullOrEmpty(startDate)) {
                d.startDate = startDate;
            }
            if (!isNullOrEmpty(endDate)) {
                d.endDate = endDate;
            }
            //  console.dir(d);
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return '<td class="center"><label><input type="checkbox" name="cb" class="ace" value="'+row.id+","+row.name+'"/><span class="lbl"></span></label></td>';
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
                    return "未关联"
                } else if (data == "unReceived"){
                    return "待领取"

                }else if (data == "receiving"){
                    return "领取中"
                } else if (data == "done"){
                    return "结束"
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
                    return "关键词领取"
                } else if (data == "jfdh"){
                    return "积分兑换领取"

                }else if (data == "yyy"){
                    return "摇一摇领取"
                }else if(data="hpf"){
                    return  "好评返领取"
                }else if(data="gw"){
                    return   "购物领取"
                }else  if(data="fxs"){
                    return   "分享领取"
                }else {
                    return  ""
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
        {"data": "id"},
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





/**
 * 商品列表dataTable
 * */
var goodsList = $('#goodsList').dataTable({
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
        "url":  baseUrl + '/mall/goods/list'
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {

                return '<td class="center"><label><input type="checkbox" name="goodsId" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
            },
            "targets": 0
        }, {
            "render": function (data, type, row) {
                var name = row.title;
                var imgUrls = row.imgsUrl;
                var firstImg;
                if(!isNullOrEmpty(imgUrls)){
                    firstImg = imgUrls.split(",")[0]
                }
                return  '<td align="left"><a href="#"><img src="'+baseUrl + firstImg + '" width="60px" height="60px"/>'+ name + '</a></td>';
            },
            "targets":1
        }, {
            "render": function (data, type, row) {
                var price = data/100;
                return "￥"+ price.toFixed(2);
            },
            "targets":2
        },{
            "render": function (data, type, row) {

                return data;
            },
            "targets":3
        },{
            "render": function (data, type, row) {
                return data;
            },
            "targets":4
        },{
            "render": function (data, type, row) {
                return data;

            },
            "targets":5
        },{
            "render": function (data, type, row) {
                if(data==null){
                    return  '';
                }else
                    return getLocalTime(data);
            },
            "targets":6
        },{
            "render": function (data, type, row) {
                if(data == 'onsale'){
                    return '已上架';
                }else if(data == 'instock'){
                    return '已下架';
                }
                return '未知';
            },
            "targets":7
        },{
            "render": function (data, type, row) {
                var content = '<a href="#" onclick=freshUrl("' + baseUrl + '/mall/order/detail?tid=' + row.tid + '")>查看</a>';

                return content;
            },
            "targets": 8
        }
    ],
    "sort":false,
    "columns": [
        {"data": "id"},
        {"data": null},
        {"data": "price"},
        {"data": "quantity"},
        {"data": "exchangeAmount"},
        {"data": "point"},
        {"data": "listTime"},
        {"data": "status"},
        {"data": null},
    ]
});