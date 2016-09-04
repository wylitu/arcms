//搜索
$("#statisticsSearch").click(function () {
    costDetailModal.draw();
});

//成功购买客户列表
var costDetailModal = $('#costDetailTable').DataTable({
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
        "url": baseUrl + "/statistics/get/costDetail",
        "data": function (d) {
            var p1 = $("#statisticsSearchForm input[name='sellerName']").val();
            var p2 = $("#statisticsSearchForm input[name='sellerType']").val();
            var p3 = $("#statisticsSearchForm input[name='sellerCity']").val();
            var p4 = $("#statisticsSearchForm input[name='stStart']").val();
            var p5 = $("#statisticsSearchForm input[name='stEnd']").val();
            var p6 = $("#statisticsSearchForm input[name='recommendName']").val();

            d.sellerName = p1;
            d.sellerType = p2;
            d.sellerCity = p3;
            d.stStart = p4;
            d.stEnd = p5;
            d.recommendName = p6;
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return "";
                } else {
                    return format(row.consumeTime,'yyyy-MM-dd HH:mm:ss');
                }
            },
            "targets": 7
        }
    ],
    "sort": false,
    "columns": [
        {"data": "sellerName"},
        {"data": "title"},
        {"data": "categoryName"},
        {"data": "city"},
        {"data": "commission"},
        {"data": "userName"},
        {"data": "payment"},
        {"data": "consumeTime"},
        {"data": "money"},
        {"data": "sellerMoney"},
        {"data": "icardMoney"},
        {"data": "vipUserMoney"},
        {"data": "toUserMoney"},
        {"data": "fromUserName"}
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
