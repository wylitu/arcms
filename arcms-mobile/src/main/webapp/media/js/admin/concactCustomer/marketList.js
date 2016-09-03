$('#searchForm')[0].reset();
$("#searchForm input[name='type']").click(function(){
    tableModal.draw();
})
//营销活动查询
var tableModal = $('#marketListTable').DataTable({
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
        "url":  baseUrl+"contactCustomer/marketingList",
        "data": function (d) {
            var type = $("#searchForm input[name='type']:checked").val();
            d.type = type;
        }
    },
    "columnDefs": [{
            "render": function (data, type, row) {
                if (data == "sale"){
                    return "销售";
                }else if (data == "increase_fans"){
                    return "增粉";
                }else if (data == "spread"){
                    return "传播";
                }else{
                    return "其他";
                }
            },
            "targets": 1
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return data+"人";
                }

            },
            "targets":2
        },{
            "render": function (data, type, row) {
                return '<label>'+rate(row.feedbackNumber,row.wxPersonCount)+'</label>';
            },
            "targets": 3
        },{
            "render": function (data, type, row) {
                if(data==null || data<new Date().getTime()){
                    return  '已过期';
                }else
                    return getLocalTime(data);

            },
            "targets":4
        },{
            "render": function (data, type, row) {
                if(data==null){
                }else
                    return getLocalTime(data);
            },
            "targets":5
        },{
            "render": function (data, type, row) {
                if (row.executeTime < new Date().getTime()){
                    return '<label><p><a href="javascript:view('+row.activityId+');">查看</a></p></label>';
                }else{
                    if (row.status == "stop"){
                        return '<label><p><a href="javascript:view('+row.activityId+');">查看</a>' +
                            '|<a onclick="edit('+row.activityId+');" href="javascript:void(0)">编辑</a>|<a onclick="operate('+row.activityId+');" href="javascript:void(0)">开始</a></p></label>';
                    }else{
                        return '<label><p><a href="javascript:view('+row.activityId+');">查看</a>|' +
                            '<a onclick="edit('+row.activityId+');" href="javascript:void(0)">编辑</a>|<a onclick="operate('+row.activityId+');" href="javascript:void(0)">暂停</a></p></label>';
                    }
                }
            },
            "targets": 6
        }
    ],
    "sort":false,
    "columns": [
        {"data": "title"},
        {"data": "target"},
        {"data": "wxPersonCount"},
        {"data": null},
        {"data": "executeTime"},
        {"data": "gmtCreated"},
        {"data": null}
    ]
});

//计算百分比
function rate(var1,var2){
    if (var2 == 0){
        return "0";
    }
    var percent = var1*100/var2;
    var rate = percent.toFixed(1);
    return rate+"%";
}

//营销活动编辑
function edit(e){
    $.ajax({
        type : "post",
        url : baseUrl+"contactCustomer/edit",
        data : {activityId:e},
        dataType : "json",
        success : function(data){
            if (data.errorCode !=-1){
                var time = getLocalTime(data.marketResult.executeTime);
                var exeDate = time.replace("上午","");
                $('#editForm input[name="activityId"]').attr('value',data.marketResult.activityId);
                $('#editForm input[name="title"]').val(data.marketResult.title);
                $('#editForm input[name="executeTime"]').val(exeDate);
                $('#editForm input[name="target"]').each(function(){
                    if ($(this).val() == data.marketResult.target){
                        $(this).prop('checked','checked');
                    }
                })
                $('#marketingEditModal').modal('show');
            }else{
                alert(data.errorMessage);
            }
        }
    });
}

//保存营销编辑
$('#saveMarketing').click(function(){
    var datas = $("#editForm").serialize();
    $.ajax({
        async: false,
        type : "post",
        url : baseUrl+"contactCustomer/save",
        data : datas,
        dataType : "json",
        success : function(data){
            if (data.errorCode == 0){
                alert("编辑成功！");
            }else{
                alert("编辑失败！");
            }
        }
    });
    $('#marketingEditModal').modal('hide');
})

//暂停或开启
function operate(val){
    $.post(baseUrl+"contactCustomer/stateSwitch",{activityId:val},function(data){
        if (data.errorCode == "0"){
            alert("操作成功");
            window.location.reload();
        }else{
            alert("操作失败");
        }
    });
}

function view(val){
    freshUrl(baseUrl+'contactCustomer/marketDetail?activityId='+val);
}
