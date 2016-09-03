$('#searchForm')[0].reset();
$('#SearchBut').click(function(){
    tableModal.draw();
})

//营销活动查询
var tableModal = $('#validateDetailTable').DataTable({
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
        "url":  baseUrl+"userValidate/showValidate",
        "data": function (d) {
            var name = $("#searchForm input[name='name']").val();
            var firstSource = $("#searchForm select[name='firstSource'] option:selected").val();
            var keyword = $("#searchForm input[name='keyword']").val();
            var time = $("#searchForm select[name='time'] option:selected").val();

            d.name = name;
            d.firstSource = firstSource;
            d.keyword = keyword;
            d.time = time;
        }
    },
    "columnDefs": [{
            "render": function (data, type, row) {
                if (data == "1"){
                    return "成功";
                }else{
                    return "失败";
                }
            },
            "targets": 2
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return getLocalTime(data);
                }
            },
            "targets": 3
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return "无";
                }else{
                    return "有";
                }
            },
            "targets": 4
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return '<label><a href="'+baseUrl+'knowCustomer/userPortrait?userId='+row.userId+'">查看</a></label>';
                }
            },
            "targets": 7
        }
    ],
    "sort":false,
    "columns": [
        {"data": "wxNickName"},
        {"data": "mobile"},
        {"data": "result"},
        {"data": "validateDate"},
        {"data": "userPortraitId"},
        {"data": "name"},
        {"data": "totalPurchaseTimes"},
        {"data": "null"}
    ]
});