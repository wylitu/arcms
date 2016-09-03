    //createToken('addForm');

    /**
     * 查看详细信息方法
     * @type {*|jQuery}
     */
    detail();

    /**
     * 获取表单详细信息
     */
    function  detail(){
        $.post( baseUrl+"/ruleConfig/detail", {ruleConfigId:0}, function(data){
           if(!isNullOrEmpty(data.rule.ruleId)){
               loadData('#addForm', data.rule);
           }
        }, "json")
    }

    /**
     * 加载数据
     * @param topSelector
     * @param obj
     */
    function loadData(topSelector, obj){
        $("#addForm input[name='id']").val(obj.ruleId);
        $("#addForm input:radio[name='type'][value='false']").prop("checked","checked");
        $.each(obj.ruleExpressions,function(i,value){
            var   expectation_value=value.expectation_value;
            if(value.fact_attribute=="dayOfMonth"){
                var   array=expectation_value.split(",");
                $("input[name='fixDate1']").val(array[0]);
                $("input[name='fixDate2']").val(array[1]);
            }else  if(value.fact_attribute=="cachCountPerMonth"){
                var array1=expectation_value.split(",");
                $("input[name='maxCountPerMon']").val(array1[1]);
            }else  if(value.fact_attribute=="hbCurrentCashAmount"){
                var   array2=expectation_value.split(",");
                $.each(array2,function(i,val){
                    $(".denomination").eq(i).val(array2[i+1]);
                });
            }else if(value.fact_attribute=="smsVerify"){
                $("#addForm input:radio[name='type'][value='true']").prop("checked","checked");
                $("input[name='remindDate']").val(value.operator);
                $("textarea[name='smsContent']").val(expectation_value);
            }
        });
    }


    /**
     * 增加或更新表单
     */
    function  saveOrUpdateForm(){
        var data = $('#addForm').serialize();
        var struct='{"ruleExpressions":[';
        if(!isNullOrEmpty($("input[name='fixDate1']").val())&&!isNullOrEmpty($("input[name='fixDate2']").val())){
            struct+='{fact_attribute:"dayOfMonth","operator":"[]","expectation_value":"'+$("input[name='fixDate1']").val()+','+$("input[name='fixDate2']").val()+'","fact_obj":"com.shufensoft.crm.ruleEngine.domain.RuleDate","initFlag":false,"demo":"活动提现日"},';
        }
        if(!isNullOrEmpty($("input[name='maxCountPerMon']").val())&&$("input[name='maxCountPerMon']").val()!=0){
            struct+='{fact_attribute:"cachCountPerMonth","operator":"[]","expectation_value":"0,'+$("input[name='maxCountPerMon']").val()+'","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"demo":"用户每月提现数量"},';
        }
        struct+='{fact_attribute:"hbCurrentCashAmount","operator":"{}","expectation_value":"1/1,';
        $.each($(".denomination"), function(i,val){
            if(!isNullOrEmpty(val.value)){//当面额值不为空的时候拼接数据
                struct+= ''+val.value+',';
            }
        });
        struct+= '","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"demo":"红包提现面额"},';
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
                console.dir(data);
                if(data.errorCode == 0){
                    $('#addForm input[name="id"]').val(data.id);
                    alert("保存成功!");
                }else{
                    alert(data.errorMessage);
                }
            }, error: function () {
                alert("error");
            }
        })
    }

    function  change(){
        if($("#addForm input:radio[name='type']:checked").val()=='true'){
            $("#addForm  textarea").removeAttr("readonly");
            $("#addForm  input[name='remindDate']").attr("disabled",false);
        }else{
            $("#addForm  textarea").val('');
            $("#addForm  input[name='remindDate']").val('');
            $("#addForm  textarea").attr("readOnly","readOnly");
            $("#addForm  input[name='remindDate']").attr("disabled","disabled");
        }

    }

    //表单验证
    var MyValidator = function() {
        var handleSubmit = function() {
            $('.form-horizontal').validate({
                errorElement : 'span',
                errorClass : 'help-block',
                focusInvalid : false,
                rules : {
                    fixDate1 : {
                        digits:true,
                        compareDayPerMon:true
                    },
                    fixDate2 : {
                        digits:true,
                        compareDayPerMon:true
                    },
                    maxCountPerMon : {
                        digits:true
                    },
                    ammount2 : {
                        digits:true,
                        min:2
                    },
                    ammount3 : {
                        digits:true,
                        min:2
                    },
                    ammount4: {
                        digits:true,
                        min:2
                    },
                    smsContent:{
                        smsContentVerify:true
                    },
                    remindDate: {
                        remindDateVerify:true
                    }
                },
                messages : {
                    fixDate1 : {
                        digits:"必须是整数",
                        compareDayPerMon:"开始时间应小于等于结束时间"
                    },
                    fixDate2 : {
                        digits:"必须是整数",
                        compareDayPerMon:"结束时间应大于等于开始时间"
                    },
                    maxCountPerMon : {
                        digits:"必须是整数"
                    },
                    ammount2 : {
                        digits:"必须是整数",
                        min:"输入值需大于1"
                    },
                    ammount3 : {
                        digits:"必须是整数",
                        min:"输入值需大于1"
                    },
                    ammount4: {
                        digits:"必须是整数",
                        min:"输入值需大于1"
                    },
                    smsContent:{
                        smsContentVerify:"短信内容不为空且不超过70个字!"
                    },remindDate:{
                        remindDateVerify:"短信提醒时间段不能为空!"
                    }
                },
                highlight : function(element) {
                    $(element).closest('.form-group').addClass('has-error');
                },

                success : function(label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement : function(error, element) {
                    element.parent('div').append(error);
                },

                submitHandler : function(form) {
                    saveOrUpdateForm();//增加或更新表单
                }
            });

            $('.form-horizontal input').keypress(function(e) {
                if (e.which == 13) {
                    if ($('.form-horizontal').validate().form()) {
                        $('.form-horizontal').submit();
                    }
                    return false;
                }
            });
        }
        return {
            init : function() {
                handleSubmit();
            }
        };
    }();
    MyValidator.init();

    /**
     * 每日提现验证
     */
    jQuery.validator.addMethod("compareDayPerMon",function(value,element){
        fixDate1 = $("input[name='fixDate1']").val();
        fixDate2 = $("input[name='fixDate2']").val();
        if(!isNullOrEmpty(fixDate1)&&!isNullOrEmpty(fixDate2)&&fixDate1>fixDate2){
            return false;
        }else{
            return true;
        }
    });

    /**
     * 短信验证
     */
    jQuery.validator.addMethod("smsContentVerify",function(value,element){
        if(($("#addForm input:radio[name='type']:checked").val()=='true')){
            var  smsContent=$("textarea[name='smsContent']").val();
          if(isNullOrEmpty(smsContent)){
              return false;
          }
            smsContent=smsContent.trim();
            if(smsContent.length>60){
                return false;
            }
        }
        return true;
    });

    /**
     * 短信提醒时间段验证
     */
    jQuery.validator.addMethod("remindDateVerify",function(value,element){
        if(($("#addForm input:radio[name='type']:checked").val()=='true')){
            if(isNullOrEmpty($("input[name='remindDate']").val())){
                return false;
            }
        }
        return true;
    });

