//搜索
$("#memberRechargeSearch").click(function () {
    rechargeListModal.draw();
});

var retEnum = {
    "payType":{"0":"其他方式0","1":"爱卡币充值微信","2":"会员微信充值","3":"爱卡币会员消费","4":"订单支付微信","5":"爱卡币充值支付宝","6":"会员充值支付宝","7":"订单支付支付宝","8":"支付宝批量付款","9":"orderPayOff","10":"其他方式10"},
    "payStatus":{"0":"流转中","1":"成功","2":"失败"}
};

var queryEnum = {
    "payStatus":{"初始化":"0","支付成功":"1"}
};

//成功购买客户列表
var rechargeListModal = $('#rechargeListTable').DataTable({
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
    "bDestroy": true,
    "processing": true,
    "searching": false,
    "serverSide": true,
    "ajax": {
        "url": baseUrl + "/member/query/rechargelist",
        "data": function (d) {
            var p1 = $("#memberRechargeForm input[name='rechargeFlow']").val();
            var p2 = $("#memberRechargeForm input[name='status']").val();
            var p3 = $("#memberRechargeForm input[name='userName']").val();
            var p4 = $("#memberRechargeForm input[name='startTime']").val();
            var p5 = $("#memberRechargeForm input[name='endTime']").val();
            var p6 =  $("#memberRechargeForm select[name='payType'] option:selected").val();

            d.rechargeFlow = p1;
            d.status = queryEnum.payStatus[p2];
            d.userName = p3;
            d.startDate = p4;
            d.endDate = p5;
            d.payType = p6;
        }
    },
    "columnDefs": [

        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    if (row.payType == '1' || row.payType=='5'){
                        return data;
                    }
                    return "-";

                }
            },
            "targets": 3
        },
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return format(row.gmtCreated,'yyyy-MM-dd HH:mm:ss');
                }
            },
            "targets": 6
        },
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return retEnum.payStatus[row.payStatus];
                }
            },
            "targets": 7
        }
    ],
    "sort": false,
    "columns": [
        {"data": "payCode"},
        {"data": "userId"},
        {"data": "userName"},
        {"data": "objectId"},
        {"data": "payAmount"},
        {"data": "payTypeName"},
        {"data": "gmtCreated"},
        {"data": "payStatus"}
    ]
});

var format = function(time, format){
    var t = new Date(time);
    var tf = function(i){return (i < 10 ? '0' : '') + i};
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}



/********************************************************************/

$("#memberFeeSearch").click(function () {
    memberFreeModal.draw();
});

//成功购买客户列表
var memberFreeModal = $('#memberFeeTable').DataTable({
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
    "bDestroy": true,
    "processing": true,
    "searching": false,
    "serverSide": true,
    "ajax": {
        "url": baseUrl + "/member/query/payList",
        "data": function (d) {
            var p1 = $("#memberFeeForm input[name='rechargeFlow']").val();
            var p2 = $("#memberFeeForm input[name='status']").val();
            var p3 = $("#memberFeeForm input[name='userName']").val();
            var p4 = $("#memberFeeForm input[name='startTime']").val();
            var p5 = $("#memberFeeForm input[name='endTime']").val();

            d.rechargeFlow = p1;
            d.status = queryEnum.payStatus[p2];
            d.userName = p3;
            d.startTime = p4;
            d.endTime = p5;
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return format(row.gmtCreated,'yyyy-MM-dd HH:mm:ss');
                }
            },
            "targets": 4
        },
         {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return format(row.gmtCreated,'yyyy-MM-dd HH:mm:ss');
                }
            },
            "targets": 5
        },
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return retEnum.payType[row.payType];
                }
            },
            "targets": 2
        },
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return retEnum.payStatus[row.payStatus];
                }
            },
            "targets": 6
        }
    ],
    "sort": false,
    "columns": [
        {"data": "payCode"},
        {"data": "userName"},
        {"data": "payType"},
        {"data": "payAmount"},
        {"data": "gmtCreated"},
        {"data": "gmtCreated"},
        {"data": "payStatus"}
    ]
});

