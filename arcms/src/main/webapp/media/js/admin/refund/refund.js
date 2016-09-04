var statusDict = {
    WAIT_VERIFY : '等待审核',
    WAIT_BUYER_CONSUME : 'REFUND_SUCCESS'}

 var refundList = $('#refundList').dataTable({
     "language": {
         "paginate": {
             "first": "首页",
             "last": "末页",
             "next": "下一页",
             "previous": "上一页"
         },
         "lengthMenu": "每页 _MENU_ 条记录",
         "zeroRecords": "没有数据",
         "info": "共 _TOTAL_ 条记录， 当前第 _PAGE_ 页",
         "sInfoEmpty": "0条记录",
         "sProcessing":   "加载中,请稍后...",
         "infoFiltered": ""
     },
     "pagingType": "full_numbers",
     "processing": true,
     "searching": false,
     "serverSide": true,
     "bInfo":true,
     "bStateSave":true,
     "sInfoEmpty": "0条记录",
     "ajax": {
         "url":  baseUrl + '/refund/list',
         "data": function(d){
             var id = $("#searchRefundForm :input[name='id']").val();
             var status = $("#searchRefundForm :input[name='status']").val();

             if (!isNullOrEmpty(id)) {
                 d.id = id;
             }

             if (!isNullOrEmpty(status)) {
                 d.status = status;
             }
         }
     },
     "columnDefs": [
         /*{
             "render": function (data, type, row) {
                 return statusDict[data]
             },
             "targets":1
         },*/{
             "render": function (data, type, row) {
                 return '￥' + data
             },
             "targets":1
         }
     ],
     "sort": false,
     "columns": [
         {"data":  "id"},
         //{"data": "tid","sorting":false},
         //{"data": "refundStatus","sorting":false},
         {"data": "refundPrice","sorting":false},
         {"data": "gmtCreated","sorting":false},
         {"data": "refundReason","sorting":false},
         {"data": "userName","sorting":false},
         {"data": "sellerName","sorting":false}
     ]
 });

$('#refundSearch').click(function(event){
    event.preventDefault()
    $('#refundList').DataTable().draw();
})
