//搜索
$("#userMessageSearch").click(function () {
    tableModal.draw();
})

//成功购买客户列表
var tableModal = $('#msgTable').DataTable({
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
        "url": baseUrl + "message/get/feedbackList",
        "data": function (d) {
            var content = $("#searchForm input[name='msgContent']").val();
            var start = $("#searchForm input[name='msgStartTime']").val();
            var end = $("#searchForm input[name='msgEndTime']").val();

            d.content = content;
            d.start = start;
            d.end = end;
        }
    },
    "columnDefs": [
         {
            "render": function (data, type, row) {
                if (data == null) {
                    return row.title;
                } else {
                    return data;
                }
            },
            "targets": 0
        }, {
            "render": function (data, type, row) {
                if (data == null) {
                    return '';
                } else {
                    return row.content;
                }
            },
            "targets": 1
        }, {
            "render": function (data, type, row) {
                if (data == null) {
                    return '';
                } else {
                    return format(row.gmtCreated,'yyyy-MM-dd HH:mm:ss');
                }
            },
            "targets": 2
        }
    ],
    "sort": false,
    "columns": [
        {"data": "title"},
        {"data": "content"},
        {"data": "gmtCreated"}
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

