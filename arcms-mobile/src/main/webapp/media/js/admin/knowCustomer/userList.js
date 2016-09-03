$('#searchForm')[0].reset();
$('#SearchBut').click(function(){
    tableModal.draw();
})

//营销活动查询
var tableModal = $('#userListTable').DataTable({
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
        "url":  baseUrl+"knowCustomer/customerList",
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
    "columnDefs": [
        {
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return '<label><a href="javascript:void(0);" onclick="goPortriat('+row.userId+')">'+data+'</a></label>';
                }
            },
            "targets": 0
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return "￥ "+(data/100).toFixed(2)+"元";
                }
            },
            "targets": 4
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return getLocalTime(data);
                }
            },
            "targets": 6
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
        {"data": "name"},
        {"data": "mobile"},
        {"data": "province"},
        {"data": "purchaseAverageCycle"},
        {"data": "purchaseAverageAmount"},
        {"data": "totalPurchaseTimes"},
        {"data": "lastPurchaseDate"},
        {"data": "firstSource"},
        {"data": "contribution"},
        {"data": "jdPin"},
        {"data": "tbBuyerNick"}
    ]
});

//转到用户画像页面
function goPortriat(val){
    $.ajax({
        type : "post",
        url : baseUrl+"knowCustomer/withPortrait",
        data : {userId:val},
        dataType : "json",
        success : function(data){
            if (data.count == 1){
                //freshUrl('/sf-web/knowCustomer/userPortrait?userId='+val);
                //window.open(baseUrl+"knowCustomer/userPortrait?userId="+val);
                freshUrl(baseUrl+"knowCustomer/userPortrait?userId="+val);
            }else{
                alert("该用户画像不存在");
                return false;
            }
        }
    });
}

//单页全选
$('table th input:checkbox').on('click' , function(){
    var that = this;
    $(this).closest('table').find('tr > td:first-child input:checkbox')
        .each(function(){
            this.checked = that.checked;
            $(this).closest('tr').toggleClass('selected');
        });
});

//已选用户数量
//var htm = '<span class="col-sm-10">已选择<b class="userCount">&nbsp;0&nbsp;</b>条记录</span>';
//$("#userListTable_length").append(htm);

//点击创建营销
$('#createMarketing').click(function(){
    freshUrl('/sf-web/contactCustomer/newMarket');
})