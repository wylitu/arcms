/**
 * Created by lanpeng on 16/3/1.
 */

//console.log("the heart order start !!!!!!");


var statusMap = {
    "1":"新建",
    "10":"已开奖",
    "20":"已发货",
    "30":"已失效",

};

var luckStatusMap = {
    "-1":"尚未开奖",
    "0":"未中奖",
    "1":"中奖"
};

function deliverGoods(heartGoodsOrderId){
    if(window.confirm("确定此订单发货?")){
        $.ajax({
            "type":'post',
            "url":baseUrl+'/heart/order/deliver',
            "data":{
                "id":heartGoodsOrderId
            },
            success:function(resp){
                if(resp.errorCode == 0){
                    $('#imgTable').DataTable().draw();
                }else{
                    alert(resp.errorMessage);
                }
            }
        });
    }
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
        "url": baseUrl + '/heart/order/list/getdata',
        "data": function (d) {
            var goodsName = $("#searchForm :input[name='goodsName']").val();
            var startDate = $("#searchForm :input[name='startDate']").val();
            var endDate = $("#searchForm :input[name='endDate']").val();
            var orderLuckNumber = $("#searchForm :input[name='orderLuckNumber']").val();
            if (goodsName) {
                d.goodsName = goodsName;
            }
            if (orderLuckNumber) {
                d.orderLuckNumber = orderLuckNumber;
            }

            if (startDate)
                d.startDate = startDate;
            {
            }
            if (endDate) {
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
                if (data == null) {
                    return  '';
                } else
                    return getLocalTime(data);
            },
            "targets": 5
        },

        {
            "render": function (data, type, row) {
                if (data) {

                    return  statusMap[data];
                }
                return '未知';
            },
            "targets": 6
        },

        {
            "render": function (data, type, row) {
                if (data) {
                    return  luckStatusMap[data];
                } else
                    return "未知";
            },
            "targets": 8
        },
        {
            "render": function (data, type, row) {
                var operHtml = "";
                if(row.orderStatus == '10'){
                    operHtml += (" <a class='deliverGoods' href='javascript:void(0);' onclick='deliverGoods("+row.id+")'>发货</a>");
                }

                return operHtml;
            },
            "targets":9
        }

    ],
    "fnDrawCallback": function (data) {
    },
    "columns": [
        {"data": "id", "sortable": false},
        {"data": "goodsName", "sortable": false},
        {"data": "userName", "sortable": false},
        {"data": "orderShare", "sortable": false},
        {"data": "orderPrice", "sortable": false},
        {"data": "orderDate", "sortable": false},
        {"data": "orderStatus", "sortable": false},
        {"data": "orderLuckNumber", "sortable": false},
        {"data": "orderIsLuck", "sortable": false},
        {"data": "null", "sortable": false}
    ]
});




$("#searchForm").submit(function () {
    $('#imgTable').DataTable().draw();
    return false;
});



