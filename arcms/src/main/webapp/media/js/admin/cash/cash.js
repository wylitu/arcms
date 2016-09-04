


/**
 * 分页
 * @type {*|jQuery}
 */
var oTable = $('#cashTable').dataTable({
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
    "bInfo":true,
    "bStateSave":true,
    "sInfoEmpty": "0条记录",
    "ajax": {
        "url": baseUrl + '/cash/datas',
        "data": function (d) {
            var sellerName = $("#searchForm :input[name='sellerName']").val();
            var status = $("#searchForm :input[name='status']").val();
            var startDate = $("#searchForm :input[name='startDate']").val();
            var endDate = $("#searchForm :input[name='endDate']").val();
            if (!isNullOrEmpty(sellerName)) {
                d.sellerName = sellerName;
            }
            if (!isNullOrEmpty(status)) {
                d.status = status;
            }
            if (!isNullOrEmpty(startDate))
                d.startDate = startDate;
            {
            }
            if (!isNullOrEmpty(endDate)) {
                d.endDate = endDate;
            }
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return '<th ><label><input type="checkbox" class="ace" value="' + data + '"/><span class="lbl"></span></label></th>';
            },
            "targets": 0
        },
        {
            "render": function (data, type, row) {
                if (data == "WAIT_CASH") {
                    return "待提现"
                } else if (data == "CASHED") {
                    return "已提现"
                }
            },
            "targets": 3
        },
        {
            "render": function (data, type, row) {
                if (data == null) {
                    return  '';
                } else
                    return getLocalTime(data);
            },
            "targets": 4
        }
    ],
    "columns": [
        {"data": "id", "sortable": false},
        {"data": "icardMoney", "sortable": false},
        {"data": "sellerName", "sortable": false},
        {"data": "status", "sortable": false},
        {"data": "gmtCreated","sortable": false},
    ]
});



/**
 * 全选和反选
 */
function selectAll() {
    if ($('#allCheckbox:checked').val() == 'true') {
        $(".table td :input[type='checkbox']").prop("checked", "checked");
    } else {
        $(".table td :input[type='checkbox']").prop("checked", false);
    }
}

/**
 * 设置状态
 */
function  setStateForm(state){
    event.preventDefault();
    var checks = $(".table td :input[type='checkbox']:checked");
    if (checks.length == 0) {
        alert("没有选中的商家,请选择商家！");
        return;
    }
    var saveDataAry=[]

    $.each(checks, function (index, check) {
        var data={'id':$(check).val(),'state':state};
        saveDataAry.push(data);
    })

    var  saveStr=JSON.stringify(saveDataAry).replace(/\"/g,"'");
    var imgId = [];

    $.ajaxFileUpload({
        url : baseUrl + '/cash/setState',
        secureuri:false,
        fileElementId: imgId,  //这里不在是以前的id了，要写成数组的形式哦！
        dataType: 'json',
        data: {
            StateData:saveStr
        },
        success: function (){
            $('#cashTable').DataTable().draw();
            alert("设置状态成功!");
        },
        error: function(data){
            alert("保存失败!");
        }
    });

}

$('#cashSearch').click(function(event){
    $('#cashTable').DataTable().draw();
    event.preventDefault()
})