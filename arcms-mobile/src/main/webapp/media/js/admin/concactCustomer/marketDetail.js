//短信营销明细
var smstableModal = $('#smsDetailTable').DataTable({
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
        "url":  baseUrl+"contactCustomer/searchActivity",
        "data": function (d) {
            var activityId = $("#searchForm input[name='activityId']").val();
            var type = $("#searchForm input[name='type']:checked").val();
            var activityTitle = $("#searchForm input[name='activityTitle']").val();
            var startTime = $("#searchForm input[name='startTime']").val();
            var endTime = $("#searchForm input[name='endTime']").val();
            var isSuccess = $("#searchForm option:selected").val();

            d.activityId = activityId;
            d.type = type;
            d.activityTitle = activityTitle;
            d.startTime = startTime;
            d.endTime = endTime;
            d.isSuccess = isSuccess;
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                if (data == "1"){
                    return '是';
                }else {
                    return '否';
                }
            },
            "targets": 4
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else if (data == "TB"){
                    return '淘宝';
                }else if (data == 'JD'){
                    return '京东';
                }
            },
            "targets": 7
        }
    ],
    "sort":false,
    "columns": [
        {"data": "activityTitle"},
        {"data": "name"},
        {"data": "mobile"},
        {"data": "wxNickName"},
        {"data": "isSuccess"},
        {"data": "smsAcceptNumber"},
        {"data": "smsAcceptCircle"},
        {"data": "firstSource"},
        {"data": "contribution"}
    ]
});

//邮件营销明细
var emailtableModal = $('#emailDetailTable').DataTable({
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
        "url":  baseUrl+"contactCustomer/searchActivity",
        "data": function (d) {
            var activityId = $("#searchForm input[name='activityId']").val();
            var type = $("#searchForm input[name='type']:checked").val();
            var activityTitle = $("#searchForm input[name='activityTitle']").val();
            var startTime = $("#searchForm input[name='startTime']").val();
            var endTime = $("#searchForm input[name='endTime']").val();
            var isSuccess = $("#searchForm option:selected").val();

            d.activityId = activityId;
            d.type = type;
            d.activityTitle = activityTitle;
            d.startTime = startTime;
            d.endTime = endTime;
            d.isSuccess = isSuccess;
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                if (data == "1"){
                    return '是';
                }else {
                    return '否';
                }
            },
            "targets": 4
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else if (data == "TB"){
                    return '淘宝';
                }else if (data == 'JD'){
                    return '京东';
                }
            },
            "targets": 8
        }
    ],
    "sort":false,
    "columns": [
        {"data": "activityTitle"},
        {"data": "name"},
        {"data": "email"},
        {"data": "wxNickName"},
        {"data": "isSuccess"},
        {"data": "isOpen"},
        {"data": "smsAcceptNumber"},
        {"data": "smsAcceptCircle"},
        {"data": "firstSource"},
        {"data": "contribution"}
    ]
});
//搜索按钮
$("#SearchBut").click(function(){
    var type = $("#searchForm input[name='type']:checked").val();
    if (type == null){
        alert("请选择活动类型！");
    }
    if (type == "sms"){
        smstableModal.draw();
    }else if (type == "email"){
        emailtableModal.draw();
    }
})
//div切换
$("#searchForm input[name='type']").click(function(){
    var selectedVal = $(this).val();
    if (selectedVal == "sms"){
        smstableModal.draw();
        $(".smsMarketTable").show();
        $(".emailMarketTable").hide();
    }else if (selectedVal == "email"){
        emailtableModal.draw();
        $(".smsMarketTable").hide();
        $(".emailMarketTable").show();
    }
})
//重置按钮
$("#resetBut").click(function(){
    $('#searchForm input[name="activityId"]').attr('value','');
    $('#searchForm')[0].reset();
})
