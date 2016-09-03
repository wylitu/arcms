       change();
    //创建红包池方法
       function  addForm(){
           var data = $('#addForm').serialize();
           var poolStruct='{"ruleExpressions":[';
           if( $('#hbpoolModal  input[name="config"]:checked').val()=="regular"){//当选取为规则配置的时候
             poolStruct+='{fact_attribute:"denomination","operator":"=/","expectation_value":"'+$("#hbpoolModal .ammount").val()*100+'/'+$("#hbpoolModal .number").val() +'","fact_obj":"com.shufensoft.crm.biz.domain.hongbao.HBPool","initFlag":true,"isRuleEngine":true,"demo":"固定面额的红包数量"},';
           }else{//当选取为非规则配置的时候
               var len=$(".ammStart").length;//获取随机红包金额的开始值数量
               poolStruct+='{fact_attribute:"range","operator":"R[]/","expectation_value":"';
               $.each($(".ammStart"), function(i,val){
                   if(val.value!=''&&$(".ammEnd").get(i).value!=''&&$(".irNumber").get(i).value!=''){//当红包个数和红包金额都不为空的时候拼接数据
                   poolStruct+= ''+val.value*100+'-'+$(".ammEnd").get(i).value*100+'/'+$(".irNumber").get(i).value +',';
                   }
               })
               poolStruct+= '","fact_obj":"com.shufensoft.crm.biz.domain.generation.hongbao.HBPool","initFlag":true,"demo":"随机金额红包数量"},';
           }
           //每个微信昵称（openid）限领一个条件满足,并且该红包类型不是好评返红包类型
           if($('#hbpoolModal  input[name="limitObtainTime"]:checked').val()=='true'&&$('#hbpoolModal  input[name="type"]:checked').val()!="hpf"){
              poolStruct+='{fact_attribute:"limitObtainTime","operator":"=","expectation_value":"0","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"isRuleEngine":true,"demo":"每个微信昵称（openid）限领一个","condition_remind":"亲,该红包你已领取,期待下次活动"},';
           }

           //当红包类型为好评返回红包时,此时这里面的条件不走规则引擎
           if($('#hbpoolModal  input[name="type"]:checked').val()=="hpf"){
               poolStruct+='{fact_attribute:"withWordLimit","operator":"=","expectation_value":"'+ $('#hbpoolModal  input[name="withWordLimit"]').val()+'","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"isRuleEngine":false,"demo":"带字好评数限制"},';

               //每个快递单号限制领一个条件满足
               if($('#hbpoolModal  input[name="limitPostTime"]:checked').val()=='true'){
                   poolStruct+='{fact_attribute:"limitPostTime","operator":"=","expectation_value":"1","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"isRuleEngine":false,"demo":"每个快递单号限制领一个","condition_remind":"亲,此次好评您已领取红包"},';
               }

              //获取返现平台数据
              if($('#hbpoolModal  input[name="platform"]:checked').length>0){
                  poolStruct+='{fact_attribute:"platform","operator":"()","expectation_value":"';
                  $.each($('#hbpoolModal  input[name="platform"]:checked'), function(i,val){
                      if(val.value!=''){
                          poolStruct+= ''+val.value +',';
                      }
                  })
                  poolStruct+= '","fact_obj":"com.shufensoft.crm.biz.domain.generation.hongbao.HBPool","initFlag":true,"isRuleEngine":false,"demo":"返现平台"},';
              }
           }
           //活动开始时间,结束时间
         poolStruct+='{fact_attribute:"currentTime","operator":"[]","expectation_value":"'+$('#hbpoolModal  input[name="startDate"]').val()+','+$('#hbpoolModal  input[name="endDate"]').val()+'","fact_obj":"com.shufensoft.crm.ruleEngine.domain.RuleDate","initFlag":false,"isRuleEngine":true,"demo":"活动起始时间限制","condition_remind":"sorry,活动已结束,期待下次光临"}]}';
        data+="&ruleExpression="+poolStruct;
        $.ajax({
               type: "post", // 请求方式
               url:  baseUrl+"/hbPool/add", //url地址
               data: data, //数据
               dataType: "json",
               success: function (data, ifo) {
                   if(data.errorCode == 0){
                       alert("添加成功");
                       $('#imgTable').DataTable().draw();
                       $('#addForm')[0].reset();
                       $('#hbpoolModal').modal('hide');
                       changeModal();
                   }else{
                       alert(data.errorMessage);
                   }
               }, error: function () {
                   alert("error");
               }
           });
       }
       //更新红包池方法
       function updateForm(){
           var data = $('#updateForm').serialize();
           var poolStruct='{"ruleExpressions":[';
           if( $('#hbpoolDetailModal  input[name="config"]:checked').val()=="regular"){//当选取为规则配置的时候
               poolStruct+='{fact_attribute:"denomination","operator":"=/","expectation_value":"'+$("#hbpoolDetailModal .ammount").val()*100+'/'+$("#hbpoolDetailModal .number").val() +'","fact_obj":"com.shufensoft.crm.biz.domain.hongbao.HBPool","initFlag":true,"demo":"固定面额的红包数量"},';
               poolStruct+='{fact_attribute:"denomination","operator":"=/","expectation_value":"'+$("#hbpoolDetailModal .ammount").val()+'/'+$("#hbpoolDetailModal .number").val() +'","fact_obj":"com.shufensoft.crm.biz.domain.generation.hongbao.HBPool","initFlag":true,"demo":"固定面额的红包数量"},';
           }else{//当选取为非规则配置的时候
               var len=$("#hbpoolDetailModal .ammStart").length;//获取随机红包金额的开始值数量
               poolStruct+='{fact_attribute:"range","operator":"R[]/","expectation_value":"';
               $.each($("#hbpoolDetailModal .ammStart"), function(i,val){
                   if(val.value!=''&&$("#hbpoolDetailModal .ammEnd").get(i).value!=''&&$("#hbpoolDetailModal .irNumber").get(i).value!=''){//当红包个数和红包金额都不为空的时候拼接数据
                       poolStruct+= ''+val.value*100+'-'+$("#hbpoolDetailModal .ammEnd").get(i).value*100+'/'+$("#hbpoolDetailModal .irNumber").get(i).value*100 +',';
                   }
               })
               poolStruct+= '","fact_obj":"com.shufensoft.crm.biz.domain.generation.hongbao.HBPool","initFlag":true,"demo":"随机金额红包数量"},';
           }
           //每个微信昵称（openid）限领一个条件满足
           if($('#hbpoolDetailModal  input[name="limitObtainTime"]:checked').val()=='true'){
               poolStruct+='{fact_attribute:"limitObtainTime","operator":"=","expectation_value":"0","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"demo":"每个微信昵称（openid）限领一个","condition_remind":"亲,该红包你已领取,期待下次活动"},';
           }
           //当红包类型为好评返回红包时,此时这里面的条件不走规则引擎
           if($('#hbpoolDetailModal  input[name="type"]:checked').val()=="hpf"){
               poolStruct+='{fact_attribute:"withWordLimit","operator":"=","expectation_value":"'+ $('#hbpoolDetailModal  input[name="withWordLimit"]').val()+'","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"isRuleEngine":false,"demo":"带字好评数限制"},';

               //每个快递单号限制领一个条件满足
               if($('#hbpoolDetailModal  input[name="limitPostTime"]:checked').val()=='true'){
                   poolStruct+='{fact_attribute:"limitPostTime","operator":"=","expectation_value":"1","fact_obj":"com.shufensoft.crm.biz.vo.UserVO","initFlag":false,"isRuleEngine":false,"demo":"每个快递单号限制领一个","condition_remind":"亲,此次好评您已领取红包"},';
               }

               //获取返现平台数据
               if($('#hbpoolDetailModal  input[name="platform"]:checked').length>0){
                   poolStruct+='{fact_attribute:"platform","operator":"()","expectation_value":"';
                   $.each($('#hbpoolDetailModal  input[name="platform"]:checked'), function(i,val){
                       if(val.value!=''){
                           poolStruct+= ''+val.value +',';
                       }
                   })
                   poolStruct+= '","fact_obj":"com.shufensoft.crm.biz.domain.generation.hongbao.HBPool","initFlag":true,"isRuleEngine":false,"demo":"返现平台"},';
               }
           }
           //活动开始时间,结束时间
           poolStruct+='{fact_attribute:"currentTime","operator":"[]","expectation_value":"'+$('#hbpoolDetailModal  input[name="startDate"]').val()+','+$('#hbpoolDetailModal  input[name="endDate"]').val()+'","fact_obj":"com.shufensoft.crm.ruleEngine.domain.RuleDate","initFlag":false,"demo":"活动起始时间限制","condition_remind":"sorry,活动已结束,期待下次光临"}]}';
           data+="&ruleExpression="+poolStruct;
           $.ajax({
               type: "post", // 请求方式
               url:  baseUrl+"/hbPool/update", //url地址
               data: data, //数据
               dataType: "json",
               success: function (data, ifo) {
                   if(data.errorCode == 0){
                       alert("更新成功");
                       $('#imgTable').DataTable().draw();
                       $('#updateForm')[0].reset();
                       $('#hbpoolDetailModal').modal('hide');
                   }else{
                       alert(data.errorMessage);
                   }
               }, error: function () {
                   alert("error");
               }
           })
       }

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
              //  console.dir(d);
            }
        },
        "columnDefs": [
            {
                "render": function (data, type, row) {
                    return '<td class="center"><label><input type="checkbox" class="ace" value="'+data+'"/><span class="lbl"></span></label></td>';
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
            },{
                "render": function (data, type, row) {
                    return '<td> <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"> <a class="blue" href="javascript:view('+row.id+')"> <i class="icon-zoom-in bigger-130"></i> </a>  <a class="green" href="javascript:edit('+row.id+')"> <i class="icon-pencil bigger-130"></i> </a> <a class="blue" href="javascript:viewObtain('+row.id+')"> 查看领用明细</a>  </div>  <div class="visible-xs visible-sm hidden-md hidden-lg"> <div class="inline position-relative"> <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown"> <i class="icon-caret-down icon-only bigger-120"></i> </button>  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close"> <li> <a href="#" class="tooltip-info" data-rel="tooltip" title="View"> <span class="blue"> <i class="icon-zoom-in bigger-120"></i> </span> </a> </li>  <li> <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit"> <span class="green"> <i class="icon-edit bigger-120"></i> </span> </a> </li>  <li> <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete"> <span class="red"> <i class="icon-trash bigger-120"></i> </span> </a> </li> </ul> </div> </div> </td>';
                },
                "targets":9
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
            {"data": "endDate"},
            {"data": null}
        ]
    });

    /**
     * 查看
     * @param id
     */
      function view(id){
          $.post( baseUrl+"/hongbao/detail", {hbPoolId:id}, function(data){
              loadData('#hbpoolDetailModal', data.hbPoolVO)
              $('#hbpoolDetailModal input').attr('readOnly','readOnly');
              $('#hbpoolDetailModal .addBtn,#hbpoolDetailModal .sure,#hbpoolDetailModal .cancel').hide();
              $('#hbpoolDetailModal').modal('show');
          }, "json")

      }

    /**
     * 编辑
     * @param id
     */
     function edit(id){
        $.post( baseUrl+"/hongbao/detail", {hbPoolId:id}, function(data){
            loadData('#hbpoolDetailModal', data.hbPoolVO)
            $('#hbpoolDetailModal input').removeAttr('readOnly');
            $('#hbpoolDetailModal .addBtn,#hbpoolDetailModal .sure,#hbpoolDetailModal .cancel').show();
            $('#hbpoolDetailModal').modal('show');
        }, "json")

     }


    /**
     * 加载数据
     * @param topSelector
     * @param obj
     */
    function loadData(topSelector, obj){
        $('#hbpoolDetailModal input[name="limitObtainTime"]').prop("checked",false);
        $('#hbpoolDetailModal input[name="limitPostTime"]').prop("checked",false);
        //遍历表达式,给金额数量赋值，规则金额例:  2/3  不规则金额例:1-3/3,4-6/2
        //operator,=/ 等于金额/数量 : R[]/ 随机金额数量 : = 等于  : [] 区间
        $.each(obj.rule.ruleExpressions,function(i,value){
            var   expectation_value=value.expectation_value;
            if(value.operator=="=/"){
              var array=  expectation_value.split("/");
                $("#hbpoolDetailModal .ammount").val(array[0]/100);
                $("#hbpoolDetailModal .number").val(array[1]);
            }else  if(value.fact_attribute=="withWordLimit"){
                $("#hbpoolDetailModal input[name='withWordLimit']").val(expectation_value);
            }else  if(value.fact_attribute=="limitPostTime"){
                $('#hbpoolDetailModal input[name="limitPostTime"]').prop("checked","checked");
            }else if(value.fact_attribute=="platform"){
                var   array=expectation_value.split(",");
                $.each(array,function(i,value){
                    $("#hbpoolDetailModal input[name='platform'][value='"+value+"']").prop("checked","checked");
                });
            }else  if(value.operator=="[]"){
                var   array4=expectation_value.split(",");
               $("#hbpoolDetailModal :input[name='startDate']").val(array4[0]);
                $("#hbpoolDetailModal :input[name='endDate']").val(array4[1]);
            }else if(value.fact_attribute=="limitObtainTime"){
                $('#hbpoolDetailModal input[name="limitObtainTime"]').prop("checked","checked");
            }else  if(value.operator=="R[]/"){
                var array1=  expectation_value.split(",");
                $.each(array1,function (j,value1){
                    var array2=  value1.split("/");
                    if(!isNullOrEmpty(array2[0])&&!isNullOrEmpty(array2[1])){
                        var  array3=(array2[0]).split("-");
                        $("#hbpoolDetailModal .ammStart").eq(j).val(array3[0]/100);
                        $("#hbpoolDetailModal .ammEnd").eq(j).val(array3[1]/100);
                        $("#hbpoolDetailModal .irNumber").eq(j).val(array2[1]/100);
                        if(j>=$("#hbpoolDetailModal .ammStart").length){
                          $("#mdiv1").append('<div class="form-group"> <div class="col-sm-16" style="width: 760px"> <input type="text" id="form-field-1" class="ammStart"  value="'+array3[0]/100+'" style="width: 90px"/> <label > --- </label> <input type="text" id="form-field-1" class="ammEnd" value="'+array3[1]+'" style="width: 90px"/> <label > 元随机红包,共 </label> <input type="text" id="form-field-1" class="irNumber" style="width: 90px"  value="'+array2[1]/100+'" onblur="count()"/> <label > 个 </label> </div> </div>');
                        }
            }
                });
            }
        });
        var key,value,tagName,type,arr;
        for(x in obj){
            key = x;
            value = obj[x];
            $(topSelector + " [name='"+key+"']," + topSelector + " [name='"+key+"[]']").each(function(){
                if("startDate"==key||"endDate"==key){
                return;
                }
                tagName = $(this)[0].tagName;
                type = $(this).attr('type');
                if(tagName=='INPUT'){
                    if(type=='radio'){
                      if($(this).val()==value){
                          $(this).prop('checked',"checked");
                          changeModal();
                        }else{
                      $(this).removeAttr('checked');
                  }
                    }else if(type=='checkbox'){
                        arr = value.split(',');
                        for(var i =0;i<arr.length;i++){
                            if($(this).val()==arr[i]){
                                $(this).prop('checked',"checked");
                                break;
                            }
                        }
                    }else{
                        $(this).val(value);
                    }
                }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                    $(this).val(value);
                }
            });
        }
    }

    /**
     * 查看领用明细
     */
      function viewObtain(id){
        freshUrl(baseUrl+"hbObtain/list?hbPoolId="+id);
      }


    $('table th input:checkbox').on('click' , function(){
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox')
            .each(function(){
                this.checked = that.checked;
                $(this).closest('tr').toggleClass('selected');
            });
    });

    //查询
    $("#searchForm").submit(function () {
        $('#imgTable').DataTable().draw()
        return false; //阻止表单默认提交
    });

     //红包类别radio按钮的切换
    function change(){
        if(  $('#hbpoolModal  input[name="type"]:checked').val()=="gjc"||$('#hbpoolModal  input[name="type"]:checked').val()=="gw"){
            $("#show,#hpfDiv").hide();
            $("#limitObtainTime").show();
        }
        else if($('#hbpoolModal  input[name="type"]:checked').val()=="hpf"){
            $("#show,#limitObtainTime").hide();
            $("#hpfDiv").show();
        }else{
            $("#show,#limitObtainTime").show();
            $("#hpfDiv").hide();
        }
        $('#hbpoolModal #mdiv input[type="text"],#hbpoolModal #rdiv input[type="text"],#hbpoolModal  input[name="number"]').val('');
    }

    //红包规则按钮的切换
    function configChange(){
      if( $('#hbpoolModal  input[name="config"]:checked').val()=="regular"){
          $('#hbpoolModal #mdiv input[type="text"]').val('');
      }else
          $('#hbpoolModal #rdiv input[type="text"]').val('');
         $('#hbpoolModal  input[name="number"] ').val('');
    }


 function change1(){
     changeModal();
     $('#hbpoolDetailModal #mdiv1 input[type="text"],#hbpoolDetailModal #rdiv1 input[type="text"],#hbpoolDetailModal  input[name="number"]').val('');
 }

 function  changeModal(){
     if(  $('#hbpoolDetailModal  input[name="type"]:checked').val()=="gjc"||$('#hbpoolDetailModal  input[name="type"]:checked').val()=="gw"){
         $("#show1,#hpfDiv1").hide();
         $("#limitObtainTime1").show();
     }
     else if($('#hbpoolDetailModal  input[name="type"]:checked').val()=="hpf"){
         $("#show1,#limitObtainTime1").hide();
         $("#hpfDiv1").show();
     }else{
         $("#show1,#limitObtainTime1").show()
         $("#hpfDiv1").hide();
     }
 }


$("#delHbpool").click(function (event) {
        event.preventDefault()
        if (confirm("确认删除吗")) {
            var checks = $(".table td :input[type='checkbox']:checked");
            if (checks.length == 0) {
                alert("没有选中的红包池,请选择红包池！");
                return;
            }
            var ids = [];
            $.each(checks, function (index, check) {
                ids.push($(check).val());
            })
            $.ajax({
                type: "post", // 请求方式
                url: baseUrl + "/hbPool/delete", //url地址
                data: JSON.stringify(ids), //数据
                contentType: "application/json",
                dataType: "json",
                success: function (response, ifo) {
                    $('#imgTable').DataTable().draw();
                    alert("删除成功");
                }, error: function () {
                    alert("error");
                }
            })
        }
    });

 //添加不规则记录
function addText(){
    $("#mdiv").append('<div class="form-group"> <div class="col-sm-16" style="width: 760px"> <input type="text" id="form-field-1" class="ammStart"  style="width: 90px"/> <label > --- </label> <input type="text" id="form-field-1" class="ammEnd"  style="width: 90px"/> <label > 元随机红包,共 </label> <input type="text" id="form-field-1" class="irNumber" style="width: 90px"   onblur="count(\'hbpoolModal\')" /> <label > 个 </label> </div> </div>');
}

function addText1(){
    $("#mdiv1").append('<div class="form-group"> <div class="col-sm-16" style="width: 760px"> <input type="text" id="form-field-1" class="ammStart"  style="width: 90px"/> <label > --- </label> <input type="text" id="form-field-1" class="ammEnd"  style="width: 90px"/> <label > 元随机红包,共 </label> <input type="text" id="form-field-1" class="irNumber" style="width: 90px"  onblur="count(\'hbpoolDetailModal\')"/> <label > 个 </label> </div> </div>');
}
       //计算红包总数量
       function   count(modlName){
           var count=0;
           console.dir( $("#"+modlName+"  .number"));
           if( $( "#"+modlName +" input[name='config']:checked").val()=="regular"){//当选取为规则配置的时候
               $.each($("#"+modlName+"  .number"), function(i,val){
                   count=count+val.value*1;
               });
           }else{//当选取为非规则配置的时候
               $.each($("#"+modlName+"  .irNumber"), function(i,val){
                   count=count+val.value*1;
               });
           }
           $("#"+modlName +" input[name='number']").val(count);
       }

  //表单验证
    var MyValidator = function() {
           var handleSubmit = function() {
               $.each($('.form-horizontal'),function(i,value){
                   $('.form-horizontal').eq(i).validate({
                       errorElement : 'span',
                       errorClass : 'help-block',
                       focusInvalid : false,
                       rules : {
                           name : {
                               required : true
                           }
                       },
                       messages : {
                           name : {
                               required :"红包池名称必填!"
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
                           if("addForm"==form.id){
                               addForm()//增加红包池
                           }else
                               updateForm();//更新红包池
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
               });
           }
           return {
               init : function() {
                   handleSubmit();
               }
           };
       }();
       MyValidator.init();





