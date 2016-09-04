//编辑树参数

var setting1 = {

    view: {
        selectedMulti: false
    },
    check: {
        enable: true
    },

    callback: {
        onCheck: onCheck1
    },
    async: {
        type: "get",
        enable: true,
        url:"" ,
        autoParam: ["id=id", "resourceId=resourceId", "name=n", "level=lv"],
        otherParam: {"otherParam": "zTreeAsyncTest"}
    }


};
function onCheck1(e, treeId, treeNode) {
    count1();
    if (clearFlag) {
        clearCheckedOldNodes1();
    }
}
function clearCheckedOldNodes1() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        nodes = zTree.getChangeCheckedNodes();
    for (var i = 0, l = nodes.length; i < l; i++) {
        nodes[i].checkedOld = nodes[i].checked;
    }
}

function count1(name) {
    s = '';
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1");
    var nodes = zTree.getCheckedNodes(true);
    for (i = 0; i < nodes.length; i++) {
        s += nodes[i].resourceId + ",";

    }
    $("#resourceIds1").val(s);


}
//新增树参数

var setting = {

    view: {
        selectedMulti: false
    },
    check: {
        enable: true
    },

    callback: {
        onCheck: onCheck
    },
    async: {
        type: "get",
        enable: true,
        url:"" ,
        autoParam: ["id=id", "resourceId=resourceId", "name=n", "level=lv"],
        otherParam: {"otherParam": "zTreeAsyncTest"}
    }


};
var clearFlag = false;
function onCheck(e, treeId, treeNode) {
    count();
    if (clearFlag) {
        clearCheckedOldNodes();
    }
}
function clearCheckedOldNodes() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        nodes = zTree.getChangeCheckedNodes();
    for (var i = 0, l = nodes.length; i < l; i++) {
        nodes[i].checkedOld = nodes[i].checked;
    }
}


function count(name) {
    s = '';
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = zTree.getCheckedNodes(true);
    for (i = 0; i < nodes.length; i++) {
        s += nodes[i].resourceId + ",";

    }
    $("#resourceIds").val(s);


}


function createTree(name,url) {
    setting.async.url=url;
    $.fn.zTree.init($("#"+name), setting);
    count(name);
    clearFlag = $("#last").attr("checked");
}

function createTree1(name,url) {
    setting1.async.url=url;
    $.fn.zTree.init($("#"+name), setting1);
    count1(name);
    clearFlag = $("#last").attr("checked");
}
$('#searchForm')[0].reset();

//搜索
$("#SearchBut").click(function () {
    tableModal.draw();
})

//添加用户
$("#add").click(function () {


    createTree("treeDemo","admin/roleResourceList");
    $("#init").bind("change", createTree);
    $("#last").bind("change", createTree);
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
//删除
$("#reSet").click(function () {
    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null) {
        alert('请先选择一个角色');
        return false;
    }

    if (window.confirm("确定删除吗？")) {
        $.ajax({
            type: "post",
            url: baseUrl + "admin/delRole",
            data: {id: id},
            dataType: "json",
            success: function (data) {
                if (data.errorCode == 0) {
                    tableModal.draw();

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

$("#edit").click(function () {

    var id = $("input[name='userBox']:checked").val();

    if (id == '' || id == null) {
        alert('请先选择一个角色');
        return false;
    }


    $.ajax({
        type: "post",
        url: baseUrl + "admin/getRole",
        data: {id: id},
        dataType: "json",
        success: function (data) {
            if (data != null && data != undefined) {
                $("#editForm input[name='id']").val(data.role.id);
                $("#editForm input[name='roleId']").val(data.role.roleId);

                $("#editForm input[name='description']").val(data.role.description);
                $("#editForm input[name='name']").val(data.role.name);

            }
            createTree1("treeDemo1","admin/roleResourceList?roleId="+id);
            $("#init").bind("change", createTree1);
            $("#last").bind("change", createTree1);
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
        url: baseUrl + "admin/editRole",
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
        "url": baseUrl + "admin/roleList",
        "data": function (d) {
            var name = $("#searchForm input[name='name']").val();

            d.name = name;
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
                    return '';
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
        }
    ],
    "sort": false,
    "columns": [
        {"data": "roleId"},
        {"data": "name"},
        {"data": "description"},


    ]
});