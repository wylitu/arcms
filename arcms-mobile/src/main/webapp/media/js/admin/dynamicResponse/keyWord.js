//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue_success = UE.getEditor('successEditor',{
    toolbars: [
        [
            'source', //源代码
            'horizontal', //分隔线
            'link', //超链接
            'spechars', //特殊字符
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter' //居中对齐
        ]
    ]
});

//创建表单token，防止表单重复提交
createToken('addForm');

//页面div布局切换
$("#addForm input[name='bizType']").click(function(){
    $("#addForm input[name='title']").val("");
    $("#addForm input[name='keyWord']").val("");
    var boxVal = $(this).val();
    if (boxVal == "point_exchange" || boxVal == "express_query"){
        $(".successContent").show();
        $("#selectPool").hide();
        $("#successEdit").show();
        $("#signEdit").hide();
        $(".keywordContent").show();
        $(".introductory").hide();
    }else if (boxVal == "introduce_word"){
        $(".introductory").show();
        $(".keywordContent").hide();
        $(".successContent").hide();
        $.post(baseUrl+"dynamicResponse/getIntroduce",function(data){
            if (data != null){
                $("#addForm textarea[name='introduceWord']").val(data.introduceWord);
                $("#addForm input[name='title']").val(data.title);
            }else{
                alert("引导语内容为空");
            }
        },"json");
    }else if (boxVal == "sign_sendintegral"){
        $(".introductory").hide();
        $(".successContent").show();
        $("#selectPool").hide();
        $("#successEdit").hide();
        $("#signEdit").show();
        $(".keywordContent").show();
        $.post(baseUrl+"dynamicResponse/getSign",function(data){
            if (data != null){
                $("#signWord ").val(data.signWord);
                $("#addForm input[name='title']").val(data.title);
                $("#addForm input[name='keyWord']").val(data.keyword);
            }else{
                alert("签到送积分内容为空");
            }
        },"json");
    }else{
        $(".successContent").show();
        $("#selectPool").show();
        $("#successEdit").hide();
        $("#signEdit").hide();
        $(".keywordContent").show();
        $(".introductory").hide();
    }
});

//弹出悬浮层，展现红包池列表
function selPool(){
    //红包列表
    var TableModal = $('#HBTable').DataTable({
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
            "url":  baseUrl+'/hbPool/datas',
            "data": function (d) {
                var name = $("#searchForm :input[name='name']").val();
                var type = $("#searchForm :input[name='type']").val();
                var startDate = $("#searchForm :input[name='startDate']").val();
                var endDate = $("#searchForm :input[name='endDate']").val();
                if (!isNullOrEmpty(name)) {
                    d.name = name;
                }
                if (!isNullOrEmpty(type)) {
                    d.type = type;
                }
                if (!isNullOrEmpty(startDate)) {
                    d.startDate = startDate;
                }
                if (!isNullOrEmpty(endDate)) {
                    d.endDate = endDate;
                }
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="cbox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            }, {
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return getLocalTime(data);
                },
                "targets":2
            }, {
                "render": function (data, type, row) {

                    if(data == "unBounded"){
                        return "未关联";
                    } else if (data == "unReceived"){
                        return "待领取";

                    }else if (data == "receiving"){
                        return "领取中";
                    } else if (data == "done"){
                        return "结束";
                    }
                    return data;
                },
                "targets":3
            },{
                "render": function (data, type, row) {
                    return (row.number-row.numberObtain);
                },
                "targets":6
            },{
                "render": function (data, type, row) {
                    if(data == "gjc"){
                        return "关键词领取";
                    } else if (data == "jfdh"){
                        return "积分兑换领取";
                    }else if (data == "yyy"){
                        return "摇一摇领取";
                    }else if(data="hpf"){
                        return  "好评返领取";
                    }else if(data="gw"){
                        return   "购物领取";
                    }else  if(data="fxs"){
                        return   "分享领取";
                    }else {
                        return  "";
                    }
                    return data;
                },
                "targets":7
            },{
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return getLocalTime(data);
                },
                "targets":8
            }
        ],
        "sort":false,
        "columns": [
            {"data": "poolId"},
            {"data": "name"},
            {"data": "gmtCreated"},
            {"data": "states"},
            {"data": "number"},
            {"data": "numberObtain"},
            {"data": "null"},
            {"data": "type"},
            {"data": "endDate"}
        ]
    });
    $('#hbpoolModal').modal('show');
    $('#searchBut').click(function(){
        TableModal.draw();
    });
    $('#submitBut').click(function(){
        var id = '';
        var name = '';
        var i = 0;
        $(":checkbox[name='cbox']").each(function(){
            if ($(this).prop('checked')){
                id = $(this).val();
                name = $(this).parent().parent().next().html();
                i++;
            }
        });
        if (i > 1){
            alert('红包池只能选择一个');
            return false;
        }
        if (id == ''|| id == null){
            alert("未选择红包池");
            return false;
        }
        $("#addForm input[name='bizId']").val(id);
        $("#poolInput").val(name);
    });
}

//保存回复设置
$('#save').click(function(){
    var title = $("#addForm input[name='title']").val();
    var bizType = $("#addForm input[name='bizType']:checked").val();
    var keyWord = $("#addForm input[name='keyWord']").val();
    var matchType = $("#addForm input[name='matchType']:checked").val();
    var bizId = $("#addForm input[name='bizId']").val();

    var successReplyContent;
    var type = $("#addForm input[name='bizType']").val();
    if (type == 'receive_hb' || type == 'goodComment_hb'){
        successReplyContent = $("#addForm input[name='bizId']").val();
    }else{
        successReplyContent = ue_success.getContentTxt();
    }

    var introduceWord = $("#addForm input[name='introduceWord']").val();

    formData = {title:title,bizType:bizType,keyWord:keyWord,matchType:matchType,bizId:bizId,successReplyContent:successReplyContent,
        introduceWord:introduceWord};
    $.ajax({
        type : "post",
        url : baseUrl+"dynamicResponse/saveConfig",
        data : formData,
        dataType : "json",
        success : function(data){
            if (data.errorCode == "-1"){
                alert('保存失败');
            }else{
                alert('保存成功');
            }
        }
    });
})