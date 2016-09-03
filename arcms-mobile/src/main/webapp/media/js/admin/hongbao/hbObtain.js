var oTable = $('#hbObtainTable').dataTable({
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
        "url":  baseUrl+'/hbObtain/datas',
        "data": function (d) {
            var poolId = $("#searchForm :input[name='poolId']").val();
            var wxNickName = $("#searchForm :input[name='wxNickName']").val();
            var startDate = $("#searchForm :input[name='startDate']").val();
            var endDate = $("#searchForm :input[name='endDate']").val();
            var status = $("#searchForm :input[name='status']").val();
            if (!isNullOrEmpty(poolId)) {
                d.poolId = poolId;
            }
            if (!isNullOrEmpty(wxNickName)) {
                d.wxNickName = wxNickName;
            }
            if (!isNullOrEmpty(startDate)) {
                d.startDate = startDate;
            }
            if (!isNullOrEmpty(endDate)) {
                d.endDate = endDate;
            }
            if (!isNullOrEmpty(status)) {
                d.status = status;
            }

        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return '<td class="center"><label><input type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
            },
            "targets": 0
        }, {
            "render": function (data, type, row) {
                if(data == "0"){
                    return "男"
                } else{
                    return "女"

                }
            },
            "targets":3
        },{
            "render": function (data, type, row) {
                if(data == "0"){
                    return "否"
                } else if(data=="1"){
                    return "是"
                }else
                    return  '';
            },
            "targets":7
        },{
            "render": function (data, type, row) {
                if(data == "0"){
                    return "否"
                } else if(data=="1"){
                    return "是"
                }else
                    return '';
            },
            "targets":8
        },{
            "render": function (data, type, row) {
                if(data==null){
                    return  '';
                }else
                    return getLocalTime(data);
            },
            "targets":9
        }
    ],
    "sort":false,
    "columns": [
        {"data": "id"},
        {"data": "poolName"},
        {"data": "wxNickName"},
        {"data": "sex"},
        {"data": "amount"},
        {"data": "interactionsTimes"},
        {"data": "mobile"},
        {"data": "isOldUser"},
        {"data": "isVerify"},
        {"data": "obtainDate"}
    ]
});

$("#searchForm").submit(function () {
    $('#hbObtainTable').DataTable().draw()
    return false; //阻止表单默认提交
});


//请求红包池查询
function hbPoolList(){
   // this.location.href=baseUrl+"hbPool/list";
    freshUrl(baseUrl+"hbPool/list");
}