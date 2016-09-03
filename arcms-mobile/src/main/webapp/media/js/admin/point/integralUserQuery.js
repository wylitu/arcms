
   var oTable = $('#pointUserQueryTable').dataTable({

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
            "url": baseUrl + '/integral/integralUserQueryDetailList',
            "data": function (d) {
                var wxNickName = $("#pointUserQueryForm :input[name='wxNickName']").val();

                var mobile = $("#pointUserQueryForm :input[name='mobile']").val();

                var queryType = $("#pointUserQueryForm :input[name='queryType']").val();

                if (!isNullOrEmpty(mobile)) {
                    d.mobile = mobile;
                }

                if (!isNullOrEmpty(wxNickName)) {
                    d.wxNickName = wxNickName;
                }

                if (!isNullOrEmpty(queryType)) {
                    d.queryType = queryType;
                }

            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="openId" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            },
            {
                "render": function (data, type, row) {

                    var fromSource = "";
                    if ("TB"==data){
                        fromSource = "淘宝";
                    }
                    if ("JD"==data){
                        fromSource = "京东";
                    }

                    return fromSource;
                },
                "targets": 4
            },
            {
                "render": function (data, type, row) {

                    return data;

                },
                "targets": 5
            }, {
                "render": function (data, type, row) {
                    return data;
                },
                "targets": 6
            }, {
                "render": function (data, type, row) {
                    return data;
                },
                "targets": 7
            },
//                    {"visible": false, "targets": [3]}
        ],
        "sort": false,
        "columns": [
            {"data": "wxOpenid"},
            {"data": "wxNickName"},
            {"data": "receiveContent"},
            {"data": "times"},
            {"data": "firstSource"},
            {"data": "createTime"},
            {"data": "replyTime"},
            {"data": "replyContent"},
        ]
    });



$("#pointUserQueryForm").submit(function () {
    $('#pointUserQueryTable').DataTable().draw()
    return false; //阻止表单默认提交
});

$("#replyButton").click(function (event){
    event.preventDefault()
    if (confirm("确定要发送吗")) {
        var checks = $(".table td :input[type='checkbox']:checked");

        if (checks.length == 0) {
            alert("没有选中的红包池,请选择红包池！");
            return;
        }
        var ids = [];
        $.each(checks, function (index, check) {

            ids.push($(check).val());
        });

        var content = $("#pointReplyForm :input[name='content']").val();

        $.ajax({
            type : "post",
            url : baseUrl+"integral/sendMessageToUser",
            data : {openId:ids.toString(),content:content},
            dataType : "json",
            success : function(data){
                if (data.errorCode == '0'){
                    alert("发送成功！");
                }else{
                    alert("发送失败！");
                    return false;
                }
            }
        });
    }
});







