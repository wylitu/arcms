
$('#searchForm')[0].reset();
//搜索

$("#SearchBut").click(function(){
    tableModal.draw();
})

//添加用户
$("#add").click(function(){

    $('#userAddModal').modal('show');
    return false;
})

//保存用户
$("#save").click(function(){
    var data = $("#addForm").serialize();

    sendAjax(data);
})

//编辑用户
$("#saveEdit").click(function(){
    var data = $("#editForm").serialize();

    sendAjax(data);
})

//删除
$("#reSet").click(function(){
    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null){
        alert('请先选择一个模块');
        return false;
    }

    if (window.confirm("确定删除吗？")){
        $.ajax({
            type : "post",
            url : baseUrl+"admin/delResource",
            data : {id:id},
            dataType : "json",
            success : function(data){
                if (data.errorCode == 0){
                    tableModal.draw();

                    alert(data.errorMessage);
                }
            },
            error : function(){
                alert("系统异常，请稍后重试！");
            }
        })
    }

    return false;
})


function Change() {
    var strvalue = $("#type").val();
    if(strvalue=="menu"){
        $("#url").hide();
    }else{
        $("#url").show();

    }

}

function Change1() {
    var strvalue = $("#type1").val();
    if(strvalue=="menu"){
        $("#url1").hide();
    }else{
        $("#url1").show();

    }

}

$("#edit").click(function(){

    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null){
        alert('请先选择一个模块');
        return false;
    }

    $.ajax({
        type : "post",
        url : baseUrl+"admin/getResource",
        data : {resourceId:id},
        dataType : "json",
        success : function(data){
            if (data != null && data != undefined){
                $("#editForm input[name='resourceId']").val(data.resource.resourceId);
                $("#editForm select[name='type']").val(data.resource.type);

                $("#editForm input[name='parentResourceId']").val(data.resource.parentResourceId);
                $("#editForm input[name='name']").val(data.resource.name);
                $("#editForm input[name='url']").val(data.resource.url);
                $("#editForm input[name='priority']").val(data.resource.priority);
                $("#editForm input[name='description']").val(data.resource.description);
            }
            Change1();

            $('#userEditModal').modal('show');

        },
        error : function(){
            alert("系统异常，请稍后重试！");
        }
    })

    return false;
})

function sendAjax(data){
    $.ajax({
        type : "post",
        url : baseUrl+"admin/editResource",
        dataType : "json",
        data : data,
        success : function(data){
            if (data.errorCode == 0){
                alert(data.errorMessage);
                $('#userAddModal').modal('hide');
                $('#userEditModal').modal('hide');
                tableModal.draw();
            }else{
                alert(data.errorMessage);
            }
        },
        error : function(){
            alert("系统异常，请稍后重试！");
        }
    })
}

//成功购买客户列表

var tableModal = $('#imgTable').DataTable({
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
        "url":  baseUrl+"admin/resourceList",
        "data": function (d) {
            var userName = $("#searchForm input[name='name']").val();
            var id = $("#id").val();

            d.name = userName;
            d.id = id;

        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return '<td class="center"><label><input name="userBox" type="checkbox"class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
            },
            "targets": 0
        },{
            "render": function (data, type, row) {
                if (data == null){
                    return '';
                }else{
                    return data;
                }
            },
            "targets": 1
        },{
            "render": function (data, type, row) {
                if (data == null ){
                    return '';
                }else{
                    return data;
                }


            },
            "targets": 2
        },{
            "render": function (data, type, row) {
                if (data == null ){
                    return '';
                }else{
                    return data;
                }
            },
            "targets": 3
        },{
            "render": function (data, type, row) {
                if (data == null ){
                    return '';
                }else{
                    return data;
                }
            },
            "targets": 4
        },{
            "render": function (data, type, row) {
                if (data == null || data == ''){
                    return '无';
                }else{
                    return data;
                }
            },
            "targets": 5
        },{
            "render": function (data, type, row) {
                if (data == null ){
                    return '';
                }else{
                    return data;
                }
            },
            "targets": 6
        },{
            "render": function (data, type, row) {
                if (data == null ){
                    return '';
                }else{
                    return data;
                }
            },
            "targets": 7
        },{
            "render": function (data, type, row) {
                return getLocalTime(data);
            },
            "targets": 8
        }
    ],
    "sort":false,
    "columns": [
        {"data":"resourceId"},

        {"data": "name"},
        {"data": "type"},

        {"data":"priority"},
        {"data": "url"},
        {"data": "parentResourceId"},
        {"data": "description"},
        {"data": "createdBy"},
        {"data": "gmtCreated"},

    ]
});