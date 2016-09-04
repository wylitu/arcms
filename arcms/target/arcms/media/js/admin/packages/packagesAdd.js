var signCount = 1;
$(document).ready(
        function () {
            $('.delIcon').on("click",function(){
                    $(this).parent().parent().remove();
                });
        }
    );

function addSignInCofig(){

    signCount++;

    if($("#indexpage").css("display")=="block"){
         $("#package1").append('<div class="col-xs-16 col-sm-12" id="input_group_'+signCount+'" style="margin-top: 10px"><div class="col-sm-3"></div><div class="integralSignIn1 input-group">&nbsp;套餐名称：&nbsp;&nbsp;<input type="text" class="packageName" id="packageName_'+signCount+'"/>&nbsp;&nbsp;套餐金额：&nbsp;&nbsp;<input  type="text" class="packageMoney" id="packageMoney_'+signCount+'"/>&nbsp;&nbsp;套餐年限：&nbsp;&nbsp;<input  class="packageYear" type="text" id="packageYear_'+signCount+'"/>&nbsp;&nbsp;<span class="delIcon" id="del_'+signCount+'"></span></div> </div>');
    }

    if($("#point").css("display")=="block"){
        $("#package2").append('<div class="col-xs-16 col-sm-12" id="input_group_'+signCount+'" style="margin-top: 10px"><div class="col-sm-3"></div><div class="integralSignIn2 input-group">&nbsp;套餐名称：&nbsp;&nbsp;<input type="text" class="packagesName" id="packagesName_'+signCount+'"/>&nbsp;&nbsp;充值金额：&nbsp;&nbsp;<input  type="text" class="rechargeMoney" id="rechargeMoney_'+signCount+'"/>&nbsp;&nbsp;爱卡币数量：&nbsp;&nbsp;<input  class="icardMoney" type="text" id="icardMoney_'+signCount+'"/>&nbsp;&nbsp;<span class="delIcon" id="del_'+signCount+'"></span></div> </div>');
    }

    $('.delIcon').on("click",function(){
        $(this).parent().parent().remove();
    });
}

function saveOrUpdateFrom(){
    //var data = $('#signInConifgForm').serialize();

    var ids = [];

    var errCode = 0;
    if($("#indexpage").css("display")=="block"){

       $(".integralSignIn1").each(function(index){

               var packageName = $(this).find(".packageName").val();
               var packageMoney = $(this).find(".packageMoney").val();
               var packageYear = $(this).find(".packageYear").val();

               if ((packageName == null || packageName =='') || (packageMoney == null || packageMoney == '') || (packageYear == null || packageYear =='')){
                   alert("信息不能为空。");
                   errCode = -1;
                   return;
               }

               if (packageName !=null && packageMoney!=null && packageYear!=null){
                   var package = {"packageName":packageName,"packageMoney":packageMoney,"packageYear":packageYear};

                   ids.push(package);

               }

           });


           if (errCode != -1){
               $.ajax({
                   type : "post",
                   url : baseUrl+"packages/savepay",
                   //data : {signInConfig:ids.toString()},
                   data : JSON.stringify(ids),
                   async:false,
                   dataType : "json",
                   contentType:"application/json",
                   success : function(data){
                       if (data.errorCode == '0'){
                           alert("保存成功！");
                       }else{
                           alert("保存失败！");
                           return false;
                       }
                   }
               });
           }
    }

    if($("#point").css("display")=="block"){

        $(".integralSignIn2").each(function(index){

                var packagesName = $(this).find(".packagesName").val();
                var rechargeMoney = $(this).find(".rechargeMoney").val();
                var icardMoney = $(this).find(".icardMoney").val();

                if ((packagesName == null || packagesName =='') || (rechargeMoney == null || rechargeMoney == '') || (icardMoney == null || icardMoney =='')){
                    alert("信息不能为空。");
                    errCode = -1;
                    return;
                }

                if (packagesName !=null && rechargeMoney!=null && icardMoney!=null){
                    var package = {"packagesName":packagesName,"rechargeMoney":rechargeMoney,"icardMoney":icardMoney};

                    ids.push(package);

                }

            });


            if (errCode != -1){
                $.ajax({
                    type : "post",
                    url : baseUrl+"packages/saverecharge",
                    //data : {signInConfig:ids.toString()},
                    data : JSON.stringify(ids),
                    async:false,
                    dataType : "json",
                    contentType:"application/json",
                    success : function(data){
                        if (data.errorCode == '0'){
                            alert("保存成功！");
                        }else{
                            alert("保存失败！");
                            return false;
                        }
                    }
                });
            }

    }

}







