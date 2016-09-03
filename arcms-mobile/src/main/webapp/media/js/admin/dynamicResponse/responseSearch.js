$('#searchForm')[0].reset();
$('#searchBut').click(function(){
    tableModal.draw();
})
var tableModal = $('#responseSearchTable').DataTable({
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
        "url":  baseUrl+"dynamicResponse/responseList",
        "data": function (d) {
            var bizType = $("#searchForm input[name='bizType'] option:selected").val();
            var title = $("#searchForm input[name='title']").val();
            d.bizType = bizType;
            d.title = title;
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }
                return getLocalTime(data);
            },
            "targets": 5
        },{
            "render": function (data, type, row) {
                return '<label><p><a href="javascript:view('+row.id+');">查看</a>|' +
                    '<a  href="javascript:edit('+row.id+');">编辑</a>|<a href="javascript:delet('+row.id+')">删除</a></p></label>';
            },
            "targets": 7
        }
    ],
    "sort":false,
    "columns": [
        {"data": "title"},
        {"data": "bizType"},
        {"data": "keyWord"},
        {"data": "matchType"},
        {"data": "successReplyContent"},
        {"data": "gmtModified"},
        {"data": "failReplyContent"},
        {"data": null}
    ]
});

//查看

//编辑
function edit(){

}

//删除
function delet(){

}