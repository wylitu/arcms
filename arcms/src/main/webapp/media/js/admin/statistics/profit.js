//搜索
$("#profitReseach").click(function () {
    profitTabel.draw();
});

//商家获益列表
var profitTabel = $('#profitTable').DataTable({
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
        "url": baseUrl + "/statistics/get/profit",
        "data": function (d) {
            var p1 = $("#profitForm input[name='sellerName']").val();
            var p2= $("#profitForm input[name='sellerCity']").val();

            d.sellerName = p1;
            d.sellerCity = p2;
        }
    },
    "columnDefs": [
        //{
        //    "render": function (data, type, row) {
        //        if (data == null) {
        //            return "";
        //        } else {
        //            return format(row.consumeTime,'yyyy-MM-dd HH:mm:ss');
        //        }
        //    },
        //    "targets": 7
        //}
    ],
    "sort": false,
    "columns": [
        {"data": "userName"},
        {"data": "userCount"},
        {"data": "memberCount"},
        {"data": "cost"},
        {"data": "tradeFinish"},
        {"data": "consumedCard"},
        {"data": "sellerProfit"}
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


