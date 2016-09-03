$("#password1").blur(function() {
if($('#password1').val()!=$('#password').val()){
    alert('两次登录密码不一致');
    $('#password1').val("");
    $('#password').focus();
}
} )


$('#searchForm')[0].reset();

//搜索
$("#SearchBut").click(function () {
    tableModal.draw();
})

//添加用户
$("#add").click(function () {
    $('#userAddModal').modal('show');
    return false;
})

//保存用户
$("#save").click(function () {
    var data = $("#addForm").serialize();

    sendAjax(data);
})

//编辑用户
$("#saveEdit").click(function () {
    var data = $("#editForm").serialize();

    sendAjax(data);
})

//重置密码
$("#reSet").click(function () {
    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null) {
        alert('请先选择一个用户');
        return false;
    }

    if (window.confirm("密码将重置为123456,确定修改吗？")) {
        $.ajax({
            type: "post",
            url: baseUrl + "admin/resetPassword",
            data: {id: id},
            dataType: "json",
            success: function (data) {
                if (data.errorCode == 0) {
                    alert(data.errorMessage);
                }
            },
            error: function () {
                alert("系统异常，请稍后重试！");
            }
        })
    }

    return false;
})

//分配角色
$("#distributeRole").click(function () {
    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null) {
        alert('请先选择一个用户');
        return false;
    }
    $.ajax({
        type: "post",
        url: baseUrl + "admin/userRoles",
        data: {id: id},
        dataType: "json",
        success: function (data) {
            if (data != null && data != undefined) {



                $("#userId").val(data.userId);
                var html = "";
                html += '';

                for (i = 0; i < data.count; i++) {
                    html += '	<div class="checkbox"><label><input name="roleId" class="ace"  type="checkbox" ';
                    if(data.roles[i].checked ){
                        html+='checked="checked"';
                    }

                   html+='value="' + data.roles[i].roleId + '"/><span class="lbl"> ' + data.roles[i].name + '</span>  </label></div>';

                }
            }
            html += '';


            $("#distributeModal .control-group").html(html);

            $("#distributeModal").modal("show");
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })

    return false;
})
//授权
$("#saveEdit1").click(function () {
    var id = "";
    $(".checkbox").each(function () {
        if ($(this).find("input[name=roleId]:checked").val() != undefined) {
            id += $(this).find("input[type=checkbox]:checked").val() + ",";
        }
    });
    var userId = $("#userId").val();

    $.ajax({
        type: "post",
        url: baseUrl + "admin/authorize",
        data: {roleIds: id, userId: userId},
        dataType: "json",
        success: function (data) {
            tableModal.draw();

            $('#distributeModal').modal('hide');
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })


})
$("#edit").click(function () {

    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null) {
        alert('请先选择一个用户');
        return false;
    }

    $.ajax({
        type: "post",
        url: baseUrl + "admin/getUser",
        data: {id: id},
        dataType: "json",
        success: function (data) {
            if (data != null && data != undefined) {
                $("#editForm input[name='id']").val(data.user.id);
                $("#editForm input[name='userName']").val(data.user.userName);
                $("#editForm input[name='name']").val(data.user.name);
                $("#editForm input[name='mobile']").val(data.user.mobile);
                $("#editForm input[name='email']").val(data.user.email);
                $("#editForm input[name='city']").val(data.user.city);
                $("#editForm input[name='district']").val(data.user.district);
                $("#editForm input[name='province']").val(data.user.province);


                $("#editForm input[name='address']").val(data.user.address);
            }

            $('#userEditModal').modal('show');
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })

    return false;
})

function sendAjax(data) {
    $.ajax({
        type: "post",
        url: baseUrl + "admin/editUser",
        dataType: "json",
        data: data,
        success: function (data) {
            if (data.errorCode == 0) {
                alert(data.errorMessage);
                $('#userAddModal').modal('hide');
                $('#userEditModal').modal('hide');
                tableModal.draw();
            } else {
                alert(data.errorMessage);
            }
        },
        error: function () {
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
    "bDestroy": true,
    "processing": true,
    "searching": false,
    "serverSide": true,
    "ajax": {
        "url": baseUrl + "admin/userList",
        "data": function (d) {
            var userName = $("#searchForm input[name='userName']").val();
            var mobile = $("#searchForm input[name='mobile']").val();
            var roleId = $("#searchForm select[name='role'] option:selected").val();

            d.userName = userName;
            d.mobile = mobile;
            d.roleId = roleId;
        }
    },
    "columnDefs": [
        {
            "render": function (data, type, row) {
                return '<td class="center"><label><input name="userBox" type="checkbox"class="ace" value="' + data + '"/><span class="lbl"></span></label></td>';
            },
            "targets": 0
        }, {
            "render": function (data, type, row) {
                if (data == null) {
                    return row.userName;
                } else {
                    return data;
                }
            },
            "targets": 1
        }, {
            "render": function (data, type, row) {
                if (data == null) {
                    return '';
                } else {
                    return data;
                }
            },
            "targets": 2
        }, {
            "render": function (data, type, row) {
                if (data == null) {
                    return '';
                } else {
                    return data;
                }
            },
            "targets": 3
        }, {
            "render": function (data, type, row) {
                if (data == null) {
                    return '';
                } else {
                    return data;
                }
            },
            "targets": 4
        }, {
            "render": function (data, type, row) {
                if (data == null || data == '') {
                    return '无';
                } else {
                    return data;
                }
            },
            "targets": 5
        }, {
            "render": function (data, type, row) {
                if (data == true) {
                    return '可用';
                } else {
                    return '不可用';
                }
            },
            "targets": 6
        }, {
            "render": function (data, type, row) {
                return getLocalTime(data);
            },
            "targets": 7
        }
    ],
    "sort": false,
    "columns": [
        {"data": "id"},
        {"data": "userName"},
        {"data": "name"},
        {"data": "email"},
        {"data": "mobile"},
        {"data": "roleName"},
        {"data": "enabled"},
        {"data": "gmtCreated"}


    ]
});