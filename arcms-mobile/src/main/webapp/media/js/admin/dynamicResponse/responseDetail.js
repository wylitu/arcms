$('#searchForm')[0].reset();
$('#searchBut').click(function(){
    tableModal.draw();
})
var tableModal = $('#responseDetailTable').DataTable({
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
    "bDestroy":true,
    "processing": true,
    "searching": false,
    "serverSide": true,
    "ajax": {
        "url":  baseUrl+"dynamicResponse/sceneReplySearch",
        "data": function (d) {
            var bizType = $("#searchForm input[name='bizType'] option:selected").val();
            var wxNickName = $("#searchForm input[name='wxNickName']").val();
            d.bizType = bizType;
            d.wxNickName = wxNickName;
        }
    },
    "sort":false,
    "columns": [
        {"data": "bizType"},
        {"data": "receiveContent"},
        {"data": "msgType"},
        {"data": "wxNickName"},
        {"data": "replyContent"}
    ]
});