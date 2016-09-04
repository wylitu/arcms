/**
 * Created by lanpeng on 16/3/1.
 */

//console.log("the heart goods start !!!!!!");
var heartGoodsStatus = {
    "-1":"未知",
    "1":"待上架",
    "10":"进行中",
    "20":"待开奖",
    "30":"收集失败",
    "40":"已下架",
    "50":"已删除",
    "60":"已开奖"

};


function onSaleHeartGoods(id){
    //console.log("上架 id= " + id);
    if(window.confirm("确定要上架?")){
        $.ajax({
            "type":'post',
            "url":baseUrl+'/heart/goods/onSale',
            "data":{
                "id":id
            },
            success:function(resp){
                if(resp.errorCode == 0){
                    $('#imgTable').DataTable().draw();
                }else{
                    alert(resp.errorMessage);
                }
            }
        })
    }
}

function offSaleGoods(id){
    if(window.confirm("确定要下架?")){
        $.ajax({
            "type":'post',
            "url":baseUrl+'/heart/goods/offSale',
            "data":{
                "id":id
            },
            success:function(resp){
                if(resp.errorCode == 0){
                    $('#imgTable').DataTable().draw();
                }else{
                    alert(resp.errorMessage);
                }
            }
        })
    }
}



var currObj = null;
function modifyHeartGoodsUI(rowObj){
    currObj = rowObj;
}
function modifyHeartGoods(id){
    //console.log("修改 id= " + id);
}

function delHeartGoods(id){
    //console.log("删除 id= " + id);
    if(window.confirm("确定要删除?")){
        $.ajax({
            "type":'post',
            "url":baseUrl+'/heart/goods/delete',
            "data":{
                "id":id
            },
            success:function(resp){
                if(resp.errorCode == 0){
                    $('#imgTable').DataTable().draw();
                }else{
                    alert(resp.errorMessage);
                }
            }
        })
    }
}


function openPrize(heartGoodsId){
    $.ajax({
        "type":'post',
        "url":baseUrl+'/heart/goods/openPrize',
        "data":{
            "id":heartGoodsId
        },
        success:function(resp){
            if(resp.errorCode == 0){
                $('#imgTable').DataTable().draw();
            }else{
                alert(resp.errorMessage);
            }
        }
    })
}

var MyValidator = function () {
    var handleSubmit = function () {
        $.each($('.form-horizontal'), function (i, value) {
            $('.form-horizontal').eq(i).validate({
                errorElement: 'span',
                errorClass: 'help-block',
                focusInvalid: false,
                rules: {
                    goodsPeriod:{
                        required: true
                    },
                    goodsName: {
                        required: true
                    },
                    goodsImg: {
                        required: true
                    },
                    goodsDetailImg: {
                        required: true
                    },
                    goodsShare:{
                        digits:true,
                        min:1
                    },
                    goodsPrize:{
                        required: true,
                        digits:true,
                        min:1
                    },
                    goodsMinPrize:{
                        required: true,
                        digits:true,
                        min:1
                    }/*,
                    goodsStartDate:{
                        required: true,
                    },
                    goodsEndDate:{
                        required: true,
                    }*/
                },
                messages: {
                    goodsPeriod:{
                        required: "必填!"
                    },
                    goodsName: {
                        required: "必填!"
                    },
                    goodsImg: {
                        required: "必填!"
                    },
                    goodsDetailImg: {
                        required: "必填!"
                    },
                    goodsShare: {
                        required: "必填!",
                        digits:"必须是整数",
                        min:"输入值需大于等于1"
                    },
                    goodsMinShare: {
                        required: "必填!",
                        digits:"必须是整数",
                        min:"输入值需大于等于1"
                    },
                    goodsMinPrize: {
                        required: "必填!",
                        digits:"必须是整数",
                        min:"输入值需大于等于1"
                    }/*,
                    goodsStartDate:{
                        required: "必填!",
                    },
                    goodsEndDate:{
                        required: "必填!",
                    }*/
                },

                highlight: function (element) {
                    $(element).closest('.form-group').addClass('has-error');
                },

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    element.parent('div').append(error);
                },

                submitHandler: function (form) {
                    if ("addForm" == form.id) {
                        addForm()//增加红包池
                    } else
                        updateForm();//更新红包池
                }
            });

            $('.form-horizontal input').keypress(function (e) {
                if (e.which == 13) {
                    if ($('.form-horizontal').validate().form()) {
                        $('.form-horizontal').submit();
                    }
                    return false;
                }
            });
        });
    }
    return {
        init: function () {
            handleSubmit();
        }
    };
}();

MyValidator.init();


// 新增宝贝,模态框弹出
function addHeartGoodsUI(){
    $('#addForm')[0].reset();
    $('#addHeartGoodsModal').modal('show');
}

function addForm(){
  //处理数据
    var addFormObj = $('#addForm');
    var heartGoods = {
        "goodsPeriod":addFormObj.find("input[name=goodsPeriod]").val(),
        "goodsName":addFormObj.find("input[name=goodsName]").val(),
        "goodsDesc":addFormObj.find("textarea[name='goodsDesc']").val(),
        "goodsShare":addFormObj.find("input[name='goodsShare']").val(),
        "goodsMinPrize":addFormObj.find("input[name='goodsMinPrize']").val(),
        "goodsMinShare":addFormObj.find("input[name='goodsMinShare']").val(),
        "goodsMinPrizeUnit":addFormObj.find("input[name='goodsMinPrizeUnit']").val(),
        //"goodsStartDate":addFormObj.find("input[name='goodsStartDate']").val(),
        //"goodsEndDate":addFormObj.find("input[name='goodsEndDate']").val()
        "goodsStartDate":new Date(),
        "goodsEndDate":"2099-12-31 23:59:59"
    };

    var files = [];
    $.each($('input[type="file"]'),function(i,value){
        files.push($(':input[type="file"]').eq(i).attr('id'));
    });
    //
    var  postData = JSON.stringify(heartGoods).replace(/\"/g,"'");

    $.ajaxFileUpload({
        url : baseUrl + '/heart/goods/add',
        secureuri:false,
        fileElementId: files,
        dataType: 'json',
        data: {"heartGoods":postData},
        success: function(reponseData){
            if(reponseData.errorCode == 0){
                //alert(reponseData.info);
                $('#imgTable').DataTable().draw();
                $('#addForm')[0].reset();
                $('#addHeartGoodsModal').modal('hide');
            }else{
                alert(reponseData.errorMessage);
            }
        },
        error: function(reponseData){
            alert("保存失败!");
        }
    });
}


// 分页
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
        "url": baseUrl + '/heart/goods/list/getdata',
        "data": function (d) {
            var goodsName = $("#searchForm :input[name='goodsName']").val();
            var goodsStartDate = $("#searchForm :input[name='goodsStartDate']").val();
            var goodsEndDate = $("#searchForm :input[name='goodsEndDate']").val();
            if (goodsName) {
                d.goodsName = goodsName;
            }

            if (goodsStartDate)
                d.goodsStartDate = goodsStartDate;
            {
            }
            if (goodsEndDate) {
                d.goodsEndDate = goodsEndDate;
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
                if (data == null) {
                    return  '';
                } else
                    return getLocalTime(data);
            },
            "targets": 2
        },
        {
            "render": function (data, type, row) {

                if(!data)
                    return heartGoodsStatus["-1"];
                return heartGoodsStatus[data];
            },
            "targets": 3
        },
        //{
        //    "render": function (data, type, row) {
        //        if (data == null) {
        //            return  '';
        //        } else
        //            return getLocalTime(data);
        //    },
        //    "targets": 4
        //},
        //{
        //    "render": function (data, type, row) {
        //        if (data == null) {
        //            return  '';
        //        } else
        //            return getLocalTime(data);
        //    },
        //    "targets": 5
        //},
        {
            "render": function (data, type, row) {
                if(data==null){
                    return "0";

                }else{
                    return data;
                }
            },
            "targets": 5
        },
        {
            "render": function (data, type, row) {
               var operHtml = "";
                if(row){
                    if(row.goodsStatus == 1){
                        //上架
                        operHtml += " <a class='onSaleHeartGoods' href='javascript:void(0);' onclick='onSaleHeartGoods("+row.id+")'>上架</a>";
                        //operHtml += " <a class='modifyHeartGoods' href='javascript:void(0);' onclick='modifyHeartGoodsUI(row)'>修改</a>";
                    }else if(row.goodsStatus == 10){
                        operHtml += " <a class='offSaleGoods' href='javascript:void(0);' onclick='offSaleGoods("+row.id+")'>下架</a>";
                    }
                }
                operHtml += " <a class='delHeartGoods' href='javascript:void(0);' onclick='delHeartGoods("+row.id+")'>删除</a>";
                return operHtml;
            },
            "targets":7
        },

    ],
    "fnDrawCallback": function (data) {
    },
    "columns": [
        {"data": "id", "sortable": false},
        {"data": "goodsName", "sortable": false},
        {"data": "gmtCreated", "sortable": false},
        {"data": "goodsStatus", "sortable": false},
        //{"data": "goodsStartDate", "sortable": false},
        //{"data": "goodsEndDate", "sortable": false},
        {"data": "goodsShare", "sortable": false},
        {"data": "goodsCollectShare", "sortable": false},
        {"data": "goodsPrizeLuckNumber", "sortable": false},
        {"data": "null", "sortable": false}
    ]
});




$("#searchForm").submit(function () {
    $('#imgTable').DataTable().draw();
    return false;
});



