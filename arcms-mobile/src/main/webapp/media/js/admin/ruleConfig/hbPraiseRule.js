
    /**
     * 选择带字红包池
     */
    function selPool(type) {
        $('#hbType').val(type);
        $('#hbPoolSelectedModal').modal('show');
        $.each($(':checkbox[name="cb"]'),function(i,value){
            $(this).click(function(){
                if($(this).prop('checked')){
                    $(':checkbox[name="cb"]').removeAttr('checked');
                    $(this).prop('checked','checked');
                }
            });
        });
    }




    /**
     * 红包池分页
     * @type {*|jQuery}
     */
    var oTable = $('#imgTable').dataTable({
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
            "url":  baseUrl+'/hbPool/datas',
            "data": function (d) {
                var name = $("#searchForm :input[name='name']").val();
                var startDate = $("#searchForm :input[name='startDate']").val();
                var endDate = $("#searchForm :input[name='endDate']").val();
                d.type = 'hpf';//默认设置类型为好评返红包
                d.status='unBounded'//默认设置红包为未关联
                if (!isNullOrEmpty(name)) {
                    d.name = name;
                }
                if (!isNullOrEmpty(startDate)) {
                    d.startDate = startDate;
                }
                if (!isNullOrEmpty(endDate)) {
                    d.endDate = endDate;
                }
                //  console.dir(d);
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input type="checkbox" name="cb" class="ace" value="'+row.id+","+row.name+'"/><span class="lbl"></span></label></td>';
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
                        return "未关联"
                    } else if (data == "unReceived"){
                        return "待领取"

                    }else if (data == "receiving"){
                        return "领取中"
                    } else if (data == "done"){
                        return "结束"
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
                        return "关键词领取"
                    } else if (data == "jfdh"){
                        return "积分兑换领取"

                    }else if (data == "yyy"){
                        return "摇一摇领取"
                    }else if(data="hpf"){
                        return  "好评返领取"
                    }else if(data="gw"){
                        return   "购物领取"
                    }else  if(data="fxs"){
                        return   "分享领取"
                    }else {
                        return  ""
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
            {"data": "id"},
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



    /**
     * 多文件上传
     */
    $("#multiFileUpload").zyUpload({
        width            :   "750px",                 // 宽度
        height           :   "230px",                 // 宽度
        itemWidth        :   "100px",                 // 文件项的宽度
        itemHeight       :   "100px",                 // 文件项的高度
        url              :   "/upload/UploadAction",  // 上传文件的路径
        multiple         :   false,                    // 是否可以多个文件上传
        dragDrop         :   false,                    // 是否可以拖动上传文件
        del              :   true,                    // 是否可以删除文件
        finishDel        :   false,  				  // 是否在上传文件完成后删除预览
        /* 外部获得的回调接口 */
        onSelect: function(files, allFiles){                    // 选择文件的回调方法
            console.info("当前选择了以下文件：");
            console.info(files);
            console.info("之前没上传的文件：");
            console.info(allFiles);
        },
        onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法
            console.info("当前删除了此文件：");
            console.info(file);
            console.info("当前剩余的文件：");
            console.info(surplusFiles);
        },
        onSuccess: function(file){                    // 文件上传成功的回调方法
            console.info("此文件上传成功：");
            console.info(file);
        },
        onFailure: function(file){                    // 文件上传失败的回调方法
            console.info("此文件上传失败：");
            console.info(file);
        },
        onComplete: function(responseInfo){           // 上传完成的回调方法
            console.info("文件上传完成");
            console.info(responseInfo);
        }
    });


    /**
     * 确定操作
     */
    $("#sure").click(function (event) {
        var checks = $(".table td :input[type='checkbox']:checked");
        if (checks.length == 0) {
            alert("没有选中的红包池,请选择红包池！");
            return;
        }
       var  value= $(':checkbox[name="cb"]:checked').val();
       var array=  value.split(",");
        if("withWord"==$('#hbType').val()){
            $('#hbPraiseForm input[name="withWordId"]').val();
            $('#hbPraiseForm input[name="withWord"]').val(array[1]);
        }else{
            $('#hbPraiseForm input[name="withPictureId"]').val(array[0]);
            $('#hbPraiseForm input[name="withPicture"]').val(array[1]);
        }

       $('#hbPoolSelectedModal').modal('hide');
    });


    /**
     * 查询
     */
    $("#searchForm").submit(function () {
        $('#imgTable').DataTable().draw();
        return false; //阻止表单默认提交
    });

    /**
     * 保存更新方法
     */
    function  saveOrUpdateForm(){
        var struct='{"ruleExpressions":[';
        if(!isNullOrEmpty($('#hbPraiseForm input[name="withWordId"]').val())){
            struct+='{fact_attribute:"dayOfMonth","operator":"[]","expectation_value":"'+$("input[name='fixDate1']").val()+'","fact_obj":"com.shufensoft.crm.ruleEngine.domain.RuleDate","initFlag":false,"demo":"带字好评"}]}';
        }
        if(!isNullOrEmpty($("input[name='maxCountPerMon']").val())&&$("input[name='maxCountPerMon']").val()!=0){
            struct+='{fact_attribute:"cachCountPerMonth","operator":"[]","expectation_value":"[0,'+$("input[name='maxCountPerMon']").val()+']","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"demo":"用户每月提现数量"},';
        }
        struct+='{fact_attribute:"hbCurrentCashAmount","operator":"{}","expectation_value":"{1/1,';
        $.each($(".denomination"), function(i,val){
            if(!isNullOrEmpty(val.value)){//当面额值不为空的时候拼接数据
                struct+= ''+val.value+',';
            }
        });
        struct+= '}","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"demo":"红包提现面额"},';
        //如果设置了需要提前一天短信提醒
        if(($("#addForm input:radio[name='type']:checked").val()=='true')){
            struct+='{fact_attribute:"smsVerify","operator":"'+$("input[name='remindDate']").val()+'","expectation_value":"'+$("textarea[name='smsContent']").val()+'","fact_obj":"sms","initFlag":false,"demo":"短信验证信息"}]}';
        }
        data+="&value="+struct;
        $.ajax({
            type: "post", // 请求方式
            url:  baseUrl+"/ruleConfig/hbCash/saveOrUpdate", //url地址
            data: data, //数据
            dataType: "json",
            success: function (data, ifo) {
                //alert(response)
                //var data = JSON.parse(response.text)
                if(data.errorCode == 0){
                    alert("保存成功!");
                }else{
                    alert(data.errorMessage);
                }
            }, error: function () {
                alert("error");
            }
        })
    }