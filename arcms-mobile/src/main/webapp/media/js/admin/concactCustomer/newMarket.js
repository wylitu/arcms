//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('emailEditor',{
    toolbars: [
        [
            'source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'subscript', //下标
            'superscript', //上标
            'formatmatch', //格式刷
            'pasteplain', //纯文本粘贴模式
            'selectall', //全选
            'horizontal', //分隔线
            'removeformat', //清除格式
            'simpleupload', //单图上传
            'insertimage', //多图上传
            'inserttitle', //插入标题
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'link', //超链接
            'emotion', //表情
            'spechars', //特殊字符
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'forecolor'//字体颜色
        ]
    ]
});


createToken('markActForm');
//表单重置
$('#markActForm')[0].reset();
//临时存放已选择用户的数量,sms[0]每次刷新筛选table时的用户数,sms[1]用户手动选择,sms[2]是否全选
var smsData = [0,0,0];
var emailData = [0,0,0];

//显示用户已经选择多少条记录
function submitsmsUser(){
    var count = $('.smsUserTable_Num').html();
    $('.userNum').html(count);
}
function submitemailUser(){
    var count = $('.emailUserTable_Num').html();
    $('.user3Num').html(count);
}
function submitwxUser(){
    var count = $('.wxUserTable_Num').html();
    $('.user4Num').html(count);
}
//点击 “发送”  按钮，发送营销信息
$("#sendActBut").click(function(){
    var data = $("#markActForm").serialize();
    var count = null;
    var tempSize = null;

    var selected = $("#markActForm input[name='type']:checked").val();
    if (selected == "sms") {
        tempSize = smsData[0];
        count = $('.userNum').html();
    }else if (selected == "email"){
        tempSize = emailData[0];
        count = $('.user3Num').html();
    }else if (selected == "wx"){

    }

    if (count == tempSize){
        data = data +"&selectAll=1";
    }else{
        data = data +"&selectAll=0&userArr="+idList;
    }
    alert(data);
    return false;
    $.ajax({
        type: "post",
        url:  baseUrl+"contactCustomer/sendActivity",
        data: data,
        dataType: "text",
        success: function(data){
            if (data.errorCode == 0){
                alert("活动发送成功！");
            }else{
                alert("活动正在后台发送。。。！");
            }
        }
    });
})

function seeSms() {
    //短信营销客户选择
    var smsTableModal = $('#smsUserTable').DataTable({
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
            "url":  baseUrl+'contactCustomer/concactUser',
            "data": function (d) {
                var keyword = $("#smsClientSelectedForm input[name='keyword']").val();
                var userType = $("#smsClientSelectedForm input[name='userType']:checked").val();
                var purchasePrice = $("#smsClientSelectedForm input[name='purchasePrice']:checked").val();
                var minPrice = $("#smsClientSelectedForm input[name='minPrice']").val();
                var maxPrice = $("#smsClientSelectedForm input[name='maxPrice']").val();
                var purchaseNum = $("#smsClientSelectedForm input[name='purchaseNum']:checked").val();

                d.keyword = keyword;
                d.userType = userType;
                d.purchasePrice = purchasePrice;
                d.minPrice = minPrice;
                d.maxPrice = maxPrice;
                d.purchaseNum = purchaseNum;
                d.mobile = "1";
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="smsBox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            }, {
                "render": function (data, type, row) {
                    if (data ==null && row.province == null){
                        return '';
                    }
                    return row.province+"/"+data;
                },
                "targets":3
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else{
                        return data+' 次';
                    }
                },
                "targets":4
            },{
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return '￥ '+(data/100).toFixed(2)+' 元';
                },
                "targets":5
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else{
                        return data+' 次';
                    }
                },
                "targets":6
            }, {
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return getLocalTime(data);
                },
                "targets":7
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else if (data == "TB"){
                        return '淘宝客户';
                    }else if (data == 'JD'){
                        return '京东客户';
                    }
                },
                "targets": 8
            }
        ],
        "sort":false,
        "fnDrawCallback": function(data){
            smsData[0] = data.json.recordsTotal;
//            $("#remainCount").html(data.json.remainCount);
            addInfo(smsData,'smsUserTable','smsBox');
            if (smsData[2] == 1){
                $('#smsUserTable th input:checkbox').click();
            }
        },
        "columns": [
            {"data": "userId"},
            {"data": "name"},
            {"data": "mobile"},
            {"data": "city"},
            {"data": "purchaseAverageCycle"},
            {"data": "purchaseAverageAmount"},
            {"data": "totalPurchaseTimes"},
            {"data": "lastPurchaseDate"},
            {"data": "firstSource"},
            {"data": "contribution"},
            {"data": "liveness"}
        ]
    });
    $("#smsUserTable_Se").remove();
    var htm = '<span id="smsUserTable_Se" class="col-sm-10">已选择<b class="smsUserTable_Num">&nbsp;0&nbsp;</b>条记录</span>';
    $("#smsUserTable_length").append(htm);
    $('#smsClientSelectedModal').modal('show');
    $("#smsfilterBut").click(function(){
        smsTableModal.draw();
        return false;
    })
    $("#smsAllBut").on('click',function(){
        $('#smsUserTable th input:checkbox').click();
        smsData[2] = 1;
        $(".smsUserTable_Num").html(smsData[0]);
        return false;
    })
    addInfo(smsData,'smsUserTable','smsBox');
}

function seeEmail() {
    //邮件营销客户选择
    var emailTableModal = $('#emailUserTable').DataTable({
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
            "url":  baseUrl+'contactCustomer/concactUser',
            "data": function (d) {
                var keyword = $("#emailClientSelectedForm input[name='keyword']").val();
                var userType = $("#emailClientSelectedForm input[name='userType']:checked").val();
                var purchasePrice = $("#emailClientSelectedForm input[name='purchasePrice']:checked").val();
                var minPrice = $("#emailClientSelectedForm input[name='minPrice']").val();
                var maxPrice = $("#emailClientSelectedForm input[name='maxPrice']").val();
                var purchaseNum = $("#emailClientSelectedForm input[name='purchaseNum']:checked").val();

                d.keyword = keyword;
                d.userType = userType;
                d.purchasePrice = purchasePrice;
                d.minPrice = minPrice;
                d.maxPrice = maxPrice;
                d.purchaseNum = purchaseNum;
                d.email = '1';
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="emailBox" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
                },
                "targets": 0
            },  {
                "render": function (data, type, row) {
                    return row.province+"/"+data;
                },
                "targets":3
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else{
                        return data+' 次';
                    }
                },
                "targets":4
            },{
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return '￥ '+(data/100).toFixed(2)+' 元';
                },
                "targets":5
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else{
                        return data+' 次';
                    }
                },
                "targets":6
            },{
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return getLocalTime(data);
                },
                "targets":7
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else if (data == "TB"){
                        return '淘宝客户';
                    }else if (data == 'JD'){
                        return '京东客户';
                    }
                },
                "targets": 8
            }
        ],
        "sort":false,
        "fnDrawCallback": function(data){
            emailData[0] = data.json.recordsTotal;
            $("#remainCount").html(data.json.remainCount);
            addInfo(emailData,'emailUserTable','emailBox');
        },
        "columns": [
            {"data": "userId"},
            {"data": "name"},
            {"data": "email"},
            {"data": "city"},
            {"data": "purchaseAverageCycle"},
            {"data": "purchaseAverageAmount"},
            {"data": "totalPurchaseTimes"},
            {"data": "lastPurchaseDate"},
            {"data": "firstSource"},
            {"data": "contribution"},
            {"data": "liveness"}
        ]
    });
    $("#emailUserTable_Se").remove();
    var htm = '<span id="emailUserTable_Se" class="col-sm-10">已选择<b class="emailUserTable_Num">&nbsp;0&nbsp;</b>条记录</span>';
    $("#emailUserTable_length").append(htm);
    $('#EmailClientSelectedModal').modal('show');
    $("#emailfilterBut").click(function(){
        emailTableModal.draw();
        return false;
    })
    $("#emailAllBut").on('click',function(){
        $(".emailUserTable_Num").html(emailData[0]);
        return false;
    })
    addInfo(emailData,'emailUserTable','emailBox');
}

//邮箱切换，异步查找客户数量
$("input[name='emailType']").on("click",function(){
    var email = '';
    $("input[name='emailType']:checked").each(function(i){
        if (i == 0){
            email = $(this).val();
        }else{
            email = email+","+$(this).val();
        }
    });

    $.ajax({
        type : "post",
        url : baseUrl+"contactCustomer/emailSelect",
        data : {str:email},
        dataType : "json",
        success : function(data){
            $('.user3Num').html(data.userCount);
        }
    });
});

//非全选时定向选择用户，数据存放在临时数组里 arr:用户保存数据的临时数组，boxName:单选框name
function addInfo(arr,tableID,boxName){
    //手动单个选择
    var $checkBox = $('#'+tableID+'  :checkbox[name="'+boxName+'"]');
    $.each($checkBox,function(i){
        var id = $(this).attr('value');
        var info = $("#"+tableID+" tr:eq("+(i+1)+") td:eq(2)").text();
        var val = id+':'+info;
        if (idList.in_array(val)){
            $(this).prop('checked','checked');
        }
        $(this).on('click',function(){
            //取得id
            var id = $checkBox.eq(i).attr('value');
            //取得电话号码或邮箱
            var info = $("#"+tableID+" tr:eq("+(i+1)+") td:eq(2)").text();
            if($(this).prop('checked')){
                $checkBox.eq(i).removeAttr('checked');
                $(this).prop('checked','checked');
                idList.addElement(id,info);
            }else {
                idList.deleteA(id,info);
            }
            arr[1] = idList.length;
            $("."+tableID+"_Num").html(arr[1]);
        });
    });

    //单页全选
    $('#'+tableID+' th input:checkbox').on('click' , function(){
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox')
            .each(function(i){
                this.checked = that.checked;
                //alert(this.checked);
                $(this).closest('tr').toggleClass('selected');
            });
    });
}

//保存已选用户的信息数组
var idList = new Array();
Array.prototype.in_array =  function(e)
{
    for(var i=0;i<this.length;i++)
    {
        if(this[i] == e)
            return true;
    }
    return false;
}
Array.prototype.deleteA = function(id,mobile){
    var e = id+':'+mobile;
    var pos = -1;
    if (idList.in_array(e)){
        for (var i=0;i<this.length;i++){
            if (this[i] == e){
                pos = i;
            }
        }
    }
    if (pos > -1) {
        this.splice(pos, 1);
        return true;
    }
    return false;
}
Array.prototype.addElement = function(id,mobile){
    var info = id+':'+mobile;
    if (!idList.in_array(info)){
        idList.push(info);
        return true;
    }
    return false;
}
//清除idlist数据
function clearList(){
    idList.length = 0;
    smsData.length = 0;
    emailData.length = 0;
}

//布局div切换
$("input[name='type']").click(function(){
    clearList();
    var selected = $(this).val();
    if (selected == "wx"){
        $(".smsContent").hide();
        $(".emialContent").hide();
        $(".wxContent").show();
        $("#showBox").show();
        $(".noticeContent").html("<a href='#'>添加链接</a>");
        $("#selectAgain").attr("onclick","seeWx()");
    }else if (selected == "sms"){
        $(".smsContent").show();
        $("#showBox").show();
        $(".wxContent").hide();
        $(".emialContent").hide();
        $(".noticeContent").html("注意事项：70字第1条短信；后续每条短信69个字；系统会根据内容长度自动分成多条短信发送（134字/2条）");
        $("#selectAgain").attr("onclick","seeSms()");
    }else if (selected == "email"){
        $(".emialContent").show();
        $(".wxContent").hide();
        $(".smsContent").hide();
        $("#showBox").hide();
        $("#selectAgain").attr("onclick","seeEmail()");
    }
});
$(".audiohref").click(function(){
    $("#showBox").hide();
    $("#uploadInput").show();
});
$(".texthref").click(function(){
    $("#showBox").show();
    $("#uploadInput").hide();
    $("#uploadText").val("");
});
function preview(){
    var text = $("textarea[name='content']").val();
    if (text == null || text == ''){
        $(".textContent").html('请在左边填写要短信内容,然后点击预览');
        alert('内容不能为空');
    }else{
        $(".textContent").html(text);
    }
}

function seeWx() {
    //微信营销客户选择
    var wxTableModal = $('#wxUserTable').dataTable({
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
            "url":  baseUrl+'contactCustomer/concactUser',
            "data": function (d) {
                var keyword = $("#wxClientSelectedForm input[name='keyword']").val();
                var userType = $("#wxClientSelectedForm input[name='userType']:checked").val();
                var isReceive = $("#wxClientSelectedForm input[name='isReceive']:checked").val();
                var integralString = $("#wxClientSelectedForm input[name='integralString']:checked").val();
                var actionTimes = $("#wxClientSelectedForm input[name='actionTimes']:checked").val();
                var purchaseNum = $("#wxClientSelectedForm input[name='purchaseNum']:checked").val();

                d.keyword = keyword;
                d.userType = userType;
                d.isReceive = isReceive;
                d.integralString = integralString;
                d.actionTimes = actionTimes;
                d.purchaseNum = purchaseNum;
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input name="cb" type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
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
                    if(data==null){
                        return  '';
                    }else
                        return data+" 次";
                },
                "targets":3
            }, {
                "render": function (data, type, row) {
                    if(data==null){
                        return  '';
                    }else
                        return data+" 次";
                },
                "targets":4
            },{
                "render": function (data, type, row) {
                    if (data == null){
                        return '';
                    }else if (data == "TB"){
                        return '淘宝客户';
                    }else if (data == 'JD'){
                        return '京东客户';
                    }
                },
                "targets": 5
            }
        ],
        "sort":false,
        "fnDrawCallback": function(data){
            $(".user2Num").html(data.json.recordsTotal);

        },
        "columns": [
            {"data": "id"},
            {"data": "name"},
            {"data": "wxSubscribeTime"},
            {"data": "interactionsTimes"},
            {"data": "totalPurchaseTimes"},
            {"data": "firstSource"},
            {"data": "wxPurchaseAverageAmount"},
            {"data": "contribution"},
            {"data": "liveness"}
        ]
    });
    $("#wxAllBut").click(function(){
        return false;
    })
    //筛选
    $("#wxfilterBut").click(function(){
        $('#wxUserTable').DataTable().draw();
        return false;
    })
    $('#wxClientSelectedModal').modal('show');
    checkBox();
}

//测试一下
$('#testBut').click(function(){
    var ten = $('#markActForm input[name="type"]:checked').val();
    if (ten == ''|| ten == null){
        alert('请先选择测试类型，如短信');
        return;
    }
    if (ten == 'sms'){
        $('#smsTestModal').modal('show');
    }else if (ten == 'email'){
        $('#emailTestModal').modal('show');
    }else{
        $('#wxTestModal').modal('show');
    }
});

//短信测试
$('#smsTest').click(function(){
    var testMobile = $("#smsTestModal input[name='testMobile']").val();
    $.post(baseUrl+'contactCustomer/smsTest',{mobile:testMobile},function(data){
        if (data.errorCode == '0'){
            alert("发送成功，请注意查收");
        }else{
            alert("短信发送失败");
        }
    });
})

//邮件测试
$('#emailTest').click(function(){
    var testEmail = $("#emailTestModal input[name='testEmail']").val();
    $.post(baseUrl+'contactCustomer/emailTest',{email:testEmail},function(data){
        if (data.errorCode == '0'){
            alert("发送成功，请注意查收");
        }else{
            alert("短信发送失败");
        }
    });
})